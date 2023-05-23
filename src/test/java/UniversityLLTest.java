import notsimcity.Field;
import notsimcity.UniversityLL;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UniversityLLTest {
    @Test
    void TestConstructor(){
        final UniversityLL u = new UniversityLL(new Field(1,1,1,1,1,1,false));
        assertNotNull(u);
    }
}
