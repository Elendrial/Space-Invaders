package me.hii488.spaceInvaders.containers;

import me.hii488.gameWorld.World;
import me.hii488.gameWorld.baseTypes.GeneralWorldContainer;

public class GameContainer extends GeneralWorldContainer{
	
	public void setup() {
		super.setup();
		this.alwaysTicks = false;
		this.grid.setupGrid(World.mainWindow.width/16, World.mainWindow.height/16);
	}
	
}
