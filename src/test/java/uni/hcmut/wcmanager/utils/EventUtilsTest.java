package uni.hcmut.wcmanager.utils;

import org.junit.Assert;
import org.junit.Test;
import uni.hcmut.wcmanager.events.Event;
import uni.hcmut.wcmanager.events.GoalEvent;

import java.util.ArrayList;
import java.util.List;

public class EventUtilsTest {
    private List<Event> events;

    @Test
    public void shouldEventBeNotRightAfter90() {
        events = new ArrayList<>();

        Event event1 = new GoalEvent(null, null, 59);
        Event event2 = new GoalEvent(null, null, 76);
        Event event3 = new GoalEvent(null, null, 89);
        Event event4 = new GoalEvent(null, null, 90);
        Event event5 = new GoalEvent(null, null, 91);

        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);

        boolean event5AfterMin90 = EventUtils.isEventRightAfterMinute(events, 4, 90);
        Assert.assertFalse(event5AfterMin90);
    }

    @Test
    public void shouldReturnEventBeRightAfter90() {
        events = new ArrayList<>();

        Event event1 = new GoalEvent(null, null, 59);
        Event event2 = new GoalEvent(null, null, 76);
        Event event3 = new GoalEvent(null, null, 89);
        Event event4 = new GoalEvent(null, null, 90);
        Event event5 = new GoalEvent(null, null, 91);

        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);

        boolean event4AfterMin90 = EventUtils.isEventRightAfterMinute(events, 3, 90);
        Assert.assertTrue(event4AfterMin90);
    }

    @Test
    public void shouldReturnEventBeRightAfter90WithDuplicateMinutes() {
        events = new ArrayList<>();

        Event event1 = new GoalEvent(null, null, 59);
        Event event2 = new GoalEvent(null, null, 76);
        Event event3 = new GoalEvent(null, null, 90);
        Event event4 = new GoalEvent(null, null, 90);
        Event event5 = new GoalEvent(null, null, 91);

        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);

        boolean event3AfterMin90 = EventUtils.isEventRightAfterMinute(events, 2, 90);
        Assert.assertTrue(event3AfterMin90);
    }

    @Test
    public void shouldReturnEventBeRightAfter90WithDuplicateMinutes1() {
        events = new ArrayList<>();

        Event event1 = new GoalEvent(null, null, 90);
        Event event2 = new GoalEvent(null, null, 90);
        Event event3 = new GoalEvent(null, null, 90);
        Event event4 = new GoalEvent(null, null, 90);
        Event event5 = new GoalEvent(null, null, 90);

        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);

        boolean event3AfterMin90 = EventUtils.isEventRightAfterMinute(events, 0, 90);
        Assert.assertTrue(event3AfterMin90);
    }

    @Test
    public void shouldReturnEventBeNotRightAfter90WithDuplicateMinutes() {
        events = new ArrayList<>();

        Event event1 = new GoalEvent(null, null, 59);
        Event event2 = new GoalEvent(null, null, 76);
        Event event3 = new GoalEvent(null, null, 90);
        Event event4 = new GoalEvent(null, null, 90);
        Event event5 = new GoalEvent(null, null, 91);

        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);

        boolean event4AfterMin90 = EventUtils.isEventRightAfterMinute(events, 3, 90);
        Assert.assertFalse(event4AfterMin90);
    }

    @Test
    public void shouldReturnTrueWhenEventsIsSorted() {
        events = new ArrayList<>();

        Event event1 = new GoalEvent(null, null, 59);
        Event event2 = new GoalEvent(null, null, 76);
        Event event3 = new GoalEvent(null, null, 90);
        Event event4 = new GoalEvent(null, null, 90);
        Event event5 = new GoalEvent(null, null, 91);

        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);

        Assert.assertTrue(EventUtils.isEventListInAscendingOrder(events));
    }

    @Test
    public void shouldReturnFalseWhenEventsIsNotSort() {
        events = new ArrayList<>();

        Event event1 = new GoalEvent(null, null, 59);
        Event event2 = new GoalEvent(null, null, 76);
        Event event3 = new GoalEvent(null, null, 32);
        Event event4 = new GoalEvent(null, null, 43);
        Event event5 = new GoalEvent(null, null, 91);

        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);

        Assert.assertFalse(EventUtils.isEventListInAscendingOrder(events));
    }
}
