package uni.hcmut.wcmanager.utils;

import org.junit.Assert;
import org.junit.Test;
import uni.hcmut.wcmanager.enums.MatchType;
import uni.hcmut.wcmanager.enums.RoundName;

public class MatchUtilsTest {
    @Test
    public void test_getMatch_DRAWABLE(){
        Assert.assertEquals(MatchType.DRAWABLE, MatchUtils.getType(RoundName.GROUP_STAGE));
    }

    @Test
    public void test_getMatch_1_16(){
        Assert.assertEquals(MatchType.KNOCKOUT, MatchUtils.getType(RoundName.ROUND_OF_SIXTEEN));
    }

    @Test
    public void test_getMatch_1_8(){
        Assert.assertEquals(MatchType.KNOCKOUT, MatchUtils.getType(RoundName.QUARTER_FINAL_ROUND));
    }

    @Test
    public void test_getMatch_1_4(){
        Assert.assertEquals(MatchType.KNOCKOUT, MatchUtils.getType(RoundName.SEMI_FINAL_ROUND));
    }

    @Test
    public void test_getMatch_final(){
        Assert.assertEquals(MatchType.KNOCKOUT, MatchUtils.getType(RoundName.FINAL));
    }
}
