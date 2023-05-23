import notsimcity.Sprite;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpriteTest {
    @Test
    void TestConstructor(){
        final Sprite sprite = new Sprite(1,1,1,1,new ImageIcon("grass.jpg").getImage());
        assertNotNull(sprite);
    }
}
