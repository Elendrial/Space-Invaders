package me.hii488.spaceInvaders;

import me.hii488.gameWorld.World;

public class SpaceInvaders {
	
	public static void main(String[] args){
		Initilisation.gameSetup();
		
		World.startGame("Space Invader", 1000, 800);
	}
	
}
