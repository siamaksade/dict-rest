package com.joker.dict.model;

public class Noun extends Word {
	private String gender;
	private String plural;

	public Noun(String text, String gender, String plural) {
		this.text = text;
		this.gender = gender;
		this.plural = plural;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPlural() {
		return plural;
	}
	public void setPlural(String plural) {
		this.plural = plural;
	}
	
	@Override
	public String toString() {
		return "Noun [text = " + text + ", gender=" + gender + ", plural=" + plural + "]";
	}
}
