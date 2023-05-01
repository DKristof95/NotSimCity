import notsimcity.Field;
import notsimcity.ForestNew;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ForestNewTest {

    @Test
    void TestConstructor(){
        final ForestNew tower = new ForestNew(new Field(0,0,0,0,0,0,false), 0, 0, 0);
        assertNotNull(tower);
    }

}
