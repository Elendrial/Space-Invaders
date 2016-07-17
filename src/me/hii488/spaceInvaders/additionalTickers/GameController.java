package me.hii488.spaceInvaders.additionalTickers;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import me.hii488.gameWorld.World;
import me.hii488.gameWorld.tickControl.ITickable;
import me.hii488.general.Position;
import me.hii488.objects.entities.GeneralEntity;
import me.hii488.spaceInvaders.Initilisation;
import me.hii488.spaceInvaders.entities.GeneralEnemyEntity;

public class GameController implements ITickable{

	public static int gameState = 0; // 0 : opening, 1 : playing game, 2 : score screen
	public static int round = 1;
	public static int score = 0;
	
	@Override
	public boolean alwaysTicks() {return false;}

	@Override
	public float randTickChance() {return 0;}

	@Override
	public void updateOnTick() {
		if(gameState == 1){
			boolean enemiesStillAlive = false;
			for(GeneralEntity e : World.getCurrentWorldContainer().getEntitiesInsideRect(new Rectangle(0,0,World.mainWindow.width-1,World.mainWindow.height-1)))
				if(e instanceof GeneralEnemyEntity) enemiesStillAlive = true;
			if(!enemiesStillAlive){
				Initilisation.standardEnemy.currentState = 4;
				for(int i = 0; i < 2; i++){
					for(int j = 0; j < 15; j++){
						Initilisation.standardEnemy.position = new Position(50+j*(Initilisation.standardEnemy.currentTexture.getWidth() + 4), Initilisation.empty.position.getY() + i*(Initilisation.standardEnemy.currentTexture.getHeight() + 4));
						Initilisation.mainContainer.addEntity(Initilisation.standardEnemy.clone());
					}
				}
				
				Initilisation.standardEnemy.currentState = 2;
				for(int i = 0; i < 2; i++){
					for(int j = 0; j < 15; j++){
						Initilisation.standardEnemy.position = new Position(50+j*(Initilisation.standardEnemy.currentTexture.getWidth() + 4), Initilisation.empty.position.getY() + 40 + i*(Initilisation.standardEnemy.currentTexture.getHeight() + 4));
						Initilisation.mainContainer.addEntity(Initilisation.standardEnemy.clone());
					}
				}
				
				Initilisation.standardEnemy.currentState = 0;
				for(int i = 0; i < 2; i++){
					for(int j = 0; j < 15; j++){
						Initilisation.standardEnemy.position = new Position(50+j*(Initilisation.standardEnemy.currentTexture.getWidth() + 4), Initilisation.empty.position.getY() + 80 + i*(Initilisation.standardEnemy.currentTexture.getHeight() + 4));
						Initilisation.mainContainer.addEntity(Initilisation.standardEnemy.clone());
					}
				}
			}
		}
	}

	@Override
	public void updateOnSec() {}

	@Override
	public void updateOnRandTick() {}

	// Inputs come from the player class, as then I don't have to implement all of the key stuff.
	public void keyDown(KeyEvent e){
		if(gameState == 0) {
			gameState++;
			loadGameContainer();
		}
		
		if(gameState == 1 && e.getKeyCode() == KeyEvent.VK_P){
			World.isPaused = !World.isPaused;
		}
	}
	
	public void loadGameContainer(){
		World.Containers.loadNewContainer(Initilisation.mainContainer);
	}
	
	public void loadEmptyContainer(){
		World.Containers.loadNewContainer(Initilisation.emptyContainer);
	}
	
}
