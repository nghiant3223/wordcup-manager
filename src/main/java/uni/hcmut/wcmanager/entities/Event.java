package uni.hcmut.wcmanager.entities;

import java.util.Date;

public abstract class Event {
    private Date at;
    private PlayerInMatch actor;
    private Match match;

    public Event(Match match, PlayerInMatch actor, Date at) {
        this.match = match;
        this.actor = actor;
        this.at = at;
    }

    public abstract void handle();
}
