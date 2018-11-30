package comn.model.controller;

import comn.ProjectConfiguration;
import comn.model.EngCoach2Model;
import comn.model.dto.Pair;
import comn.model.eventbus.AppEvent;
import comn.model.eventbus.EventBus;
import comn.model.eventbus.EventBusDispatcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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


    private EngCoach2Model engCoach;
    private EventBus eventBus;


    private boolean putStringInFirstTF;
    private Pair currentPair;

    @FXML
    public void initialize() {

        ProjectConfiguration projectConfiguration = ProjectConfiguration.getInstance();

        try {
            engCoach = projectConfiguration.getEngCoach2();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initFields();
        initEventHandlers();
    }

    private void initFields() {

        putStringInFirstTF = true;
        transcriptionTF.setVisible(false);

    }

    public void initEventHandlers() {

        eventBus = EventBusDispatcher.INSTANCE.getService(EventBus.class);

        initReverseTranslationClickedHandler();

        initShowTranscriptionClickedHandler();

    }

    @FXML
    public void nextButtonClicked() {

        if (putStringInFirstTF) {

            firstTF.setText("");
            secondTF.setText("");
            putStringInFirstTF = false;

            currentPair = engCoach.getPair();
            firstTF.setText(currentPair.getFirst());

//            System.out.println("current pair = " + currentPair);

            if (engCoach.getCurrentOrder().getOrder() == 1) {
                transcriptionTF.setText(currentPair.getTranscription());
            }

        } else {

            putStringInFirstTF = true;

            secondTF.setText(currentPair.getSecond());

            if (engCoach.getCurrentOrder().getOrder() == 2) {
                transcriptionTF.setText(currentPair.getTranscription());
            }

        }


    }


    public void initShowTranscriptionClickedHandler(){

        eventBus.addEventHandler(AppEvent.SHOW_TRANSCRIPTION_CLICKED, event -> {

            if (!transcriptionTF.isVisible()) {

                transcriptionTF.setVisible(true);

            } else {

                transcriptionTF.setVisible(false);
            }

        });

    }

    public void initReverseTranslationClickedHandler(){

        eventBus.addEventHandler(AppEvent.REVERSE_TRANSLATION_CLICKED, event -> {

            if (engCoach.getCurrentOrder() == EngCoach2Model.TranslationOrder.FROM_ORIGIN_TO_TRANSLATION) {

                engCoach.setOrder(EngCoach2Model.TranslationOrder.FROM_TRANSLATION_TO_ORIGIN);

            } else {

                engCoach.setOrder(EngCoach2Model.TranslationOrder.FROM_ORIGIN_TO_TRANSLATION);
            }

        });
    }
}
