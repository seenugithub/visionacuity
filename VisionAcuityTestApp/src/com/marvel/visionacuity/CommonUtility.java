package com.marvel.visionacuity;

import java.util.Properties;

public class CommonUtility {

	public static String removeWhiteSpace(String str){
		if(str!=null && !str.isEmpty())
			return str.replaceAll("\\s+","");
		else
			return str;
	}
	
	public static int getSelectedDistanceImgNo(Properties props,String distance){
		if(distance!=null && distance.length()>0)
			return Integer.parseInt(props.getProperty(distance));
		else
			return 0;
	}
	
	public static int getSelectedLanguageImgNo(Properties props,String preflang){
		if(preflang!=null && preflang.length()>0)
			return Integer.parseInt(props.getProperty(preflang));
		else
			return 0;
	}
}
