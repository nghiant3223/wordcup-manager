package uni.hcmut.wcmanager.utils;

import uni.hcmut.wcmanager.events.Event;

import java.util.List;

public class EventUtils {
    public static boolean isEventRightAfterMinute(List<Event> events, int eventIndex, int minute) {
        for (int i = eventIndex - 1; i >= 0; i--) {
            if (events.get(eventIndex).getAt() >= 90) {
                return false;
            }
        }

        return true;
    }
}
