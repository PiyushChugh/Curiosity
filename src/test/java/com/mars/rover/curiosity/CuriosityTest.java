package com.mars.rover.curiosity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * This class contains various cases that verify different scenarios of caching.
 * @author Piyush Chugh
 *
 */
public class CuriosityTest {
	
	TransferData transferData = new TransferData();
	InitializeCache initializeCache = new InitializeCache();
	
	@Test
    public void testScenarioOne() {
		String message1 = "found a stone";
		String message2 = "some unknown creature drinking water";
		String message3 = "found air and water near some creature";
		String [] numberOfWords = {"2","3","1"};		
		initializeCache.initializeCacheForLocalTesting(numberOfWords);
		assertEquals(Integer.valueOf(156),transferData.getTime(message1));
		assertEquals(Integer.valueOf(432),transferData.getTime(message2));
		assertEquals(Integer.valueOf(431),transferData.getTime(message3));
    }
	
	@Test
    public void testScenarioTwo() {
		String message1 = "hi hi hi";
		String message2 = "ab bye bye";
		String message3 = "hi hi bye";
		String [] numberOfWords = {"1","1","1"};		
		initializeCache.initializeCacheForLocalTesting(numberOfWords);
		assertEquals(Integer.valueOf(96),transferData.getTime(message1));
		assertEquals(Integer.valueOf(120),transferData.getTime(message2));
		assertEquals(Integer.valueOf(82),transferData.getTime(message3));
    }
	
	@Test
    public void testScenarioThree() {
		String message1 = "ab ab ab bc cdedef";
		String message2 = "cd cd";
		String message3 = "cd";
		String [] numberOfWords = {"1","1","1"};		
		initializeCache.initializeCacheForLocalTesting(numberOfWords);
		assertEquals(Integer.valueOf(216),transferData.getTime(message1));
		assertEquals(Integer.valueOf(60),transferData.getTime(message2));
		assertEquals(Integer.valueOf(24),transferData.getTime(message3));
    }
	
	@Test
    public void testScenarioFour() {
		String message1 = "ab ab ab";
		String message2 = "ab";
		String message3 = "ab";
		String [] numberOfWords = {"1","1","1"};		
		initializeCache.initializeCacheForLocalTesting(numberOfWords);
		assertEquals(Integer.valueOf(96),transferData.getTime(message1));
		assertEquals(Integer.valueOf(22),transferData.getTime(message2));
		assertEquals(Integer.valueOf(22),transferData.getTime(message3));
    }
	
	@Test
    public void testScenarioFive() {
		String message1 = "ab ab ab";
		String message2 = "bc bc";
		String message3 = "bc ab";
		String [] numberOfWords = {"1","1","1"};		
		initializeCache.initializeCacheForLocalTesting(numberOfWords);
		assertEquals(Integer.valueOf(96),transferData.getTime(message1));
		assertEquals(Integer.valueOf(60),transferData.getTime(message2));
		assertEquals(Integer.valueOf(57),transferData.getTime(message3));
    }
	
	@Test
    public void testScenarioSix() {
		String message1 = "ab ab ab";
		String message2 = "bc ab bc";
		String message3 = "ab";
		String [] numberOfWords = {"1","1","1"};		
		initializeCache.initializeCacheForLocalTesting(numberOfWords);
		assertEquals(Integer.valueOf(96),transferData.getTime(message1));
		assertEquals(Integer.valueOf(93),transferData.getTime(message2));
		assertEquals(Integer.valueOf(22),transferData.getTime(message3));
    }
	
	@Test
    public void testScenarioSeven() {
		String message1 = "aaa aaa";
		String message2 = "aaa aaa";
		String message3 = "aaa aaa";
		String [] numberOfWords = {"0","0","0"};		
		initializeCache.initializeCacheForLocalTesting(numberOfWords);
		assertEquals(Integer.valueOf(84),transferData.getTime(message1));
		assertEquals(Integer.valueOf(84),transferData.getTime(message2));
		assertEquals(Integer.valueOf(84),transferData.getTime(message3));
    }
	
	@Test
    public void testScenarioEight() {
		String message1 = "aa aaa aaaa";
		String message2 = "bb bbb bbbb";
		String message3 = "aa aaa aaaa";
		String [] numberOfWords = {"2","2","2"};		
		initializeCache.initializeCacheForLocalTesting(numberOfWords);
		assertEquals(Integer.valueOf(132),transferData.getTime(message1));
		assertEquals(Integer.valueOf(132),transferData.getTime(message2));
		assertEquals(Integer.valueOf(84),transferData.getTime(message3));
    }
	
	@Test
    public void testScenarioNine() {
		String message1 = "hi hi hi";
		String message2 = "hi bye";
		String message3 = "hi";
		String [] numberOfWords = {"1","1","1"};		
		initializeCache.initializeCacheForLocalTesting(numberOfWords);
		assertEquals(Integer.valueOf(96),transferData.getTime(message1));
		assertEquals(Integer.valueOf(69),transferData.getTime(message2));
		assertEquals(Integer.valueOf(22),transferData.getTime(message3));
    }
	
}
