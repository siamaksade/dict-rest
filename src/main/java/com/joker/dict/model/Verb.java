package com.joker.dict.model;

import java.util.Arrays;

public class Verb extends Word {
	private String[] present;
	private String[] past;
	private String presentPerfect;
	
	public void setPresent(String[] present) {
		this.present = present;
	}
	
	public void setPast(String[] past) {
		this.past = past;
	}
	
	public void setPresentPerfect(String presentPerfect) {
		this.presentPerfect = presentPerfect;
	}
	
	public String[] getPresent() {
		return present;
	}
	
	public String[] getPast() {
		return past;
	}
	
	public String getPresentPerfect() {
		return presentPerfect;
	}
	
	public String getDescription() {
		return present[5] + " " + "(" + present[2] + ", " + presentPerfect + ", " + past[5] + ")";
	}

	@Override
	public String toString() {
		return "Verb [present=" + Arrays.toString(present) + ", past="
				+ Arrays.toString(past) + ", presentPerfect="
				+ presentPerfect + "]";
	}
}
