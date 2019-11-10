package uni.hcmut.wcmanager.rounds;

import uni.hcmut.wcmanager.entities.Group;
import uni.hcmut.wcmanager.entities.Team;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.rounds.IRound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupStage implements IRound {
    private List<Group> groups;
    private RoundName name;

    public GroupStage(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public void run() {
        for (Group group : groups) {
            System.out.printf("===== Group %s started =====\n", group.getName());
            group.run();
            System.out.printf("===== Group %s ended =====\n\n", group.getName());
        }

        for (Group group : groups) {
            group.displayResult();
        }
    }

    @Override
    public Map<Integer, Team[]> getResult() {
        Map<Integer, Team[]> result = new HashMap<>();

        for (Group g : groups) {
            Team[] twoBestTeams = {g.getTeamByPlace(0), g.getTeamByPlace(1)};
            result.put(g.getName().getId(), twoBestTeams);
        }

        return result;
    }

    @Override
    public RoundName getName() {
        return RoundName.GROUP_STAGE;
    }
}
