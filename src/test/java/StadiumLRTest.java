import notsimcity.Field;
import notsimcity.StadiumLR;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StadiumLRTest {
    @Test
    void TestConstructor(){
        final StadiumLR s = new StadiumLR(new Field(1,1,1,1,1,1,false));
        assertNotNull(s);
    }
}
