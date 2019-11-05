package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.enums.RoundName;

import java.util.Map;

public class GroupStage implements Round {
    private Group[] groups;
    private RoundName name;

    public GroupStage(Group[] groups) {
        this.groups = groups;

        this.name = RoundName.GROUP_STAGE;
    }

    @Override
    public void run() {

    }

    public Map<Integer, Team[]> getResult() {
        return null;
    }

    @Override
    public RoundName getName() {
        return null;
    }
}
