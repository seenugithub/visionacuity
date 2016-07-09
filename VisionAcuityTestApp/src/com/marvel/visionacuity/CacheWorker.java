package com.marvel.visionacuity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

public class CacheWorker implements Runnable{

	final static Logger logger = Logger.getLogger(CacheWorker.class);
	String cacheDirectory = null;
	String imagePrefix = null;
	int imageNo = 0;
	
	public String getCacheDirectory() {
		return cacheDirectory;
	}

    public void setCacheDirectory(String cacheDirectory) {
		this.cacheDirectory = cacheDirectory;
	}
    
	public String getImagePrefix() {
		return imagePrefix;
	}

	public void setImagePrefix(String imagePrefix) {
		this.imagePrefix = imagePrefix;
	}

	public int getImageNo() {
		return imageNo;
	}

	public void setImageNo(int imageNo) {
		this.imageNo = imageNo;
	}

	@Override
	public void run() {
		//logger.debug("Caching thread started @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime()));
		ImageCaching.refreshCache(cacheDirectory,imagePrefix,imageNo);
		//logger.debug("Caching thread finished @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime()));
	}

}
