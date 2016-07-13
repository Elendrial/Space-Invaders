package me.hii488.spaceInvaders.containers;

import me.hii488.gameWorld.GeneralWorldContainer;
import me.hii488.gameWorld.World;

public class GameContainer extends GeneralWorldContainer{
	
	public void setup() {
		super.setup();
		this.alwaysUpdate = false;
		this.grid.setupGrid(World.mainWindow.width/16, World.mainWindow.height/16);
	}
	
}
