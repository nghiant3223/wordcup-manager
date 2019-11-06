package uni.hcmut.wcmanager.randomizers;

import uni.hcmut.wcmanager.entities.Group;
import uni.hcmut.wcmanager.entities.Team;
import uni.hcmut.wcmanager.enums.GroupName;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupStageDraw {
    private static Random random = new Random();

    static List<Integer> getDraws() {
        List<Integer> draws = new ArrayList<>();

        for (int i = 0; i < 32; i++) {
            draws.add(i);
        }

        return draws;
    }

    public static List<Group> draw(List<Team> teams) {
        if (teams.size() != 32) {
            throw new InvalidParameterException("There must be 32 teams");
        }

        List<Group> groups = new ArrayList<Group>() {{
            add(new Group(GroupName.A));
            add(new Group(GroupName.B));
            add(new Group(GroupName.C));
            add(new Group(GroupName.D));
            add(new Group(GroupName.E));
            add(new Group(GroupName.F));
            add(new Group(GroupName.G));
            add(new Group(GroupName.H));
        }};

        List<Integer> draws = getDraws();


        for (Team team : teams) {
            int index = random.nextInt(draws.size());
            int draw = draws.get(index);

            Group group = groups.get(draw / 4);
            group.addTeam(team);

            draws.remove(index);
        }

        return groups;
    }
}
