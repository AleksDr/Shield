package com.game.shield.objects;

import com.badlogic.gdx.math.Vector2;

public class Projectiles {

	//-------------------------------------
	//Private fields
	
	private int value_;
	private Vector2 position_;
	private Vector2 speed_;
	private float sinAngle_;
	private float cosAngle_;
	private float angle_;
	private boolean status_;
	
	//-------------------------------------
	//Constructors, getters, setters
	
	public Projectiles()
	{
		status_ = false;
	}
	
	public Projectiles(int midPointX, int midPointY, int radius)
	{
		status_ = true;
		if((int)(Math.random()*100)<=10)
			value_ = 12;
		else
			value_ = -(1 + (int)(Math.random() * 3))*2;
		
		angle_ = (float)Math.random()* 360;
		sinAngle_ = (float)Math.sin(angle_ * Math.PI / 180);
		cosAngle_ = (float)Math.cos(angle_ * Math.PI / 180);
		position_ = new Vector2 (midPointX + cosAngle_ * radius, midPointY + sinAngle_* radius);
		float temp = (float)Math.random() * 100 + 30;
		speed_ = new Vector2 (temp * cosAngle_, temp * sinAngle_); //1 + Math.random() * 5
	}
	
	public int getValue()
	{
		return value_;
	}
	
	public float getAngle()
	{
		return angle_;
	}
	
	public float getPositionX()
	{
		return position_.x;
	}
	
	public float getPositionY()
	{
		return position_.y;
	}
	
	public float getSpeedX()
	{
		return speed_.x;
	}
	
	public float getSpeedY()
	{
		return speed_.y;
	}
	
	public boolean getStatus()
	{
		return status_;
	}
	
	public void setAngle(float angle)
	{
		angle_ = angle;
	}
	
	public void setValue(int value)
	{
		value_ = value;
	}
	
	public void setPositionX(float positionX)
	{
		position_.x = positionX;
	}
	
	public void setPositionY(float positionY)
	{
		position_.y = positionY;
	}
	
	public void setSpeed(float x, float y)
	{
		speed_ = new Vector2 (x, y);
	}
	
	public void setStatus(boolean status)
	{
		status_ = status;
	}
	
	//-------------------------------------
	//Other methods
	
	//Returns distance between projectile and mid
	public float distance(float midPointY, float midPointX)
	{
		return (float)Math.sqrt ((midPointX-position_.x)*(midPointX-position_.x) + (midPointY-position_.y)*(midPointY-position_.y));
	}
	
	//Checks the collision with shield
	public boolean isCollision (float shieldAngle, float shieldCapacity)
	{
		if ((angle_ - shieldCapacity / 2 <= 0) && ((360 - angle_) >= (360 + (shieldAngle - shieldCapacity / 2))) && ((360 - angle_) <= 360))
			return true;
		else if((angle_ + shieldCapacity / 2 >= 360) && ((360 - angle_) <= (- 360 + shieldAngle + shieldCapacity / 2)) && ((360 - angle_) >= 0))
			return true;
		else if(((360 - angle_)>= (shieldAngle-shieldCapacity/2)) && ((360 - angle_) <= (shieldAngle+shieldCapacity/2)))
			return true;
		else 
			return false;
	}
	
	//Moves the point in the direction of mid
	public void moveProjectile (int midPointX, int midPointY, float delta)
	{
		if(position_.x < midPointX)
			position_.x += delta * Math.abs(speed_.x);
		else
			position_.x -= delta * Math.abs(speed_.x);
		
		if(position_.y < midPointY)
			position_.y += delta * Math.abs(speed_.y);
		else
			position_.y -= delta * Math.abs(speed_.y);
	}

}
