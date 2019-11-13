package uni.hcmut.wcmanager.events;

import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.PlayerInMatch;
import uni.hcmut.wcmanager.entities.TeamInMatch;
import uni.hcmut.wcmanager.events.CardEvent;

import java.security.InvalidParameterException;

public class YellowCardEvent extends CardEvent {
    public YellowCardEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        if (!actor.isStillInMatch()) {
            throw new InvalidParameterException("Player is not playing");
        }

        actor.incrementYellowCard();

        TeamInMatch playersTeam = actor.getTeamInMatch();
        playersTeam.incrementYellowCardCount();

        System.out.println(">" + playersTeam.getYellowCardCount());

        // If this player has received 2 yellow cards
        if (actor.getYellowCardCount() == 2) {
            playersTeam.sendPlayerOff(actor);
            afterRedCardEvent();
        }
    }
}
