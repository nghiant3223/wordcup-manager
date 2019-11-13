package uni.hcmut.wcmanager.events;

import uni.hcmut.wcmanager.constants.MatchRule;
import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.PlayerInMatch;
import uni.hcmut.wcmanager.entities.TeamInMatch;
import uni.hcmut.wcmanager.events.Event;
import uni.hcmut.wcmanager.randomizers.CoachSimulator;

import java.security.InvalidParameterException;

public class InjuryEvent extends Event {

    public InjuryEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        if (!actor.isPlaying()) {
            throw new InvalidParameterException("Player is not playing");
        }

        TeamInMatch playersTeam = actor.getTeamInMatch();

        // If player's team cannot substitute player
        if (!playersTeam.isAbleToSubstitute()) {
            if (playersTeam.getPlayingPlayers().size() < MatchRule.MIN_PLAYING_PLAYER_COUNT) {
                match.endDueToLackOfPlayers(playersTeam);
                return;
            }

            playersTeam.sendPlayerOff(actor);
            String teamName = playersTeam.getTeam().getName();
            System.out.printf("❎ %s is not able to substitute player\n", teamName);
            return;
        }

        PlayerInMatch outPlayer = actor;
        PlayerInMatch inPlayer = CoachSimulator.chooseRandomPlayer(playersTeam.getBenchPlayers());
        playersTeam.substitutePlayer(outPlayer, inPlayer);
    }
}