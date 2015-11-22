package com.game.shield.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetLoader {

	
	//-------------------------------------
	//Fields
	
	public static Sound gameOver_;
	public static Sound collision_;
	public static BitmapFont font_;
	
	//-------------------------------------
	//Constructors, getters, setters
	
	//-------------------------------------
	//Other methods
	
	 public static void load() 
	 {   
		 gameOver_ = Gdx.audio.newSound(Gdx.files.internal("data/GameOver.wav"));
		 collision_ = Gdx.audio.newSound(Gdx.files.internal("data/Collision.wav"));
		 font_ = new BitmapFont(Gdx.files.internal("data/ShieldFont.fnt"));
		 font_.getData().setScale(1f, -1f);
	 }
	 
	 public static void dispose() 
	 {   
		 font_.dispose();
	 }
}
