package me.hii488.spaceInvaders;

import me.hii488.gameWorld.World;
import me.hii488.gameWorld.Initialisation.IInitilisation;
import me.hii488.gameWorld.Initialisation.WorldInitialisation;
import me.hii488.general.Position;
import me.hii488.spaceInvaders.containers.GameContainer;
import me.hii488.spaceInvaders.entities.SpaceInvaderPlayer;
import me.hii488.spaceInvaders.entities.StandardEnemy;
import me.hii488.spaceInvaders.tiles.BackgroundTile;
import me.hii488.spaceInvaders.tiles.BlockTile;
import me.hii488.spaceInvaders.tiles.InjuredBlockTile;
import me.hii488.spaceInvaders.tiles.InvisibleWallTile;

public class Initilisation implements IInitilisation{

	public static Initilisation initClass = new Initilisation();
	
	public static void gameSetup(){
		WorldInitialisation.initList.add(initClass);
	}
	
	@Override
	public void preInit() {
		tilePreInit();
		containerPreInit();
		entityPreInit();
		worldPreInit();
	}
	
	@Override
	public void init() {
		worldInit();
		entityInit();
	}
	
	
	public static BackgroundTile backgroundTile;
	public static BlockTile blockTile;
	public static InjuredBlockTile injuredBlockTile;
	public static InvisibleWallTile invisWallTile;
	
	public void tilePreInit(){
		backgroundTile = new BackgroundTile();
		backgroundTile.setup();
		
		blockTile = new BlockTile();
		blockTile.setup();
		
		injuredBlockTile = new InjuredBlockTile();
		injuredBlockTile.setup();
		
		invisWallTile = new InvisibleWallTile();
		invisWallTile.setup();
	}
	
	
	
	public static GameContainer mainContainer = new GameContainer();
	
	public void containerPreInit(){
		mainContainer.setup();
		// BASIC BACKGROUDN
		mainContainer.grid.fillRectWithTileType(backgroundTile, 0, 0, mainContainer.grid.gridSize[0], mainContainer.grid.gridSize[1]);
		// TO STOP PLAYER GOING OFF EDGE OF MAP
		mainContainer.grid.setTileType(invisWallTile, mainContainer.grid.gridSize[0]-1, mainContainer.grid.gridSize[1]-1);
		mainContainer.grid.setTileType(invisWallTile, 0, mainContainer.grid.gridSize[1]-1);
		
		// MAKING WALLS/COVER
		int amount = (int) Math.floor(mainContainer.grid.gridSize[0]/10);
		int shift = (mainContainer.grid.gridSize[0]%10)/2;
		
		for(int i = 0; i < amount; i++){
			for(int j = 2; j < 8; j++){
				mainContainer.grid.setTileType(blockTile, i*10 + j + shift, mainContainer.grid.gridSize[1] - 4);
			}
			
			for(int j = 3; j < 7; j++){
				mainContainer.grid.setTileType(blockTile, i*10 + j + shift, mainContainer.grid.gridSize[1] - 5);
			}
			
			mainContainer.grid.setTileType(blockTile, i*10 + 2 + shift, mainContainer.grid.gridSize[1] - 3);
			mainContainer.grid.setTileType(blockTile, i*10 + 7 + shift, mainContainer.grid.gridSize[1] - 3);
		}
		
	}
	
	
	public static StandardEnemy standardEnemy;
	
	public void entityPreInit(){
		standardEnemy = new StandardEnemy();
		standardEnemy.setup();
	}
	
	public void entityInit(){
		standardEnemy.position = mainContainer.grid.getPositionFromTileCoords(new Position(mainContainer.grid.gridSize[0]/2, 3));
		mainContainer.entities.add(standardEnemy);
	}
	
	
	
	public void worldPreInit(){
		World.player = new SpaceInvaderPlayer();
	}
	
	public void worldInit(){
		World.Containers.addContainer(mainContainer);
		World.Containers.loadNewContainer(mainContainer.ID);
	}

}
