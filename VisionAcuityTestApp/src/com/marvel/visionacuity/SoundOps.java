package com.marvel.visionacuity;

import org.apache.log4j.Logger;


public class SoundOps implements Runnable {

	final static Logger logger = Logger.getLogger(SoundOps.class);
	
    private boolean muteOperation;
	GPIO_Pin soundPin;
	
	public SoundOps(GPIO_Pin soundPin) {
		this.soundPin=soundPin;
    }

    public boolean isMuteOperation() {
		return muteOperation;
	}

	public void setMuteOperation(boolean muteOperation) {
		this.muteOperation = muteOperation;
	}

    public void run() {
       try{
			if(muteOperation==false)
			{
				/*logger.debug("getPin ==>"+soundPin.getPin());
				logger.debug("getPinMode ==>"+soundPin.getPinMode());
				logger.debug("getPinStatus ==>"+soundPin.getPinStatus());
				logger.debug("getModeURI ==>"+soundPin.getModeURI());
				logger.debug("getStatusURI ==>"+soundPin.getStatusURI());*/
				logger.debug("Beep is on.");
				soundPin.setHIGH();
				Thread.sleep(20);
				soundPin.setLOW();
			}else{
				logger.debug("Sound is muted");
			}
		}catch(Exception e){
			logger.debug("Error while enabling / disabling the sound");
		   logger.error("Error occured! ",e);
	    }
    }
}
