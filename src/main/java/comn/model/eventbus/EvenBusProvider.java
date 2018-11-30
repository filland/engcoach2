package comn.model.eventbus;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;

public class EvenBusProvider implements EventBus {

    private Group group;

    public EvenBusProvider() {
        group = new Group();
    }

    @Override
    public void fireEvent(Event event) {
        group.fireEvent(event);
    }

    @Override
    public <T extends Event> void addEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        group.addEventHandler(eventType, eventHandler);
    }

    @Override
    public <T extends Event> void removeEventHandler(EventType<T> eventType, EventHandler<? super T> eventHandler) {
        group.removeEventHandler(eventType, eventHandler);
    }
}
