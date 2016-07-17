package me.hii488.spaceInvaders.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import me.hii488.gameWorld.World;
import me.hii488.general.Position;
import me.hii488.general.Vector;
import me.hii488.objects.entities.Player;
import me.hii488.spaceInvaders.Initilisation;
import me.hii488.spaceInvaders.additionalTickers.GameController;

public class SpaceInvaderPlayer extends Player{
	
	public int cooldown = 0;
	public int lives = 3;
	
	@Override
	public void setup() {
		this.textureName = "player.png";
		super.setup();
		this.speed = 5;
	}
	
	@Override
	public void onLoad(){
		this.position = World.getCurrentWorldContainer().grid.getPositionFromTileCoords(new Position(World.getCurrentWorldContainer().grid.gridSize[0]/2, World.getCurrentWorldContainer().grid.gridSize[1]-1));
	}
	
	@Override
	public void updateOnTick(){
		super.updateOnTick();
		if(cooldown > 0) cooldown -= 1;
		if(cooldown < 0) cooldown = 0;
	}
	
	@Override
	protected Vector allowedVector(Vector v){
		v.setDy(0);
		return super.allowedVector(v);
	}
	
	public void keyDown(KeyEvent arg0){
		super.keyDown(arg0);
		
		if(arg0.getKeyCode() == KeyEvent.VK_SPACE && cooldown == 0){
			Bullet b = new Bullet();
			b.shooter = this;
			b.position = this.position.clone();
			b.position.addToLocation(this.currentTexture.getWidth()/2, -this.currentTexture.getHeight());
			b.setup();
			World.getCurrentWorldContainer().addEntity(b);
			cooldown = 15;
		}
		
		Initilisation.gameController.keyDown(arg0);
	}
	
	public void render(Graphics g){
		if(GameController.gameState == 0){
			g.setColor(Color.WHITE);
			Font f = g.getFont();
			g.setFont(Font.decode(Font.MONOSPACED + "-24"));
			g.drawString("PRESS ANYTHING TO START", World.mainWindow.width/2-150, World.mainWindow.height/2-40);
			g.setFont(f);
		}
		
		if(GameController.gameState == 1){
			super.render(g);
			for(int i = 0; i < this.lives; i++)	g.drawImage(Initilisation.smallPlayerIcon, i*(Initilisation.smallPlayerIcon.getWidth() + 4) + 40, 8, null);
			g.setColor(Color.white);
			g.drawString("Lives: ", 5, 17);
			g.drawString("Score:" + GameController.score, 100, 17);
			g.setColor(Color.black);
		}
		
		if(GameController.gameState == 2){
			g.setColor(Color.WHITE);
			Font f = g.getFont();
			g.setFont(Font.decode(Font.MONOSPACED + "-24"));
			g.drawString("PRESS ANYTHING TO RESTART", World.mainWindow.width/2-170, World.mainWindow.height/2-120);
			g.drawString("SCORE: " + GameController.score, World.mainWindow.width/2-100, World.mainWindow.height/2-40);
			g.setFont(f);
		}
		
		if(World.isPaused){
			g.setColor(Color.WHITE);
			Font f = g.getFont();
			g.setFont(Font.decode(Font.MONOSPACED + "-24"));
			g.drawString("PRESS P TO UNPAUSE", World.mainWindow.width/2-150, World.mainWindow.height/2-40);
			g.setFont(f);
		}
		
	}
	
	public void isShot(Bullet b){
		this.lives--;
		if(lives == 0){
			GameController.gameState = 2;
			Initilisation.gameController.loadEmptyContainer();
			return;
		}
		else{
			this.position = World.getCurrentWorldContainer().grid.getPositionFromTileCoords(new Position(World.getCurrentWorldContainer().grid.gridSize[0]/2, World.getCurrentWorldContainer().grid.gridSize[1]-1));
		}
	}
	
}
