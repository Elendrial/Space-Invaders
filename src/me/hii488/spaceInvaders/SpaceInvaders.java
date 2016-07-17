package me.hii488.spaceInvaders;

import me.hii488.gameWorld.World;
import me.hii488.general.Settings;

public class SpaceInvaders {
	
	public static void main(String[] args){
		Initilisation.gameSetup();
		Settings.WorldSettings.TargetTPS = 1000;
		World.startGame("Space Invader", 1000, 800);
	}
	
}
