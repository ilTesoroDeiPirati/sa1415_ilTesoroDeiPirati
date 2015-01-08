package impl;

import interfaces.ICard;

public class Card implements ICard {
	
	private String type, value;
	private int confidence;
	
	public Card(String type, String value){
		this.type = type;
		this.value = value;
	}
	
	public Card(String value){
		this.value = value;
	}
	
	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}
	
	public void setConfidence(int confidence){
		this.confidence = confidence;
	}
	
	public int getConfidence(){
		return confidence;
	}

}
