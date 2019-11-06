package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.enums.GroupName;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.randomizers.EventGenerator;
import uni.hcmut.wcmanager.utils.PerformanceOrder;

import java.util.*;

public class Group {
    static {
        matchPairs = new int[][]{{0, 1}, {2, 3}, {0, 3}, {1, 2}, {1, 3}, {2, 0}};
    }

    private GroupName name;
    private List<Team> teams;
    private List<TeamPerformance> teamPerformances;
    private static int[][] matchPairs;
    private List<Match> matches;

    public Group(GroupName name) {
        this.name = name;

        teams = new ArrayList<>();
        matches = new ArrayList<>();
        teamPerformances = new ArrayList<>();
    }

    public GroupName getName() {
        return name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void addTeam(Team team) {
        TeamPerformance teamPerf = new TeamPerformance(team, this);
        teamPerformances.add(teamPerf);
        teams.add(team);
    }

    public TeamInMatch[] getVersusMatch(Team t1, Team t2) {
        for (Match match : matches) {
            if (t1 == match.getHomeTeam().getTeam() && t2 == match.getAwayTeam().getTeam()) {
                return new TeamInMatch[]{match.getHomeTeam(), match.getAwayTeam()};
            }

            if (t1 == match.getAwayTeam().getTeam() && t2 == match.getHomeTeam().getTeam()) {
                return new TeamInMatch[]{match.getAwayTeam(), match.getHomeTeam()};
            }
        }

        return null;
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

        for (TeamPerformance tp : teamPerformances) {
            System.out.printf(rowTemplate, tp.getTeam().getName(), tp.getMatchPlayed(),
                    tp.getWinCount(), tp.getDrawCount(), tp.getLostCount(),
                    tp.getGoalFor(), tp.getGoalAgainst(), tp.getGoalDiff(),
                    tp.getYellowCard(), tp.getScore());
        }

        System.out.println("-".repeat(52));
    }

    public void run() {
        for (int[] pair : matchPairs) {
            Team home = teams.get(pair[0]);
            Team away = teams.get(pair[1]);

            // Start match of two teams
            Match match = new Match(home, away, RoundName.GROUP_STAGE);
            match.start(new EventGenerator());

            // Update team's performance in group
            for (TeamPerformance tp : teamPerformances) {
                if (tp.getTeam() == match.getHomeTeam().getTeam()
                        || tp.getTeam() == match.getAwayTeam().getTeam()) {
                    tp.update(match);
                }
            }

            // Add current match to `matches`
            matches.add(match);

            // Sort teams's performances in group
            teamPerformances.sort(new PerformanceOrder());
        }
    }
}
