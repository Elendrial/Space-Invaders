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
		super.setup();
	}
	
	@Override
	public void updateOnTick(){
		super.updateOnTick();
	}
	
	public StandardEnemy clone(){
		return new StandardEnemy(this);
	}
	
}
