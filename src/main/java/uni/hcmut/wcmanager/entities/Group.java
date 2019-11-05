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

    public List<Team> getTeams() {
        return teams;
    }

    public void addTeam(Team team) {
        TeamPerformanceInGroup teamPerf = new TeamPerformanceInGroup(team);
        teamPerformances.add(teamPerf);
        teams.add(team);
    }

    public List<TeamPerformanceInGroup> getTeamPerformances() {
        return teamPerformances;
    }

    public Team getTeamByPlace(int place) {
        return null;
    }

    public void showOverallResult() {
        String headerTemplate = "|%-15s|%-3s|%-3s|%-3s|%-3s|%-3s|%-3s|%-3s|%-3s|%-2s|\n";
        String rowTemplate = "|%-15s|%-3d|%-3d|%-3d|%-3d|%-3d|%-3d|%-3s|%-3s|%-2s|\n";

        System.out.printf("\nGroup %s\n", getName());
        System.out.println("-".repeat(52));
        System.out.printf(headerTemplate, "Team", "MP", "W", "D", "L", "GF", "GA", "GD", "YC", "S");
        System.out.println("-".repeat(52));

        for (TeamPerformanceInGroup perf : teamPerformances) {
            System.out.printf(rowTemplate, perf.getTeam().getName(), perf.getMatchPlayed(),
                    perf.getWinCount(), perf.getDrawCount(), perf.getLostCount(),
                    perf.getGoalFor(), perf.getGoalAgainst(),
                    perf.getGoalDiff(), perf.getYellowCard(), perf.getScore());
        }

        System.out.println("-".repeat(52));
    }

    public void run() {
        for (int[] pair : matches) {
            Team home = teams.get(pair[0]);
            Team away = teams.get(pair[1]);

            Match match = new Match(home, away, RoundName.GROUP_STAGE);
            EventGenerator eventGenerator = new EventGenerator();
            match.start(eventGenerator);

            for (TeamPerformanceInGroup teamPerf : teamPerformances) {
                if (teamPerf.getTeam() == match.getHomeTeam().getTeam()
                        || teamPerf.getTeam() == match.getAwayTeam().getTeam()) {
                    teamPerf.update(match);
                }
            }
        }
    }
}
