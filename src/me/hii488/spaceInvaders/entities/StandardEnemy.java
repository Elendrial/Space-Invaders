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
		super.setup();
	}
	
	@Override
	public void updateOnTick(){
		super.updateOnTick();
		
		if(notDestroyed){
			if(World.rand.nextFloat() < (1/(4*60))){
				Bullet b = new Bullet();
				b.shooter = this;
				b.position = this.position.clone();
				b.position.addToLocation(0, this.textureImage.getHeight()+1);
				b.setup();
				b.speed = -b.speed;
				World.getCurrentWorldContainer().entities.add(b);
			}
		}
	}
	
	public StandardEnemy clone(){
		return new StandardEnemy(this);
	}
	
}
