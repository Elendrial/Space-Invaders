package me.hii488.spaceInvaders.entities;

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
			ticksUntilTextureSwitch = 10;
		}
		ticksUntilTextureSwitch--;
	}
	
	public StandardEnemy clone(){
		return new StandardEnemy(this);
	}
	
}
