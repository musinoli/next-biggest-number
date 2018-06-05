import org.junit.Assert;
import org.junit.Test;

public class NextSmallestNumberTest {

    @Test
    public void run() {
        assertValue(36271, 36217);
        assertValue(845, 584);
        assertValue(4317, 4173);
        assertValue(1013, -1);
        assertValue(74012, 72410);
        assertValue(6013, 3610);
        assertValue(1234, -1);
        assertValue(4321, 4312);
        assertValue(57, -1);
        assertValue(75, 57);
    }

    private void assertValue(long input, long expected) {
        Assert.assertEquals(expected, NextSmallestNumber.nextSmallest(input));
    }
}
