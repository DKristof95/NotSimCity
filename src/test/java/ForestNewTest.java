import notsimcity.Field;
import notsimcity.ForestNew;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ForestNewTest {

    @Test
    void TestConstructor(){
        final ForestNew forest = new ForestNew(new Field(1,1,1,1,1,1,false), 2023, 1, 1);
        assertNotNull(forest);
    }

}
