import notsimcity.Field;
import notsimcity.PowerPlantLR;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PowerPlantLRTest {
    @Test
    void TestConstructor(){
        final PowerPlantLR pp = new PowerPlantLR(new Field(0,0,0,0,0,0,false));
        assertNotNull(pp);
    }
}
