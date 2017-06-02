package com.mars.rover.curiosity;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * This class is responsible for transferring the actual data, implementing least used algorithm for caching and
 * calculating the total time in hours for a message.
 * @author Piyush Chugh
 *
 */
public class TransferData {
	
	private static final Integer SATELLITE1 = 2;
	private static final Integer SATELLITE2 = 3;
	private static final Integer SATELLITE3 = 4;
	private static Integer IS_EMPTY = 0;
	
	/**
	 * This is the first method of this class that can be called to get the time taken by a message to travel from
	 * Curiosity to NASA.
	 * @param message
	 * @return
	 */
    public Integer getTime(String message) {
    	Integer time = 0;
    	// The following variable is utilized in case when at least satellite 1's words that can be cached = 0
    	IS_EMPTY = 0;
    	time = transfer(message,InitializeCache.satellite1Cache,SATELLITE1,time);
    	time = transfer(message,InitializeCache.satellite2Cache,SATELLITE2,time);
    	time = transfer(message,InitializeCache.satellite3Cache,SATELLITE3,time);
    	return time += message.length(); // For Satellite 3 to NASA
    }
	
	 /**
     * This master method coordinates among all other methods to transfer the actual message and return the time in hours.
     * It handles various scenarios, like if a word is in cache or it needs to be added to a set for later comparison based 
     * on Least Frequently Used algorithm.
     * @param message
     * @param cache
     * @param wordLength
     * @param time
     * @return
     */
    private Integer transfer(String message, List<WordOccurrence> cache, Integer wordLength, Integer time) {	
    	String [] wordsOfMessage = message.split(" ");
    	String messageEdited = message;
    	boolean hasMessageChanged = false;
    	boolean cacheHasNoSpaceButNeedToDeleteElement = false;
    	boolean isWordAlreadyPresentInCache = false;
    	int count = 0;
    	Set<WordOccurrence> wordsToBeCached = new LinkedHashSet<WordOccurrence>();
    	boolean sameLine = false;
    	for(String word : wordsOfMessage) {
    		// The following boolean is used because if same lettered word comes again, we won't be sending it.
    		// It will be saved for comparison later, based on least frequently used algorithm.
    		boolean addedInThisIteration = false;
    		if(word.length() == wordLength) {     
    			cacheHasNoSpaceButNeedToDeleteElement = true;
    			for(int i = 0; i < cache.size() ; i++) {
    				if(cache.get(i) != null && word.equals(cache.get(i).getWord())) {    	
    					// This condition is used if an cacheable entry is coming again, we're increasing its occurrence count
    					isWordAlreadyPresentInCache = true;
    					cacheHasNoSpaceButNeedToDeleteElement = false;
    					cache.set(i, new WordOccurrence(word, cache.get(i).getOccurrence()+1));
    					break;
    				}
    			    if(cache.get(i) == null && !isWordAlreadyPresentInCache) {
    			    	// Here a new cacheable entry is added
    			    	addedInThisIteration = true;
    			    	sameLine = true;
    			    	cacheHasNoSpaceButNeedToDeleteElement = false;
    			        cache.set(i, new WordOccurrence(word,0));
    			        break;
    			    }
    			    if(!isWordAlreadyPresentInCache && !addedInThisIteration) {
    			    	// If words with same no. of letters(2/3/4) come up, but cache is not free, then 2nd entry won't be added to it now.
    			    	// It will be saved for comparison later, based on least frequently used algorithm.
    			    	savePotentialCacheableWord(wordsToBeCached, word);    			    	
    			    }
    			}
			}
    		
    		if(CollectionUtils.isNotEmpty(cache) && cache.get(0) != null) {
    			// This is used to calculate time if a cached word is encountered
    			// Notice if the word in cache was added in the same line/same iteration, it won't be affecting the time in that case
    			// Will be used next line onwards.
    			for(int i = 0; i < cache.size() && cache.get(i) != null; i++) {
	    			if(word.equals(cache.get(i).getWord()) && !addedInThisIteration && !sameLine) {
	    				hasMessageChanged = true;
	    				messageEdited = editMessage(message, messageEdited, word);
	    				count++;
	    				break;
	    			}
	    			// increment the occurrence, for least frequently used
	    			if(addedInThisIteration && cache.get(i).getWord().equals(word)) {
	    				cache.set(i, new WordOccurrence(word, cache.get(i).getOccurrence()+1));
	    				break;
	    			}
	    		}
    		}
		}
    	    	
		removeLeastUsedWordsInCache(cache, cacheHasNoSpaceButNeedToDeleteElement, wordsToBeCached, checkIfCacheHasSpace(cache));
    	
		if(cache.equals(InitializeCache.satellite1Cache) && IS_EMPTY == 0) {
			if(InitializeCache.satellite1Cache.size() == 0) {
				// This variable is particularly used in case when number of words to be stored on satellite1(and (satellite2 or satellite3)) is 0
				// So that this 'if' explicitly fails and control goes to the else part, to get message.length * 5
				IS_EMPTY++;
			}
			time = calculateTimeForCuriosityToSatellite1Transfer(message, time, messageEdited, hasMessageChanged,count);
		} else {
			time = calculateTimeForTransferBetweenSatellites(message, time, messageEdited, hasMessageChanged, count);
		}
    	return time;    	
    }

	/**
	 * This method adds a word to a set for later use.
	 * This set is later on used in Least Frequently algorithm to determine if a word needs to be added to satellite's cache or not,
	 * based on its number of occurrences.
	 * @param wordsToBeCached
	 * @param word
	 */
	private void savePotentialCacheableWord(Set<WordOccurrence> wordsToBeCached, String word) {
		boolean increaseOccurrence = false;
		WordOccurrence potentialWordToBeCached = null;
		for(WordOccurrence wordcache : wordsToBeCached) {
			if(word.equals(wordcache)) {
				increaseOccurrence = true;
				potentialWordToBeCached = wordcache;
			}
		}
		if(increaseOccurrence) {
			// Same word that was added in cache in the same line, occurs again, hence we need to increase its occurrence.
			wordsToBeCached.remove(potentialWordToBeCached);
			wordsToBeCached.add(new WordOccurrence(potentialWordToBeCached.getWord(),potentialWordToBeCached.getOccurrence()+1));
		} else {
			// Word is not present in cache and cache is full, so save it for later comparison. 
			wordsToBeCached.add(new WordOccurrence(word,1));
		}
	}

	/**
	 * This method calculates the time for transfer between satellite 1 and satellite 2 | satellite 2 and satellite 3
	 * @param message
	 * @param time
	 * @param messageEdited
	 * @param hasMessageChanged
	 * @param count
	 * @return
	 */
	private Integer calculateTimeForTransferBetweenSatellites(String message, Integer time, String messageEdited,
			boolean hasMessageChanged, int count) {
		// 1 byte takes 5 hours
		if(!hasMessageChanged) {
			time += message.length()*5;
		} else if(StringUtils.isNotBlank(messageEdited)) {
			// count is used for number of spaces, that are not to be counted.
			time += (messageEdited.length()-count)*5;
		}
		return time;
	}

	/**
	 * This method calculates the time from Curiosity and satellite 1
	 * @param message
	 * @param time
	 * @param messageEdited
	 * @param hasMessageChanged
	 * @param count
	 * @return
	 */
	private Integer calculateTimeForCuriosityToSatellite1Transfer(String message, Integer time,
			String messageEdited, boolean hasMessageChanged, int count) {
		// 1 byte takes 1 hour
		if(!hasMessageChanged) {
			time += message.length();
		} else if(StringUtils.isNotBlank(messageEdited)) {
			// count is used for number of spaces, that are not to be counted.
			time += messageEdited.length() - count;
		}
		return time;
	}

	/**
	 * This method is used when we find a cached word , hence we are editing our message, so that time may be calculated accordingly.
	 * @param message
	 * @param messageEdited
	 * @param word
	 * @return
	 */
	private String editMessage(String message, String messageEdited, String word) {
		if(messageEdited.equals(message)) {
			messageEdited = message.replaceFirst(word, "");
		} else {
			messageEdited = messageEdited.replaceFirst(word, "");
		}
		return messageEdited;
	}

	/**
	 * This method uses Least Frequently Used algorithm, based on the word's occurrence in messages.
	 * If the "potential" cacheable entry has less occurrences than all currently cached entries, it won't add it to the cache.
	 * @param cache
	 * @param cacheHasNoSpaceButNeedToDeleteElement
	 * @param wordsToBeCached
	 * @param cacheHasSpace
	 */
	private void removeLeastUsedWordsInCache(List<WordOccurrence> cache,
			boolean cacheHasNoSpaceButNeedToDeleteElement, Set<WordOccurrence> wordsToBeCached, boolean cacheHasSpace) {
		if(!cacheHasSpace && cacheHasNoSpaceButNeedToDeleteElement) {
			for(WordOccurrence wordToBeCached : wordsToBeCached) {
				boolean noChange = true;
				int leastOccurringPosition = 0;
				for(int i = 0; i < cache.size() && cache.get(i) != null; i++) {    					
				    if(cache.get(i).getOccurrence() < wordToBeCached.getOccurrence()) {
				    	leastOccurringPosition = i;
				    	noChange = false;
				    }
				}
				if(!noChange) {
					cache.set(leastOccurringPosition, new WordOccurrence(wordToBeCached.getWord(),(1)));
				}
			}				
		}
	}

	/**
	 * This method returns true if cache has free space.
	 * @param cache
	 * @return
	 */
	private boolean checkIfCacheHasSpace(List<WordOccurrence> cache) {
		boolean arrayHasSpace = false;
    	for(int i = 0; i < cache.size() ; i++) {
    		if(cache.get(i) == null) {
    			arrayHasSpace = true;
    			break;
    		}
    	}
		return arrayHasSpace;
	}   
	
}
