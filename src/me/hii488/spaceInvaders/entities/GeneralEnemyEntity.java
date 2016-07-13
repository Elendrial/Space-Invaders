package me.hii488.spaceInvaders.entities;

import me.hii488.objects.entities.GeneralEntity;

public class GeneralEnemyEntity extends GeneralEntity{
	
	public int health = 1;
	
	@Override
	public void updateOnTick(){
		super.updateOnTick();
		if(alive) if(health <= 0) this.destroy();
	}
	
}
