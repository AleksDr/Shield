package com.game.shield.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetLoader {

	
	//-------------------------------------
	//Fields
	
	public static Preferences prefs;
	public static Sound gameOver_;
	public static Sound collision_;
	public static BitmapFont font_;
	public static int shieldCapacity_;
	
	//-------------------------------------
	//Constructors, getters, setters
	
	//-------------------------------------
	//Other methods
	
	 public static void load() 
	 {   
		 FileHandle handle = Gdx.files.internal("data/file.txt");
		 String line = handle.readString();
		 shieldCapacity_ = Integer.parseInt(line);
		 prefs = Gdx.app.getPreferences("ZombieBird");
		 if (!prefs.contains("highScore")) {
			    prefs.putInteger("highScore", 0);
			}
		 gameOver_ = Gdx.audio.newSound(Gdx.files.internal("data/GameOver.wav"));
		 collision_ = Gdx.audio.newSound(Gdx.files.internal("data/Collision.wav"));
		 font_ = new BitmapFont(Gdx.files.internal("data/ShieldFont.fnt"));
		 font_.getData().setScale(1f, -1f);
	 }
	 
	 public static void dispose() 
	 {   
		 font_.dispose();
	 }
	 
	 public static void setHighScore(int val) 
	 {
		 prefs.putInteger("highScore", val);
		 prefs.flush();
	 }
	 
	 public static int getHighScore() 
	 {
		 return prefs.getInteger("highScore");
	 }
	 
}
