package me.hii488.spaceInvaders.entities;

import me.hii488.gameWorld.World;
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
		if(notDestroyed) if(health <= 0) this.destroy();
	}
	
	@Override
	public void updateOnRandTick(){
		if(notDestroyed){
			Bullet b = new Bullet();
			b.currentState = 1;
			b.setup();
			b.shooter = this;
			b.position = this.position.clone();
			b.position.addToLocation(this.currentTexture.getWidth()/2-2, this.currentTexture.getHeight() + 1);
			b.speed = -b.speed;
			World.getCurrentWorldContainer().addEntity(b);
		}
	}
	
	public GeneralEnemyEntity clone(){
		return new GeneralEnemyEntity(this);
	}
	
}
