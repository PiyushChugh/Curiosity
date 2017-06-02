package com.mars.rover.curiosity;

import java.util.Arrays;
import java.util.List;

/**
 * This class is used to initialize fixed sized lists based on user's input.
 * @author Piyush Chugh
 *
 */
public class InitializeCache {
	
	public static List<WordOccurrence> satellite1Cache;
	public static List<WordOccurrence> satellite2Cache;
	public static List<WordOccurrence> satellite3Cache;
	
	/**
	 * This method takes input from the user and initializes the lists.
	 * Also handles the exceptions in case user enters any invalid data.
	 * @param numberOfWordsForSatelliteCache
	 */
	public void initializeSatellitesCache(String numberOfWordsForSatelliteCache) {
		String [] wordsStoredPerSatellite = numberOfWordsForSatelliteCache.split(",");
		parseWordsPerSatelliteAndInitializeCache(wordsStoredPerSatellite);
	}

	/**
	 * This method is used particularly in case of local JUnit testing.
	 * It doesn't ask for input from users, nor does it add any business value, but this fastens the debugging process as 
	 * you need not type the messages each time you want to debug the code.
	 * @param args
	 */
	public void initializeCacheForLocalTesting(String[] args) {
		parseWordsPerSatelliteAndInitializeCache(args);
	}   
	
	/**
	 * This method parses the string to integers to get number of words to be cached per satellite.
	 * Also handles exceptions.
	 * @param wordsStoredPerSatellite
	 */
	private void parseWordsPerSatelliteAndInitializeCache(String [] wordsStoredPerSatellite) {
		Integer satellite1CacheLimit = 0;
		Integer satellite2CacheLimit = 0;
		Integer satellite3CacheLimit = 0;
		
		if(wordsStoredPerSatellite.length != 3) {
			try {
				throw new Exception();
			} catch (Exception e) {
				System.out.println("Please enter Memory Size for all 3 satellites! Use format: N1,N2,N3");
				System.exit(0);
			}
		}
		
		try {
			satellite1CacheLimit = Integer.parseInt(wordsStoredPerSatellite[0]);
			satellite2CacheLimit = Integer.parseInt(wordsStoredPerSatellite[1]);
			satellite3CacheLimit = Integer.parseInt(wordsStoredPerSatellite[2]);
		} catch (NumberFormatException e) {
			System.out.println("Please enter valid numbers!");
			System.exit(0);
		}		
		initializeCache(satellite1CacheLimit, satellite2CacheLimit, satellite3CacheLimit);
	}
	
	/**
	 * This method initializes fixed sized lists and handles exceptions.
	 * @param satellite1CacheLimit
	 * @param satellite2CacheLimit
	 * @param satellite3CacheLimit
	 */
	private void initializeCache(Integer satellite1CacheLimit, Integer satellite2CacheLimit, Integer satellite3CacheLimit) {
		if(satellite1CacheLimit < 0 || satellite2CacheLimit < 0 || satellite3CacheLimit < 0) {
			try {
				throw new Exception();
			} catch (Exception e) {
				System.out.println("Negative number for Memory Size not allowed. Please try again!");
				System.exit(0);
			}
		}
		
		// Creating fixed size lists for 3 satellites
		satellite1Cache = Arrays.asList(new WordOccurrence[satellite1CacheLimit]);
		satellite2Cache = Arrays.asList(new WordOccurrence[satellite2CacheLimit]);
		satellite3Cache = Arrays.asList(new WordOccurrence[satellite3CacheLimit]);
	}
	
}
