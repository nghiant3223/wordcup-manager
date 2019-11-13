package uni.hcmut.wcmanager.randomizers;

import uni.hcmut.wcmanager.constants.MatchRule;
import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.TeamInMatch;

import java.security.InvalidParameterException;
import java.util.Random;

public class PenaltyShootoutGenerator {
    private static Random random = new Random();

    private boolean[][] shootout;

    public PenaltyShootoutGenerator(boolean[][] shootout) {
        this.shootout = shootout;
    }

    public PenaltyShootoutGenerator() {

    }

    public void playPenaltyShootout(Match match) {
        TeamInMatch homeTeam = match.getHomeTeam();
        TeamInMatch awayTeam = match.getAwayTeam();

        if (shootout != null) {
            for (int i = 0; i < shootout.length; i++) {
                examineEachShootoutTurn(homeTeam, awayTeam, i);

                int scoreDiff = homeTeam.getPenaltyShootScore() - awayTeam.getPenaltyShootScore();
                int remainingTurn = MatchRule.MAX_PENALTY_SHOOTOUT - i - 1;
                if (scoreDiff > remainingTurn) {
                    return;
                }
            }

            // If penalty shootout result is determined in 5 turn but there's still turn provided
            if (homeTeam.getPenaltyShootScore() != awayTeam.getPenaltyShootScore()
                    && shootout.length > MatchRule.MAX_PENALTY_SHOOTOUT) {
                throw new InvalidParameterException("Redundant shootout turn");
            }

            for (int i = MatchRule.MAX_PENALTY_SHOOTOUT; i < shootout.length; i++) {
                examineEachShootoutTurn(homeTeam, awayTeam, i);

                // If penalty shootout result is determined in 5 turns but there's still turn provided
                if (homeTeam.getPenaltyShootScore() != awayTeam.getPenaltyShootScore()
                        && i != shootout.length - 1) {
                    throw new InvalidParameterException("Redundant shootout turn");
                }
            }

            // If all penalty shootout turns is considered but winner is not determined yet
            if (homeTeam.getPenaltyShootScore() != awayTeam.getPenaltyShootScore()) {
                throw new InvalidParameterException("Not enough shootout turn provided");
            }

            return;
        }

        TeamInMatch first = random.nextBoolean() ? match.getHomeTeam() : match.getAwayTeam();
        TeamInMatch second = first != match.getHomeTeam() ? match.getHomeTeam() : match.getAwayTeam();

        for (int i = 0; i < MatchRule.MAX_PENALTY_SHOOTOUT; i++) {
            generateEachShootoutTurn(first, second);

            int scoreDiff = first.getPenaltyShootScore() - second.getPenaltyShootScore();
            int remainingTurn = MatchRule.MAX_PENALTY_SHOOTOUT - i - 1;
            if (scoreDiff > remainingTurn) {
                return;
            }
        }

        // Keep taking turn to do penalty-kick until one has more score than the other
        while (first.getPenaltyShootScore() == second.getPenaltyShootScore()) {
            generateEachShootoutTurn(first, second);
        }
    }

    private void generateEachShootoutTurn(TeamInMatch firstTeam, TeamInMatch secondTeam) {
        boolean firstTeamPenalty = random.nextBoolean();
        boolean secondTeamPenalty = random.nextBoolean();

        firstTeam.addPenaltyShootoutHistory(firstTeamPenalty);
        secondTeam.addPenaltyShootoutHistory(secondTeamPenalty);

        if (firstTeamPenalty) {
            firstTeam.incrementPenaltyShootScore();
        }

        if (secondTeamPenalty) {
            secondTeam.incrementPenaltyShootScore();
        }
    }

    private void examineEachShootoutTurn(TeamInMatch homeTeam, TeamInMatch awayTeam, int i) {
        boolean[] round = shootout[i];

        boolean homeTeamPenalty = round[0];
        boolean awayTeamPenalty = round[1];

        homeTeam.addPenaltyShootoutHistory(homeTeamPenalty);
        awayTeam.addPenaltyShootoutHistory(awayTeamPenalty);

        if (homeTeamPenalty) {
            homeTeam.incrementPenaltyShootScore();
        }

        if (awayTeamPenalty) {
            awayTeam.incrementPenaltyShootScore();
        }
    }
}
