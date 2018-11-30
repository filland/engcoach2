package comn.model.controller;

import comn.model.eventbus.AppEvent;
import comn.model.eventbus.EventBus;
import comn.model.eventbus.EventBusDispatcher;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;

public class TopMenuController {

    @FXML
    private CheckMenuItem showTranscriptionMI;
    @FXML
    private CheckMenuItem reverseTranslationMI;

    private EventBus eventBus;

    public TopMenuController() {

        eventBus = EventBusDispatcher.INSTANCE.getService(EventBus.class);
    }


    @FXML
    public void showTranscriptionClicked() {

        eventBus.fireEvent(new AppEvent(AppEvent.SHOW_TRANSCRIPTION_CLICKED));
    }

    @FXML
    public void reverseTranslationClicked() {

        eventBus.fireEvent(new AppEvent(AppEvent.REVERSE_TRANSLATION_CLICKED));
    }

}
