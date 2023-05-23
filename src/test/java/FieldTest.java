import notsimcity.Field;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FieldTest {
    @Test
    void TestConstructorGrass(){
        final Field field = new Field(1,1,1,1,1,1,false);
        assertNotNull(field);
    }
    @Test
    void TestConstructorBuilding(){
        final Field field = new Field(1,1,1,1,1,1,false,new ImageIcon("grass.png").getImage());
        assertNotNull(field);
    }
}
