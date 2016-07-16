package me.hii488.spaceInvaders.additionalTickers;

import me.hii488.gameWorld.World;
import me.hii488.gameWorld.tickControl.ITickable;
import me.hii488.objects.entities.GeneralEntity;
import me.hii488.spaceInvaders.entities.GeneralEnemyEntity;

public class EnemyLogic implements ITickable{

	@Override
	public boolean alwaysTicks() {return true;}

	@Override
	public float randTickChance() {return 0;}
	
	public int currentMovementState = 0;
	public boolean movementStateUpdatedInTick = false;
	public int ticksUntilMovement = 20;
	
	@Override
	public void updateOnTick() {
		if(ticksUntilMovement <= 0){
			movementStateUpdatedInTick = false;
			for(GeneralEntity e : World.getCurrentWorldContainer().getEntities()){
				if(e instanceof GeneralEnemyEntity){
					if(!movementStateUpdatedInTick){
						if(World.getCurrentWorldContainer().grid.getGridPositionOn(e.position).getX() >= World.getCurrentWorldContainer().grid.gridSize[0]-1){
							currentMovementState = currentMovementState == 1 ? 2 : 1;
							movementStateUpdatedInTick = true;
						}
						if(World.getCurrentWorldContainer().grid.getGridPositionOn(e.position).getX() <= 1){
							currentMovementState = currentMovementState == 3 ? 0 : 3;
							movementStateUpdatedInTick = true;
						}
					}
				}
			}
			
			for(GeneralEntity e : World.getCurrentWorldContainer().getEntities()){
				if(e instanceof GeneralEnemyEntity){
					switch(currentMovementState){
					case 0:
						e.position.addToLocation(e.currentTexture.getWidth() + 4, 0);
						break;
					case 1:
						e.position.addToLocation(0, e.currentTexture.getHeight() + 4);
						break;
					case 2:
						e.position.addToLocation(-e.currentTexture.getWidth() + 4, 0);
						break;
					case 3:
						e.position.addToLocation(0, e.currentTexture.getHeight() + 4);
						break;
					}
				}
			}
			ticksUntilMovement = 20;
		}
		ticksUntilMovement--;
	}

	@Override
	public void updateOnSec() {}

	@Override
	public void updateOnRandTick() {}

}
