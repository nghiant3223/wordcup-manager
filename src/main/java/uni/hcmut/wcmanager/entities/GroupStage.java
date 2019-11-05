package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.enums.RoundName;

import java.util.List;
import java.util.Map;

public class GroupStage implements Round {
    private List<Group> groups;
    private RoundName name;

    public GroupStage(List<Group> groups) {
        this.groups = groups;
        this.name = RoundName.GROUP_STAGE;
    }

    @Override
    public void run() {

    }

    @Override
    public Map<Integer, Team[]> getResult() {
        return null;
    }

    @Override
    public RoundName getName() {
        return null;
    }
}
