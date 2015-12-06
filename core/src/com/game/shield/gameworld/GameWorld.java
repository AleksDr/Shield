package com.game.shield.gameworld;

import com.game.shield.objects.Projectiles;
import com.game.shield.objects.Shield;
import com.game.shield.helpers.AssetLoader;

public class GameWorld {
	
	//-------------------------------------
	//Fields
	
	private final int AMOUNT_OF_PROJECTILES = 20;
	private Shield shield_;
	private Projectiles projectiles_[];
	private int midPointX_;
	private int midPointY_;
	private int score_;
	private GameState currentState_;
	private boolean sound_;
	
	public enum GameState 
	{
	    READY, RUNNING, GAMEOVER, HIGHSCORE
	}
	
	
	//-------------------------------------
	//Constructors, getters, setters
	
	public GameWorld (int midPointX, int midPointY)
	{
		currentState_ = GameState.READY;
		projectiles_ = new Projectiles [AMOUNT_OF_PROJECTILES];
		for( int i = 0; i < AMOUNT_OF_PROJECTILES; i++)
		{
			projectiles_[i] = new Projectiles ();
		}
		shield_ = new Shield (AssetLoader.shieldCapacity_, 270, 50);
		midPointX_ = midPointX;
		midPointY_ = midPointY;
		score_ = 0;
		sound_ = true;
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
		return AMOUNT_OF_PROJECTILES;
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
	
	public boolean getSound()
	{
		return sound_;
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
	
	public void getSound(boolean sound)
	{
		sound_ = sound;
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
	
	//Returns TRUE if state of the game is HIGHSCORE
	public boolean isHighScore() {
	    return currentState_ == GameState.HIGHSCORE;
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
		for( int i = 0; i < AMOUNT_OF_PROJECTILES; i++)
		{
			projectiles_[i].restartDefault();
		}
		shield_ = new Shield (AssetLoader.shieldCapacity_, 270, 50);
		score_ = 0;
	}
	
	//Changes the state of the game to RUNNING
	public void soundSwitch()
	{
		if(sound_==false)
			sound_ = true;
		else
			sound_ = false;
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
		default:
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
		for(int i = 0; i < AMOUNT_OF_PROJECTILES; i++)
		{
			//Checks if this projectile in the  array exists
			//if no - creates it with some chance
			if(!projectiles_[i].getStatus())
			{
				if(Math.random()>=0.9995-((float)(score_+10)/50000))
				{
					projectiles_[i].restart(midPointX_, midPointY_+20, Math.max(midPointX_*2, (midPointY_+20)*2));
				}
			}
			//if yes - moves it and checks the collision with the shield
			else
			{
				//Moves projectile
				projectiles_[i].moveProjectile(midPointX_, midPointY_+20, delta);
				//If it reaches the shield checks the collision with it
				if(projectiles_[i].distance(midPointX_, midPointY_+20) <= shield_.getRadius())
				{
					//If it hits the shield...
					if(projectiles_[i].isCollision(shield_.getAngle(), shield_.getCapacity()))
					{
						if(sound_)
						AssetLoader.collision_.play(); //sound of collision
						
						//Changes the capacity of the shield
						if(projectiles_[i].getValue()<0)
							shield_.changeCapacity(projectiles_[i].getValue()/2);
						else
							shield_.changeCapacity((float)(projectiles_[i].getValue()*1.5));
						
						projectiles_[i].setStatus(false); //destroyes the projectile
						score_++; //increases the score
						
					}
					// ... and if it does not hit the shield
					else
					{
						// If it was "healing" projectile - game continues, shield recovers
						if(projectiles_[i].getValue()>0)
						{
							shield_.changeCapacity(projectiles_[i].getValue()*3); //changes the capacity of the shield
							projectiles_[i].setStatus(false); //destroyes the projectile
							score_++; //increases the score
							if(sound_)
							AssetLoader.collision_.play(); //sound of collision
						}
						// If it was "damaging" - the game is over
						else
						{
							if(sound_)
							AssetLoader.gameOver_.play(); //sound of GameOver
							if (score_ > AssetLoader.getHighScore()) //changes the current game state to HIGHSCORE if score_ > highscore
							{
				                AssetLoader.setHighScore(score_);
				                currentState_ = GameState.HIGHSCORE;
				            }
							else
							currentState_ = GameState.GAMEOVER; //changes the current game state to GAMEOVER
								
						}
					}
				}
			}
		}
    }
	

}
