package com.marvel.visionacuity;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;


public class KeyHandler {

	final static Logger logger = Logger.getLogger(KeyHandler.class);
	
	Properties props=null;
	Properties bookmarkprops=null;
	ImageDisplay imgObj=null;
	KeyProcessor kprocessor=null;
	
	SoundOps soundobj = null;
	GPIO_Pin soundPin=null;
	boolean muteOperation = false;
	
	int curr_menu_no=1;
	int curr_edu_menu_no=0;
	int curr_pediatric_menu_no=0;
	int curr_setting_menu_no=0;
	boolean isEduSubMenuDisplay=false;
	boolean isPediatricSubMenuDisplay=false;
	boolean isSettingSubMenuDisplay=false;
	boolean isScreenSaverTurnedOn=false;
	String parent_dir=null;
	String curr_dir=null;
	String curr_img=null;
	int curr_img_no=0;
	String curr_lang=null;
	String curr_dist=null;
	String curr_file_prefix=null;
	int FILE_MAX_COUNT=0;
	int distanceAndlanguage=0;  //0--OFF, 1--ON		
	
	String prev_dir=null;
	String prev_img=null;
	int prev_img_no=0;
	String prev_lang=null;
	String prev_dist=null;
	String prev_file_prefix=null;
	
	int MENU_MAX_COUNT=0;
	int MENU_ROW_COUNT=0;
	int MENU_COLUMN_COUNT=0;
	int EDU_MENU_MAX_COUNT=0;
	int EDU_MENU_ROW_COUNT=0;
	int EDU_MENU_COLUMN_COUNT=0;
	
	
	public KeyHandler(Properties props,Properties bookmarkprops,KeyProcessor kprocessor)
	{
		this.props=props;
		this.bookmarkprops=bookmarkprops;
		this.kprocessor=kprocessor;
		this.parent_dir=props.getProperty("parent_dir");
		this.curr_dir=parent_dir;
	}
	
	//--------------------------------------------------------------------------------------------------
	public void welcomeHandler(){
		try{
			curr_dist = bookmarkprops.getProperty("pref_distance"); 
			logger.debug("-----------------------------------------");
			logger.debug("--------------"+curr_dist+"---------------------------");
			logger.debug("-----------------------------------------");
			imgObj=new ImageDisplay(parent_dir.concat(props.getProperty("welcome")));
			
			try {
				soundPin=new GPIO_Pin(8);
				soundPin.setModeOUTPUT();
				soundobj=new SoundOps(soundPin);
			} catch (Exception e) {
				//logger.debug("soundPin is not available");
				logger.error("Error occured! ",e);
			}
			
			EDU_MENU_MAX_COUNT=Integer.parseInt(props.getProperty("edu_menu_max_count"));
			EDU_MENU_ROW_COUNT=Integer.parseInt(props.getProperty("edu_menu_row_count"));
			EDU_MENU_COLUMN_COUNT=Integer.parseInt(props.getProperty("edu_column_count"));
			
		} catch (MalformedURLException e) {
			logger.error("Error occured! ",e);
		} catch (Exception e) {
	   		logger.error("Error occured! ",e);
	   	}
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void menuHandler(){
		menuHandler(1);
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void menuHandler(int menuimgno){
		try{
			String product_dir=props.getProperty("menu_dir");
			FILE_MAX_COUNT=Integer.parseInt(props.getProperty("menu_max_count"));
			curr_dir=product_dir.replace("{parent_dir}", parent_dir);
			kprocessor.showMenu(menuimgno,curr_dir, imgObj);
			
			MENU_MAX_COUNT=FILE_MAX_COUNT;
			MENU_ROW_COUNT=Integer.parseInt(props.getProperty("menu_row_count"));
			MENU_COLUMN_COUNT=Integer.parseInt(props.getProperty("menu_column_count"));
			
		} catch (Exception e) {
			logger.error("Error occured! ",e);
		} 
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void eduMenuHandler(){
		eduMenuHandler(1);
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void eduMenuHandler(int eduimgno){
		FILE_MAX_COUNT=Integer.parseInt(props.getProperty("educhart_max_count"));
		curr_dir=props.getProperty("educhart_dir").replace("{parent_dir}", parent_dir);
		kprocessor.showEducationalChart(eduimgno,curr_dir, imgObj);
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void pediatricMenuHandler(){
		pediatricMenuHandler(1);
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void pediatricMenuHandler(int pimgno){
		curr_dir=props.getProperty("pediatricchart_dir").replace("{distance_dir}", props.getProperty("distance_dir"));
		curr_dir=curr_dir.replace("{parent_dir}", parent_dir);
		FILE_MAX_COUNT=Integer.parseInt(props.getProperty("pediatricchart_max_count"));
		kprocessor.showPediatricChartNew(pimgno,curr_dir, imgObj);
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void settingMenuHandler(){
		settingMenuHandler(1);
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void settingMenuHandler(int settingimgno){
		FILE_MAX_COUNT=Integer.parseInt(props.getProperty("setting_max_count"));
		curr_dir=props.getProperty("setting_dir").replace("{parent_dir}", parent_dir);
		kprocessor.showSetting(settingimgno,curr_dir, imgObj);
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void handle(String inputkey){
		//logger.debug("Received inputkey :"+inputkey);
		
		if(!isScreenSaverTurnedOn){ // keep in mute when screen shaver is enabled
			soundobj.setMuteOperation(muteOperation);
        	new Thread(soundobj).start(); // Enable / Disable Beep sound for each key press. Comment this line when doing development test
		}
	
		try {
			logger.debug("Matching code ["+inputkey+"] @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime())); 
			
			if(isScreenSaverTurnedOn && inputkey.indexOf(RemoteKeypad.KEY_SCREENSAVER)==-1){
				logger.debug("Screen saver is on, keys will not work until disable the screen saver");
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_HOME)!=-1){
				//welcomeHandler();
				menuHandler();
			}else if(inputkey.indexOf(RemoteKeypad.KEY_BACK)!=-1){
				if(isEduSubMenuDisplay==true){
					logger.debug("back to educational chart key pressed");
					eduMenuHandler(curr_edu_menu_no);
					isEduSubMenuDisplay=false;
				}else if(isPediatricSubMenuDisplay==true){
					logger.debug("back to pediatric chart key pressed");
					pediatricMenuHandler(curr_pediatric_menu_no);
					isPediatricSubMenuDisplay=false;
				}else if(isSettingSubMenuDisplay){
					settingMenuHandler(curr_setting_menu_no);
					isSettingSubMenuDisplay=false;
				}else{
					logger.debug("back to main menu key pressed");
					menuHandler(curr_menu_no);
				}
	
			}else if(inputkey.indexOf(RemoteKeypad.KEY_MUTE)!=-1){
				logger.debug("mute key pressed");
				logger.debug("previuos muteOperation : "+muteOperation);
				if(muteOperation){
					muteOperation=false;
					KeyProcessor.MENU_IMG_PREFIX=KeyProcessor.UNMUTE_MENU_IMG_PREFIX;
				}else{
					muteOperation=true;
					KeyProcessor.MENU_IMG_PREFIX=KeyProcessor.MUTE_MENU_IMG_PREFIX;
				}
				logger.debug("muteOperation is "+muteOperation);
				if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.UNMUTE_MENU_IMG_PREFIX) ||
						kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.MUTE_MENU_IMG_PREFIX)		){
					menuHandler(kprocessor.getCurrImgNo());
				}
					
			}else if(inputkey.indexOf(RemoteKeypad.KEY_HOME)!=-1){
				
				logger.debug("home key pressed");
				imgObj=new ImageDisplay(parent_dir.concat(props.getProperty("home")));
					
			}else if(inputkey.indexOf(RemoteKeypad.KEY_OKAY)!=-1){
				
				logger.debug("okay key pressed");
				logger.debug("kprocessor.getCurrFilePrefix() :"+kprocessor.getCurrFilePrefix());
				if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.MENU_IMG_PREFIX)){
					storeCurrentMenu();
					triggerCurrentMenu();
				}else if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.DISTANCE_IMG_PREFIX)){
					curr_dist=props.getProperty(kprocessor.getCurrImg());
					bookmarkprops.setProperty("pref_distance", curr_dist);
					PropertyUtil.getInstance().storeBookmarkPropertiesIntoFile(bookmarkprops);
					logger.debug("selected distance is "+curr_dist);
				}else if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.SETTING_IMG_PREFIX)){
					storeCurrentMenu();
					triggerSettingMenu();
				}else if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.SETTING_2_SUBMENU_IMG_PREFIX)){
					curr_lang=props.getProperty(kprocessor.getCurrImg());
					logger.debug("preference language1 is  "+curr_lang);
					bookmarkprops.setProperty("pref_language1", curr_lang);
					PropertyUtil.getInstance().storeBookmarkPropertiesIntoFile(bookmarkprops);
				}else if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.SETTING_3_SUBMENU_IMG_PREFIX)){
					curr_lang=props.getProperty(kprocessor.getCurrImg());
					bookmarkprops.setProperty("pref_language2", curr_lang);
					logger.debug("preference language2 is  "+curr_lang);
					PropertyUtil.getInstance().storeBookmarkPropertiesIntoFile(bookmarkprops);
				}else if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.SETTING_4_SUBMENU_IMG_PREFIX)){
					curr_lang=props.getProperty(kprocessor.getCurrImg());
					bookmarkprops.setProperty("pref_language3", curr_lang);
					logger.debug("preference language3 is  "+curr_lang);
					PropertyUtil.getInstance().storeBookmarkPropertiesIntoFile(bookmarkprops);
				}else if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.SETTING_5_SUBMENU_IMG_PREFIX)){
					logger.debug("mute key operation selected");	
					muteOperation=Boolean.parseBoolean(props.getProperty(kprocessor.getCurrImg()));
					logger.debug("selected muteOperation is "+muteOperation);
					
					if(muteOperation==false){
						KeyProcessor.MENU_IMG_PREFIX=KeyProcessor.UNMUTE_MENU_IMG_PREFIX;
					}else{
						KeyProcessor.MENU_IMG_PREFIX=KeyProcessor.MUTE_MENU_IMG_PREFIX;
					}
					
				}else if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.EDUCHART_IMG_PREFIX)){
					storeCurrentMenu();
					triggerEducationMenu();
				}else if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.PEDIATRICCHART_IMG_PREFIX)){
					storeCurrentMenu();
					triggerPediatricMenu();
				}
				//kprocessor.sayOkay(curr_dir, imgObj);
				
			}else if(inputkey.indexOf(RemoteKeypad.KEY_NEXT)!=-1){
				logger.debug("next key pressed");
				if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.SETTING_IMG_PREFIX)){
					storeCurrentMenu();
					triggerSettingMenu();
				}else{
					kprocessor.showNextImg(curr_dir, FILE_MAX_COUNT, imgObj);
				}
				storeCurrentMenu();
				
			}else if(inputkey.indexOf(RemoteKeypad.KEY_PREV)!=-1){
				logger.debug("prev key pressed");
				
				if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.SETTING_IMG_PREFIX)){
					storeCurrentMenu();
					triggerSettingMenu();
				}else{
					kprocessor.showPrevImg(curr_dir, FILE_MAX_COUNT, imgObj);
				}
				storeCurrentMenu();
				
			}else if(inputkey.indexOf(RemoteKeypad.KEY_UP)!=-1){
				logger.debug("up key pressed");
				if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.MENU_IMG_PREFIX)){
					int topno=getTopMenuNo();
					if(topno!=-1)
						menuHandler(topno);
					storeCurrentMenu();
				}else if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.EDUCHART_IMG_PREFIX)){
					int topno=getTopEduMenuNo();
					if(topno!=-1)
						eduMenuHandler(topno);
					storeCurrentMenu();
				}else{
					kprocessor.showUpImg(curr_dir, FILE_MAX_COUNT, imgObj);
				}
				
				
			}else if(inputkey.indexOf(RemoteKeypad.KEY_DOWN)!=-1){
				logger.debug("down key pressed ");
				if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.MENU_IMG_PREFIX)){
					int downno=getDownMenuNo();
					if(downno!=-1)
						menuHandler(downno);
					storeCurrentMenu();
				}else if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.EDUCHART_IMG_PREFIX)){
					int downno=getDownEduMenuNo();
					if(downno!=-1)
						eduMenuHandler(downno);
					storeCurrentMenu();
				}else{
					kprocessor.showDownImg(curr_dir, FILE_MAX_COUNT, imgObj);
				}
				storeCurrentMenu();
			}else if(inputkey.indexOf(RemoteKeypad.KEY_CARTOON_INFO)!=-1){
				
				logger.debug("cartoon key pressed");
				String cartoon_dir=props.getProperty("cartoon_dir");
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("cartoon_max_count"));
				curr_dir=cartoon_dir.replace("{parent_dir}", parent_dir);
				kprocessor.showFirstCartoon(curr_dir, imgObj);
				
				
			}else if(inputkey.indexOf(RemoteKeypad.KEY_ANIMAL_INFO)!=-1){
				
				logger.debug("animal key pressed");
				String animal_dir=props.getProperty("animal_dir");
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("animal_max_count"));
				curr_dir=animal_dir.replace("{parent_dir}", parent_dir);
				kprocessor.showFirstAnimal(curr_dir, imgObj);
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_HORIZONTAL)!=-1){
					
				logger.debug("horizontal key pressed");
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("horizontal_max_count"));
				curr_dir=props.getProperty("horizontal_dir").replace("{parent_dir}", parent_dir);
				kprocessor.showFirstHorizontal(curr_dir, imgObj);

			}else if(inputkey.indexOf(RemoteKeypad.KEY_LANDOLTRING_CHART)!=-1){
				
				logger.debug("landolt ring chart key pressed");
				curr_dir=props.getProperty("landoltring_dir").replace("{distance_dir}", props.getProperty("distance_dir"));
				curr_dir=curr_dir.replace("{parent_dir}", parent_dir);
				curr_dir=curr_dir.replace("{curr_dist}", curr_dist);
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("landoltring_max_count"));
				kprocessor.showFirstLandoltringChart(curr_dir, imgObj);	
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_E_CHART)!=-1){
				
				logger.debug("E chart key pressed");
				curr_dir=props.getProperty("echart_dir").replace("{distance_dir}", props.getProperty("distance_dir"));
				curr_dir=curr_dir.replace("{parent_dir}", parent_dir);
				curr_dir=curr_dir.replace("{curr_dist}", curr_dist);
				
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("echart_max_count"));
				kprocessor.showFirstEChart(curr_dir, imgObj);	
				
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_ENGLISH_ALPHA)!=-1){
				
				logger.debug("english alphabets key pressed");
				
				curr_dir=props.getProperty("english_alphabets_dir").replace("{distance_dir}", props.getProperty("distance_dir"));
				curr_dir=curr_dir.replace("{parent_dir}", parent_dir);
				curr_dir=curr_dir.replace("{curr_dist}", curr_dist);
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("english_alphabets_max_count"));
				kprocessor.showFirstLetter(curr_dir, imgObj);	
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_NUMBER_CHART)!=-1){
				
				logger.debug("number chart key pressed");
				curr_dir=props.getProperty("numberchart_dir").replace("{distance_dir}", props.getProperty("distance_dir"));
				curr_dir=curr_dir.replace("{parent_dir}", parent_dir);
				curr_dir=curr_dir.replace("{curr_dist}", curr_dist);
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("numberchart_max_count"));
				kprocessor.showFirstNumberChart(curr_dir, imgObj);	
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_PEDIATRIC_CHART)!=-1){
				
				logger.debug("pediatric chart key pressed");
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("pediatricchart_max_count"));
				curr_dir=props.getProperty("pediatricchart_dir").replace("{parent_dir}", parent_dir);
				kprocessor.showFirstPediatricChart(curr_dir, imgObj);
			}else if(inputkey.indexOf(RemoteKeypad.KEY_DOT)!=-1){
					
					logger.debug("dot chart new key pressed");
					FILE_MAX_COUNT=Integer.parseInt(props.getProperty("dot_max_count"));
					curr_dir=props.getProperty("dot_dir").replace("{parent_dir}", parent_dir);
					kprocessor.showFirstDotNew(curr_dir, imgObj);
				
				
			}else if(inputkey.indexOf(RemoteKeypad.KEY_LANGUAGE1)!=-1){
					
					logger.debug("prefered language 1 key pressed");
					curr_dir=props.getProperty("language1_dir").replace("{distance_dir}", props.getProperty("distance_dir"));
					curr_dir=curr_dir.replace("{parent_dir}", parent_dir);
					curr_dir=curr_dir.replace("{curr_dist}", curr_dist);
					curr_dir=curr_dir.replace("{pref_language1}", bookmarkprops.getProperty("pref_language1"));
					FILE_MAX_COUNT=Integer.parseInt(props.getProperty("language1_max_count"));
					kprocessor.showFirstLetter(curr_dir, imgObj);	
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_LANGUAGE2)!=-1){
				
				logger.debug("prefered language 2 key pressed");
				curr_dir=props.getProperty("language2_dir").replace("{distance_dir}", props.getProperty("distance_dir"));
				curr_dir=curr_dir.replace("{parent_dir}", parent_dir);
				curr_dir=curr_dir.replace("{curr_dist}", curr_dist);
				curr_dir=curr_dir.replace("{pref_language2}", bookmarkprops.getProperty("pref_language2"));
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("language2_max_count"));
				kprocessor.showFirstLetter(curr_dir, imgObj);	
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_LANGUAGE3)!=-1){
				
				logger.debug("prefered language 3 key pressed");
				curr_dir=props.getProperty("language3_dir").replace("{distance_dir}", props.getProperty("distance_dir"));
				curr_dir=curr_dir.replace("{parent_dir}", parent_dir);
				curr_dir=curr_dir.replace("{curr_dist}", curr_dist);
				curr_dir=curr_dir.replace("{pref_language3}", bookmarkprops.getProperty("pref_language3"));
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("language3_max_count"));
				kprocessor.showFirstLetter(curr_dir, imgObj);	
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_SNELIEN_CHART)!=-1){
				
				logger.debug("snelien chart key pressed");
				curr_dir=props.getProperty("snelienchart_dir").replace("{distance_dir}", props.getProperty("distance_dir"));
				curr_dir=curr_dir.replace("{parent_dir}", parent_dir);
				curr_dir=curr_dir.replace("{curr_dist}", curr_dist);
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("snelienchart_max_count"));
				kprocessor.showFirstSnelienChart(curr_dir, imgObj);
				
			}else if(inputkey.indexOf(RemoteKeypad.KEY_LOGMAR_CHART)!=-1){
				
				logger.debug("logmar chart key pressed");
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("logmarchart_max_count"));
				curr_dir=props.getProperty("logmarchart_dir").replace("{parent_dir}", parent_dir);
				kprocessor.showFirstLogmarChart(curr_dir, imgObj);
				
			}else if(inputkey.indexOf(RemoteKeypad.KEY_PEDIATRIC_CHART)!=-1){
				
				logger.debug("pediatric chart new menu key pressed");
				pediatricMenuHandler();
			}else if(inputkey.indexOf(RemoteKeypad.KEY_SETTING)!=-1){
				
				logger.debug("setting menu key pressed");
				settingMenuHandler();
					
			}else if(inputkey.indexOf(RemoteKeypad.KEY_CONTRAST_CHART)!=-1){
				
				logger.debug("contrast chart key pressed");
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("contrastchart_max_count"));
				curr_dir=props.getProperty("contrastchart_dir").replace("{parent_dir}", parent_dir);
				kprocessor.showFirstContrastChart(curr_dir, imgObj);
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_ASTIG_CHART)!=-1){
				
				logger.debug("astig chart key pressed");
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("astigfanchart_max_count"));
				curr_dir=props.getProperty("astigfanchart_dir").replace("{parent_dir}", parent_dir);
				kprocessor.showFirstAstigFanChart(curr_dir, imgObj);
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_ISHARA)!=-1){
				
				logger.debug("ishara key pressed");
				
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("ishara_max_count"));
				//curr_dir=props.getProperty("ishara_dir").replace("{parent_dir}", parent_dir);
				curr_dir=props.getProperty("ishara_dir").replace("{distance_dir}", props.getProperty("distance_dir"));
				curr_dir=curr_dir.replace("{parent_dir}", parent_dir);
				curr_dir=curr_dir.replace("{curr_dist}", curr_dist);
				
				kprocessor.showFirstIshara(curr_dir, imgObj);
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_EDU_CHART)!=-1){
				
				logger.debug("educational chart key pressed");
				eduMenuHandler();	
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_VERTICAL)!=-1){
				
				logger.debug("vertical key pressed");
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("vertical_max_count"));
				curr_dir=props.getProperty("vertical_dir").replace("{parent_dir}", parent_dir);
				kprocessor.showFirstVertical(curr_dir, imgObj);
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_MADDOX_CHART)!=-1){
				
				logger.debug("maddox chart key pressed");
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("maddox_max_count"));
				curr_dir=props.getProperty("maddox_dir").replace("{parent_dir}", parent_dir);
				kprocessor.showFirstMaddoxChart(curr_dir, imgObj);
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_RG_CHART)!=-1){
				
				logger.debug("rg chart key pressed");
				curr_dir=props.getProperty("rgchart_dir").replace("{distance_dir}", props.getProperty("distance_dir"));
				curr_dir=curr_dir.replace("{parent_dir}", parent_dir);
				curr_dir=curr_dir.replace("{curr_dist}", curr_dist);
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("rgchart_max_count"));
				kprocessor.showFirstRGChart(curr_dir, imgObj);
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_CROSS_CYLINDER_CHART)!=-1){
				
				logger.debug("cross cylinder chart key pressed");
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("crosscylinderchart_max_count"));
				curr_dir=props.getProperty("crosscylinderchart_dir").replace("{parent_dir}", parent_dir);
				kprocessor.showFirstCrossCylinderChart(curr_dir, imgObj);
			
			}else if(inputkey.indexOf(RemoteKeypad.KEY_DISTANCE)!=-1){
				
				logger.debug("distance key pressed");
				String distance_dir=props.getProperty("distance_dir");
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("distance_max_count"));
				curr_dir=distance_dir.replace("{parent_dir}", parent_dir);
				kprocessor.showDistanceList(curr_dir, imgObj);	
		
			}else if(inputkey.indexOf(RemoteKeypad.KEY_LANGUAGE)!=-1){
			
				logger.debug("language key pressed");
				curr_dir=props.getProperty("language_dir").replace("{parent_dir}", parent_dir);;
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("language_max_count"));
				kprocessor.showLanguageList(curr_dir, imgObj);	
		
			}else if(inputkey.indexOf(RemoteKeypad.KEY_PRODUCT_INFO)!=-1){
				
				logger.debug("product key pressed");
				String product_dir=props.getProperty("product_dir");
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("product_max_count"));
				curr_dir=product_dir.replace("{parent_dir}", parent_dir);
				kprocessor.showFirstProduct(curr_dir, imgObj);
				
			}else if(inputkey.indexOf(RemoteKeypad.KEY_ADVERTISEMENT)!=-1){
				
				logger.debug("advertisement key pressed");
				String advertisement_dir=props.getProperty("advertisement_dir");
				FILE_MAX_COUNT=Integer.parseInt(props.getProperty("advertisement_max_count"));
				curr_dir=advertisement_dir.replace("{parent_dir}", parent_dir);
				kprocessor.showFirstAdvertisement(curr_dir, imgObj);
				
				
			}else if(inputkey.indexOf(RemoteKeypad.KEY_LETTER)!=-1){
				
				logger.debug("letter key pressed");
				if(curr_dir!=null && curr_lang!=null)
				{
					
					curr_dir=props.getProperty("letter_dir").replace("{distance_dir}", props.getProperty("distance_dir"));
					curr_dir=curr_dir.replace("{parent_dir}", parent_dir);
					curr_dir=curr_dir.replace("{curr_dist}", curr_dist).replace("{curr_lang}", curr_lang);
				
					FILE_MAX_COUNT=Integer.parseInt(props.getProperty("letter_max_count"));
					kprocessor.showFirstLetter(curr_dir, imgObj);	
				}
		
			}else if(inputkey.indexOf(RemoteKeypad.KEY_SCREENSAVER)!=-1){
				logger.debug("Screen saver key is pressed");
				if(isScreenSaverTurnedOn){
					isScreenSaverTurnedOn=false;
					logger.debug("Screen saver disabled . showing actual image");
					kprocessor.showImage(curr_dir, imgObj);	
				}else{
					String screen_saver_dir=props.getProperty("screen_saver_dir");
					screen_saver_dir=screen_saver_dir.replace("{parent_dir}", parent_dir);
					logger.debug("Screen saver enabled . showing black image");
					kprocessor.showScreenSaverImage(screen_saver_dir, imgObj);	
					isScreenSaverTurnedOn=true;
				}
			}
			
			
			curr_img=kprocessor.getCurrImg();
			curr_img_no=kprocessor.getCurrImgNo();
			
			logger.debug("Matching code ["+inputkey+"] ended @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime())); 
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Error occured! ",e);
		}
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public int getTopMenuNo(){
		
		if((curr_menu_no-MENU_COLUMN_COUNT)<0)
			return -1;
		else
			return (curr_menu_no-MENU_COLUMN_COUNT);
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public int getDownMenuNo(){
		
		if((curr_menu_no+MENU_COLUMN_COUNT)>MENU_MAX_COUNT)
			return -1;
		else
			return (curr_menu_no+MENU_COLUMN_COUNT);
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void storeCurrentMenu(){
		if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.MENU_IMG_PREFIX)){
			curr_menu_no=kprocessor.getCurrImgNo();
		}else if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.EDUCHART_IMG_PREFIX)){
			curr_edu_menu_no=kprocessor.getCurrImgNo();
		}else if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.PEDIATRICCHART_IMG_PREFIX)){
			curr_pediatric_menu_no=kprocessor.getCurrImgNo();
		}else if(kprocessor.getCurrFilePrefix().equalsIgnoreCase(KeyProcessor.SETTING_IMG_PREFIX)){
			curr_setting_menu_no=kprocessor.getCurrImgNo();
			logger.debug("curr_setting_menu_no:"+curr_setting_menu_no);
		}
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void triggerCurrentMenu(){
		
		String chartName=props.getProperty(kprocessor.getCurrImg().toUpperCase());
		String inputkey = props.getProperty(chartName).trim();
		handle(inputkey);
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public int getTopEduMenuNo(){
		
		if((curr_menu_no-EDU_MENU_COLUMN_COUNT)<0)
			return -1;
		else
			return (curr_menu_no-EDU_MENU_COLUMN_COUNT);
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public int getDownEduMenuNo(){
		
		if((curr_menu_no+EDU_MENU_COLUMN_COUNT)>EDU_MENU_MAX_COUNT)
			return -1;
		else
			return (curr_menu_no+EDU_MENU_COLUMN_COUNT);
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void triggerEducationMenu(){
		isEduSubMenuDisplay=true;
		String edu_no=kprocessor.getCurrImg(); //edu_1,edu_2, etc....s
		String edu_sub_dir=props.getProperty(edu_no.concat("_dir")); // edu_1_dir, edu_2_dir, etc.....
		//logger.debug("education sub menu "+edu_no+" pressed");
		FILE_MAX_COUNT=Integer.parseInt(props.getProperty(edu_no.concat("_max_count")));// edu_1_max_count, edu_2_max_count, etc.....
		curr_dir=edu_sub_dir.replace("{parent_dir}", parent_dir);
		kprocessor.showFirstEduSubMenuImage(curr_dir, imgObj);
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void triggerPediatricMenu(){
		isPediatricSubMenuDisplay=true;
		String pediatric_no=kprocessor.getCurrImg(); //pediatricchart_1,pediatricchart_2, etc....s
		String pediatric_sub_dir=props.getProperty(pediatric_no.concat("_dir")); // pediatricchart_1_dir, pediatricchart_2_dir, etc.....
		logger.debug("pediatricchart new sub menu "+pediatric_no+" pressed");
		FILE_MAX_COUNT=Integer.parseInt(props.getProperty(pediatric_no.concat("_max_count")));// pediatricchart_1_max_count, pediatricchart_2_max_count, etc.....
		curr_dir=pediatric_sub_dir.replace("{pediatricchart_dir}", props.getProperty("pediatricchart_dir"));
		curr_dir=curr_dir.replace("{distance_dir}", props.getProperty("distance_dir"));
		curr_dir=curr_dir.replace("{parent_dir}", parent_dir);
		curr_dir=curr_dir.replace("{curr_dist}", curr_dist);
		kprocessor.showFirstPediatricChart(curr_dir, imgObj);
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void triggerSettingMenu(){
		isSettingSubMenuDisplay=true;
		String setting_no=kprocessor.getCurrImg(); //setting_1,setting_2, etc....s
		String setting_sub_dir=props.getProperty(setting_no.concat("_dir")); // setting_1_dir, setting_2_dir, etc.....
		logger.debug("setting sub menu "+setting_no+" pressed");
		FILE_MAX_COUNT=Integer.parseInt(props.getProperty(setting_no.concat("_max_count")));// edu_1_max_count, edu_2_max_count, etc.....
		curr_dir=setting_sub_dir.replace("{parent_dir}", parent_dir);
		if(setting_no.equalsIgnoreCase("setting_1")){
			int imgNo=CommonUtility.getSelectedDistanceImgNo(props, curr_dist);
			logger.debug("identified distance img no is :"+imgNo);
			kprocessor.showFirstSettingSubMenuImage(curr_dir, imgNo, imgObj);
		}else if(setting_no.equalsIgnoreCase("setting_2")){
			int imgNo=CommonUtility.getSelectedLanguageImgNo(props, bookmarkprops.getProperty("pref_language1"));
			kprocessor.showSecondSettingSubMenuImage(curr_dir,imgNo, imgObj);
		}else if(setting_no.equalsIgnoreCase("setting_3")){
			int imgNo=CommonUtility.getSelectedLanguageImgNo(props, bookmarkprops.getProperty("pref_language2"));
			kprocessor.showThirdSettingSubMenuImage(curr_dir,imgNo, imgObj);
		}else if(setting_no.equalsIgnoreCase("setting_4")){
			int imgNo=CommonUtility.getSelectedLanguageImgNo(props, bookmarkprops.getProperty("pref_language3"));
			kprocessor.showFourthSettingSubMenuImage(curr_dir,imgNo, imgObj);
		}else if(setting_no.equalsIgnoreCase("setting_5")){
			int imgNo=(muteOperation==false?1:2);
			kprocessor.showFifthSettingSubMenuImage(curr_dir,imgNo, imgObj);
		}
	}
	
}
