package com.quad.core;

public class Settings {
	
	public static final int WIDTH = 800/2;
	public static final int HEIGHT = 600/2;
	public static final int SCALE = 2;	
		
	public static int FPS = 60;
	
	public static boolean LIGHT = false;
	
	public static final boolean RAINING = false;
	
	public static void setLight(GameContainer gc, boolean i){
		Settings.LIGHT = i;
		gc.setLightEnable(i);
		gc.setDynamicLights(i);
	}
	
	public static void changeFps(GameContainer gc, int i){
		gc.setFrameCap(i);
	}
	

}
