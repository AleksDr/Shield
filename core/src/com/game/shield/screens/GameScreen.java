package com.game.shield.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.game.shield.gameworld.GameRenderer;
import com.game.shield.gameworld.GameWorld;
import com.game.shield.helpers.InputHandler;

public class GameScreen implements Screen 
{	
	//-------------------------------------
	//Private fields

	private GameWorld world_;
	private GameRenderer renderer_;
	
	
	//-------------------------------------
	//Constructors, getters, setters
	
    
    public GameScreen() {
        Gdx.app.log("GameScreen", "Attached");
        float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = Gdx.graphics.getWidth();
		float gameHeight = Gdx.graphics.getHeight();
		int midPointX = (int) (gameWidth / 2);
		int midPointY = (int) (gameHeight / 2);
        world_ = new GameWorld(midPointX, midPointY);
        renderer_ = new GameRenderer(world_);
        
        Gdx.input.setInputProcessor(new InputHandler(world_));
    }
    
	//-------------------------------------
	//Other methods

    @Override
    public void render(float delta) {
        world_.update(delta);
        renderer_.render();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");     
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");        
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");       
    }

    @Override
    public void dispose() {
    }

}
