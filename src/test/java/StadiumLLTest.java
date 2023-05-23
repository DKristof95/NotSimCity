import notsimcity.Field;
import notsimcity.StadiumLL;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StadiumLLTest {
    @Test
    void TestConstructor(){
        final StadiumLL s = new StadiumLL(new Field(1,1,1,1,1,1,false));
        assertNotNull(s);
    }
}
