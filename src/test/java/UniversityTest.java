import notsimcity.Field;
import notsimcity.University;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UniversityTest {
    @Test
    void TestConstructor(){
        final University u = new University(new Field(0,0,0,0,0,0,false));
        assertNotNull(u);
    }
}