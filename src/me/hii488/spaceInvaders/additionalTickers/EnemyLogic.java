package me.hii488.spaceInvaders.additionalTickers;

import java.awt.Rectangle;

import me.hii488.gameWorld.World;
import me.hii488.gameWorld.tickControl.ITickable;
import me.hii488.objects.entities.GeneralEntity;
import me.hii488.spaceInvaders.Initilisation;
import me.hii488.spaceInvaders.entities.GeneralEnemyEntity;

public class EnemyLogic implements ITickable{

	@Override
	public boolean alwaysTicks() {return true;}

	@Override
	public float randTickChance() {return 0;}
	
	public int currentMovementState = 0;
	public int ticksInState = 0;
	public boolean movementStateUpdatedInTick = false;
	public int ticksUntilMovement = 20;
	
	@Override
	public void updateOnTick() {
		if(ticksUntilMovement <= 0){
			ticksInState++;
			movementStateUpdatedInTick = false;
			for(GeneralEntity e : World.getCurrentWorldContainer().getEntities()){
				if(e instanceof GeneralEnemyEntity){
					if(!movementStateUpdatedInTick){
						if(World.getCurrentWorldContainer().grid.getGridPositionOn(e.position).getX() >= World.getCurrentWorldContainer().grid.gridSize[0]-2){
							currentMovementState = currentMovementState == 1 ? 2 : 1;
							movementStateUpdatedInTick = true;
							ticksInState = 0;
						}
						if(World.getCurrentWorldContainer().grid.getGridPositionOn(e.position).getX() <= 1){
							currentMovementState = currentMovementState == 3 ? 0 : 3;
							movementStateUpdatedInTick = true;
							ticksInState = 0;
						}
					}
				}
			}
			
			if(ticksInState == 1 && (currentMovementState == 1 || currentMovementState == 3)){
				currentMovementState = currentMovementState == 1 ? 2 : 0;
				ticksInState = 0;
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
			switch(currentMovementState){
			case 1:
				Initilisation.empty.position.addToLocation(0, Initilisation.standardEnemy.currentTexture.getHeight() + 4);
				break;
			case 3:
				Initilisation.empty.position.addToLocation(0, Initilisation.standardEnemy.currentTexture.getHeight() + 4);
				break;
			}
			
			ticksUntilMovement = (int) (20 / (GameController.round));
			
		}
		ticksUntilMovement--;
	}

	@Override
	public void updateOnSec() {
		// No need to put shooting in updateOnTick(), as it doesn't happen frequently enough.
		for(GeneralEntity e : World.getCurrentWorldContainer().getEntities()){
		//	e.showCollisionBox = true;
			if(e instanceof GeneralEnemyEntity) {
				if(World.getCurrentWorldContainer().getEntitiesInsideRect(new Rectangle(e.position.getX() + e.currentTexture.getWidth() / 2 - 2, e.position.getY() + e.currentTexture.getHeight(), 4, e.currentTexture.getHeight() * 6), false).isEmpty())  
					((GeneralEnemyEntity) e).canShoot = true;
				else ((GeneralEnemyEntity) e).canShoot = false;
			}
			
		//	e.showCollisionBox = false;
		}
	}

	@Override
	public void updateOnRandTick() {}

}
