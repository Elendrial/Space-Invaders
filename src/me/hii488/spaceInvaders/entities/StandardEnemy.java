package me.hii488.spaceInvaders.entities;

import me.hii488.gameWorld.World;

public class StandardEnemy extends GeneralEnemyEntity{
	
	public StandardEnemy(){}
	
	public StandardEnemy(GeneralEnemyEntity e) {
		super(e);
	}

	@Override
	public void setup() {
		textureName = "standardEnemy.png";
		this.health = 1;
		this.randTickChance = 0.00125f;
		super.setup();
	}
	
	@Override
	public void updateOnTick(){
		super.updateOnTick();
	}
	
	@Override
	public void updateOnRandTick(){
		//System.out.println("randomTick");
		if(notDestroyed){
			Bullet b = new Bullet();
			b.shooter = this;
			b.position = this.position.clone();
			b.position.addToLocation(this.textureImage.getWidth()/2-2, this.textureImage.getHeight() + 1);
			b.setup();
			b.speed = -b.speed;
			World.getCurrentWorldContainer().addEntity(b);
		}
	}
	
	public StandardEnemy clone(){
		return new StandardEnemy(this);
	}
	
}
