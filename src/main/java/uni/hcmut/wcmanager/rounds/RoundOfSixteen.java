package uni.hcmut.wcmanager.rounds;

import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.Team;
import uni.hcmut.wcmanager.entities.TeamInMatch;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.rounds.IRound;
import uni.hcmut.wcmanager.utils.RoundUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundOfSixteen implements IRound {
    private List<Match> matches;

    public RoundOfSixteen(List<Match> matches) {
        this.matches = matches;
    }

    @Override
    public void run() {
        for (Match match : matches) {
            match.setRoundName(RoundName.ROUND_OF_SIXTEEN);
            match.start();
        }
    }

    @Override
    public Map<Integer, Team[]> getResult() {
        return RoundUtils.getKnockoutRoundResult(matches);
    }

    @Override
    public RoundName getName() {
        return RoundName.ROUND_OF_SIXTEEN;
    }
}
