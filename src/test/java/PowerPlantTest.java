import notsimcity.Field;
import notsimcity.PowerPlant;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PowerPlantTest {
    private static final int CELL_SIZE = 50;
    private int Width = 1500;
    private int Height = 800;
    private ArrayList<ArrayList<Field>> Grid = new ArrayList<>();
    @Test
    void TestConstructor(){
        int a = Height/CELL_SIZE;
        int b = Width/CELL_SIZE;
        for (int col = 0; col < a; col++) {
            ArrayList<Field> rows = new ArrayList<Field>();
            for (int row = 0; row < b; row++) {
                Field field = new Field(CELL_SIZE, CELL_SIZE, row * CELL_SIZE, col * CELL_SIZE, 0, 0,false);
                rows.add(field);
            }
            Grid.add(rows);
        }

        final PowerPlant pp = new PowerPlant(Grid.get(0).get(0),Grid);
        assertNotNull(pp);
    }
}
