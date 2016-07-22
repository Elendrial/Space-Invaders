package me.hii488.spaceInvaders.entities;

import me.hii488.gameWorld.World;
import me.hii488.objects.entities.GeneralEntity;
import me.hii488.spaceInvaders.additionalTickers.GameController;

public class GeneralEnemyEntity extends GeneralEntity{
	
	public int health = 1;
	public boolean canShoot = false;
	
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
		if(notDestroyed && canShoot && GameController.gameState==1){
			Bullet b = new Bullet();
			b.currentState = (World.rand.nextFloat() < this.currentState/this.states) ? 3 : 1;
			b.setup();
			b.shooter = this;
			b.position = this.position.clone();
			b.position.addToLocation(this.currentTexture.getWidth()/2-2, this.currentTexture.getHeight() + 1);
			b.speed = -7- b.currentState * 3;
			World.getCurrentWorldContainer().addEntity(b);
		}
	}
	
	public GeneralEnemyEntity clone(){
		return new GeneralEnemyEntity(this);
	}
	
	public void isShot(Bullet b){}
	
}
