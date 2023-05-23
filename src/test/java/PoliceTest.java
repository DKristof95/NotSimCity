import notsimcity.Field;
import notsimcity.Police;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PoliceTest {
    @Test
    void TestConstructor(){
        final Police police = new Police(new Field(1,1,1,1,1,1,false));
        assertNotNull(police);
    }
}
