import notsimcity.Field;
import notsimcity.PowerPlantUR;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PowerPlantURTest {
    @Test
    void TestConstructor(){
        final PowerPlantUR pp = new PowerPlantUR(new Field(1,1,1,1,1,1,false));
        assertNotNull(pp);
    }
}
