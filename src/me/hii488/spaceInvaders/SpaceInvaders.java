package me.hii488.spaceInvaders;

import me.hii488.gameWorld.World;
import me.hii488.general.Settings;
import me.hii488.spaceInvaders.entities.SpaceInvaderAIPlayer;

public class SpaceInvaders {
	
	public static void main(String[] args){
		argParser(args);
		
		Initilisation.gameSetup();
		
		Settings.Logging.tpsPrinter = false;
		
		World.startGame("Space Invader", 1000, 800);
		/*for(int i = 0; i < 62; i++){
			System.out.println();
			for(int j = 0; j < 50; j++){
				System.out.print((i + 62 * j) + ", ");
			}
		}*/
	}
	
	public static void argParser(String[] args){
		if(args.length != 0){
			if(args[0].toLowerCase().equals("-ai")){
				SpaceInvaderAIPlayer p = new SpaceInvaderAIPlayer();
				Settings.WorldSettings.TargetTPS = 2500;
				for(int i = 1; i < args.length; i+=2){
					switch(args[i]){
					case "?":
						System.out.println("\nPossible Arguments:");
						System.out.println("-------------------");
						System.out.println("\n '?'\t\t\t-\tPrints out all possible arguments following the '-ai' tag");
						System.out.println("\n 'poolSize n'\t\t-\tSets the poolSize for each generation to 'n'.");
						System.out.println("\n 'topCarriedForward n'\t-\tSets the amount of children carried forward from each previous generation to 'n'. (Higher fitness carried over first).");
						System.out.println("\n 'geneMutationChance f'\t-\tSets the chance of a random gene mutation to 'f'");
						System.out.println("\n 'cutOffThreshhold f'\t-\tSets the lowest value at which an output will \"fire\" to 'f'.");
						System.out.println("\n 'hiddenLayerSizes [n,n...n,n]'\t-\tSets the amounts of nodes in the neural net's hidden layers. (Amount of hidden layers is not limited.)");
						System.out.println("\n Note: 'n' arguments must be integers, and 'f' arguements floats less than 1.");
						System.out.println("\n");
						System.exit(0);
						break;
					case "poolSize":
						try{p.lI.settings.generationSettings.childrenPerGeneration = Integer.parseInt(args[i+1]);}
						catch(Exception e){System.err.println("Error with arg " + i + ":"); e.printStackTrace();}
						break;
					case "topCarriedForward":
						try{p.lI.settings.generationSettings.additionalTopChildrenKept = Integer.parseInt(args[i+1]);}
						catch(Exception e){System.err.println("Error with arg " + i + ":"); e.printStackTrace();}
						break;
					case "geneMutationChance":
						if(Float.parseFloat(args[i+1])<= 1)try{p.lI.settings.generationSettings.mutationChance = Float.parseFloat(args[i+1]);}
							catch(Exception e){System.err.println("Error with arg " + i + ":"); e.printStackTrace();}
						else System.out.println("Gene Mutation Chance must be below or equal to 1.");
						break;
					case "cutOffThreshhold":
						if(Float.parseFloat(args[i+1]) < 1)try{p.lI.settings.neuralSettings.cutoffThreshhold = Float.parseFloat(args[i+1]);}
							catch(Exception e){System.err.println("Error with arg " + i + ":"); e.printStackTrace();}
						else System.out.println("The Cutoff Threshhold must be below 1.");
						break;
					case "hiddenLayerSizes":
						if(args[i+1].startsWith("[") && args[i+1].endsWith("]")){
							String s = args[i+1].substring(1, args[i+1].length()-2);
							int[] layers = new int[s.split(",").length + 2];
							layers[0] = 0;
							for(int j = 0; j < s.split(",").length; j++){
								layers[j+1] = Integer.parseInt(s.split(",")[j]);
							}
							layers[layers.length-1] = 3;
						}
						break;
					default:
						System.out.println("Arg " + i + " is unrecognised.");
						i--;
						break;
					}
				}
				Initilisation.p = p;
			}
			else{
				System.err.println("Arg '0' invalid, please use '-ai' to enable AI, and '-ai ?' to list all possible args.");
			}
		}
		else{
			Settings.WorldSettings.TargetTPS = 30;
		}
	}
	
}
