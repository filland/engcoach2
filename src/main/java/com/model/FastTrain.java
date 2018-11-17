package com.model;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class FastTrain extends ContentHandler implements Runnable{
	
	/*
	 * 
	 * instance of this class must be 
	 * creaded after creating
	 * FastTrainGUI class instance
	 *
	 * */
	
	/*
	 * Agenda:  not to clear the array by changing interval
	 * 
	 * */
	
	
	
	private JTextField field;
	private JLabel currentInfo;
	
	
	// 						### method startTrain
	// number of words to show
	private int numberOfWordsToShow;
	// interval between showing words
	private int interval;
	private Thread t;
	
	// 						### method run
	// to store original[0] and translation[1]
	private String array[]=new String[2];
	
	// 						### method pauseTrain
	// shows if there is a pause now
	private boolean pause=false;
	
	// 						### method getTotalNumberOfWords
	private int totalNumberOfWords;

	
	// 						### method get/set remaining number of words
	int remainingNumberOfWords;
	
	
	// 						# method 
	
	public FastTrain(JTextField destinationField, JLabel currentInfo) {
		field=destinationField;
		this.currentInfo=currentInfo;
	}
	
	
	@Override
	public void run() {
		
		readSection();
		
		
		for (int i = 1; i < (numberOfWordsToShow+1); i++) {
			try {
				
				currentInfo.setText("All/ Remaining : "+getTotalNumberOfWords()+" / "+i);
				setRemainingNumberOfWords(i);
				
				//now shows the first section
	
				
				readSection();
				
				if(i==4) {
					
					System.out.println("First and second elems of the check array:\n\t"+arrayCheck[0]+"\n\t"+arrayCheck[1]);
					
				}
				
				System.out.println(i);
				
				array=getWord();
				field.setText(array[0]);
				
				Thread.sleep(interval);

			} catch (InterruptedException eee) {}
		}
		
	}
	
	
	public void startTrain(int numberOfWordsToShow, float intervalSeconds){
		
		this.numberOfWordsToShow=numberOfWordsToShow;
		// we need millis so multiple by 1000
		System.err.println(intervalSeconds*1000);
		this.interval = (int)intervalSeconds*1000;
		
		// set the total number of words
		totalNumberOfWords=numberOfWordsToShow;
		
		t = new Thread(this);
		t.start();	
	}
	

	
	public void pauseTrain() {		
		t.suspend();
	}
	
	
	public void resumeTrain(){
		t.resume();
	}
	
	public void stopTrain(){
		t.stop();
		
	}
	
	
	public int getTotalNumberOfWords(){
		return totalNumberOfWords;
	}
	
	//set and get remaining number of words to show
	// these two are neccesary to back the rest number
	// of words by clicking the STOP button
	private void setRemainingNumberOfWords(int i){
		remainingNumberOfWords=getTotalNumberOfWords()-i;
	}
	public int getRemainingNumberOfWords(){
		return remainingNumberOfWords; 
	}
	
	
	
	public Thread getFastTrainThread() {
		return t;
	}
}
