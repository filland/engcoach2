package com.services;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;


/**
 * 
 * The class is meant to work with properties from the properties file
 * 
 * 
 * */


public class Utility {
		
	private static Properties properties=new Properties();
	private static File file=new File("properties");
	private static String [] boundaries;		
			
	
	static {
		readEngCoachProperties();
		
		//int k=(Integer.parseInt(Utility.getEngCoachProperty("sections"))) + 1;
		// and create the exact number of marks
		boundaries= new String[Integer.parseInt(Utility.getEngCoachProperty("sections")) + 1];
		for (int i = 0; i < boundaries.length; i++) {
			boundaries[i]="$"+(i+1);
		}
		
	}
	
	
	/*
	 * return the array of boundaries
	 * For example : {"$1","$2","$3","$4"}
	 * */
	public static String [] getBoundaries(){
		return boundaries;
	}
	
	
	/*
	 * this method return a boundary
	 * For example: if you pass number 3 (three)
	 * as an arguement then will be returned '$4'
	 * 
	 * */
	public static String getBoundary(int numderOfBoundary){
		return boundaries[numderOfBoundary];
	}
	
	
	protected static void readEngCoachProperties(){
		try {
			properties.load(new FileReader(file));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	protected static void saveEngCoachProperties(String message){
		try {
			properties.store(new FileWriter(file), message);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
	public static void setPathToSourceFile(String newPath){
		// replace all left slaches (\) to right ones (/)
		newPath=newPath.replace("\\", "/");
		//System.out.println(newPath);

		// set a new value to the key "path"
		properties.setProperty("path", newPath);
		// save changes
		saveEngCoachProperties("The key PATH was changed");
	}
	
	
	// this method is static because we have one properties file
	// for the whole application
	public static String getEngCoachProperty(String key) {
		return properties.getProperty(key);
	}
	
	protected void setProperty(String key, String value){
		properties.setProperty(key, value);
	}
	
	protected void addProperty(String key, String value){
		properties.put(key, value);
	}
	
	// get the array of names of sections which is read from the properties
	public static String [] getSectionsName(){
		String sections[]=new String [Integer.parseInt(getEngCoachProperty("sections"))];
		
		// read the name of sections from the properties
		for (int i = 0; i < sections.length; i++) {
			sections[i]=Utility.getEngCoachProperty("section"+(i+1));
		}
		return sections;
	}
}
