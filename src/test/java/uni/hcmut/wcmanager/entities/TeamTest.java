
package uni.hcmut.wcmanager.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.entities.Player;
import uni.hcmut.wcmanager.entities.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamTest {
    Player player;
    Team team;

    @Before
    public void init() {
        player = new Player();
        team = new Team();
    }

    @Test
    public void Test_getID(){
        team.setId(1);
        int id = team.getId();
        Assert.assertEquals(1, id);
    }

    @Test
    public void Test_getWrongID(){
        team.setId(1);
        int id = team.getId();
        Assert.assertNotEquals(2, id);
    }

    @Test
    public void Test_getName(){
        team.setName("Viet Nam");
        String name = team.getName();
        Assert.assertEquals("Viet Nam", name);
    }

    @Test
    public void Test_getWrongName(){
        team.setName("Viet Nam");
        String name = team.getName();
        Assert.assertNotEquals("Thai Lan", name);
    }

    @Test
    public void Test_getFifaCode(){
        team.setFifaCode("E053");
        String fifaCode = team.getFifaCode();
        Assert.assertEquals("E053", fifaCode);
    }

    @Test
    public void Test_getWrongFifaCode(){
        team.setFifaCode("E053");
        String fifaCode = team.getFifaCode();
        Assert.assertNotEquals("E0532", fifaCode);
    }

    @Test
    public void Test_getListPlayer(){
        Player player1 = new Player();
        Player player2 = new Player();

        player1.setFullname("Nguyen Trong Nghia");
        player1.setGoalCount(3);

        player2.setFullname("Tran Dang Khoi");
        player2.setGoalCount(4);

        List<Player> playerListExpect = new ArrayList<Player>();
        playerListExpect.add(player1);
        playerListExpect.add(player2);

        team.setPlayers(playerListExpect);
        List<Player> playerListActual = team.getPlayers();

        Assert.assertEquals(2, playerListActual.size());
        Assert.assertSame(playerListActual, playerListExpect);
    }

    @Test
    public void Test_getWrongListPlayer(){
        Player player1 = new Player();
        Player player2 = new Player();

        player1.setFullname("Nguyen Trong Nghia");
        player1.setGoalCount(3);

        player2.setFullname("Tran Dang Khoi");
        player2.setGoalCount(4);

        List<Player> playerListExpect = new ArrayList<Player>();
        playerListExpect.add(player1);
        playerListExpect.add(player2);

        List<Player> playerListExpectWrong = new ArrayList<Player>();
        playerListExpectWrong.add(player1);

        team.setPlayers(playerListExpect);
        List<Player> playerListActual = team.getPlayers();

        Assert.assertNotSame(playerListExpectWrong, playerListActual);
    }

}
