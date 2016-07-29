package me.hii488.spaceInvaders;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import me.hii488.gameWorld.World;
import me.hii488.gameWorld.Initialisation.IInitilisation;
import me.hii488.gameWorld.Initialisation.WorldInitialisation;
import me.hii488.gameWorld.baseTypes.GeneralWorldContainer;
import me.hii488.gameWorld.tickControl.TickController;
import me.hii488.general.Position;
import me.hii488.general.Settings;
import me.hii488.helpers.TextureHelper;
import me.hii488.objects.entities.GeneralEmptyEntity;
import me.hii488.spaceInvaders.additionalTickers.EnemyLogic;
import me.hii488.spaceInvaders.additionalTickers.GameController;
import me.hii488.spaceInvaders.containers.GameContainer;
import me.hii488.spaceInvaders.entities.EnemyShip;
import me.hii488.spaceInvaders.entities.SpaceInvaderAIPlayer;
import me.hii488.spaceInvaders.entities.SpaceInvaderPlayer;
import me.hii488.spaceInvaders.entities.StandardEnemy;
import me.hii488.spaceInvaders.tiles.BackgroundTile;
import me.hii488.spaceInvaders.tiles.BlockTile;
import me.hii488.spaceInvaders.tiles.InjuredBlockTile;
import me.hii488.spaceInvaders.tiles.InvisibleWallTile;

public class Initilisation implements IInitilisation{

	public static Initilisation initClass = new Initilisation();
	public static GameController gameController = new GameController();
	public static EnemyLogic enemyLogic = new EnemyLogic();
	
	public static BufferedImage smallPlayerIcon;
	
	public static void gameSetup(){
		WorldInitialisation.initList.add(initClass);
		TickController.additionalEarlyTicking.add(gameController);
		TickController.additionalEarlyTicking.add(enemyLogic);
		
		try {
			smallPlayerIcon = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\textures\\entities\\playersmall.png"));
		} catch (Exception e) {
			try {
				TextureHelper.TextureNotFoundPrint("playersmall.png", Initilisation.class);
				smallPlayerIcon = ImageIO.read(new File(System.getProperty("user.dir") + "\\resources\\" + Settings.defaultEntityTextureLocation));
			} catch (Exception e1) {
				TextureHelper.TextureNotFoundPrint(Settings.defaultEntityTextureLocation, Initilisation.class);
			}
		}
		
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
	public static GameContainer emptyContainer = new GameContainer();
	
	public void containerPreInit(){
		mainContainerPreInit();
		emptyContainerPreInit();
	}
	
	public void mainContainerPreInit(){
		this.mainContainerPreInit(mainContainer);
	}
	
	public void mainContainerPreInit(GeneralWorldContainer gwc){
		gwc.setup();
		// BASIC BACKGROUDN
		gwc.grid.fillRectWithTileType(backgroundTile, 0, 0, gwc.grid.gridSize[0], gwc.grid.gridSize[1]);
		// TO STOP PLAYER GOING OFF EDGE OF MAP
		gwc.grid.setTileType(invisWallTile, gwc.grid.gridSize[0]-1, gwc.grid.gridSize[1]-1);
		gwc.grid.setTileType(invisWallTile, 0, gwc.grid.gridSize[1]-1);
		
		// MAKING WALLS/COVER
		int amount = (int) Math.floor(gwc.grid.gridSize[0]/10);
		int shift = (gwc.grid.gridSize[0]%10)/2;
		
		for(int i = 0; i < amount; i++){
			for(int j = 2; j < 8; j++){
				gwc.grid.setTileType(blockTile, i*10 + j + shift, gwc.grid.gridSize[1] - 4);
			}
			
			for(int j = 3; j < 7; j++){
				gwc.grid.setTileType(blockTile, i*10 + j + shift, gwc.grid.gridSize[1] - 5);
			}
			
			gwc.grid.setTileType(blockTile, i*10 + 2 + shift, gwc.grid.gridSize[1] - 3);
			gwc.grid.setTileType(blockTile, i*10 + 7 + shift, gwc.grid.gridSize[1] - 3);
		}

		if(!World.Containers.containerExists(gwc.ID))World.Containers.addContainer(gwc);
	}
	
	public void emptyContainerPreInit(){
		emptyContainer.setup();
		emptyContainer.grid.fillRectWithTileType(backgroundTile, 0, 0, mainContainer.grid.gridSize[0], mainContainer.grid.gridSize[1]);
		
		if(!World.Containers.containerExists(emptyContainer.ID))World.Containers.addContainer(emptyContainer);
	}
	
	
	public static StandardEnemy standardEnemy;
	public static GeneralEmptyEntity empty;
	public static EnemyShip enemyShip;
	
	public void entityPreInit(){
		standardEnemy = new StandardEnemy();
		standardEnemy.setup();
		
		empty = new GeneralEmptyEntity();
		empty.setup();
		
		enemyShip = new EnemyShip();
		enemyShip.setup();
	}
	
	public void entityInit(){
		this.entityInit(mainContainer);
	}
	
	public void entityInit(GeneralWorldContainer gwc){
		standardEnemy.currentState = 4;
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 15; j++){
				standardEnemy.position = new Position(50+j*(standardEnemy.currentTexture.getWidth() + 4), 50 + i*(standardEnemy.currentTexture.getHeight() + 4));
				gwc.addEntity(standardEnemy.clone());
			}
		}
		
		empty.position = standardEnemy.position.clone();
		empty.position.addToLocation(0, -standardEnemy.currentTexture.getHeight());
		gwc.addEntity(empty);
		
		standardEnemy.currentState = 2;
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 15; j++){
				standardEnemy.position = new Position(50+j*(standardEnemy.currentTexture.getWidth() + 4), 90 + i*(standardEnemy.currentTexture.getHeight() + 4));
				gwc.addEntity(standardEnemy.clone());
			}
		}
		
		standardEnemy.currentState = 0;
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 15; j++){
				standardEnemy.position = new Position(50+j*(standardEnemy.currentTexture.getWidth() + 4), 130 + i*(standardEnemy.currentTexture.getHeight() + 4));
				gwc.addEntity(standardEnemy.clone());
			}
		}
	}
	
	public static boolean AI = false;
	
	public void worldPreInit(){
		World.player = AI ? new SpaceInvaderAIPlayer() : new SpaceInvaderPlayer();
	}
	
	public void worldInit(){
		World.Containers.loadNewContainer(emptyContainer.ID);
	}

}
