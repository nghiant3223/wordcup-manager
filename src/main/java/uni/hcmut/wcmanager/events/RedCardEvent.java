package uni.hcmut.wcmanager.events;

import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.PlayerInMatch;
import uni.hcmut.wcmanager.entities.TeamInMatch;
import uni.hcmut.wcmanager.events.CardEvent;

public class RedCardEvent extends CardEvent {
    public RedCardEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        actor.incrementRedCardCount();

        TeamInMatch playersTeam = actor.getTeamInMatch();
        playersTeam.incrementRedCardCount();
        playersTeam.sendPlayerOff(actor);

        afterRedCardEvent();
    }

}
