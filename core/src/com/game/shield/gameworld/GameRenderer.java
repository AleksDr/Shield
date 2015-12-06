package com.game.shield.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.game.shield.objects.Projectiles;
import com.game.shield.helpers.AssetLoader;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameRenderer 
{
	
	//-------------------------------------
	//Private fields
	
	private GameWorld world_;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch batcher;
	//Game objects
	private Projectiles projectiles_[];
	
	//-------------------------------------
	//Constructors, getters, setters
	
	public GameRenderer(GameWorld world)
	{
		world_ = world;
		cam = new OrthographicCamera();
		cam.setToOrtho(true, world.getMidPointX()*2, world.getMidPointY()*2);
		batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);
		projectiles_ = world.getProjectiles();
	}
	
	//-------------------------------------
	//Other methods
	
	public void renderShield()
	{
		 //Draws circle
    	shapeRenderer.begin(ShapeType.Line);
    	shapeRenderer.setColor(125/255f, 125/255f, 125/255f, 0);
    	shapeRenderer.circle(world_.getMidPointX(), world_.getMidPointY()+20, world_.getShield().getRadius(), 100);
    	shapeRenderer.end();
        
        //It's duct tape code, will be fixed later
        //Draws 2 arcs - inner (color of the back) and outer (blue color)
    	shapeRenderer.begin(ShapeType.Filled);
    	shapeRenderer.setColor(0, 0, 1, 0);
    	shapeRenderer.arc(world_.getMidPointX(), world_.getMidPointY()+20, world_.getShield().getRadius(), 360 - world_.getShield().getAngle() - world_.getShield().getCapacity()/2, world_.getShield().getCapacity(), 100);
    	shapeRenderer.end();

    	shapeRenderer.begin(ShapeType.Filled);
    	shapeRenderer.setColor(1, 1, 1, 0);
    	shapeRenderer.circle(world_.getMidPointX(), world_.getMidPointY()+20, world_.getShield().getRadius()-5, 100);
    	shapeRenderer.end();
	}
	
	public void renderScore()
	{
    	shapeRenderer.begin(ShapeType.Line);
    	shapeRenderer.setColor(0, 0, 1, 0);
    	shapeRenderer.rect(0, 0, world_.getMidPointX(), 40);
    	shapeRenderer.end();
        batcher.begin();
        String score = "score: " + world_.getScore() + "";
        AssetLoader.font_.draw(batcher, score, 15, 10);
        batcher.end();
	}
	
	public void renderSoundButton()
	{
		shapeRenderer.begin(ShapeType.Line);
    	shapeRenderer.setColor(0, 0, 1, 0);
    	shapeRenderer.rect(world_.getMidPointX(), 0, world_.getMidPointX(), 40);
    	shapeRenderer.end();
        batcher.begin();
        String line;
        if(world_.getSound())
        	line = "SFX: on";
        else
        	line = "SFX: off";
        AssetLoader.font_.draw(batcher, line, world_.getMidPointX() + 15, 10);
        batcher.end();
	}
	
	public void renderProjectiles()
	{
		for(int i = 0; i<world_.getAmOfProjectiles(); i++)
    	{
    		if(projectiles_[i].getStatus())
    		{
    			shapeRenderer.begin(ShapeType.Filled);
    			//Sets the color of projectile dut to its effect(value)
    			if(projectiles_[i].getValue()>0)
    				shapeRenderer.setColor(51 / 255.0f, 232 / 255.0f, 0/ 255.0f, 0);
    			else
    				shapeRenderer.setColor(0, 0, 0, 0);
    			//Draw projectiles
    			shapeRenderer.circle(projectiles_[i].getPositionX(), projectiles_[i].getPositionY(), Math.abs((float)projectiles_[i].getValue()), 60);
    			shapeRenderer.end();
    		}
    	}
	}
	
	public void renderGameOverScreen()
	{
		batcher.begin();
    	AssetLoader.font_.draw(batcher, "Game Over", world_.getMidPointX(), world_.getMidPointY()-15, 0, 1, false);
    	AssetLoader.font_.draw(batcher, "Try again?", world_.getMidPointX(), world_.getMidPointY()+15, 0, 1, false);
        batcher.end();
	}
	
	public void renderHighScore()
	{
		batcher.begin();
        String string = "New high score:";
    	AssetLoader.font_.draw(batcher, string, world_.getMidPointX(), world_.getMidPointY()-45, 0, 1, false);
    	string = world_.getScore() + "";
    	AssetLoader.font_.draw(batcher, string, world_.getMidPointX(), world_.getMidPointY()-15, 0, 1, false);
    	string = "Try again?";
    	AssetLoader.font_.draw(batcher, string, world_.getMidPointX(), world_.getMidPointY()+15, 0, 1, false);
        batcher.end();
	}
	
	public void render() 
	{
       //Sets back color to white
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        	
        renderScore();
        renderSoundButton();
        if(world_.isRunning())
        {
        	renderShield();
        	renderProjectiles();
        }
        
        //Draws the message "Touch me" at the start of the application
        if (world_.isReady()) 
        {
        	batcher.begin();
            AssetLoader.font_.draw(batcher, "Touch me", world_.getMidPointX(), world_.getMidPointY(), 0, 1, false);
            batcher.end();
        } 
        //Draws the messages "Game over" and "Try again" if the user lost
        else if (world_.isGameOver())
        {
        	renderGameOverScreen();
        }
        else if (world_.isHighScore())
        {
        	renderHighScore();
        }


    }
}
