package com.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * This service is responsible for
 * providing transcription of a english
 * word
 *
 */
public class TranscriptionService {

	private static Properties prop;
    private static Map<String, String> wordsTrans;

    private static String pathToTrascriptionSymbols=Utility.
    						getEngCoachProperty("pathToDecodedSounds");
    private static  String pathToDictionary=Utility.
    						getEngCoachProperty("pathToPronunciationDictionary");

    

	static {
        // read sounds as properties
        // in order to create transcrypts further
        prop=new Properties();
        try {
            prop.load(new FileReader(pathToTrascriptionSymbols));
        } catch (IOException e) {
            e.printStackTrace();
        }

        wordsTrans =new HashMap<String, String>();
        readDict();

    }

    
    
    
    public static String getPathToTrascriptionSymbols() {
		return pathToTrascriptionSymbols;
	}
	public static void setPathToTrascriptionSymbols(
			String pathToTrascriptionSymbols) {
		TranscriptionService.pathToTrascriptionSymbols = pathToTrascriptionSymbols;
	}


	public static String getPathToDictionary() {
		return pathToDictionary;
	}
	public static void setPathToDictionary(String pathToDictionary) {
		TranscriptionService.pathToDictionary = pathToDictionary;
	}


    private static void readDict(){
        try {
            BufferedReader b=new BufferedReader(new FileReader(pathToDictionary));
            String line;

            while ((line=b.readLine())!=null){
                if(line.isEmpty()) continue;
            	
            	String sys[]=line.split(" ", 2);

                wordsTrans.put(sys[0], sys[1]);
            }
            b.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getTranscription(String word){
		//  make sure that the WORD is really a word, 
    	// not a sentence or a phrase
		if(word.split(" ").length > 1){
			//
			return "not found";
		}
		
    	// get transcription of this word
        String transc;
        try {
			transc = wordsTrans.get(word.toUpperCase());
			
			System.out.println("Transcrypt of the word "+transc);

	        // split string into separate sounds
	        // sounds are in form of acronyms
	        String sounds[]=transc.trim().split(" ");
			
	        transc="";
	        // now perlace acronyms with typical sounds
	        for (int i = 0; i < sounds.length; i++) {
	            transc+=prop.getProperty(sounds[i]);
	        }
	          
		} catch (NullPointerException e) {
			// if the word has not been found in the dictionary
			return "not found";
		}

        return transc;
    }
}
