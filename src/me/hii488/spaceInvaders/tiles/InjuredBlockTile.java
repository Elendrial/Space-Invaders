package me.hii488.spaceInvaders.tiles;

import me.hii488.objects.tileTypes.BaseTileType;

public class InjuredBlockTile extends BaseTileType {

	@Override
	public void setup() {

		this.isCollidable = true;
		this.textureName = "injuredBlock.png";
		this.zLayer = 0;

		super.setup();
	}

}
