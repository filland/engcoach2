package comn.model.controller;

import comn.ProjectConfiguration;
import comn.model.EngCoach2Model;
import comn.model.dto.Pair;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.io.IOException;

public class MainPageController {

    @FXML
    private TextField firstTF;
    @FXML
    private TextField secondTF;
    @FXML
    private TextField transcriptionTF;
    @FXML
    private Button nextButton;

    // =========== MenuBar // ===========
    @FXML
    private CheckMenuItem showTranscriptionMI;
    @FXML
    private CheckMenuItem reverseTranslationMI;
    // =========== MenuBar // ===========

    private EngCoach2Model engCoach;
    private boolean putStringInFirstTF;
    private Pair currentPair;

    @FXML
    public void initialize(){

        ProjectConfiguration instance = ProjectConfiguration.getInstance();

        try {
            engCoach = instance.getEngCoach2();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // center text in text fields
        firstTF.setAlignment(Pos.CENTER);
        secondTF.setAlignment(Pos.CENTER);
        transcriptionTF.setAlignment(Pos.CENTER);

        initFields();
    }

    private void initFields(){

        putStringInFirstTF = true;

        transcriptionTF.setVisible(false);

    }

    @FXML
    public void nextButtonClicked(){

        if (putStringInFirstTF){

            firstTF.setText("");
            secondTF.setText("");
            putStringInFirstTF = false;

            currentPair = engCoach.getPair();
            firstTF.setText(currentPair.getFirst());

            System.out.println("current pair = "+currentPair);

            if (engCoach.getCurrentOrder().getOrder() == 1){
                transcriptionTF.setText(currentPair.getTranscription());
            }

        } else {

            putStringInFirstTF = true;

            secondTF.setText(currentPair.getSecond());

            if (engCoach.getCurrentOrder().getOrder() == 2){
                transcriptionTF.setText(currentPair.getTranscription());
            }

        }


    }

    @FXML
    public void showTranscriptionClicked(ActionEvent event){

        if (showTranscriptionMI.isSelected()){

            transcriptionTF.setVisible(true);
        } else {

            transcriptionTF.setVisible(false);
        }

    }

    @FXML
    public void reverseTranslationClicked(){

        if (engCoach.getCurrentOrder() == EngCoach2Model.TranslationOrder.FROM_ORIGIN_TO_TRANSLATION){

            engCoach.setOrder(EngCoach2Model.TranslationOrder.FROM_TRANSLATION_TO_ORIGIN);

        } else {

            engCoach.setOrder(EngCoach2Model.TranslationOrder.FROM_ORIGIN_TO_TRANSLATION);
        }
    }
}
