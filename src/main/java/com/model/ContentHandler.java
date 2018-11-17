package com.model;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.services.Utility;





// Agenda: > create enum for sections
// 		   > add new parameter to the method setBoundaries (boolean createNewListForUniqueWords)



/*			         	Summary
 * 
 * I intend to create the following methods:
 * 
 * > readFile 
 * 
 * */


public class ContentHandler {
	
	
	
	// this block is used to create a jar file
	private boolean isCreatedJarFile=Boolean.valueOf(Utility.getEngCoachProperty("jar"));
	
	private File jarPath=new File(ContentHandler.class.
												getProtectionDomain().getCodeSource().
												getLocation().getPath());
    String propertiesPath=getAbsolutePath();
	
	
	
    private String getAbsolutePath(){
    	File jarPath=new File(ContentHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    	return jarPath.getParentFile().getAbsolutePath();
    }
    
	
	
    private ArrayList<String> arrayColl=new ArrayList<String>();  // коллекция для хранения строк
    private String strRandomLine="";

    private String splitedLine[]=new String[2];
	
	
    private String filePath=Utility.getEngCoachProperty("path");
	
	
    private String startLine="$1";           // начало считывания
    private String endLine="$2";             // конец считывания строк из текстового файла
    private boolean fromEngToRus=false;   		     //  если равен FALSE, то анг на рус, если TRUE -- наоборот

    private int counter1=0;					// счетчик для проверки повторения строки в массиве строк
    protected String [] arrayCheck;					// тут храним N-ть последних выведенныхх слов
    private int exceptionNumber=0;

    private boolean flag2=true;										// для записи числа, которое является размером массива исключений
	
    private int lineNumber;            // количество выбранных из файла строк
    private int randomNumber;          // случайный номер строки (из выбранных строк)
    private int metkaBeginningEnd;     // флаг для начала и завершения считывания из текст файла
	
    // this variable is to hold english word;
    private String englishWord;
	
	
	
	
	// initialization block
	{	
//		// the following part of initialization block is for the executable jar file
		
		if (isCreatedJarFile) {
			String sysString = Utility.getEngCoachProperty("path");
			sysString = propertiesPath + "/" + sysString;
			//propertiesMap.put("path", sysString);
			System.out.println(" propertiesPath-" + sysString);
		}	
			
		System.out.println("Absolute path to main method   "+propertiesPath);
		System.out.println(" propertiesPath-" + Utility.getEngCoachProperty("path"));
		System.out.println("Создается jar file  "+isCreatedJarFile);
	}
	
	
	
	
	/*
	 * if pass TRUE then original is Rus
	 * if pass FALSE then original is Eng
	 * 
	 * */
	public void setOriginalLang(boolean zeroOrOne){
		fromEngToRus=zeroOrOne;
	}
	
	
	// set boundaries for readSection method
	public void setBoundaries(String startLine, String endLine){
		
		// to create a new array of unique words
		flag2=true;
		
		this.startLine=startLine;
		this.endLine=endLine;
		
		// to zero number of elements stored into
		// array with unique words
		counter1=0;
		
		// as the section was changed lets read new one
		readSection();
	}
	
	
	// set boundaries for readSection method
	public void setBoundaries(int numberOfSection){
		
		// to create a new array of unique words
		flag2=true;
		
		this.startLine=Utility.getBoundary(numberOfSection);
		this.endLine=Utility.getBoundary(numberOfSection+1);
		
		// to zero number of elements stored into
		// array with unique words
		counter1=0;
		
		// as the section was changed lets read the new one
		readSection();
	}
	
	
	public void readSection() {
		
		// clear this collection
		arrayColl.clear();
		
		try {
			BufferedReader file = new BufferedReader(new FileReader(filePath));

			String line = "";
			while ((line = file.readLine()) != null) {
				if (line.startsWith(endLine)) // конец поля считывания
				{
					metkaBeginningEnd = 2;
				}

				if (metkaBeginningEnd == 1) // запись строки в коллекцию
				{
					if (line.isEmpty() == false) {
						arrayColl.add(line);
					}
				}

				// начало поля считывания;
				// стоит после endLine чтобы не записывать поисковой символ
				if (line.startsWith(startLine)) {
					metkaBeginningEnd = 1;
				}
			}
			file.close();
		} catch (IOException e) {
//				firstField.setText("Ошибка");
		}
	}

		
		
		// получим случайную строку из коллекции
		private String getRandomWord() { 
			//System.out.println("Общее кол-во строк в разделе - "+arrayColl.size());
			
			// получаем число строк
			lineNumber = arrayColl.size(); 
																			
			do {
				// получаем случайное число  в  этом диапазоне
				randomNumber = (int) Math.floor(Math.random() * lineNumber); 

			} while (arrayColl.get(randomNumber).length() < 5);

			// возвращаем элемент с полученным номером
			return arrayColl.get(randomNumber);
		}

		
		private void makeWordUnique() {
			// до тех пор, пока строка не будет отличаться от строк в массиве
			while (Arrays.asList(arrayCheck).contains(strRandomLine)) {
				strRandomLine = getRandomWord();
				//System.out.println(strRandomLine);
			}
			// добавляем слово в массив для уникальных строк
			arrayCheck[counter1] = strRandomLine;
		}
		
		
		
		// получим слова для вывода на экран
		public String[] getWord() 
		{
			String splitedLineSys[] = new String[2];

			// получим случайную строку из коллекции
			strRandomLine = getRandomWord();

			if (flag2 == true) {
				// получим ~60% от числа строк в разделе
				exceptionNumber = Math.round(lineNumber * 0.6f);
				System.out.println("Size of the check array - "+ exceptionNumber);

				// создадим массив с таким числом элементов
				arrayCheck = new String[exceptionNumber];
				flag2 = false;
			}

			// check if this line is unique
			// if it's already been used then get another line
			makeWordUnique();

			
			counter1++;
			
			//System.out.println("Значение перем counter1 = "+counter1);
			
			if (counter1 == exceptionNumber)
				counter1 = 0;

			// weather by "-" or by "—"
			splitedLineSys = strRandomLine.split("-|—", 2);
			// удаляем пробелы в начале в конце
			splitedLineSys[0] = splitedLineSys[0].trim();
			
			// this line if for getting eng word to get its transcrypt
			englishWord=splitedLineSys[0];
			
			splitedLineSys[1] = splitedLineSys[1].trim();
			
			
			// to create translation from Rus to Eng
			if (fromEngToRus==true) {
				String per=splitedLineSys[0];
				splitedLineSys[0]=splitedLineSys[1];
				splitedLineSys[1]=per;
			}
			
			System.out.println(counter1 + "  " + splitedLineSys[0]+" - "+splitedLineSys[1]);
			
			/*
			 * now splitedLineSys[0] contains original, splitedLineSys[1] contains
			 * translation
			 */
			return splitedLineSys;
		}
	
	
	public String getEnglishWord(){
		return englishWord;
	}
	
	
}
