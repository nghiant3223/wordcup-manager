package uni.hcmut.wcmanager.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uni.hcmut.wcmanager.entities.Player;


public class PlayerTest {
    Player player;

    @Before
    public void init() {
        player = new Player();
    }

    @Test
    public void Test_getName(){
        player.setFullname("Trinh Thi Thu Thao");
        String fullName = player.getFullname();
        Assert.assertEquals("Trinh Thi Thu Thao", fullName);
    }

    @Test
    public void Test_getWrongName(){
        player.setFullname("Trinh Thi Thu Thao");
        String fullName = player.getFullname();
        Assert.assertNotEquals("Tran Dang Khoi", fullName);
    }

    @Test
    public void Test_getGoalCount(){
        player.setGoalCount(3);
        int goalCount = player.getGoalCount();
        Assert.assertEquals(3, goalCount);
    }

    @Test
    public void Test_getWrongGoalCount(){
        player.setGoalCount(3);
        int goalCount = player.getGoalCount();
        Assert.assertNotEquals(4, goalCount);
    }
}
