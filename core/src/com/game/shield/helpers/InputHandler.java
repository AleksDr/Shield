package com.game.shield.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.game.shield.objects.Shield;
import com.game.shield.gameworld.GameWorld;
import com.badlogic.gdx.Screen;

public class InputHandler implements InputProcessor{
	
	//-------------------------------------
	//Private fields
	
	GameWorld world_;
	
	//-------------------------------------
	//Constructors, getters, setters
	
	public InputHandler(GameWorld world)
	{
		world_ = world;
	}
	
	//-------------------------------------
	//Other methods


	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) 
	{
		if(world_.isRunning())
		{
			if(screenX > world_.getMidPointX() && screenY < 40)
				world_.soundSwitch();
			else if(screenY>40)
				world_.getShield().onClick(screenX, screenY, world_.getMidPointX(), world_.getMidPointY());
		}
		else if(world_.isGameOver()||world_.isHighScore())
		{
			world_.reStart();
		}
		else if(world_.isReady())
		{
			world_.start();
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) 
	{

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) 
	{
		world_.getShield().onClick( screenX,screenY, world_.getMidPointX(), world_.getMidPointY());
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
