package uni.hcmut.wcmanager.events;

import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.PlayerInMatch;
import uni.hcmut.wcmanager.entities.TeamInMatch;

public class OwnGoalEvent extends Event {

    public OwnGoalEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        TeamInMatch playersTeam = actor.getTeamInMatch();
        playersTeam.concede();

        TeamInMatch playersOpponentTeam = match.getOpponentTeam(playersTeam);
        playersOpponentTeam.score();
    }
}
