package com.marvel.visionacuity;

import com.marvel.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import org.apache.log4j.Logger;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

public class Application {

	final static Logger logger = Logger.getLogger(Application.class);
	
	public String comport=null;
	public String baudrate=null;
	public int ir_readbyte_count=0;
	static SerialPort serialPort;
	
	ImageDisplay imgDispObj=null;
	KeyHandler khandler=null;
	KeyProcessor kprocessor=null;
	
	/**-------------------------------------------------------------------------------------------------------------*/
	
	public static void main(String[] args) {
		
		if(args.length==0 || args.length==1){
			logger.debug("Two property files are required. Those are win_config.properies/linux_config.properties , bookmark.properties");
			System.exit(1);
		}else{
			new Application().IntializeApp(args[0],args[1]);
		}
	}
	
	/**-------------------------------------------------------------------------------------------------------------*/
	
	public void IntializeApp(String propertyfile,String bookmarkpropertyfile){
		logger.debug("-----------------------------------------------------------------");
		logger.debug("	  VISION ACUITY APPLICATION                                        ");
		logger.debug("-----------------------------------------------------------------");
		logger.debug("Initializing VisionAcuityApplication...");
	 try {
		 	   logger.debug("Loading properties...");
		       Properties props=PropertyUtil.getInstance().getPropertiesFromFile(propertyfile);
		       Properties bookmarkprops=PropertyUtil.getInstance().getBookmarkPropertiesFromFile(bookmarkpropertyfile);
		        
		        //below 3 lines for remote key receiver
		        comport=props.getProperty("comport");
		        baudrate=props.getProperty("baudrate");      
		        ir_readbyte_count=Integer.parseInt(props.getProperty("ir_readbyte_count")); 
		        
		        logger.debug("Loading Remote keys configuration from property file...");
		        RemoteKeypad.loadKeys(props);
		        
		        logger.debug("Initializing KeyHandler...");
		       
		        kprocessor = new KeyProcessor();
		        khandler = new KeyHandler(props,bookmarkprops,kprocessor);
		        
		        logger.debug("Displaying welcome image...");       
		        khandler.welcomeHandler();
		       
		        //Caching only first grid menu images 
			    ImageCaching.buildMenuCache(props);
			       
		        khandler.menuHandler(); // Displaying first grid image
		      
		        // Enable below code to test this project without remote
		        //Testing.kickOffTesting(khandler);
		      
		        // Remote key receiver listener
		        logger.debug("Initializing SerialConnector...");
		        SerialConnectorRead();
		        
		} catch (Exception e) {
	   		logger.error("Error occured! ",e);
	   	}
	}
	
	/**-------------------------------------------------------------------------------------------------------------*/
	
	public void SerialConnectorRead(){
		 logger.debug("Connecting serial port....");
		  serialPort = new SerialPort(comport);
	      try {
	        	int baudRate = Integer.parseInt(baudrate);
	        	
	   		     logger.debug("Selected Com Port :"+comport);
	   		     logger.debug("Selected Baud rate :"+baudrate);
	   		     logger.debug("Selected ir_readbyte_count :"+ir_readbyte_count);
	        	
		         serialPort.openPort();//Open serial port
		         serialPort.setParams(baudRate, 8, 1, 0);//Set params.
		         int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
		         serialPort.setEventsMask(mask);//Set mask
		         serialPort.addEventListener(new SerialPortReader());//Add SerialPortEventListener
		     
	        }
	        catch (SerialPortException ex) {
	            logger.debug("Exception Occured ",ex);
	        }
	}
	
	/**-----------------------------------------------------------------------------------------------------------------*/
	
	/*
     * In this class must implement the method serialEvent, through it we learn about 
     * events that happened to our port. But we will not report on all events but only 
     * those that we put in the mask. In this case the arrival of the data and change the 
     * status lines CTS and DSR
     */
    class SerialPortReader implements SerialPortEventListener {

     /*  public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR()){//If data is available

                if(event.getEventValue() == ir_readbyte_count){//Check bytes count in the input buffer
                    //Read data, if ir_readbyte_count bytes available 
                    try {
                    	byte buffer[]=serialPort.readBytes(ir_readbyte_count);
                    	String readed = new String(buffer);
                    	logger.debug("");
                    	logger.debug("");
                    	logger.debug("Received ir code @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime())); 
                    	logger.debug("IR Code from Receiver(v1) :"+readed);
                    	readed = CommonUtility.removeWhiteSpace(readed);
                    	logger.debug("Modified received code :"+readed);
						khandler.handle(readed);
						logger.debug("");
						logger.debug("");
                    }
                    catch (SerialPortException ex) {
                        logger.debug(ex);
                    }
                }
            }
            else if(event.isCTS()){//If CTS line has changed state
                if(event.getEventValue() == 1){//If line is ON
                    logger.debug("CTS - ON");
                }
                else {
                    logger.debug("CTS - OFF");
                }
            }
            else if(event.isDSR()){///If DSR line has changed state
                if(event.getEventValue() == 1){//If line is ON
                    logger.debug("DSR - ON");
                }
                else {
                    logger.debug("DSR - OFF");
                }
            }
        }
    	 */
    	
    	StringBuilder message = new StringBuilder();
    	 int lineCount=0;
    	 public void serialEvent(SerialPortEvent event) {
    	     if(event.isRXCHAR() && event.getEventValue() > 0){
    	         try {
    	             byte buffer[] = serialPort.readBytes();
    	             for (byte b: buffer) {
    	                     //if ( (b == '\r' || b == '\n') && message.length() > 0) {
    	            	 	 if(b=='\n'){
    	            	 		lineCount=lineCount+1;
    	            	 	 }
    	            	 	 
    	                     if ( (b == '\n' && lineCount>=2) && message.length() > 0) {
    	                         String toProcess = message.toString();
    	                         logger.debug("IR Code from Receiver(v2) :"+toProcess);
	    	                     String readed = CommonUtility.removeWhiteSpace(toProcess);
	    	                     logger.debug("Modified received code :"+readed);
	    	 					 khandler.handle(readed);
    	                         message.setLength(0);
    	                         lineCount=0;
    	                     }
    	                     else {
    	                         message.append((char)b);
    	                     }
    	             }                
    	         }
    	         catch (SerialPortException ex) {
    	             System.out.println(ex);
    	             System.out.println("serialEvent");
    	         }
    	     }
    	 }
    	 
    	
    	 /*StringBuilder message = new StringBuilder();
    	 boolean readFirstLine=true;
    	 public void serialEvent(SerialPortEvent event) {
    	     if(event.isRXCHAR() && event.getEventValue() > 0){
    	         try {
    	             byte buffer[] = serialPort.readBytes();
    	             for (byte b: buffer) {
    	            	 	
    	                     if ( (b == '\n' && readFirstLine==true) && message.length() > 0) {
    	                         String toProcess = message.toString();
    	                         logger.debug("IR Code from Receiver(v2.1) :"+toProcess);
	    	                     String readed = CommonUtility.removeWhiteSpace(toProcess);
	    	                     logger.debug("Modified received code :"+readed);
	    	 					 khandler.handle(readed);
    	                         message.setLength(0);
    	                         readFirstLine=false;
    	                     }else if(b == '\n' && readFirstLine==false){
    	                    	 logger.debug("skipped second line and setlenth 0");
    	                    	 readFirstLine=true;
    	                    	 message.setLength(0);
    	                     }
    	                     else {
    	                         message.append((char)b);
    	                     }
    	             }                
    	         }
    	         catch (SerialPortException ex) {
    	             System.out.println(ex);
    	             System.out.println("serialEvent");
    	         }
    	     }
    	 }*/
    	 
    /*	@Override
	    public void serialEvent(SerialPortEvent event) {
	        if(event.isRXCHAR() && event.getEventValue() > 0) {
	            try {
	                String receivedData = serialPort.readString(event.getEventValue());
	                logger.debug("IR Code from Receiver(v3) :"+receivedData);
                    String readed = CommonUtility.removeWhiteSpace(receivedData);
                    logger.debug("Modified received code :"+readed);
 					khandler.handle(readed);
	            }
	            catch (SerialPortException ex) {
	                System.out.println("Error in receiving string from COM-port: " + ex);
	            }
	        }
	    }
		*/
    }
     
    
    
    
     //-----------------------------------------------------------------------------------------------------------
     /*
     class SerialPortReader implements SerialPortEventListener
     {
         private int m_nReceptionPosition = 0;
         private boolean m_bReceptionActive = false;
         private byte[] m_aReceptionBuffer = new byte[2048];

         @Override
         public void serialEvent(SerialPortEvent p_oEvent)
         {
             byte[] aReceiveBuffer = new byte[2048];

             int nLength = 0;
             int nByte = 0;

             switch(p_oEvent.getEventType())
             {
                 case SerialPortEvent.RXCHAR:
                     try
                     {
                         aReceiveBuffer = serialPort.readBytes();

                         for(nByte = 0;nByte < aReceiveBuffer.length;nByte++)
                         {
                             //System.out.print(String.format("%02X ",aReceiveBuffer[nByte]));

                             m_aReceptionBuffer[m_nReceptionPosition] = aReceiveBuffer[nByte];

                             // Buffer overflow protection
                             if(m_nReceptionPosition >= 2047)
                             {

                                 // Reset for next packet
                                 m_bReceptionActive = false;
                                 m_nReceptionPosition = 0;
                             }
                             else if(m_bReceptionActive)
                             {
                                 m_nReceptionPosition++;

                                 // Receive at least the start of the packet including the length
                                 if(m_nReceptionPosition >= 14)
                                 {
                                     nLength = (short)((short)m_aReceptionBuffer[10] & 0x000000FF);
                                     nLength |= ((short)m_aReceptionBuffer[11] << 8) & 0x0000FF00;
                                     nLength |= ((short)m_aReceptionBuffer[12] << 16) & 0x00FF0000;
                                     nLength |= ((short)m_aReceptionBuffer[13] << 24) & 0xFF000000;

                                     //nLength += ..; // Depending if the length in the packet include ALL bytes from the packet or only the content part

                                     if(m_nReceptionPosition >= nLength)
                                     {
                                         // You received at least all the content

                                         // Reset for next packet
                                         m_bReceptionActive = false;
                                         m_nReceptionPosition = 0;
                                     }
                                 }

                             }
                             // Start receiving only if this is a Start Of Header
                             else if(m_aReceptionBuffer[0] == '\0')
                             {
                                 m_bReceptionActive = true;
                                 m_nReceptionPosition = 1;
                             }
                         }
                     }
                     catch(Exception e)
                     {
                         e.printStackTrace();
                     }
                     break;
                 default:
                     break;
             }
     		
     		String readed = new String(m_aReceptionBuffer);
     		readed = CommonUtility.removeWhiteSpace(readed);
     		logger.debug("Modified received code(v4) :"+readed);
     		khandler.handle(readed);
     		logger.debug("");
     		logger.debug("");
         }
     }*/
}
