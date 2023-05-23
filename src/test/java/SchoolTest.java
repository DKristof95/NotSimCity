import notsimcity.Field;
import notsimcity.School;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SchoolTest {
    @Test
    void TestConstructor(){
        final School s = new School(new Field(1,1,1,1,1,1,false));
        assertNotNull(s);
    }
    @Test
    void TestConstructorRotated(){
        final School s = new School(new Field(1,1,1,1,1,1,false),true);
        assertNotNull(s);
    }
}
