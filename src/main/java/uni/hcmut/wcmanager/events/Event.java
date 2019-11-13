package uni.hcmut.wcmanager.events;

import uni.hcmut.wcmanager.constants.MatchRule;
import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.PlayerInMatch;

import java.security.InvalidParameterException;
import java.util.Date;

public abstract class Event {
    protected int at;
    protected PlayerInMatch actor;
    protected Match match;

    public Event(Match match, PlayerInMatch actor, int at) {
        if (at >= MatchRule.EXTRA_FULL_DURATION) {
            throw new InvalidParameterException("Event time must be < 120");
        }

        this.match = match;
        this.actor = actor;
        this.at = at;
    }

    public int getAt() {
        return at;
    }

    public PlayerInMatch getActor() {
        return actor;
    }

    public Match getMatch() {
        return match;
    }

    public abstract void handle();
}
