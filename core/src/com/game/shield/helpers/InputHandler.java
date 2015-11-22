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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) 
	{
		if(world_.isRunning())
		{
			world_.getShield().onClick( screenX,screenY, world_.getMidPointX(), world_.getMidPointY());
		}
		else if(world_.isGameOver())
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
