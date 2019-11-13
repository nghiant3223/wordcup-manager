package uni.hcmut.wcmanager.events;

import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.PlayerInMatch;
import uni.hcmut.wcmanager.entities.TeamInMatch;
import uni.hcmut.wcmanager.events.CardEvent;

import java.security.InvalidParameterException;

public class RedCardEvent extends CardEvent {
    public RedCardEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        if (!actor.isStillInMatch()) {
            throw new InvalidParameterException("Player is already sent off");
        }

        actor.incrementRedCardCount();

        TeamInMatch playersTeam = actor.getTeamInMatch();
        playersTeam.incrementRedCardCount();
        playersTeam.sendPlayerOff(actor);

        afterRedCardEvent();
    }

}
