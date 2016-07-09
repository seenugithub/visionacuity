package com.marvel.visionacuity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;


public class KeyProcessor {

	final static Logger logger = Logger.getLogger(KeyProcessor.class);
	
	CacheWorker cacheWorker = new CacheWorker();
	
	private String currImg=null;
	private int currImgNo=0;
	private String currFilePrefix=null;
	public String STRING_EMPTY="";
	public int ZERO=0;
	public int ONE=1;
	public String OKAY="okay";
	
	public static  String MENU_IMG_PREFIX="menu_";
	public static final String UNMUTE_MENU_IMG_PREFIX="menu_";
	public static final String MUTE_MENU_IMG_PREFIX="mute_menu_";
	
	public static final String PRODUCT_IMG_PREFIX="product_";
	public static final String ADVERTISEMENT_IMG_PREFIX="advertisement_";
	public static final String ANIMAL_IMG_PREFIX="animal_";
	public static final String CARTOON_IMG_PREFIX="cartoon_";
	public static final String DISTANCE_IMG_PREFIX="distance_";
	public static final String LANGUAGE_IMG_PREFIX="language_";
	public static final String ISHARA_IMG_PREFIX="ishara_";
	public static final String VERTICAl_IMG_PREFIX="vertical_";
	public static final String HORIZONTAL_IMG_PREFIX="horizontal_";
	public static final String WITHOUTDOT_IMG_PREFIX="withoutdot_";
	public static final String WITHDOT_IMG_PREFIX="withdot_";
	public static final String MADDOX_IMG_PREFIX="maddox_";
	public static final String DOT_IMG_PREFIX="dot_";
	public static final String DOTCHART_IMG_PREFIX="dotchart_";
	public static final String CROSSCYLINDERCHART_IMG_PREFIX="crosscylinder_";
	public static final String ASTIGFANCHART_IMG_PREFIX="astigfanchart_";
	public static final String RGCHART_IMG_PREFIX="rgchart_";
	public static final String PERIPHERALCHART_IMG_PREFIX="peripheralchart_";
	public static final String LETTER_IMG_PREFIX="letter_";
	public static final String NUMBERCHART_IMG_PREFIX="numberchart_";
	public static final String ECHART_IMG_PREFIX="echart_";
	public static final String LANDOLTRING_IMG_PREFIX="ring_";
	
	public static final String SNELIENCHART_IMG_PREFIX="snelienchart_";
	public static final String LOGMARCHART_IMG_PREFIX="logmarchart_";
	public static final String CONTRASTCHART_IMG_PREFIX="contrastchart_";
	public static final String PEDIATRICCHART_IMG_PREFIX="pediatricchart_";
	public static final String EDUCHART_IMG_PREFIX="edu_";
	public static final String EDUCATIONAL_SUBMENU_IMG_PREFIX="edu_sub_";
	public static final String SETTING_IMG_PREFIX="setting_";
	public static final String SETTING_SUBMENU_IMG_PREFIX="setting_sub_";
	public static final String SETTING_1_SUBMENU_IMG_PREFIX="distance_";
	public static final String SETTING_2_SUBMENU_IMG_PREFIX="language1_";
	public static final String SETTING_3_SUBMENU_IMG_PREFIX="language2_";
	public static final String SETTING_4_SUBMENU_IMG_PREFIX="language3_";
	public static final String SETTING_5_SUBMENU_IMG_PREFIX="sound_";
	public static final String SCREEN_SAVER_IMG_PREFIX="screen_";
	
	
	public String getCurrImg() {
		return currImg;
	}

	public void setCurrImg(String currImg) {
		this.currImg = currImg;
	}

	public int getCurrImgNo() {
		return currImgNo;
	}

	public void setCurrImgNo(int currImgNo) {
		this.currImgNo = currImgNo;
	}

	
	public String getCurrFilePrefix() {
		if(currFilePrefix!=null)
			return currFilePrefix;
		else
			return STRING_EMPTY;
	}

	public void setCurrFilePrefix(String currFilePrefix) {
		this.currFilePrefix = currFilePrefix;
	}

	public void showFirstMenu(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(MENU_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showMenu(int imgNo,String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(imgNo);
		setCurrFilePrefix(MENU_IMG_PREFIX);
		logger.debug("menu prefix ::: "+MENU_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}

	public void showFirstProduct(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(PRODUCT_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstAdvertisement(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(ADVERTISEMENT_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstEduSubMenuImage(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(EDUCATIONAL_SUBMENU_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstSettingSubMenuImage(String curr_dir,int imgNo,ImageDisplay imgObj){
		setCurrImgNo(imgNo);
		setCurrFilePrefix(SETTING_1_SUBMENU_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showSecondSettingSubMenuImage(String curr_dir,int imgNo,ImageDisplay imgObj){
		
		setCurrImgNo(imgNo);
		setCurrFilePrefix(SETTING_2_SUBMENU_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showThirdSettingSubMenuImage(String curr_dir,int imgNo,ImageDisplay imgObj){
		
		setCurrImgNo(imgNo);
		setCurrFilePrefix(SETTING_3_SUBMENU_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFourthSettingSubMenuImage(String curr_dir,int imgNo,ImageDisplay imgObj){
		
		setCurrImgNo(imgNo);
		setCurrFilePrefix(SETTING_4_SUBMENU_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFifthSettingSubMenuImage(String curr_dir,int imgNo,ImageDisplay imgObj){
		
		setCurrImgNo(imgNo);
		setCurrFilePrefix(SETTING_5_SUBMENU_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	public void showFirstAnimal(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(ANIMAL_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstIshara(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(ISHARA_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstVertical(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(VERTICAl_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstWithOutDot(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ZERO);
		setCurrFilePrefix(WITHOUTDOT_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstWithDot(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ZERO);
		setCurrFilePrefix(WITHDOT_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstHorizontal(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(HORIZONTAL_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstMaddoxChart(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(MADDOX_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showPediatricChartNew(int imgno, String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(imgno);
		setCurrFilePrefix(PEDIATRICCHART_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}

	
	public void showFirstCrossCylinderChart(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(CROSSCYLINDERCHART_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstAstigFanChart(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(ASTIGFANCHART_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstRGChart(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(RGCHART_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstPeripheralChart(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(PERIPHERALCHART_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstLandoltringChart(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(LANDOLTRING_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstLetter(String curr_dir,ImageDisplay imgObj){
	    setCurrImgNo(ONE);
		setCurrFilePrefix(LETTER_IMG_PREFIX);
		showImage(curr_dir,imgObj);
   }
	
	public void showFirstSnelienChart(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(SNELIENCHART_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstLogmarChart(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(LOGMARCHART_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstContrastChart(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(CONTRASTCHART_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstPediatricChart(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(PEDIATRICCHART_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showFirstDotNew(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(DOT_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showEducationalChart(int imgno, String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(imgno);
		setCurrFilePrefix(EDUCHART_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showSetting(int imgno, String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(imgno);
		setCurrFilePrefix(SETTING_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
   public void showDistanceList(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ZERO);
		setCurrFilePrefix(DISTANCE_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
   
   public void showLanguageList(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ZERO);
		setCurrFilePrefix(LANGUAGE_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
   
   public void showFirstNumberChart(String curr_dir,ImageDisplay imgObj){
	    setCurrImgNo(ONE);
		setCurrFilePrefix(NUMBERCHART_IMG_PREFIX);
		showImage(curr_dir,imgObj);
  }
   
   public void showFirstEChart(String curr_dir,ImageDisplay imgObj){
	    setCurrImgNo(ONE);
		setCurrFilePrefix(ECHART_IMG_PREFIX);
		showImage(curr_dir,imgObj);
  }
   
   public void showFirstCartoon(String curr_dir,ImageDisplay imgObj){
		
		setCurrImgNo(ONE);
		setCurrFilePrefix(CARTOON_IMG_PREFIX);
		showImage(curr_dir,imgObj);
	}
	
	public void showNextImg(String curr_dir,int fileMaxCnt,ImageDisplay imgObj){
		setCurrImgNo(getCurrImgNo()+1);
		if(getCurrImgNo()<=fileMaxCnt && getCurrImgNo()<=fileMaxCnt){
			showImage(curr_dir,imgObj);
		}else{
			setCurrImgNo(ONE);
			showImage(curr_dir,imgObj);
		}
	}
	
	public void showPrevImg(String curr_dir,int fileMaxCnt,ImageDisplay imgObj){
		setCurrImgNo(getCurrImgNo()-1);
		if(getCurrImgNo()>=1 && getCurrImgNo()<=fileMaxCnt){
			showImage(curr_dir,imgObj);
		}else{
			setCurrImgNo(fileMaxCnt);
			showImage(curr_dir,imgObj);
		}
	}
	
	public void showUpImg(String curr_dir,int fileMaxCnt,ImageDisplay imgObj){
		setCurrImgNo(getCurrImgNo()-1);
		if(getCurrImgNo()>=1 && getCurrImgNo()<=fileMaxCnt){
			showImage(curr_dir,imgObj);
		}else{
			setCurrImgNo(fileMaxCnt);
			showImage(curr_dir,imgObj);
		}
	}
	
	public void showDownImg(String curr_dir,int fileMaxCnt,ImageDisplay imgObj){
		setCurrImgNo(getCurrImgNo()+1);
		if(getCurrImgNo()>=1 && getCurrImgNo()<=fileMaxCnt){
			showImage(curr_dir,imgObj);
		}else{
			setCurrImgNo(ONE);
			showImage(curr_dir,imgObj);
		}
	}
	
	public void sayOkay(String curr_dir,ImageDisplay imgObj){
		imgObj.loadImage(curr_dir.concat(getCurrFilePrefix().concat(OKAY)));
	}
	
	public void showImage(String curr_dir,ImageDisplay imgObj){
		//logger.debug("Show image started @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime()));
		// Caching Next level directory in seperate thread
		cacheWorker.setCacheDirectory(curr_dir);
		cacheWorker.setImagePrefix(getCurrFilePrefix());
		cacheWorker.setImageNo(getCurrImgNo());
		new Thread(cacheWorker).start();
		
		setCurrImg(getCurrFilePrefix().concat(getCurrImgNo()+""));
		imgObj.loadImage(curr_dir.concat(getCurrImg()));
		//logger.debug("Show image ended @ "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS").format(Calendar.getInstance().getTime())); 
	}
	
	public void showScreenSaverImage(String dir , ImageDisplay imgObj){
		imgObj.loadImage(dir.concat(SCREEN_SAVER_IMG_PREFIX).concat(ONE+""));
	}
}
