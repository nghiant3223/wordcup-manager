package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.constants.GameRule;
import uni.hcmut.wcmanager.constants.TemplateString;
import uni.hcmut.wcmanager.enums.GroupName;
import uni.hcmut.wcmanager.enums.RoundName;
import uni.hcmut.wcmanager.utils.DbUtils;
import uni.hcmut.wcmanager.utils.PerformanceOrder;

import java.util.*;

public class Group {
    private GroupName name;
    private List<Team> teams;
    private List<Match> matches;
    private List<TeamPerformance> teamPerformances;

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
        TeamPerformance teamPerformance = new TeamPerformance(team, this);
        teamPerformances.add(teamPerformance);
        teams.add(team);
    }

    public TeamInMatch[] getVersusHistory(Team t1, Team t2) {
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
        if (place >= GameRule.TEAM_COUNT_IN_GROUP) {
            return null;
        }

        return teamPerformances.get(place).getTeam();
    }

    public void displayResult() {
        System.out.printf(TemplateString.GROUP_NAME_TEMPLATE, getName());

        System.out.println(TemplateString.GROUP_SEPARATOR);
        System.out.print(TemplateString.HEADER);
        System.out.println(TemplateString.GROUP_SEPARATOR);

        for (TeamPerformance tp : teamPerformances) {
            System.out.printf(TemplateString.DATA_TEMPLATE,
                    tp.getTeam().getName(), tp.getMatchPlayed(),
                    tp.getWinCount(), tp.getDrawCount(), tp.getLostCount(),
                    tp.getGoalFor(), tp.getGoalAgainst(), tp.getGoalDiff(),
                    tp.getYellowCard(), tp.getScore());
        }

        System.out.println(TemplateString.GROUP_SEPARATOR);
    }

    public void run() {
        for (int[] pair : GameRule.MATCH_IN_GROUP) {
            Team home = teams.get(pair[0]);
            Team away = teams.get(pair[1]);

            // Start match for two teams
            Match match = new DrawableMatch(home, away);
            match.setRoundName(RoundName.GROUP_STAGE);
            match.start();

            // Update team's performance in group
            for (TeamPerformance tp : teamPerformances) {
                if (match.checkTeamCompetesInMatch(tp.getTeam())) {
                    tp.update(match);
                }
            }

            // Add current match to `matches`
            matches.add(match);

            // Sort teams's performances in group
            teamPerformances.sort(new PerformanceOrder());
        }

        for (TeamPerformance tp : teamPerformances) {
            DbUtils.persistTeamPerformance(tp);
        }
    }
}
