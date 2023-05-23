import notsimcity.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static notsimcity.ZoneType.ResidentalArea;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private static final int CELL_SIZE = 50;
    private static final int Width = 1616;
    private static final int Height = 930;
    @Test
    void TestConstructor(){
        final Game game = new Game(0);
        assertNotNull(game);
        assertNotNull(game.getMouseListeners());
    }
    @Test
    void TestSetGrid(){
        int a = Height/CELL_SIZE;
        int b = Width/CELL_SIZE;

        int roadNum = 0;
        int[] roadInd = {0,0};

        Game game = new Game(0);

        game.setWidth(Width);
        game.setHeight(Height);
        game.setGrid();

        for (int col = 0; col < a; col++) {
            for (int row = 0; row < b; row++) {
                if (game.getGrid().get(col).get(row).getClass().equals(Road.class)) {
                    roadNum++;
                    roadInd[0] = col;
                    roadInd[1] = row;
                }
            }
        }

        assertEquals(roadNum, 1);
        assertEquals(roadInd[1], 0);
        assertTrue(roadInd[0]>=0 && roadInd[0]<=a-1);

        assertNotNull(game.getGrid().get(0).get(0));
        assertNotNull(game.getGrid().get(a-1).get(b-1));
        assertTrue(game.getGrid().get(a-1).get(b-1).getClass().equals(Field.class));
        assertThrowsExactly(IndexOutOfBoundsException.class, () ->
                game.getGrid().get(a).get(b));

        Game game1 = new Game(1);

        game1.setWidth(Width);
        game1.setHeight(Height);
        game1.setGrid();
        assertNotNull(game1.getGrid().get(0).get(0));
        assertNotNull(game1.getGrid().get(a-1).get(b-1));
        assertThrowsExactly(IndexOutOfBoundsException.class, () ->
                game1.getGrid().get(a).get(b));

        assertTrue(game1.getGrid().get(1).get(0).getClass().equals(Road.class));

        roadNum = 0;
        for (int col = 0; col < a; col++) {
            for (int row = 0; row < b; row++) {
                if (game1.getGrid().get(col).get(row).getClass().equals(Road.class)) {
                    roadNum++;
                }
            }
        }
        assertEquals(roadNum, 1);

        Game game2 = new Game(2);

        game2.setWidth(Width);
        game2.setHeight(Height);
        game2.setGrid();
        assertNotNull(game2.getGrid().get(0).get(0));
        assertNotNull(game2.getGrid().get(a-1).get(b-1));
        assertThrowsExactly(IndexOutOfBoundsException.class, () ->
                game2.getGrid().get(a).get(b));

        assertTrue(game2.getGrid().get(5).get(0).getClass().equals(Road.class));

        roadNum = 0;
        for (int col = 0; col < a; col++) {
            for (int row = 0; row < b; row++) {
                if (game2.getGrid().get(col).get(row).getClass().equals(Road.class)) {
                    roadNum++;
                }
            }
        }
        assertEquals(roadNum, 1);

        Game game3 = new Game(3);

        game3.setWidth(Width);
        game3.setHeight(Height);
        game3.setGrid();
        assertNotNull(game3.getGrid().get(0).get(0));
        assertNotNull(game3.getGrid().get(a-1).get(b-1));
        assertThrowsExactly(IndexOutOfBoundsException.class, () ->
                game3.getGrid().get(a).get(b));

        assertTrue(game3.getGrid().get(10).get(0).getClass().equals(Road.class));

        roadNum = 0;
        for (int col = 0; col < a; col++) {
            for (int row = 0; row < b; row++) {
                if (game3.getGrid().get(col).get(row).getClass().equals(Road.class)) {
                    roadNum++;
                }
            }
        }
        assertEquals(roadNum, 1);
    }
    @Test
    void TestPlaceBuilding(){
        int a = Height/CELL_SIZE;
        int b = Width/CELL_SIZE;

        Game game = new Game(0);

        game.setWidth(Width);
        game.setHeight(Height);
        game.setGrid();

        for (int i = 1; i < 10; i++) {
            switch (i) {
                case 1 -> {
                    int counter = 0;
                    game.placeBuilding(ThreadLocalRandom.current().nextInt(1, a-1), ThreadLocalRandom.current().nextInt(1, b-1),1);

                    for (int col = 0; col < a; col++) {
                        for (int row = 0; row < b; row++) {
                            if (game.getGrid().get(col).get(row).getClass().equals(Road.class)) {
                                counter++;
                            }
                        }
                    }

                    assertEquals(counter, 2);
                    assertNotNull(game.getLogs().get(0));
                    assertEquals(game.getLogs().get(0).getText(), "Út építése");
                }
                case 2 -> {
                    int counter = 0;
                    game.placeBuilding(ThreadLocalRandom.current().nextInt(1, a-1), ThreadLocalRandom.current().nextInt(1, b-1),2);

                    for (int col = 0; col < a; col++) {
                        for (int row = 0; row < b; row++) {
                            if (game.getGrid().get(col).get(row).getClass().equals(Police.class)) {
                                counter++;
                            }
                        }
                    }

                    assertEquals(counter, 1);
                    assertNotNull(game.getLogs().get(1));
                    assertEquals(game.getLogs().get(1).getText(), "Rendőrség építése");
                }
                case 3 -> {
                    int counter = 0;
                    game.placeBuilding(ThreadLocalRandom.current().nextInt(1, a-2), ThreadLocalRandom.current().nextInt(1, b-2),3);

                    for (int col = 0; col < a; col++) {
                        for (int row = 0; row < b; row++) {
                            if (game.getGrid().get(col).get(row).getClass().equals(School.class)) {
                                counter++;
                            }
                        }
                    }

                    assertEquals(counter, 1);
                    assertNotNull(game.getLogs().get(2));
                    assertEquals(game.getLogs().get(2).getText(), "Iskola építése");
                }
                case 4 -> {
                    int counter = 0;
                    game.placeBuilding(ThreadLocalRandom.current().nextInt(1, a-2), ThreadLocalRandom.current().nextInt(1, b-2),4);

                    for (int col = 0; col < a; col++) {
                        for (int row = 0; row < b; row++) {
                            if (game.getGrid().get(col).get(row).getClass().equals(University.class)) {
                                counter++;
                            }
                        }
                    }

                    assertEquals(counter, 1);
                    assertNotNull(game.getLogs().get(3));
                    assertEquals(game.getLogs().get(3).getText(), "Egyetem építése");
                }
                case 5 -> {
                    int counter = 0;
                    game.placeBuilding(ThreadLocalRandom.current().nextInt(1, a-2), ThreadLocalRandom.current().nextInt(1, b-2),5);

                    for (int col = 0; col < a; col++) {
                        for (int row = 0; row < b; row++) {
                            if (game.getGrid().get(col).get(row).getClass().equals(Stadium.class)) {
                                counter++;
                            }
                        }
                    }

                    assertEquals(counter, 1);
                    assertNotNull(game.getLogs().get(4));
                    assertEquals(game.getLogs().get(4).getText(), "Stadion építése");
                }
                case 6 -> {
                    int counter = 0;
                    game.placeBuilding(ThreadLocalRandom.current().nextInt(1, a-2), ThreadLocalRandom.current().nextInt(1, b-2),6);

                    for (int col = 0; col < a; col++) {
                        for (int row = 0; row < b; row++) {
                            if (game.getGrid().get(col).get(row).getClass().equals(PowerPlant.class)) {
                                counter++;
                            }
                        }
                    }

                    assertEquals(counter, 1);
                    assertNotNull(game.getLogs().get(5));
                    assertEquals(game.getLogs().get(5).getText(), "Erőmű építése");
                }
                case 7 -> {
                    int counter = 0;
                    game.placeBuilding(ThreadLocalRandom.current().nextInt(1, a-1), ThreadLocalRandom.current().nextInt(1, b-1),7);

                    for (int col = 0; col < a; col++) {
                        for (int row = 0; row < b; row++) {
                            if (game.getGrid().get(col).get(row).getClass().equals(ForestNew.class)) {
                                counter++;
                            }
                        }
                    }

                    assertEquals(counter, 1);
                    assertNotNull(game.getLogs().get(6));
                    assertEquals(game.getLogs().get(6).getText(), "Erdő építése");
                }
                case 9 -> {
                    int counter = 0;
                    game.placeBuilding(ThreadLocalRandom.current().nextInt(1, a-1), ThreadLocalRandom.current().nextInt(1, b-1),9);

                    for (int col = 0; col < a; col++) {
                        for (int row = 0; row < b; row++) {
                            if (game.getGrid().get(col).get(row).getClass().equals(Pole.class)) {
                                counter++;
                            }
                        }
                    }

                    assertEquals(counter, 1);
                    assertNotNull(game.getLogs().get(7));
                    assertEquals(game.getLogs().get(7).getText(), "Vezeték építése");
                }
            }
        }
    }
    @Test
    public void TestClickOnField() {
        int a = Height/CELL_SIZE;
        int b = Width/CELL_SIZE;

        Game game = new Game(1);

        game.setWidth(Width);
        game.setHeight(Height);
        game.setGrid();

        game.setPos(0,0);

        game.clickOnField(1);

        int counter = 0;

        for (int col = 0; col < a; col++) {
            for (int row = 0; row < b; row++) {
                if (game.getGrid().get(col).get(row).getClass().equals(Road.class)) {
                    counter++;
                }
            }
        }
        assertEquals(counter, 2);
        assertNotNull(game.getLogs().get(0));
        assertEquals(game.getLogs().get(0).getText(), "Út építése");

        game.setPos(55,55);

        game.clickOnField(1);

        counter = 0;

        for (int col = 0; col < a; col++) {
            for (int row = 0; row < b; row++) {
                if (game.getGrid().get(col).get(row).getClass().equals(Road.class)) {
                    counter++;
                }
            }
        }
        assertEquals(counter, 3);
        assertNotNull(game.getLogs().get(1));
        assertEquals(game.getLogs().get(1).getText(), "Út építése");

        game.setPos(200,200);

        game.clickOnField(1);

        counter = 0;

        for (int col = 0; col < a; col++) {
            for (int row = 0; row < b; row++) {
                if (game.getGrid().get(col).get(row).getClass().equals(Road.class)) {
                    counter++;
                }
            }
        }
        assertEquals(counter, 3);
        assertThrowsExactly(IndexOutOfBoundsException.class, () ->
                game.getLogs().get(2));

        Game game2 = new Game(1);

        game2.setWidth(Width);
        game2.setHeight(Height);
        game2.setGrid();

        game2.setPos(0,0);

        game2.clickOnField(2);

        counter = 0;

        for (int col = 0; col < a; col++) {
            for (int row = 0; row < b; row++) {
                if (game2.getGrid().get(col).get(row).getClass().equals(Police.class)) {
                    counter++;
                }
            }
        }

        assertEquals(counter, 1);
        assertNotNull(game2.getLogs().get(0));
        assertEquals(game2.getLogs().get(0).getText(), "Rendőrség építése");

        Game game3 = new Game(1);

        game3.setWidth(Width);
        game3.setHeight(Height);
        game3.setGrid();

        game3.setPos(0,0);

        game3.clickOnField(3);

        counter = 0;
        for (int col = 0; col < a; col++) {
            for (int row = 0; row < b; row++) {
                if (game3.getGrid().get(col).get(row).getClass().equals(School.class)) {
                    counter++;
                }
            }
        }

        assertEquals(counter, 0);
        assertThrowsExactly(IndexOutOfBoundsException.class, () ->
                game3.getLogs().get(0));
    }
    @Test
    public void TestClickOnZone(){
        Game game = new Game(1);

        game.setWidth(Width);
        game.setHeight(Height);
        game.setGrid();

        game.setPos(1,1);

        game.clickOnZone(1);

        assertNotNull(game.getZones().get(0));
        assertEquals(game.getZones().get(0).getType(), ResidentalArea);
    }
    @Test
    public void TestDestroyField(){
        int a = Height/CELL_SIZE;
        int b = Width/CELL_SIZE;
        int counter = 0;

        Game game = new Game(1);

        game.setWidth(Width);
        game.setHeight(Height);
        game.setGrid();

        game.clickOnField(2);

        for (int col = 0; col < a; col++) {
            for (int row = 0; row < b; row++) {
                if (game.getGrid().get(col).get(row).getClass().equals(Police.class)) {
                    counter++;
                }
            }
        }

        assertEquals(counter, 1);
        assertNotNull(game.getLogs().get(0));
        assertEquals(game.getLogs().get(0).getText(), "Rendőrség építése");

        game.setPos(1,1);

        game.destroyField();

        counter = 0;
        for (int col = 0; col < a; col++) {
            for (int row = 0; row < b; row++) {
                if (game.getGrid().get(col).get(row).getClass().equals(Police.class)) {
                    counter++;
                }
            }
        }

        assertEquals(counter, 0);
    }
    @Test
    public void TestDestroyZone(){
        Game game = new Game(1);

        game.setWidth(Width);
        game.setHeight(Height);
        game.setGrid();

        game.setPos(1,1);

        game.clickOnZone(1);

        assertNotNull(game.getZones().get(0));
        assertEquals(game.getZones().get(0).getType(), ResidentalArea);

        game.destroyZone();

        assertThrowsExactly(IndexOutOfBoundsException.class, () ->
                game.getZones().get(0));
    }
    @Test
    public void TestAYearPassed(){
        Game game = new Game(1);

        game.setWidth(Width);
        game.setHeight(Height);
        game.setGrid();

        game.aYearPassed();

        assertNotNull(game.getLogs().get(0));
        assertEquals(game.getLogs().get(0).getText(), "Adók beszedése");

        assertNotNull(game.getLogs().get(1));
        assertEquals(game.getLogs().get(1).getText(), "Utak fenntartási költsége");

        Game game2 = new Game(1);

        game2.setWidth(Width);
        game2.setHeight(Height);
        game2.setGrid();

        game2.placeBuilding(1,1,2);
        game2.placeBuilding(2,2,3);
        game2.placeBuilding(5,5,4);
        game2.placeBuilding(10,10,5);

        game2.aYearPassed();

        assertNotNull(game2.getLogs().get(4));
        assertEquals(game2.getLogs().get(4).getText(), "Adók beszedése");

        assertNotNull(game2.getLogs().get(5));
        assertEquals(game2.getLogs().get(5).getText(), "Iskola fenntartási költsége");

        assertNotNull(game2.getLogs().get(6));
        assertEquals(game2.getLogs().get(6).getText(), "Egyetem fenntartási költsége");

        assertNotNull(game2.getLogs().get(7));
        assertEquals(game2.getLogs().get(7).getText(), "Utak fenntartási költsége");

        assertNotNull(game2.getLogs().get(8));
        assertEquals(game2.getLogs().get(8).getText(), "Rendőrség fenntartási költsége");

        assertNotNull(game2.getLogs().get(9));
        assertEquals(game2.getLogs().get(9).getText(), "Stadion fenntartási költsége");

        Game game3 = new Game(1);

        game3.setWidth(Width);
        game3.setHeight(Height);
        game3.setGrid();

        game3.setPos(1,1);
        game3.clickOnZone(1);

        while (game3.getCitizens() == 0) {
            game3.aDayPassed();
        }

        game3.aYearPassed();
    }
}
