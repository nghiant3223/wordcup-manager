package uni.hcmut.wcmanager.randomizers;

import uni.hcmut.wcmanager.constants.GameRule;
import uni.hcmut.wcmanager.constants.MatchRule;
import uni.hcmut.wcmanager.entities.Player;
import uni.hcmut.wcmanager.entities.PlayerInMatch;
import uni.hcmut.wcmanager.entities.Team;

import java.util.*;

public class CoachSimulator {
    private static Random random = new Random();

    public static List<ArrayList<Player>> presentMatchLineup(Team team) {
        ArrayList<Player> allPlayers = new ArrayList<>(team.getPlayers());
        ArrayList<Player> startings = new ArrayList<>();
        ArrayList<Player> substitutes = new ArrayList<>();
        List<ArrayList<Player>> lineup = new ArrayList<>();

        int randomRange = MatchRule.MAX_STARTING_PLAYER_COUNT - MatchRule.MIN_STARTING_PLAYER_COUNT;
        int startingPlayerCount = MatchRule.MIN_STARTING_PLAYER_COUNT + random.nextInt(randomRange);
        int substitutePlayerCount = 1 + random.nextInt(MatchRule.MAX_SUBSTITUTE_PLAYER_COUNT);

        while (startings.size() < startingPlayerCount) {
            int index = random.nextInt(allPlayers.size());
            Player chosen = allPlayers.get(index);
            startings.add(chosen);
            allPlayers.remove(chosen);
        }

        while (substitutes.size() < substitutePlayerCount) {
            int index = random.nextInt(allPlayers.size());
            Player chosen = allPlayers.get(index);
            substitutes.add(chosen);
            allPlayers.remove(chosen);
        }

        lineup.add(startings);
        lineup.add(substitutes);

        return lineup;
    }

    // Choose which player to go onto the field
    public static PlayerInMatch chooseRandomPlayer(List<PlayerInMatch> players) {
        int playerIndex = random.nextInt(players.size());
        return players.get(playerIndex);
    }
}
