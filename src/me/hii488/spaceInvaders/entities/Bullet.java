package me.hii488.spaceInvaders.entities;

import java.util.ArrayList;

import me.hii488.gameWorld.World;
import me.hii488.general.EntityHelper;
import me.hii488.general.Grid;
import me.hii488.objects.entities.GeneralEntity;
import me.hii488.spaceInvaders.Initilisation;
import me.hii488.spaceInvaders.tiles.BlockTile;
import me.hii488.spaceInvaders.tiles.InjuredBlockTile;

public class Bullet extends GeneralEntity{
	
	public int speed = 7;
	public GeneralEntity shooter;
	
	@Override
	public void setup() {
		textureName = "bullet.png";
		super.setup();
	}
	
	@Override
	public void updateOnTick() {
		this.position.addToLocation(0, -speed);
		super.updateOnTick();
		if(alive){
			ArrayList<GeneralEntity> collidingWith = EntityHelper.getCollidingEntities(this);
			for(GeneralEntity e : collidingWith){
				//System.out.println("Colliding with: " + e.getClass().getSimpleName());
				if(e instanceof GeneralEnemyEntity){
					((GeneralEnemyEntity) e).health -= 1;
					this.destroy();
					return;
				}
			}
			
			Grid g = World.getCurrentWorldContainer().grid;
			
			if(g.getTile(g.getGridPositionOn(this.position)).getTileType() instanceof InjuredBlockTile){
				g.setTileType(Initilisation.backgroundTile, g.getGridPositionOn(this.position));
				this.destroy();
			}
			
			if(g.getTile(g.getGridPositionOn(this.position)).getTileType() instanceof BlockTile){
				g.setTileType(Initilisation.injuredBlockTile, g.getGridPositionOn(this.position));
				this.destroy();
			}
			
		}
	}
	
}
