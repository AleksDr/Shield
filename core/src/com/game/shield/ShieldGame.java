package com.game.shield;

import com.badlogic.gdx.ApplicationAdapter;
import com.game.shield.helpers.AssetLoader;
import com.game.shield.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;


public class ShieldGame extends Game 
{
	
	//-------------------------------------
	//Private fields
	
	//-------------------------------------
	//Constructors, getters, setters
	
	//-------------------------------------
	//Other methods

	@Override
	public void create () 
	{
		Gdx.app.log("ZBGame", "created");;
		AssetLoader.load();
		setScreen(new GameScreen());
	}
	
	@Override
	public void dispose() 
	{
		super.dispose();
		AssetLoader.dispose();
	}


}
