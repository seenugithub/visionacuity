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
    }	 
    	
}
