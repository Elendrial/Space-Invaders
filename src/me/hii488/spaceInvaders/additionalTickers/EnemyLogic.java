package me.hii488.spaceInvaders.additionalTickers;

import java.awt.Rectangle;

import me.hii488.gameWorld.World;
import me.hii488.gameWorld.tickControl.ITickable;
import me.hii488.objects.entities.GeneralEmptyEntity;
import me.hii488.objects.entities.GeneralEntity;
import me.hii488.spaceInvaders.Initilisation;
import me.hii488.spaceInvaders.entities.EnemyShip;
import me.hii488.spaceInvaders.entities.GeneralEnemyEntity;
import me.hii488.spaceInvaders.entities.SpaceInvaderPlayer;
import me.hii488.spaceInvaders.entities.StandardEnemy;

public class EnemyLogic implements ITickable{

	@Override
	public boolean alwaysTicks() {return true;}

	@Override
	public float randTickChance() {return 0.00067f;}
	
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
				if(e instanceof StandardEnemy){
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
				if(e instanceof StandardEnemy){
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
		
		for(GeneralEntity ge : World.getCurrentWorldContainer().getEntities()){
			if(ge instanceof EnemyShip) ge.position.addToLocation((((EnemyShip)ge).movementState) * 5, 0);
			if(ge instanceof GeneralEmptyEntity) if(ge.position.getAbsY() > World.mainWindow.height - 100) {
				((SpaceInvaderPlayer)World.player).lives = 0;
				((SpaceInvaderPlayer)World.player).isShot(null);
			}
		}
		
		checkIfAbleToShoot();
	}

	public void checkIfAbleToShoot(){
		for(GeneralEntity e : World.getCurrentWorldContainer().getEntities()){
			if(e instanceof GeneralEnemyEntity) {
				if(World.getCurrentWorldContainer().getEntitiesInsideRect(new Rectangle(e.position.getX() + e.currentTexture.getWidth() / 2 - 2, e.position.getY() + e.currentTexture.getHeight(), 4, e.currentTexture.getHeight() * 6), false).isEmpty())  
					((GeneralEnemyEntity) e).canShoot = true;
				else ((GeneralEnemyEntity) e).canShoot = false;
			}
		}
	}
	
	@Override
	public void updateOnSec() {
		if(minSecWait > 0) minSecWait--;
	}

	public static int minSecWait = 0;
	
	@Override
	public void updateOnRandTick() {
		if(minSecWait <= 0){
			Initilisation.enemyShip.movementState = World.rand.nextBoolean() ? 1 : -1;
			Initilisation.enemyShip.position = Initilisation.empty.position.clone();
			Initilisation.enemyShip.position.setX(Initilisation.enemyShip.movementState == 1 ? 5 : (World.mainWindow.width-Initilisation.enemyShip.currentTexture.getWidth()));
			try{Initilisation.enemyShip.position.addToLocation(0, -32);}catch(Exception e){}
			World.getCurrentWorldContainer().addEntity(Initilisation.enemyShip.clone());
			minSecWait = 10;
		}
	}

}
