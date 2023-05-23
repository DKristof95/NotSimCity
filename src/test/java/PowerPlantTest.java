import notsimcity.Field;
import notsimcity.Police;
import notsimcity.PowerPlant;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PowerPlantTest {
    private static final int CELL_SIZE = 50;
    private int Width = 1500;
    private int Height = 800;
    @Test
    void TestConstructor(){

        final PowerPlant pp = new PowerPlant(new Field(1,1,1,1,1,1,false));
        assertNotNull(pp);
    }
    /*@Test
    void TestCheckPowerNeed(){
        int a = Height/CELL_SIZE;
        int b = Width/CELL_SIZE;

        ArrayList<ArrayList<Field>> Grid = new ArrayList<>();


        for (int col = 0; col < a; col++) {
            ArrayList<Field> rows = new ArrayList<Field>();
            for (int row = 0; row < b; row++) {
                    Field field = new Field(CELL_SIZE, CELL_SIZE, row * CELL_SIZE, col * CELL_SIZE, 0, 0,false);
                rows.add(field);
            }
            Grid.add(rows);
        }

        Grid.get(0).set(0, new Police(Grid.get(0).get(0)));

        final PowerPlant pp = new PowerPlant(Grid.get(1).get(0));
        Grid.get(1).set(0,pp);

        pp.checkPowerNeed(1,0,Grid);

        assertTrue(Grid.get(0).get(0).hasPower);

    }*/
}
