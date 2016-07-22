package me.hii488.spaceInvaders.entities;

import me.hii488.objects.entities.Player;
import me.hii488.spaceInvaders.additionalTickers.GameController;

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
		this.states = 5;
		super.setup();
	}
	
	public int ticksUntilTextureSwitch = 9;
	
	@Override
	public void updateOnTick(){
		super.updateOnTick();
		// Ternary operators weren't working here for some reason :/
		if(ticksUntilTextureSwitch <= 0) {
			switch(currentState){
			case 0 : currentState = 1; break;
			case 1 : currentState = 0; break;
			case 2 : currentState = 3; break;
			case 3 : currentState = 2; break;
			case 4 : currentState = 5; break;
			case 5 : currentState = 4; break;
			}
			ticksUntilTextureSwitch = 10 / GameController.round;
		}
		ticksUntilTextureSwitch--;
	}
	
	public StandardEnemy clone(){
		return new StandardEnemy(this);
	}
	
	public void isShot(Bullet b){
		health--;
		if(b.shooter instanceof Player){
			switch(currentState){
			case 0:
			case 1:
				GameController.score += 10;
				break;
			case 2:
			case 3:
				GameController.score += 20;
				break;
			case 4:
			case 5:
				GameController.score += 50;
				break;
			}
		}
	}
	
}
