package me.hii488.spaceInvaders.entities;

import java.awt.event.KeyEvent;

import me.hii488.gameWorld.World;
import me.hii488.general.Position;
import me.hii488.general.Vector;
import me.hii488.objects.entities.Player;

public class SpaceInvaderPlayer extends Player{
	
	public int cooldown = 0;
	
	@Override
	public void setup() {
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
			b.position.addToLocation(this.textureImage.getWidth()/2, -this.textureImage.getHeight());
			b.setup();
			World.getCurrentWorldContainer().addEntity(b);
			cooldown = 30;
		}
		
	}
	
}
