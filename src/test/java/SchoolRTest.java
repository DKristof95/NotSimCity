import notsimcity.Field;
import notsimcity.SchoolR;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SchoolRTest {
    @Test
    void TestConstructor(){
        final SchoolR s = new SchoolR(new Field(1,1,1,1,1,1,false));
        assertNotNull(s);
    }
    @Test
    void TestConstructorRotated(){
        final SchoolR s = new SchoolR(new Field(1,1,1,1,1,1,false),true);
        assertNotNull(s);
    }
}
