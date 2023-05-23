import notsimcity.House;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HouseTest {
    @Test
    void TestConstructor(){
        final House house = new House(1,1,1,1, 1);
        assertNotNull(house);
    }
}
