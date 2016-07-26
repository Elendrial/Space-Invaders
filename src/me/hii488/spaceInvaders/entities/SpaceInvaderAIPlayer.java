package me.hii488.spaceInvaders.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import me.hii488.LearningIntelligence;
import me.hii488.gameWorld.World;
import me.hii488.gameWorld.baseTypes.Grid;
import me.hii488.general.Position;
import me.hii488.general.Settings;
import me.hii488.objects.entities.GeneralEntity;
import me.hii488.spaceInvaders.Initilisation;
import me.hii488.spaceInvaders.additionalTickers.GameController;

public class SpaceInvaderAIPlayer extends SpaceInvaderPlayer{
	
	public int cooldown = 0;
	public int lives = 3;
	
	public LearningIntelligence lI = new LearningIntelligence();
	
	@Override
	public void setup() {
		super.setup();
		
		lI.settings.generationSettings.childrenPerGeneration = 50;
		lI.settings.generationSettings.additionalTopChildrenKept = 10;
		lI.settings.generationSettings.mutationChance = 0.025f;
		lI.settings.neuralSettings.inputs = World.getCurrentWorldContainer().grid.gridSize[0] * World.getCurrentWorldContainer().grid.gridSize[1];
		lI.settings.neuralSettings.outputs = new String[]{"l", "r", "s"};
		lI.settings.neuralSettings.nodesPerLayer = new int[]{lI.settings.neuralSettings.inputs, 100, 30, 3};
		lI.settings.neuralSettings.cutoffThreshhold = 0.5f;
		
		lI.initialSetup();
	}
	
	@Override
	public void onLoad(){
		this.position = World.getCurrentWorldContainer().grid.getPositionFromTileCoords(new Position(World.getCurrentWorldContainer().grid.gridSize[0]/2, World.getCurrentWorldContainer().grid.gridSize[1]-1));
	}
	
	public int count = 100;
	
	@Override
	public void updateOnTick(){
		if(GameController.gameState == 1){
			getInputs();
			super.updateOnTick();
			count--;
			if(count <=0){
				count = 100;
				GameController.score += 1;
			}
		}
		if(GameController.gameState == 0){
			Initilisation.gameController.keyDown(null);
		}
		if(GameController.gameState == 2){
			lI.setFitness(GameController.score);
			lI.nextSpecies();
			Initilisation.gameController.keyDown(null);
		}
		
		if(cooldown > 0) cooldown--;
	}
	
	public float[] inputClone = new float[0];
	
	@SuppressWarnings("unused")
	public void getInputs(){
		float[] f = new float[lI.settings.neuralSettings.inputs];
		for(float f1 : f) f1 = 0;
		
		Grid g = World.getCurrentWorldContainer().grid;
		for(int i = 0; i < g.gridSize[0]; i++){
			for(int j = 0; j < g.gridSize[1]; j++){
				if(g.getTile(i, j).getTileType().isCollidable) f[i + g.gridSize[0] * j] = 3;
//				for(GeneralEntity e : EntityHelper.getEntitiesIntersectingWithRect(new Rectangle(i * 16, j * 16, 16 ,16))) f[i + g.gridSize[0] * j] -= 2;
			}
		}
		
		try { for(GeneralEntity e : World.getCurrentWorldContainer().getEntities()){
			Position p = g.getGridPositionOn(e.position);
			if(e instanceof StandardEnemy) f[p.getX() + g.gridSize[0] * p.getY()] = -2;
			if(e instanceof EnemyShip) f[p.getX() + g.gridSize[0] * p.getY()] = -4;
			if(e instanceof Bullet && e.currentState !=0) f[p.getX() + g.gridSize[0] * p.getY()] = -1;
			if(e instanceof Bullet && e.currentState ==0) f[p.getX() + g.gridSize[0] * p.getY()] = 1;
			if(e instanceof SpaceInvaderAIPlayer) f[p.getX() + g.gridSize[0] * p.getY()] = 5;
		}}catch(Exception e){}
		
		inputClone = f.clone();
		
		String[] s = lI.getOutputs(f);
	//	System.out.println(Arrays.toString(s));
		
		queuedMovement.setVector(0, 0);
		for(String str : s){
			switch(str){
			case "l":
				queuedMovement.addtoDx(-speed);
				break;
			case "r":
				queuedMovement.addtoDx(speed);
				break;
			case "s":
				if(cooldown <= 0){
					Bullet b = new Bullet();
					b.shooter = this;
					b.position = this.position.clone();
					b.position.addToLocation(this.currentTexture.getWidth()/2, -this.currentTexture.getHeight());
					b.setup();
					World.getCurrentWorldContainer().addEntity(b);
					cooldown = 15;
				}
				break;
			}
		}
	}
	
	public void keyDown(KeyEvent arg0){
		if(arg0.getKeyCode() == KeyEvent.VK_P) Settings.WorldSettings.TargetTPS += 100;
		if(arg0.getKeyCode() == KeyEvent.VK_M) Settings.WorldSettings.TargetTPS -= 100;
	}
	
	public void render(Graphics g){
		super.render(g);
		
		Color c = g.getColor();
		g.setColor(Color.white);
		g.drawString("Generation: " + lI.generation, 5, World.mainWindow.height - 5);
		g.drawString("Species: " + lI.speciesNumber, 100, World.mainWindow.height - 5);
		g.drawString("Top Fitness: " + lI.highestFitness, 200, World.mainWindow.height - 5);
/*
		try{
			Grid grid = World.getCurrentWorldContainer().grid;
			for(int i = 0; i < grid.gridSize[0]; i++){
				for(int j = 0; j < grid.gridSize[1]; j++){
					float f = inputClone[i + grid.gridSize[0] * j];
					g.setColor(f > 0 ? new Color(0f,1f,0f,.5f ) : (f == 0 ? new Color(0f,0f,0f,.5f ) : new Color(1f,0f,0f,.5f )));
					g.drawRect(i * 16, j * 16, 16, 16);
				}
			}
		}catch(Exception e){}*/
		g.setColor(c);
	}
	
}
