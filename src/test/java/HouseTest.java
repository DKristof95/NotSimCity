import notsimcity.House;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HouseTest {
    @Test
    void TestConstructor(){
        final House house = new House(0,0,0,0);
        assertNotNull(house);
    }
}
