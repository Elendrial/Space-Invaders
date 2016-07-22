package me.hii488.spaceInvaders.entities;

import me.hii488.objects.entities.Player;
import me.hii488.spaceInvaders.additionalTickers.GameController;

public class EnemyShip extends GeneralEnemyEntity{
	
	public int movementState = 1; // 1 = left to right, -1 = right to left 
	
	public EnemyShip(){}
	
	public EnemyShip(EnemyShip e) {
		super(e);
		this.movementState = e.movementState;
	}

	@Override
	public void setup() {
		textureName = "shipEnemy.png";
		this.health = 1;
		super.setup();
	}
	
	@Override
	public void updateOnTick(){
		super.updateOnTick();
		
	}
	
	public EnemyShip clone(){
		return new EnemyShip(this);
	}
	
	public void isShot(Bullet b){
		health--;
		if(b.shooter instanceof Player){
			GameController.score += (1 + movementState) * 125 - movementState * this.position.getX() * 0.2;
		}
	}
	
}
