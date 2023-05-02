import notsimcity.Field;
import notsimcity.School;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SchoolTest {
    @Test
    void TestConstructor(){
        final School s = new School(new Field(0,0,0,0,0,0,false));
        assertNotNull(s);
    }
    @Test
    void TestConstructorRotated(){
        final School s = new School(new Field(0,0,0,0,0,0,false),true);
        assertNotNull(s);
    }
}
