package uni.hcmut.wcmanager.randomizers;

import uni.hcmut.wcmanager.constants.MatchConstants;
import uni.hcmut.wcmanager.entities.Player;
import uni.hcmut.wcmanager.entities.Team;

import java.util.*;

public class TeamRandomizer {
    public static List<ArrayList<Player>> createLineup(Team team) {
        ArrayList<Player> allPlayers = new ArrayList<>(team.getPlayers());
        ArrayList<Player> startings = new ArrayList<>();
        ArrayList<Player> substitutes = new ArrayList<>();
        List<ArrayList<Player>> lineup = new ArrayList<>();

        Random random = new Random();

        while (startings.size() < MatchConstants.STARTING_PLAYER_COUNT) {
            int index = random.nextInt(allPlayers.size());
            Player chosen = allPlayers.get(index);
            startings.add(chosen);
            allPlayers.remove(chosen);
        }

        while (substitutes.size() < MatchConstants.SUBSTITUTE_PLAYER_COUNT) {
            int index = random.nextInt(allPlayers.size());
            Player chosen = allPlayers.get(index);
            substitutes.add(chosen);
            allPlayers.remove(chosen);
        }

        lineup.add(startings);
        lineup.add(substitutes);

        return lineup;
    }
}
