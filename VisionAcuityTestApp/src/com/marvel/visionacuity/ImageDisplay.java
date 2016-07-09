package com.marvel.visionacuity;

import java.awt.Cursor;
import java.awt.Graphics; 
import java.awt.GraphicsEnvironment; 
import java.awt.Image; 
import java.awt.Point;
import java.awt.Toolkit; 
import java.awt.event.MouseEvent; 
import java.awt.event.MouseListener; 
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException; 
import java.net.URL; 
 




import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame; 

import org.apache.log4j.Logger;
 
public class ImageDisplay extends JFrame { 
  
	final static Logger logger = Logger.getLogger(ImageDisplay.class);
	
    private static final long serialVersionUID = 1L;
    Image screenImage; // downloaded image  
    int w, h; // Display height and width 
 
    public void loadImage(String imagefile)
    {
    	//logger.debug("getting image  @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime())); 
    	//logger.debug("image file =>"+(imagefile+".jpg").replace(File.separator+File.separator, File.separator));
    	//screenImage=Toolkit.getDefaultToolkit().getImage(imagefile+".jpg");
    	screenImage=ImageCaching.getImageFromCache((imagefile+".jpg").replace(File.separator+File.separator, File.separator));
    	//logger.debug("Repainting started @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime())); 
    	repaint();
    	//logger.debug("Repained ended @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime())); 
    }
 
    
   public  ImageDisplay(String source) throws Exception
     { 
 
        // Exiting program on window close 
        addWindowListener(new WindowAdapter() { 
            public void windowClosing(WindowEvent e) { 
                System.exit(0);
            } 
        });
 
        // Exitig program on mouse click 
        addMouseListener(new MouseListener() { 
            public void mouseClicked(MouseEvent e) { System.exit(0); } 
            public void mousePressed(MouseEvent e) {} 
            public void mouseReleased(MouseEvent e) {} 
            public void mouseEntered(MouseEvent e) {} 
            public void mouseExited(MouseEvent e) {} 
        } 
        );
 
        
        this.setUndecorated(true); // remove window frame  
        this.setSize(1280,720); // window should be visible 
        this.setVisible(true);
       
        // switching to fullscreen mode 
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
 
        // getting display resolution: width and height 
        w = this.getWidth();
        h = this.getHeight();
        logger.debug("Display resolution: " + String.valueOf(w) + "x" + String.valueOf(h));
 
        // loading image  
        screenImage = Toolkit.getDefaultToolkit().getImage(source); // otherwise - file 
        
        //Hide Cursor
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Point hotSpot = new Point(0,0);
        BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT); 
        Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, hotSpot, "InvisibleCursor");        
        setCursor(invisibleCursor);
    } 
 
    public void paint (Graphics g) { 
        if (screenImage != null)
            g.drawImage(screenImage, w/2 - screenImage.getWidth(this)/2,h/2-screenImage.getHeight(this)/2,this);
    } 
    
    public void repaint (Graphics g) { 
        if (screenImage != null) {
        	logger.debug("Draw method started @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime())); 
        	g.drawImage(screenImage, w/2 - screenImage.getWidth(this)/2,h/2-screenImage.getHeight(this)/2,this);
            logger.debug("Draw method ended @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime())); 
        }
    } 
 
 
} 