package uni.hcmut.wcmanager.enums;

import org.junit.Assert;
import org.junit.Test;

public class RoundNameTest {
    @Test
    public void test_getID_GROUP_STAGE(){
        RoundName Round = RoundName.GROUP_STAGE;
        int id = Round.getId();
        Assert.assertEquals(1, id);
    }

    @Test
    public void test_getID_ROUND_OF_SIXTEEN(){
        RoundName Round = RoundName.ROUND_OF_SIXTEEN;
        int id = Round.getId();
        Assert.assertEquals(2, id);
    }

    @Test
    public void test_getID_QUARTER_FINAL_ROUND(){
        RoundName Round = RoundName.QUARTER_FINAL_ROUND;
        int id = Round.getId();
        Assert.assertEquals(3, id);
    }

    @Test
    public void test_getID_SEMI_FINAL_ROUND(){
        RoundName Round = RoundName.SEMI_FINAL_ROUND;
        int id = Round.getId();
        Assert.assertEquals(4, id);
    }

    @Test
    public void test_getID_FINAL(){
        RoundName Round = RoundName.FINAL;
        int id = Round.getId();
        Assert.assertEquals(5, id);
    }

    @Test
    public void test_Wrong_getID_FINAL(){
        RoundName Round = RoundName.FINAL;
        int id = Round.getId();
        Assert.assertNotEquals(3, id);
    }
}
