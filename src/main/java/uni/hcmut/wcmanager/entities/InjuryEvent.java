package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.constants.GameRule;
import uni.hcmut.wcmanager.randomizers.CoachSimulator;

public class InjuryEvent extends Event {

    public InjuryEvent(Match match, PlayerInMatch actor, int at) {
        super(match, actor, at);
    }

    @Override
    public void handle() {
        TeamInMatch playersTeam = actor.getTeamInMatch();

        // If player's team cannot substitute player
        if (!playersTeam.isAbleToSubstitute()) {
            if (playersTeam.getPlayingPlayers().size() < GameRule.MIN_PLAYING_PLAYER_COUNT) {
                match.endDueToLackOfPlayers(playersTeam);
                return;
            }

            String teamName = playersTeam.getTeam().getName();
            System.out.printf("❎ %s is not able to substitute player\n", teamName);
            return;
        }

        PlayerInMatch outPlayer = actor;
        PlayerInMatch inPlayer = CoachSimulator.chooseRandomPlayer(playersTeam.getBenchPlayers());
        playersTeam.substitutePlayer(outPlayer, inPlayer);
    }
}