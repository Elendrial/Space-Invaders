package me.hii488.spaceInvaders.tiles;

import me.hii488.objects.tileTypes.BaseTileType;

public class BackgroundTile extends BaseTileType {

	@Override
	public void setup() {
		this.textureName = "blackbackground.png";
		this.zLayer = 0;
		this.isCollidable = false;
		super.setup();
	}

}
