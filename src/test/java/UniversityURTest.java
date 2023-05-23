import notsimcity.Field;
import notsimcity.UniversityUR;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UniversityURTest {
    @Test
    void TestConstructor(){
        final UniversityUR u = new UniversityUR(new Field(1,1,1,1,1,1,false));
        assertNotNull(u);
    }
}