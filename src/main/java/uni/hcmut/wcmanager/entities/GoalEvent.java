package uni.hcmut.wcmanager.entities;

import java.util.Date;

public class GoalEvent extends Event {
    public GoalEvent(Match match, PlayerInMatch actor, Date at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {

    }
}
