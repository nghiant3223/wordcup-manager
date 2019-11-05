package uni.hcmut.wcmanager.entities;

import uni.hcmut.wcmanager.enums.GroupName;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private GroupName name;
    private List<Team> teams;
    private List<TeamPerfInGroup> teamPerfs;

    public Group(GroupName name) {
        this.name = name;

        teams = new ArrayList<>();
        teamPerfs = new ArrayList<>();
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

    public List<TeamPerfInGroup> getTeamPerfs() {
        return teamPerfs;
    }

    public void setTeamPerfs(List<TeamPerfInGroup> teamPerfs) {
        this.teamPerfs = teamPerfs;
    }

    public Team getTeamByPlace(int place) {
        return null;
    }
}
