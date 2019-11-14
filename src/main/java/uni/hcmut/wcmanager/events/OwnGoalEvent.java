package uni.hcmut.wcmanager.events;

import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.PlayerInMatch;
import uni.hcmut.wcmanager.entities.TeamInMatch;

import java.security.InvalidParameterException;

public class OwnGoalEvent extends Event {

    public OwnGoalEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        if (!actor.isPlaying()) {
            throw new InvalidParameterException("Player is not playing");
        }

        TeamInMatch playersTeam = actor.getTeamInMatch();
        playersTeam.concede();

        TeamInMatch playersOpponentTeam = match.getOpponentTeam(playersTeam);
        playersOpponentTeam.score();

        System.out.printf("%s: %s has just commit own goal at %d\n",
                playersTeam.getTeam().getName(), actor.getPlayer().getFullname(), at);

    }
}
