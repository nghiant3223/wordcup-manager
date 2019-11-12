package uni.hcmut.wcmanager.enums;

import org.junit.Assert;
import org.junit.Test;

public class GroupNameTest {
    @Test
    public void test_getID_groupA(){
        GroupName group = GroupName.A;
        int id = group.getId();
        Assert.assertEquals(1, id);
    }

    @Test
    public void test_getID_groupB(){
        GroupName group = GroupName.B;
        int id = group.getId();
        Assert.assertEquals(2, id);
    }

    @Test
    public void test_getID_groupC(){
        GroupName group = GroupName.C;
        int id = group.getId();
        Assert.assertEquals(3, id);
    }

    @Test
    public void test_getID_groupD(){
        GroupName group = GroupName.D;
        int id = group.getId();
        Assert.assertEquals(4, id);
    }

    @Test
    public void test_getID_groupE(){
        GroupName group = GroupName.E;
        int id = group.getId();
        Assert.assertEquals(5, id);
    }

    @Test
    public void test_getID_groupF(){
        GroupName group = GroupName.F;
        int id = group.getId();
        Assert.assertEquals(6, id);
    }

    @Test
    public void test_getID_groupG(){
        GroupName group = GroupName.G;
        int id = group.getId();
        Assert.assertEquals(7, id);
    }

    @Test
    public void test_getID_groupH(){
        GroupName group = GroupName.H;
        int id = group.getId();
        Assert.assertEquals(8, id);
    }

    @Test
    public void test_Wrong_getID_groupH(){
        GroupName group = GroupName.H;
        int id = group.getId();
        Assert.assertNotEquals(1, id);
    }
}
