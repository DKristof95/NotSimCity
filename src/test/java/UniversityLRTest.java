import notsimcity.Field;
import notsimcity.UniversityLR;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UniversityLRTest {
    @Test
    void TestConstructor(){
        final UniversityLR u = new UniversityLR(new Field(1,1,1,1,1,1,false));
        assertNotNull(u);
    }
}