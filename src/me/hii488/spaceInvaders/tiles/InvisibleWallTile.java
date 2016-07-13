package me.hii488.spaceInvaders.tiles;

import me.hii488.objects.tileTypes.BaseTileType;

public class InvisibleWallTile extends BaseTileType{
	
	@Override
	public void setup() {
		this.textureName = "invisWall.png";
		this.zLayer = 0;
		this.isCollidable = true;
		super.setup();
	}
	
}
