package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.enums.GroupName;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.randomizers.EventGenerator;

import java.util.ArrayList;
import java.util.List;

public class Group {
    static {
        matches = new int[][]{{0, 1}, {2, 3}, {0, 3}, {1, 2}, {1, 3}, {2, 0}};
    }

    private GroupName name;
    private List<Team> teams;
    private List<TeamPerformanceInGroup> teamPerformances;
    private static int[][] matches;

    public Group(GroupName name) {
        this.name = name;

        teams = new ArrayList<>();
        teamPerformances = new ArrayList<>();
    }

    public GroupName getName() {
        return name;
    }

    public void setName(GroupName name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<TeamPerformanceInGroup> getTeamPerfs() {
        return teamPerformances;
    }

    public void setTeamPerfs(List<TeamPerformanceInGroup> teamPerfs) {
        this.teamPerformances = teamPerfs;
    }

    public Team getTeamByPlace(int place) {
        return null;
    }

    public void run() {
        for (int[] pair : matches) {
            Team home = teams.get(pair[0]);
            Team away = teams.get(pair[1]);

            Match match = new Match(home, away, RoundName.GROUP_STAGE);
            EventGenerator eventGenerator = new EventGenerator();
            match.start(eventGenerator);
            // TODO: Update team performance
        }
    }
}
