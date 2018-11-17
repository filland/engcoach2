package com.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.services.Utility;

public class AddChangeSection extends Utility {
	
	private String currentSection;
	//private String nameOfSections []=Utility.getBoundaries();
	private String nameOfSectionKeys[];
	
	
	public AddChangeSection() {
		init();
	}
	
	
	private void init(){
		nameOfSectionKeys=new String[Integer.parseInt(Utility.getEngCoachProperty("sections"))];
		for (int i = 0; i < nameOfSectionKeys.length; i++) {
			nameOfSectionKeys[i]="section"+(i+1);
		}
	}
	
	
	public void setCurrentSection(int number) {
		// call this one to refresh data in the array 
		// as we have just added a new section
		init();
		
		currentSection=nameOfSectionKeys[number];
	}
	
	
	public void changeSectionName(String newName){
	
		setProperty(currentSection, newName);
		saveEngCoachProperties("The name of a section was changed");
		
	}
	
	public void addNewSection(String name){
		// change value of the key "section" in the prop
		int number=Integer.parseInt(Utility.getEngCoachProperty("sections"));
		addProperty("sections", (number+1)+"");
		
		// add the name into the properties file
		addProperty("section"+(number+1), name);
		
		addMarkIntoSourceFile(number+1);
		
		saveEngCoachProperties("A new section was added");
		
	}
	
	private void addMarkIntoSourceFile(int number){
		try {
			BufferedWriter b=new BufferedWriter(
					new FileWriter(Utility.getEngCoachProperty("path"), true));
			
			b.append("\n\n$"+(number));
			b.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
