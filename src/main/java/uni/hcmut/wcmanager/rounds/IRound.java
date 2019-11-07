package uni.hcmut.wcmanager.rounds;

import uni.hcmut.wcmanager.entities.Team;
import uni.hcmut.wcmanager.enums.RoundName;

import java.util.Map;

public interface IRound {
    public void run();

    public Map<Integer, Team[]> getResult();

    public RoundName getName();
}
