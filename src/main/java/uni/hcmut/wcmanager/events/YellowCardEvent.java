package uni.hcmut.wcmanager.events;

import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.PlayerInMatch;
import uni.hcmut.wcmanager.entities.TeamInMatch;
import uni.hcmut.wcmanager.events.CardEvent;

public class YellowCardEvent extends CardEvent {
    public YellowCardEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        actor.incrementYellowCard();

        TeamInMatch playersTeam = actor.getTeamInMatch();
        playersTeam.incrementYellowCardCount();

        // If this player has received 2 yellow cards
        if (actor.getYellowCardCount() == 2) {
            playersTeam.sendPlayerOff(actor);
            afterRedCardEvent();
        }
    }
}
