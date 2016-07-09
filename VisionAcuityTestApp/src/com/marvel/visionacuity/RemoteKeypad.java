package com.marvel.visionacuity;

import java.util.Properties;

import org.apache.log4j.Logger;


public class RemoteKeypad {

	final static Logger logger = Logger.getLogger(RemoteKeypad.class);
	
	public static  String KEY_HOME=null;
	public static  String KEY_LANDOLTRING_CHART=null;
	public static  String KEY_E_CHART=null;
	public static String KEY_ENGLISH_ALPHA=null;
	public static  String KEY_NUMBER_CHART=null;
	public static  String KEY_PEDIATRIC_CHART=null;
	public static String KEY_LANGUAGE1=null;
	public static String KEY_LANGUAGE2=null;
	public static String KEY_LANGUAGE3=null;
	public static  String KEY_SNELIEN_CHART=null;
	public static  String KEY_LOGMAR_CHART=null;
	public static  String KEY_DOT=null;
	public static  String KEY_CONTRAST_CHART=null;
	public static  String KEY_ASTIG_CHART=null;
	public static  String KEY_ISHARA=null;
	public static  String KEY_EDU_CHART=null;
	public static  String KEY_VERTICAL=null;
	public static  String KEY_MADDOX_CHART=null;
	public static  String KEY_RG_CHART=null;
	public static  String KEY_CROSS_CYLINDER_CHART=null;	
	public static  String KEY_DISTANCE=null;
	public static  String KEY_SETTING=null;
	public static  String KEY_LANGUAGE=null;
	public static  String KEY_PRODUCT_INFO=null;
	public static String KEY_ADVERTISEMENT=null;
	
	public static  String KEY_MUTE=null;
	public static  String KEY_OKAY=null;
	public static  String KEY_NEXT=null;
	public static  String KEY_PREV=null;
	public static  String KEY_UP=null;
	public static  String KEY_DOWN=null;
	public static  String KEY_BACK=null;
	
	public static  String KEY_CARTOON_INFO=null;
	public static  String KEY_ANIMAL_INFO=null;
	public static  String KEY_LETTER=null;
	public static  String KEY_HORIZONTAL=null;
	public static  String KEY_WITHOUTDOT=null;
	public static  String KEY_WITHDOT=null;
	public static  String KEY_PERIPHERAL_CHART=null;
	public static  String KEY_SCREENSAVER=null;
	
	
	public static void loadKeys(Properties props){
		
		KEY_HOME = props.getProperty("KEY_HOME").trim();
		KEY_LANDOLTRING_CHART = props.getProperty("KEY_LANDOLTRING_CHART").trim();
		KEY_E_CHART = props.getProperty("KEY_E_CHART").trim();
		KEY_ENGLISH_ALPHA = props.getProperty("KEY_ENGLISH_ALPHA").trim();
		KEY_NUMBER_CHART = props.getProperty("KEY_NUMBER_CHART").trim();
		KEY_PEDIATRIC_CHART = props.getProperty("KEY_PEDIATRIC_CHART").trim();
		KEY_LANGUAGE1 = props.getProperty("KEY_LANGUAGE1").trim();
		KEY_LANGUAGE2 = props.getProperty("KEY_LANGUAGE2").trim();
		KEY_LANGUAGE3 = props.getProperty("KEY_LANGUAGE3").trim();
		KEY_SNELIEN_CHART = props.getProperty("KEY_SNELIEN_CHART").trim();
		KEY_LOGMAR_CHART = props.getProperty("KEY_LOGMAR_CHART").trim();
		KEY_DOT = props.getProperty("KEY_DOT").trim();
		KEY_CONTRAST_CHART = props.getProperty("KEY_CONTRAST_CHART").trim();
		KEY_ASTIG_CHART = props.getProperty("KEY_ASTIG_CHART").trim();
		KEY_ISHARA = props.getProperty("KEY_ISHARA").trim();
		KEY_EDU_CHART = props.getProperty("KEY_EDU_CHART").trim();
		KEY_VERTICAL = props.getProperty("KEY_VERTICAL").trim();
		KEY_MADDOX_CHART = props.getProperty("KEY_MADDOX_CHART").trim();
		KEY_RG_CHART = props.getProperty("KEY_RG_CHART").trim();
		KEY_CROSS_CYLINDER_CHART = props.getProperty("KEY_CROSS_CYLINDER_CHART").trim();
		KEY_DISTANCE =props.getProperty("KEY_DISTANCE").trim();
		KEY_SETTING =props.getProperty("KEY_SETTING").trim();
		KEY_LANGUAGE = props.getProperty("KEY_LANGUAGE").trim();
		KEY_PRODUCT_INFO = props.getProperty("KEY_PRODUCT_INFO").trim();
		KEY_ADVERTISEMENT = props.getProperty("KEY_ADVERTISEMENT").trim();
		
		KEY_MUTE = props.getProperty("KEY_MUTE").trim();
		KEY_OKAY = props.getProperty("KEY_OKAY").trim();
		KEY_NEXT = props.getProperty("KEY_NEXT").trim();
		KEY_PREV = props.getProperty("KEY_PREV").trim();
		KEY_UP = props.getProperty("KEY_UP").trim();
		KEY_DOWN = props.getProperty("KEY_DOWN").trim();
		KEY_BACK = props.getProperty("KEY_BACK").trim();
		
		KEY_CARTOON_INFO = props.getProperty("KEY_CARTOON_INFO").trim();
		KEY_ANIMAL_INFO = props.getProperty("KEY_ANIMAL_INFO").trim();
		KEY_LETTER = props.getProperty("KEY_LETTER").trim();
		KEY_HORIZONTAL = props.getProperty("KEY_HORIZONTAL").trim();
		KEY_WITHOUTDOT = props.getProperty("KEY_WITHOUTDOT").trim();
		KEY_WITHDOT = props.getProperty("KEY_WITHDOT").trim();
		KEY_PERIPHERAL_CHART = props.getProperty("KEY_PERIPHERAL_CHART").trim();
		KEY_SCREENSAVER = props.getProperty("KEY_SCREENSAVER").trim();
		
	}
	
}
