import notsimcity.Field;
import notsimcity.Stadium;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StadiumTest {
    @Test
    void TestConstructor(){
        final Stadium s = new Stadium(new Field(1,1,1,1,1,1,false));
        assertNotNull(s);
    }
}
