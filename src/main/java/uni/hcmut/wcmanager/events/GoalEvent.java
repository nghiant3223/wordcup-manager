package uni.hcmut.wcmanager.events;

import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.PlayerInMatch;
import uni.hcmut.wcmanager.entities.TeamInMatch;
import uni.hcmut.wcmanager.events.Event;

import java.security.InvalidParameterException;

public class GoalEvent extends Event {
    public GoalEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        if (!actor.isPlaying()) {
            throw new InvalidParameterException("Player is not playing");
        }

        actor.incrementGoalCount();


        TeamInMatch playersTeam = actor.getTeamInMatch();
        playersTeam.score();

        TeamInMatch playersOpponentTeam = match.getOpponentTeam(playersTeam);
        playersOpponentTeam.concede();

        System.out.printf("%s: %s has just scored at %d\n",
                playersTeam.getTeam().getName(), actor.getPlayer().getFullname(), at);
    }
}
