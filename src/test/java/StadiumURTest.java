import notsimcity.Field;
import notsimcity.StadiumUR;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StadiumURTest {
    @Test
    void TestConstructor(){
        final StadiumUR s = new StadiumUR(new Field(1,1,1,1,1,1,false));
        assertNotNull(s);
    }
}
