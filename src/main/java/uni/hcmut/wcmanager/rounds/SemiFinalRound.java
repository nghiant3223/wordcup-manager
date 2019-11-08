package uni.hcmut.wcmanager.rounds;

import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.Team;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.utils.RoundUtils;

import java.util.List;
import java.util.Map;

public class SemiFinalRound implements IRound {
    private List<Match> matches;

    public SemiFinalRound(List<Match> matches) {
        this.matches = matches;
    }

    @Override
    public void run() {
        for (Match match : matches) {
            match.setRoundName(RoundName.SEMI_FINAL_ROUND);
            match.start();
        }
    }

    @Override
    public Map<Integer, Team[]> getResult() {
        return RoundUtils.getKnockoutRoundResult(matches);
    }

    @Override
    public RoundName getName() {
        return RoundName.SEMI_FINAL_ROUND;
    }
}
