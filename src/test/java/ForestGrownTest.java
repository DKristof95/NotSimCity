import notsimcity.Field;
import notsimcity.ForestGrown;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ForestGrownTest {
    @Test
    void TestConstructor(){
        final ForestGrown forest = new ForestGrown(new Field(0,0,0,0,0,0,false));
        assertNotNull(forest);
    }
}
