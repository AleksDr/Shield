package com.game.shield.objects;

public class Shield {
	
	//-------------------------------------
	//Private fields
	
	private float capacity_;
	private float angle_;
	private float radius_;
	
	//Constants for the min and max shield capacity
	private final int minCapacity_ = 5;
	private final int maxCapacity_ = 180;
	
	//-------------------------------------
	//Constructors, getters, setters
	
	public Shield(float capacity, float angle, float radius)
	{
		capacity_ = capacity;
		angle_ = angle;
		radius_ = radius;
	}
	
	public float getCapacity()
	{
		return capacity_;
	}
	
	public float getRadius()
	{
		return radius_;
	}
	
	public float getAngle()
	{
		return angle_;
	}
	
	public void setRadius(float radius)
	{
		radius_ = radius;
	}
	
	public void setCapacity(float capacity)
	{
		capacity_ = capacity;
	}
	
	public void setAngle(float angle)
	{
		angle_ = angle;
	}
	
	//-------------------------------------
	//Other methods
	
	//Reduces(increases) the capacity of the shield
	public void changeCapacity(float dCapacity)
	{
		capacity_ += dCapacity;
		if(capacity_ < minCapacity_)
			capacity_ = minCapacity_;
		if(capacity_ > maxCapacity_)
			capacity_ = maxCapacity_;
			
	}
	
	//Reduces(increases) the angle of the shield
	public void changeAngle(float dAngle)
	{
		angle_ = (angle_ + dAngle) % 360;
		
	}
	
	//Changing the direction of shield due to your click
	public void onClick(float x, float y, int midPointX, int midPointY)
	{
		float temp = (float)Math.sqrt((midPointX-x)*(midPointX-x) + (midPointY-y)*(midPointY-y));
		if(y <= midPointY)
		{
			if(x >= midPointX)
			{
				angle_ = (float)((Math.abs(Math.asin((midPointY - y) / temp)) * 180 / Math.PI));
			}
			else
			{
				angle_ = 180 - (float)((Math.abs(Math.asin((midPointY - y) / temp))) * 180 / Math.PI);
			}
		}
		else
		{
			if(x >= midPointX)
			{
				angle_ = 180 + (float)((Math.abs(Math.acos((midPointX - x) / temp))) * 180 / Math.PI);
			}
			else
			{
				angle_ = 180 + (float)((Math.abs(Math.asin((midPointY - y) / temp))) * 180 / Math.PI);
			}
		}


	}

}
