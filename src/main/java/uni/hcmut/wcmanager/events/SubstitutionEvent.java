package uni.hcmut.wcmanager.events;

import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.PlayerInMatch;
import uni.hcmut.wcmanager.entities.TeamInMatch;
import uni.hcmut.wcmanager.events.Event;
import uni.hcmut.wcmanager.randomizers.CoachSimulator;

import java.security.InvalidParameterException;

public class SubstitutionEvent extends Event {
    public SubstitutionEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        if (!actor.isPlaying()) {
            throw new InvalidParameterException("Player is not playing");
        }

        TeamInMatch outPlayersTeam = actor.getTeamInMatch();

        if (!outPlayersTeam.isAbleToSubstitute()) {
            String teamName = outPlayersTeam.getTeam().getName();
            System.out.printf("❎ %s is not able to substitute player\n", teamName);
            return;
        }

        PlayerInMatch outPlayer = actor;
        PlayerInMatch inPlayer = CoachSimulator.chooseRandomPlayer(outPlayersTeam.getBenchPlayers());
        outPlayersTeam.substitutePlayer(outPlayer, inPlayer);
    }
}
