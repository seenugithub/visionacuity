package com.marvel.visionacuity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyUtil {
	
	final static Logger logger = Logger.getLogger(PropertyUtil.class);
	
	private static PropertyUtil instance = null;
	Properties properties = null;
	String bookmarkFilePath=null;
	
	private PropertyUtil(){	}
	
	public static PropertyUtil getInstance(){
		if(instance==null){
			instance=new PropertyUtil();
		}
		return instance;
	}
	
	public Properties getPropertiesFromFile(String FILE_PATH) {
		 // Properties object we are going to fill up.
		   try {
			   properties = new Properties();
			// If file exists as an absolute path, load as input stream.
			   final Path path = Paths.get(FILE_PATH);
			   if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
			      properties.load(new FileInputStream(FILE_PATH));
			   } else {
			      // Otherwise, use resource as stream.
			      properties.load(getClass().getClassLoader().getResourceAsStream(
			    		  FILE_PATH));
			   }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Error occured! ",e);
		}
		   return properties;
	}
	
	public Properties getBookmarkPropertiesFromFile(String FILE_PATH){
		bookmarkFilePath=FILE_PATH;
		return getPropertiesFromFile(bookmarkFilePath);
	}
	
	public void storeBookmarkPropertiesIntoFile(Properties prop){
		// Write properties file.
		  try {
		      prop.store(new FileOutputStream(bookmarkFilePath), null);
		  } catch (IOException e) {
			  logger.error("Error occured! ",e);
		  }
	}
}
