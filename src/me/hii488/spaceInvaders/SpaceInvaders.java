package me.hii488.spaceInvaders;

import me.hii488.gameWorld.World;
import me.hii488.general.Settings;

public class SpaceInvaders {
	
	public static void main(String[] args){
		Initilisation.gameSetup();
		Settings.WorldSettings.TargetTPS = 2200;
		World.startGame("Space Invader", 1000, 800);
		
		/*for(int i = 0; i < 62; i++){
			System.out.println();
			for(int j = 0; j < 50; j++){
				System.out.print((i + 62 * j) + ", ");
			}
		}*/
	}
	
}
