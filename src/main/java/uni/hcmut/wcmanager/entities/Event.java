package uni.hcmut.wcmanager.entities;

import java.util.Date;

public abstract class Event {
    protected int at;
    protected PlayerInMatch actor;
    protected Match match;

    public Event(Match match, PlayerInMatch actor, int at) {
        this.match = match;
        this.actor = actor;
        this.at = at;
    }

    public abstract void handle();
}
