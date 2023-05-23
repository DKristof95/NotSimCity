import notsimcity.Field;
import notsimcity.University;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UniversityTest {
    @Test
    void TestConstructor(){
        final University u = new University(new Field(1,1,1,1,1,1,false));
        assertNotNull(u);
    }
}