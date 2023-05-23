import notsimcity.Field;
import notsimcity.Pole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PoleTest {
    @Test
    void TestConstructor(){
        final Pole pole = new Pole(new Field(1,1,1,1,1,1,false));
        assertNotNull(pole);
    }
}
