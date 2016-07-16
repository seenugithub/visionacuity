package com.marvel.visionacuity;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Testing {

	final static Logger logger = Logger.getLogger(Testing.class);
	
	public static ArrayList<String> getKeys(){
		ArrayList<String> arr=new ArrayList<String>();
		arr.add(RemoteKeypad.KEY_MUTE);
		arr.add(RemoteKeypad.KEY_NEXT);
		arr.add(RemoteKeypad.KEY_NUMBER_CHART);
		arr.add(RemoteKeypad.KEY_UP);
		arr.add(RemoteKeypad.KEY_UP);
		arr.add(RemoteKeypad.KEY_NEXT);
		
		//arr.add(RemoteKeypad.KEY_BACK);

		//arr.add(RemoteKeypad.KEY_BACK);
		//arr.add(RemoteKeypad.KEY_BACK);
		//arr.add(RemoteKeypad.KEY_NEXT);
		//arr.add(RemoteKeypad.KEY_OKAY);
		
		

		//arr.add(RemoteKeypad.KEY_DOWN);
		//arr.add(RemoteKeypad.KEY_DOWN);
		//arr.add(RemoteKeypad.KEY_PREV);
        return arr;
	}
	
	public static void kickOffTesting(KeyHandler khandler){
		 try {
			for(String str : Testing.getKeys()){
			    	Thread.sleep(200);
			    	khandler.handle(str);
			    }
		} catch (Exception e) {
			logger.debug("Error occured in WindowsUnitTest - kickOffTesting",e);
		}
		
	}
}
