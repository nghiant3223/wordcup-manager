package uni.hcmut.wcmanager.events;

import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.PlayerInMatch;
import uni.hcmut.wcmanager.entities.TeamInMatch;
import uni.hcmut.wcmanager.events.Event;

public class GoalEvent extends Event {
    public GoalEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        actor.incrementGoalCount();

        TeamInMatch playersTeam = actor.getTeamInMatch();
        playersTeam.score();

        TeamInMatch playersOpponentTeam = match.getOpponentTeam(playersTeam);
        playersOpponentTeam.concede();
    }
}
