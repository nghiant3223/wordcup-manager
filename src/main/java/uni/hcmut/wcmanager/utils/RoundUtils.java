package uni.hcmut.wcmanager.utils;

import uni.hcmut.wcmanager.entities.Match;
import uni.hcmut.wcmanager.entities.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundUtils {
    public static Map<Integer, Team[]> getKnockoutRoundResult(List<Match> matches) {
        Map<Integer, Team[]> result = new HashMap<>();

        for (int i = 0; i < matches.size(); i++) {
            Match match = matches.get(i);
            Team[] winner = {match.getWinner().getTeam()};
            result.put(i, winner);
        }

        return result;
    }

    public static Map<Integer, Team[]> getFinalResult(Match match) {
        Map<Integer, Team[]> result = new HashMap<>();

        Team[] winner = {match.getWinner().getTeam()};
        result.put(0, winner);

        return result;
    }
}
