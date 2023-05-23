import notsimcity.Zone;
import notsimcity.ZoneType;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ZoneTest {
    @Test
    void TestConstructor(){
        final Zone z = new Zone(1,1,1,1,new ImageIcon("grass.jpg").getImage(), ZoneType.ResidentalArea);
        assertNotNull(z);
    }
}
