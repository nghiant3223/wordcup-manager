package uni.hcmut.wcmanager.rounds;

import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.Team;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.utils.RoundUtils;

import java.util.Map;

public class Final implements IRound {
    private Match match;

    public Final(Match match) {
        this.match = match;
    }

    @Override
    public void run() {
        match.setRoundName(RoundName.FINAL);
        match.start();
    }

    @Override
    public Map<Integer, Team[]> getResult() {
        return RoundUtils.getFinalResult(match);
    }

    @Override
    public RoundName getName() {
        return RoundName.FINAL;
    }
}
