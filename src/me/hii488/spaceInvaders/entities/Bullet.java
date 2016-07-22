package me.hii488.spaceInvaders.entities;

import java.util.ArrayList;

import me.hii488.gameWorld.World;
import me.hii488.gameWorld.baseTypes.Grid;
import me.hii488.general.Position;
import me.hii488.helpers.EntityHelper;
import me.hii488.objects.entities.GeneralEntity;
import me.hii488.spaceInvaders.Initilisation;
import me.hii488.spaceInvaders.tiles.BlockTile;
import me.hii488.spaceInvaders.tiles.InjuredBlockTile;

public class Bullet extends GeneralEntity{
	
	public int speed = 13;
	public GeneralEntity shooter;
	
	@Override
	public void setup() {
		textureName = "bullet.png";
		this.states = 4;
		super.setup();
	}
	
	public int ticksUntilTextureSwitch = 0;
	
	@Override
	public void updateOnTick() {
		this.position.addToLocation(0, -speed);
		super.updateOnTick();
		if(notDestroyed){
			ArrayList<GeneralEntity> collidingWith = EntityHelper.getCollidingEntities(this);
			for(GeneralEntity e : collidingWith){
				if(e instanceof GeneralEnemyEntity){
					((GeneralEnemyEntity) e).isShot(this);
					if(notDestroyed)this.destroy();
				}
				if(e instanceof SpaceInvaderPlayer){
					((SpaceInvaderPlayer) e).isShot(this);
					if(notDestroyed)this.destroy();
				}
			}
			
			Grid g = World.getCurrentWorldContainer().grid;
			
			Position p = this.position.clone();
			p.addToLocation(-6, 0);
			
			
			if(g.getTile(g.getGridPositionOn(p)).getTileType() instanceof InjuredBlockTile){
				g.setTileType(Initilisation.backgroundTile, g.getGridPositionOn(p));
				this.destroy();
			}
			
			if(g.getTile(g.getGridPositionOn(p)).getTileType() instanceof BlockTile){
				g.setTileType(Initilisation.injuredBlockTile, g.getGridPositionOn(p));
				this.destroy();
			}
			
			
			ticksUntilTextureSwitch--; // Ternary operators weren't working here for some reason :/
			if(ticksUntilTextureSwitch <= 0) {
				switch(currentState){
				case 1 : currentState = 2; break;
				case 2 : currentState = 1; break;
				case 3 : currentState = 4; break;
				case 4 : currentState = 3; break;
				}
				ticksUntilTextureSwitch = 7;
			}
			
		}
	}
	
}
