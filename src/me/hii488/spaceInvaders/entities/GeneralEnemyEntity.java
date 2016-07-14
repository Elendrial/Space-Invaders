package me.hii488.spaceInvaders.entities;

import java.awt.Rectangle;

import me.hii488.objects.entities.GeneralEntity;

public class GeneralEnemyEntity extends GeneralEntity{
	
	public int health = 1;
	
	public GeneralEnemyEntity(){}
	
	public GeneralEnemyEntity(GeneralEnemyEntity e) {
		super(e);
		this.health = e.health;
	}

	@Override
	public void updateOnTick(){
		super.updateOnTick();
		collisionBox = new Rectangle(position.getX()-1,position.getY(),textureImage.getWidth()+2, textureImage.getHeight());
		if(notDestroyed) if(health <= 0) this.destroy();
	}
	
	public GeneralEnemyEntity clone(){
		return new GeneralEnemyEntity(this);
	}
	
}
