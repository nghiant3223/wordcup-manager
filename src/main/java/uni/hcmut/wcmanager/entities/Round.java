package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.enums.RoundName;

import java.util.Map;

public interface Round {
    public abstract void run();
    public abstract Map<Integer, Team[]> getResult();
    public RoundName getName();
}
