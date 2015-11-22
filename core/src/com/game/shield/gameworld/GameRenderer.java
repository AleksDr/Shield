package com.game.shield.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.game.shield.objects.Projectiles;
import com.game.shield.helpers.AssetLoader;
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
	
	
	public void render() 
	{
       //Sets back color to white
        Gdx.gl.glClearColor(1, 1, 1, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //Draws game objects if the game is running
        if(world_.isRunning())
        {
        	batcher.begin();
            String score = "score: " + world_.getScore() + "";
            AssetLoader.font_.draw(batcher, score, 15, 15);
            batcher.end();
            
            //It's duct tape code, will be fixed later
            //Draws 2 arcs - inner (color of the back) and outer (blue color)
        	shapeRenderer.begin(ShapeType.Filled);
        	shapeRenderer.setColor(0, 0, 1, 0);
        	shapeRenderer.arc(world_.getMidPointX(), world_.getMidPointY(), world_.getShield().getRadius(), 360 - world_.getShield().getAngle() - world_.getShield().getCapacity()/2, world_.getShield().getCapacity(), 20);
        	shapeRenderer.end();

        	shapeRenderer.begin(ShapeType.Filled);
        	shapeRenderer.setColor(1, 1, 1, 0);
        	shapeRenderer.arc(world_.getMidPointX(), world_.getMidPointY(), world_.getShield().getRadius()-5, (360 - world_.getShield().getAngle()) - world_.getShield().getCapacity()/2, world_.getShield().getCapacity(), 20);
        	shapeRenderer.end();
        	
        	//Draws projectiles
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
        			shapeRenderer.circle(projectiles_[i].getPositionX(), projectiles_[i].getPositionY(), Math.abs((float)projectiles_[i].getValue()*2), 60);
        			shapeRenderer.end();
        		}
        	}
        }
        
        batcher.begin();
        //Draws the message "Touch me" at the start of the application
        if (world_.isReady()) 
        {
            AssetLoader.font_.draw(batcher, "Touch me", world_.getMidPointX()-65, world_.getMidPointY());
        } 
        //Draws the messages "Game over" and "Try again" if the user lost
        else if (world_.isGameOver())
        {
        	AssetLoader.font_.draw(batcher, "Game Over", world_.getMidPointX()-75, world_.getMidPointY()-15);
        	AssetLoader.font_.draw(batcher, "Try again?", world_.getMidPointX()-70, world_.getMidPointY()+15);    
        }
        batcher.end();


    }
}
