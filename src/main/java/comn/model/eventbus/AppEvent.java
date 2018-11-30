package comn.model.eventbus;

import javafx.event.Event;
import javafx.event.EventType;

public class AppEvent extends Event {

    public final static EventType<AppEvent> ANY =
            new EventType<>(Event.ANY, "App event");

    public final static EventType<AppEvent> REVERSE_TRANSLATION_CLICKED =
            new EventType<>(ANY, "REVERSE_TRANSLATION_CLICKED");

    public final static EventType<AppEvent> SHOW_TRANSCRIPTION_CLICKED =
            new EventType<>(ANY, "SHOW_TRANSCRIPTION_CLICKED");

    public AppEvent(EventType<AppEvent> eventType) {
        super(eventType);
    }
}
