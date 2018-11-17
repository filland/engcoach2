package com.model;


import javax.swing.*;


public class EngCoach extends ContentHandler {
	
	private JTextField firstField;
	private JTextField secondField;
	private String englishWord;

	 // счетчик для показа оригинала и перевода в два клика
	private int counter=0;                  


	private String splitedLineSetWordInField[]=new String[2];
	
	
	public EngCoach(JTextField original, JTextField translation) {
		firstField=original;
		secondField=translation;
	}

	
	// set a pair to be showed
	private void setPair(String [] pair){
		if(pair.length!=2) 
			throw new IndexOutOfBoundsException("Это не положе на пару оригинал-перевод");
		
		splitedLineSetWordInField=pair;
	}
		

	// fist click -- a word is put in the firstField
	// second click -- its translation is put in the secondField
	public void setWordInField() {
		
		secondField.setText("");		
		
		if (counter == 0) {
			counter++;
			
			readSection();
			setPair(getWord());
			
			firstField.setText(splitedLineSetWordInField[0]);


			// выводим текст во вторую строку
		} else {	 
			secondField.setText(splitedLineSetWordInField[1]);

			counter=0;
		}
	}
	
//	public String getEnglishWord(){
//		return splitedLineSetWordInField[0];
//	}

}
