package com.marvel.visionacuity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class ImageCaching implements Runnable{

	final static Logger logger = Logger.getLogger(ImageCaching.class);
	
	//static volatile Map<String, BufferedImage> cachedImageMap = null;   // this must be volatile
	static volatile Map<String, BufferedImage> cachedMenuImageMap = null; 
	public static Map<String, BufferedImage> cachedImageMap = Collections.synchronizedMap(new LRUCache<String, BufferedImage>(20));
	static String currCachedDirPath = null;
	static String currMenuCachedDirPath = null;
	static Object lockbox = new Object();  

	public static void refreshCache(String newimgdir,String imagePrefix, int imageNo){
	
		//if its not main menu directly, start previous and next images , because already we cached main menu images in onload
		if(!currMenuCachedDirPath.equalsIgnoreCase(newimgdir)){
			logger.debug("start recaching additional images ( "+newimgdir+" )");
			buildNewCacheUsingLRU(newimgdir,imagePrefix,imageNo);
			currCachedDirPath=newimgdir;
			logger.debug("Directory caching done  @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime()));
		}
		else{
			logger.debug("Main Menu Directory already cached ( "+newimgdir+" )");
		}
	}
	
	// Caching only first grid images in program initial load
	public static void buildMenuCache(Properties props) {
		logger.debug("Caching Menus started  @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime())); 
		currCachedDirPath=props.getProperty("menu_dir").replace("{parent_dir}", props.getProperty("parent_dir"));
		currMenuCachedDirPath=currCachedDirPath;
		buildMenuNewCache(new File(currMenuCachedDirPath));
		logger.debug("Caching Menus Done!!!   @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime())); 
	}
	
	public static void buildNewCache(String dir,String imagePrefix, int imageNo) {       // this is called by the producer     
	    Map<String, BufferedImage> newCachingImageMap = new HashMap<String, BufferedImage>();          // when the data needs to be updated

	    synchronized (lockbox) {                 // this must be synchronized because
	                                         // of the Java memory model
	    	
	    	int prevImageNo = (imageNo-2)<0?0:imageNo-2;
	    	int nextImageNo = imageNo+2;
	    	try {
				
	    			for(int img=prevImageNo;img<=nextImageNo;img++){
	    				StringBuffer sb = new StringBuffer("").append(dir).append(imagePrefix).append(img).append(".jpg");
	    				File file = new File(sb.toString());
	    				if(file.exists()){
	    					logger.debug("Caching "+sb.toString());
	    					newCachingImageMap.put(file.getCanonicalPath(),ImageIO.read(new File(file.getCanonicalPath())));
	    				}
	    			}
			} catch (Exception e) {
				logger.error("Error occured! ",e);
			}
	    }                 
	/* After the above synchronization block, everything that is in the HashMap is 
	   visible outside this thread */

	/* Now make the updated set of values available to the consumer threads.  
	   As long as this write operation can complete without being interrupted, 
	   and is guaranteed to be written to shared memory, and the consumer can 
	   live with the out of date information temporarily, this should work fine */

	    cachedImageMap = newCachingImageMap;

	}
	
	public static void buildMenuNewCache(File dir) {       // this is called by the producer     
	    Map<String, BufferedImage> newCachingImageMap = new HashMap<String, BufferedImage>();          // when the data needs to be updated

	    synchronized (lockbox) {                 // this must be synchronized because
	                                         // of the Java memory model
	    	try {
				File[] files = dir.listFiles();
				for (File file : files) {
					if (!file.isDirectory()) {
						BufferedImage bimg = ImageIO.read(new File(file.getCanonicalPath()));
						newCachingImageMap.put(file.getCanonicalPath(),bimg);
						logger.debug("Caching main menu "+file.getCanonicalPath());
					}
				}

			} catch (IOException e) {
				logger.error("Error occured! ",e);
			}
	    }                 
	/* After the above synchronization block, everything that is in the HashMap is 
	   visible outside this thread */

	/* Now make the updated set of values available to the consumer threads.  
	   As long as this write operation can complete without being interrupted, 
	   and is guaranteed to be written to shared memory, and the consumer can 
	   live with the out of date information temporarily, this should work fine */

	    cachedMenuImageMap = newCachingImageMap;

	}
	
	public static BufferedImage getImageFromCache(Object imagefile) {
		Map<String, BufferedImage> m = null;
	    m = cachedImageMap;               // no locking around this is required
	    
	    try {
	    	if(cachedMenuImageMap!=null && cachedMenuImageMap.containsKey(imagefile)){
	    		logger.debug("displaying main menu image from Cache ( "+imagefile+" )");
				return (BufferedImage)cachedMenuImageMap.get(imagefile);
	    	}if(m!=null && m.containsKey(imagefile)){
				logger.debug("displaying image from Cache ( "+imagefile+" )");
				return (BufferedImage)m.get(imagefile);
			}else{
				logger.debug("displaying image from Disk ( "+imagefile+" )");
				return (BufferedImage)ImageIO.read(new File((String) imagefile));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error occured! ",e);
		}
		return null;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
	public static void buildNewCacheUsingLRU(String dir,String imagePrefix, int imageNo) {       // this is called by the producer     
	
    	try {
			
    		   if(!currCachedDirPath.equalsIgnoreCase(dir) || cachedImageMap==null || cachedImageMap.isEmpty()){
    			 for(int img=imageNo;img<=imageNo+5;img++){
    				 StringBuffer sb = new StringBuffer("").append(dir).append(imagePrefix).append(img).append(".jpg");
     				loadImageThruExecutor(sb.toString());
    			 }
    		   }else{
    		   
    			   int prevImageNo = (imageNo-1)<0?0:imageNo-1;
    		       int nextImageNo = imageNo+1;
	    		   
    		       for(int img=prevImageNo;img<=nextImageNo;img++){
	    				StringBuffer sb = new StringBuffer("").append(dir).append(imagePrefix).append(img).append(".jpg");
	    				loadImageThruExecutor(sb.toString());
	    			}
    		   }
		} catch (Exception e) {
			logger.error("Error occured! ",e);
		}
    	
	}
	    
	public static void loadImageThruExecutor(String imgPath){
		ExecutorService executer = Executors.newSingleThreadExecutor();
		executer.execute(new ImageLoadingTask(cachedImageMap,imgPath));
		executer.shutdown();
	}
}


class ImageLoadingTask implements Runnable{
	final static Logger logger = Logger.getLogger(ImageLoadingTask.class);
	Map<String, BufferedImage> cache;
	String imgPath;
	ImageLoadingTask(Map<String, BufferedImage> cache,String imgPath){
		this.cache = cache;
		this.imgPath = imgPath.replace(File.separator+File.separator, File.separator);
	}
	public void run() {
		try {
			if(!cache.containsKey(imgPath)){
				//Load image from file
				File file = new File(imgPath);
				if(file.exists()){
					logger.debug("Caching done - "+imgPath);
					cache.put(file.getCanonicalPath(),ImageIO.read(new File(file.getCanonicalPath())));
				}else{
					logger.debug("Caching failed : ImageNotFound - "+imgPath);
				}
			
			}else{
				logger.debug("Already Cached - "+imgPath);
			}
		} catch (IOException e) {
			logger.debug("Error occured in ImageLoadingTask ",e);
		}
	}
}