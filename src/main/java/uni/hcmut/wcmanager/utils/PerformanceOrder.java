package uni.hcmut.wcmanager.utils;

import uni.hcmut.wcmanager.entities.TeamInMatch;
import uni.hcmut.wcmanager.entities.TeamPerformance;

import java.util.Comparator;
import java.util.Random;

public class PerformanceOrder implements Comparator<TeamPerformance> {
    private static Random random = new Random();

    @Override
    public int compare(TeamPerformance teamA, TeamPerformance teamB) {
        // Compare score
        int scoreDiff = teamB.getScore() - teamA.getScore();
        if (scoreDiff != 0) {
            return scoreDiff;
        }

        // Compare goal diff
        int goalDiff = teamB.getGoalDiff() - teamA.getGoalDiff();
        if (goalDiff != 0) {
            return goalDiff;
        }

        // Compare card
        int cardDiff = teamB.getYellowCard() - teamA.getYellowCard();
        if (cardDiff != 0) {
            return cardDiff;
        }

        // Get versus match
        TeamInMatch[] twoTeamInVersusMatch = teamA.getGroup().getVersusHistory(teamA.getTeam(), teamB.getTeam());
        if (twoTeamInVersusMatch == null) {
            return 0;
        }

        TeamInMatch teamAInMatch = twoTeamInVersusMatch[0];
        TeamInMatch teamBInMatch = twoTeamInVersusMatch[1];

        // Compare versus history
        int versusDiff = teamBInMatch.getGoalFor() - teamAInMatch.getGoalFor();
        if (versusDiff != 0) {
            return versusDiff;
        }

        // Randomly choose which team to get higher order
        boolean teamBHigherOrder = random.nextBoolean();
        if (teamBHigherOrder) {
            return 1;
        }

        return -1;
    }

}
