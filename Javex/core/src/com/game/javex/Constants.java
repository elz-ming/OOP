package com.game.javex;

public class Constants {
	
	public static boolean muted = false;
	
	
	public static final int VIEWPORT_WIDTH = 1920;
	public static final int VIEWPORT_HEIGHT = 1080;
	public static final int WORLD_WIDTH = 3200;
	public static final int WORLD_HEIGHT = 960;
	public static final float PPM = 100;
	
	public static final float VIEWPORT_WIDTH_METER = VIEWPORT_WIDTH /PPM;
	public static final float VIEWPORT_HEIGHT_METER = VIEWPORT_HEIGHT /PPM;
	public static final float WORLD_WIDTH_METER = WORLD_WIDTH /PPM;
	public static final float WORLD_HEIGHT_METER = WORLD_HEIGHT /PPM;
	
	public static final short PLAYER_BIT = 1;
	public static final short PLAYER_BOTTOM_BIT = 2; 
	public static final short ENEMY_BIT = 4; 
	public static final short ENEMY_HEAD_BIT = 8;
	public static final short TERRAIN_BIT = 16;
	public static final short REWARD_BIT = 32;
	
	public static final int PLAYER_WIDTH = 32;
	public static final int PLAYER_HEIGHT = 32;
	
	public static final int ENEMY_WIDTH = 32;
	public static final int ENEMY_HEIGHT = 32;
	
	public static final int BOSS_WIDTH = 192;
	public static final int BOSS_HEIGHT = 192;
	
	public static final int COIN_WIDTH = 16;
	public static final int COIN_HEIGHT = 16;
	
	public static final int ENEMY_MAX_SPEED = 2;
	
	public static final String MENU_IMG_PATH = "image_background/menu.png";
	public static final String WORLDSELECTION_IMG_PATH = "image_background/worldselection.png";
	public static final String CHATBOX_IMG_PATH = "image_background/chatbox.png";
	public static final String EARTH_IMG_PATH = "image_background/earth.png";
	public static final String MARS_IMG_PATH = "image_background/mars.png";
	public static final String VENUS_IMG_PATH = "image_background/venus.png";
	public static final String END_IMG_PATH = "image_background/end.png";
	
	public static final String MENU_AUDIO_PATH = "audio/menu.mp3";
	public static final String EARTH_AUDIO_PATH = "audio/earth.mp3";
	public static final String MARS_AUDIO_PATH = "audio/mars.mp3";
	public static final String VENUS_AUDIO_PATH = "audio/venus.mp3";
	public static final String END_AUDIO_PATH = "audio/end.mp3";
	
	public static final String PLAYER_IMG_PATH = "image_entity/player.png"; 
	public static final String ENEMY_IMG_PATH = "image_entity/enemy.png";
	public static final String BOSS_IMG_PATH = "image_entity/boss.png";
	public static final String COIN_IMG_PATH = "image_entity/coin.png";
	public static final String TERRAIN_IMG_PATH = "image_entity/terrain.png"; 
	
	public static final String EARTH_MAP_PATH = "map/WorldMap.tmx";
	public static final String MARS_MAP_PATH = "";
	public static final String VENUS_MAP_PATH = "";
}
