package com.game.shield.gameworld;

import com.badlogic.gdx.Gdx;
import com.game.shield.objects.Projectiles;
import com.game.shield.objects.Shield;
import com.game.shield.helpers.AssetLoader;;

public class GameWorld {
	
	//-------------------------------------
	//Fields
	
	private final int amountOfProjectiles_ = 20;
	private Shield shield_;
	private Projectiles projectiles_[];
	private int midPointX_;
	private int midPointY_;
	private int score_;
	private GameState currentState_;
	
	public enum GameState {
	    READY, RUNNING, GAMEOVER
	}
	
	//-------------------------------------
	//Constructors, getters, setters
	
	public GameWorld (int midPointX, int midPointY)
	{
		currentState_ = GameState.READY;
		projectiles_ = new Projectiles [amountOfProjectiles_];
		for( int i = 0; i < amountOfProjectiles_; i++)
		{
			projectiles_[i] = new Projectiles ();
		}
		shield_ = new Shield (60, 270, 50);
		midPointX_ = midPointX;
		midPointY_ = midPointY;
		score_ = 0;
	}
	
	public int getScore()
	{
		return score_;
	}
	
	public Shield getShield()
	{
		return shield_;
	}
	
	public int getAmOfProjectiles()
	{
		return amountOfProjectiles_;
	}
	
	public Projectiles[] getProjectiles()
	{
		return projectiles_;
	}
	
	public int getMidPointX()
	{
		return midPointX_;
	}
	
	public int getMidPointY()
	{
		return midPointY_;
	}
	
	public void setShield(Shield shield)
	{
		shield_ = shield;
	}
	
	public void setProjectiles(Projectiles projectiles[])
	{
		projectiles_ = projectiles;
	}
	
	public void setMidPointX(int x)
	{
		midPointX_ = x;
	}
	
	public void setMidPointY(int y)
	{
		midPointY_ = y;
	}
	
	public void setScore(int score)
	{
		score_ = score;
	}
	
	//-------------------------------------
	//Other methods
	
	//Returns TRUE if state of the game is RUNNING
	public boolean isRunning()
	{
		return currentState_ == GameState.RUNNING;
	}
	
	//Returns TRUE if state of the game is GAMEOVER
	public boolean isGameOver()
	{
		return currentState_ == GameState.GAMEOVER;
	}
	
	//Returns TRUE if state of the game is READY
	public boolean isReady()
	{
		return currentState_ == GameState.READY;
	}
	
	//Changes the state of the game to RUNNING
	public void start()
	{
		currentState_ = GameState.RUNNING;
	}
	
	//Restarts the game (surprise)
	//Changes the state of the game to RUNNING and resets all values which were changed during the game
	public void reStart()
	{
		start();
		for( int i = 0; i < amountOfProjectiles_; i++)
		{
			projectiles_[i] = new Projectiles ();
		}
		shield_ = new Shield (60, 270, 50);
		score_ = 0;
	}
	
	//Updates the game due to it current state
	public void update(float delta) 
	{
		switch (currentState_) 
		{
		case READY:
			updateReady(delta);
			break;
			
		case RUNNING:
			updateRunning(delta);
		break;
	    }
	}

	//Does nothing
	private void updateReady(float delta) 
	{
		
	}
	
	//Updates the game's world if the game is RUNNING
	public void updateRunning(float delta) 
	{
		//Updates projectiles
		for(int i = 0; i < amountOfProjectiles_; i++)
		{
			//Checks if this projectile in the  array exists
			//if no - creates it with some chance
			if(!projectiles_[i].getStatus())
			{
				if(Math.random()>=0.9995-((float)(score_+10)/50000))
				{
					projectiles_[i] = new Projectiles (midPointX_, midPointY_, Math.max(midPointX_*2, midPointY_*2));
				}
			}
			//if yes - moves it and checks the collision with the shield
			else
			{
				//Moves projectile
				projectiles_[i].moveProjectile(midPointX_, midPointY_, delta);
				//If it reaches the shield checks the collision with it
				if(projectiles_[i].distance(midPointY_, midPointX_) <= shield_.getRadius())
				{
					//If it hits the shield...
					if(projectiles_[i].isCollision(shield_.getAngle(), shield_.getCapacity()))
					{
						AssetLoader.collision_.play(); //sound of collision
						
						//Changes the capacity of the shield
						if(projectiles_[i].getValue()<0)
							shield_.changeCapacity(projectiles_[i].getValue()/2);
						else
							shield_.changeCapacity(projectiles_[i].getValue());
						
						projectiles_[i].setStatus(false); //destroyes the projectile
						score_++; //increases the score
						
					}
					// ... and if it does not hit the shield
					else
					{
						// If it was "healing" projectile - game continues, shield recovers
						if(projectiles_[i].getValue()>0)
						{
							shield_.changeCapacity(projectiles_[i].getValue()*2); //changes the capacity of the shield
							projectiles_[i].setStatus(false); //destroyes the projectile
							score_++; //increases the score
							AssetLoader.collision_.play(); //sound of collision
						}
						// If it was "damaging" - the game is over
						else
						{
							AssetLoader.gameOver_.play(); //sound of GameOver
							currentState_ = GameState.GAMEOVER; //changes the current game state to GAMEOVER
								
						}
					}
				}
			}
		}
    }
	

}
