import notsimcity.Field;
import notsimcity.PowerPlantLL;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PowerPlantLLTest {
    @Test
    void TestConstructor(){
        final PowerPlantLL pp = new PowerPlantLL(new Field(1,1,1,1,1,1,false));
        assertNotNull(pp);
    }
}
