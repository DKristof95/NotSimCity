import notsimcity.Citizen;
import notsimcity.House;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CitizenTest {
    @Test
    void TestConstructor(){
        final Citizen citizen = new Citizen(new House(1,1,1,1,1));
        assertNotNull(citizen);
    }
}
