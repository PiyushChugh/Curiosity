package com.mars.rover.curiosity;

public class WordOccurrence {
	
	private String word;
	private Integer occurrence;
	
	public WordOccurrence(String word, Integer occurrence) {
		this.word = word;
		this.occurrence = occurrence;
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Integer getOccurrence() {
		return occurrence;
	}
	public void setOccurrence(Integer occurrence) {
		this.occurrence = occurrence;
	}

}
