package com.model;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import com.services.Utility;

/*
 нужен метод ля чтения файла с данными 
 и записи разделов по коллекциям - done
 

 
 
 */

public class AddNewContent {

	private JTextField o1s[];
	private JTextField t1s[];

	// path to a the file with content
	private String filePath = Utility.getEngCoachProperty("path");
	private ArrayList<String> lists[];
	// mark to find the beginning of a section
	private String startLine="$1";
	// mark to find the end of a section
	private String endLine="$2";
	
	
	// mark-boundaries which are for getting content from the file
	private String boundaries [];
	private int numberOfSections;
	
	// to walk the file and fill arrayLists from array !lists! 
	private int counter1=0;
	
	// to set a list to write a new content in it
	private int numberOfList=-1;
	
		

	public AddNewContent(JTextField original[], JTextField translation []) {
		 
		 o1s=original;
		 t1s=translation;
		 
		 init();
		 readFile();
	 }

	
	private void init(){
		numberOfSections=Integer.parseInt(Utility.getEngCoachProperty("sections"));
		
		boundaries = Utility.getBoundaries();
		
		lists = new ArrayList[Integer.parseInt(Utility.getEngCoachProperty("sections"))-1];
		for (int i = 0; i < lists.length; i++) {
			lists[i] = new ArrayList<String>();
		}
		
		
	}
	
	

	public void readFile() {
		
		init();
		
		try {
			BufferedReader b = new BufferedReader(new FileReader(filePath));
			
			String line="";
			int MetkaBeginningEnd=1;
			
			while((line=b.readLine())!=null)
			{
				
				if(line.startsWith(endLine)) // конец поля считывания
				{
					MetkaBeginningEnd=2;
					

					// to stop reading the file
					if(counter1==(numberOfSections-2)
						&& endLine.equals("$"+(numberOfSections))) {
						System.out.println("break");
						break;
					}
					//System.out.println(counter1 +"   "+ endLine);
	
					nextSection();		
				}
			
				if(MetkaBeginningEnd==1 && line.isEmpty()==false)  // запись строки в коллекцию
				{
					// to avoid duplication
					if(line.equals("$1")) line="";
					
					lists[counter1].add(line);
					//System.out.println(line);
				}
				
				//начало поля считывания; 
				//стоит после endLine чтобы не записывать поисковой символ
				if(line.startsWith(startLine)) 
				{
					MetkaBeginningEnd=1;
				}
			}
			b.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File in not found");
		} catch (IOException e) {
			System.out.println("A problem during reading the file");
		}
	}
	
	
	
	
	
	
	// read source file and add each section into a separate list
	public boolean writeFile(){
		boolean success=false;

//		init();
		
		try {
			BufferedWriter b=new BufferedWriter(new FileWriter(filePath));
			
			
			// walk through the array and add data from each list
			for (int i = 0; i < lists.length; i++) {
				b.append(boundaries[i]);
				
				System.out.println(boundaries[i]);
				
				b.append("\n\n\n");
				
				for (String line : lists[i]) {
					b.append(line+"\n");
					System.out.println(line);
				}
				
				b.append("\n\n\n");
				
				// add the last mark "$#"
				if(i==(lists.length-1)) b.append(boundaries[i+1]);
			}
			b.close();
			
			success=true;
			
		} catch (IOException e) {
			System.out.println("Some problems during writing in the file");
		}
		
		return success;
	}
	
	
	// to move between sections (for example: first section is from $1 to $2, 
	// the second one is from $2 to $3)
	private void nextSection() {
		++counter1;

		startLine=boundaries[counter1];
		endLine=boundaries[counter1+1];
	}
	
	// to set a list to write a new content in it
	public void setTargetSection(int numberOfList){
		if (lists.length < numberOfList) {
			JOptionPane.showMessageDialog(null, 
					"There is no such section", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			this.numberOfList=numberOfList;
		}
	}
	
	
	/*
	 * This method gets data from fields and adds them into 
	 * a certain list which had been selected before
	 * */
	public void addContentInSection(){
		String line="";
		
		for (int i = 0; i < o1s.length; i++) {
			if(o1s[i].getText().isEmpty() || t1s[i].getText().isEmpty()) continue;
			
			line=o1s[i].getText() + " - " + t1s[i].getText();
			lists[numberOfList].add(line);
		}
	}
	
	// this method is to display the section via array with section's names
	// to replace variable "subject"
	public int getDestination(){
		return numberOfList;
	}
	

}
