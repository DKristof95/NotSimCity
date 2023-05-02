import notsimcity.Field;
import notsimcity.Road;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoadTest {
    @Test
    void TestConstructor(){
        final Road road = new Road(new Field(0,0,0,0,0,0,true), new ImageIcon("ut_viz.png").getImage());
        assertNotNull(road);
    }
}
