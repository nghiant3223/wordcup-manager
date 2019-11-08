package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.constants.TemplateString;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.randomizers.EventGenerator;
import uni.hcmut.wcmanager.randomizers.PenaltyShootoutGenerator;

public class KnockoutMatch extends Match {
    public KnockoutMatch(Team homeTeam, Team awayTeam, RoundName roundName) {
        super(homeTeam, awayTeam, roundName);
        this.roundName = roundName;
    }

    public void start() {
        EventGenerator generator = new EventGenerator();
        enterMainHalvesAndExtraHalves(generator);

        // If match's already finished due to lack of players
        // or winner is determined in main halves or extra halves
        if (isFinished()) {
            setWinner();
            finish();
            return;
        }

        // If there is a team who have higher goal than the other after 90min or 105min or 120min
        if (homeTeam.getGoalFor() != awayTeam.getGoalFor()) {
            setFinished();
            setWinner();
            finish();
            return;
        }

        penaltyResult = new int[2];
        homeTeam.initPenaltyShootoutHistory();
        awayTeam.initPenaltyShootoutHistory();

        PenaltyShootoutGenerator simulator = new PenaltyShootoutGenerator();
        enterPenaltyShootout(simulator);

        penaltyResult[0] = homeTeam.getPenaltyShootScore();
        penaltyResult[1] = awayTeam.getPenaltyShootScore();

        setFinished();
        setWinner();
        finish();
    }

    private void enterMainHalvesAndExtraHalves(EventGenerator generator) {
        generator.playEventForKnockoutMatch(this);
    }

    private void enterPenaltyShootout(PenaltyShootoutGenerator simulator) {
        simulator.playPenaltyShootout(this);
    }

    protected void finish() {
        super.finish();

        System.out.println(String.format(TemplateString.MATCH_RESULT_TEMPLATE,
                homeTeam.getTeam().getName(), homeTeam.getGoalFor(),
                awayTeam.getGoalFor(), awayTeam.getTeam().getName()));

        if (penaltyResult != null) {
            StringBuilder homeTeamPenaltyHistory = new StringBuilder();
            StringBuilder awayTeamPenaltyHistory = new StringBuilder();

            for (int i = 0; i < homeTeam.getPenaltyShootoutHistory().size(); i++) {
                homeTeamPenaltyHistory.append(homeTeam.getPenaltyShootoutHistory().get(i) ? "o" : "x");
                awayTeamPenaltyHistory.append(awayTeam.getPenaltyShootoutHistory().get(i) ? "o" : "x");
            }

            System.out.println(String.format(TemplateString.PENALTY_RESULT_TEMPLATE,
                    homeTeamPenaltyHistory.toString(), awayTeamPenaltyHistory.toString()));
        }

        System.out.println(String.format(TemplateString.WINNER_RESULT_TEMPLATE,
                "WINNER", getWinner().getTeam().getName()));
        System.out.println(TemplateString.MATCH_SEPARATOR);
    }

    @Override
    public void setWinner() {
        if (!isFinished) {
            throw new IllegalStateException("Cannot set winner if match's not finished yet");
        }

        if (homeTeam.getGoalFor() > awayTeam.getGoalFor()) {
            winner = homeTeam;
            return;
        }

        if (homeTeam.getGoalFor() < awayTeam.getGoalFor()) {
            winner = awayTeam;
            return;
        }

        if (homeTeam.getPenaltyShootScore() > awayTeam.getPenaltyShootScore()) {
            winner = homeTeam;
        } else {
            winner = awayTeam;
        }
    }
}