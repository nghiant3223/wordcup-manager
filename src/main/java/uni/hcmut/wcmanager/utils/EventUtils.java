package uni.hcmut.wcmanager.utils;

import uni.hcmut.wcmanager.events.Event;

import java.util.List;

public class EventUtils {
    public static boolean isEventRightAfterMinute(List<Event> events, int eventIndex, int minute) {
        Event e = events.get(eventIndex);

        if (eventIndex - 1 >= 0) {
            Event beforeE = events.get(eventIndex - 1);
            return e.getAt() >= minute && beforeE.getAt() < minute;
        } else {
            return e.getAt() >= minute;
        }
    }

    public static boolean isEventListInAscendingOrder(List<Event> events) {
        for (int i = 0; i < events.size() - 1; i++) {
            if (events.get(i).getAt() > events.get(i + 1).getAt()) {
                return false;
            }
        }

        return true;
    }
}
