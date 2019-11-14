package uni.hcmut.wcmanager.utils;

import org.junit.Assert;
import org.junit.Test;

public class LangUtilsTest {
    @Test
    public void test_intInRange_True(){
        Assert.assertTrue(LangUtils.intInRange(3, 2, 5));
    }

    @Test
    public void test_intInRange_True_Equal(){
        Assert.assertTrue(LangUtils.intInRange(3, 3, 5));
    }

    @Test
    public void test_intInRange_gte_False(){
        Assert.assertFalse(LangUtils.intInRange(3, 4, 5));
    }

    @Test
    public void test_intInRange_False(){
        Assert.assertFalse(LangUtils.intInRange(3, 2, 1));
    }
}
