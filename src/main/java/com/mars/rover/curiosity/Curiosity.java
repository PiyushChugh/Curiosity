package com.mars.rover.curiosity;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains the main method.
 * @author Piyush Chugh
 *
 */
public class Curiosity {	
	
    public static void main( String[] args ) {
    	Scanner scanner = null;
		String message1 = null;
		String message2 = null;
		String message3 = null;
		String numberOfWordsForSatelliteCache = null;		
		InitializeCache initializeCache = new InitializeCache();
		TransferData transferData = new TransferData();
		
    	if(args.length > 0) {
    		// For local JUnit debugging, it becomes very quick, need not to type the messages each time    
    		// See CuriosityTest.java
    		initializeCache.initializeCacheForLocalTesting(args);
			message1 = args[3];
			message2 = args[4];
			message3 = args[5];
    	} else {
    		// Calling the main method the normal way
        	try {
        		scanner = new Scanner(System.in);
        		// The following methods initializes our fixed sized lists, based on user's input.
        		numberOfWordsForSatelliteCache = scanner.nextLine();
        		initializeCache.initializeSatellitesCache(numberOfWordsForSatelliteCache);    			
    			message1 = scanner.nextLine();
    			message2 = scanner.nextLine();
    			message3 = scanner.nextLine();
        	} catch (Exception e) {
        		System.out.println("Caught Exception:" + e.getMessage());
        	} finally {
        		scanner.close();
        	}
    	}
		
		List<String> listOfMessages = new ArrayList<String>();
		listOfMessages.add(message1);
		listOfMessages.add(message2);
		listOfMessages.add(message3);
		
		System.out.println();
		for(String message : listOfMessages) {
			System.out.println(message+","+transferData.getTime(message)+" hours");
		}
    }
}
