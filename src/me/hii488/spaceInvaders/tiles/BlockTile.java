package me.hii488.spaceInvaders.tiles;

import me.hii488.objects.tileTypes.BaseTileType;

public class BlockTile extends BaseTileType {

	@Override
	public void setup() {

		this.isCollidable = true;
		this.textureName = "block.png";
		this.zLayer = 0;

		super.setup();
	}

}
