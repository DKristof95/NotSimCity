package notsimcity;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

import static java.lang.Math.floor;
import static notsimcity.ZoneType.*;

public class Game extends JPanel {
    private static final int CELL_SIZE = 50, FPS = 240;
    private static final Random rand = new Random();
    private ArrayList<ArrayList<Field>> Grid = new ArrayList<>();
    private ArrayList<Zone> zones = new ArrayList<>();
    private ArrayList<Citizen> citizens = new ArrayList<>();
    private ArrayList<MoneyLog> logs = new ArrayList<>();
    private Player player;
    private String s_day = "01", s_month = "01", timetext = "";
    private java.util.List<Sprite> spriteComponents = new ArrayList<>();
    private int time = 0, day = 1, month = 1, year = 2023, gameSpeed = 1, Pos_y, Pos_x, Width, Height, buildingMode = 0, sizeHelper = 0, Money = 50000, starter, randomRes, No_schoolExists = 0, No_universityExists = 0, monthly_tax = 0, mapNum;
    private double taxMultiplier = 1.0, satisfactionMod = 0.0, satisfactionExtra = 0.0;
    private Timer timer;
    private boolean scrolled = false, stadiumExists = false;
    private final Image Ut1 = new ImageIcon("ut_viz.png").getImage(), Ut2 = new ImageIcon("ut_fugg.png").getImage();
    private final Image Border = new ImageIcon("yellow_border.png").getImage();
    private final Image GBorder = new ImageIcon("green_border.png").getImage();
    private final Image OBorder = new ImageIcon("orange_border.png").getImage();
    private final Image BBorder = new ImageIcon("blue_border.png").getImage();
    private final Image House = new ImageIcon("house.png").getImage();
    private final Image Office = new ImageIcon("office.png").getImage();
    private final Image Factory = new ImageIcon("factory.png").getImage();
    private final Image Grass = new ImageIcon("grass.jpg").getImage();
    private final Image StadiumLL = new ImageIcon("stadium_lower_left.png").getImage();
    private final Image StadiumLR = new ImageIcon("stadium_lower_right.png").getImage();
    private final Image StadiumUL = new ImageIcon("stadium_upper_left.png").getImage();
    private final Image StadiumUR = new ImageIcon("stadium_upper_right.png").getImage();
    private final Image PowerPlant = new ImageIcon("power_plant.png").getImage();
    private static int buildingSelected = 0;

    public Game(int map_num) {
        super();
        mapNum = map_num;
    }

    public void setGrid() {
        int a = Height/CELL_SIZE;
        int b = Width/CELL_SIZE;
        for (int col = 0; col < a; col++) {
            ArrayList<Field> rows = new ArrayList<>();
            for (int row = 0; row < b; row++) {
                Field field = new Field(CELL_SIZE, CELL_SIZE, row * CELL_SIZE, col * CELL_SIZE, 0, 0,false);
                rows.add(field);
            }
            Grid.add(rows);
        }
        if(mapNum == 0){
            int randomNum = ThreadLocalRandom.current().nextInt(1, a-1);
            starter = randomNum;
            Grid.get(randomNum).set(0, new Road(Grid.get(randomNum).get(0), Ut1));

            for (int i = 0; i < 10; i++) {
                int randX = ThreadLocalRandom.current().nextInt(1, a-1);
                int randY = ThreadLocalRandom.current().nextInt(1, b-1);

                Grid.get(randX).set(randY, new ForestGrown(Grid.get(randX).get(randY),true));
            }
        }
        else if(mapNum == 1){
            starter = 1;
            Grid.get(starter).set(0, new Road(Grid.get(starter).get(0), Ut1));
            Grid.get(4).set(5, new ForestGrown(Grid.get(4).get(5),true));
            Grid.get(7).set(10, new ForestGrown(Grid.get(7).get(10),true));
            Grid.get(10).set(16, new ForestGrown(Grid.get(10).get(16),true));
            Grid.get(15).set(8, new ForestGrown(Grid.get(15).get(8),true));
            Grid.get(9).set(15, new ForestGrown(Grid.get(9).get(15),true));
            Grid.get(12).set(12, new ForestGrown(Grid.get(12).get(12),true));
        }
        else if(mapNum == 2){
            starter = 5;
            Grid.get(starter).set(0, new Road(Grid.get(starter).get(0), Ut1));
            Grid.get(2).set(5, new ForestGrown(Grid.get(2).get(5),true));
            Grid.get(7).set(15, new ForestGrown(Grid.get(7).get(15),true));
            Grid.get(13).set(22, new ForestGrown(Grid.get(13).get(22),true));
            Grid.get(12).set(10, new ForestGrown(Grid.get(12).get(10),true));
            Grid.get(6).set(9, new ForestGrown(Grid.get(6).get(9),true));
        }
        else if(mapNum == 3){
            starter = 10;
            Grid.get(starter).set(0, new Road(Grid.get(starter).get(0), Ut1));
            Grid.get(13).set(20, new ForestGrown(Grid.get(13).get(20),true));
            Grid.get(4).set(4, new ForestGrown(Grid.get(4).get(4),true));
            Grid.get(3).set(11, new ForestGrown(Grid.get(3).get(11),true));
            Grid.get(15).set(5, new ForestGrown(Grid.get(15).get(5),true));
        }
    }

    public static int cordinateToNum(int x) {
        return (int)(floor(x/CELL_SIZE));
    }

    public void setGameSpeed(int speed) {
        this.gameSpeed = speed;
        if(speed == 0) {
            timer.stop();
        }
        else if(speed == 1) {
            timer.setDelay(1000);
            timer.start();
        }
        else if(speed == 2) {
            timer.setDelay(10);
            timer.start();
        }
        else if(speed == 3) {
            timer.setDelay(1);
            timer.start();
        }
    }

    public int getGameSpeed() {
        return this.gameSpeed;
    }
    public ArrayList<MoneyLog> getLogs() {return this.logs;}

    public int getCitizens() {return this.citizens.size();}

    public double getSatisfaction() {
        double avgSatisfaction = 0.0;
        for(int i = 0; i < this.citizens.size();i++){
            avgSatisfaction += citizens.get(i).getSatisfaction();
        }
        return Math.round((avgSatisfaction/this.citizens.size()) * 100.0) / 100.0;
    }

    public int getMoney() {
        return this.Money;
    }

    public void placeBuilding(int i, int j, int building) {
        MoneyLog log = new MoneyLog(0, 0, "", timetext);
        switch (building) {
            case 1 -> {
                if (scrolled) {
                    Grid.get(i).set(j, new Road(Grid.get(i).get(j), Ut2));
                } else {
                    Grid.get(i).set(j, new Road(Grid.get(i).get(j), Ut1));
                }
                log.setText("Út építése");
            }
            case 2 -> {
                Grid.get(i).set(j, new Police(Grid.get(i).get(j)));
                if(i-1 >=0 && j-1 >=0 && i+1 < Height/CELL_SIZE && j+1 < Width/CELL_SIZE && (Grid.get(i).get(j+1).hasPower || Grid.get(i).get(j-1).hasPower || Grid.get(i+1).get(j).hasPower || Grid.get(i-1).get(j).hasPower)) {
                    Grid.get(i).get(j).setHasPower(true);
                }
                log.setText("Rendőrség építése");
            }
            case 3 -> {
                if (!scrolled) {
                    Grid.get(i).set(j, new School(Grid.get(i).get(j)));
                    Grid.get(i).set(j+1, new SchoolR(Grid.get(i).get(j+1)));
                    if(i-1 >=0 && j-1 >=0 && i+1 < Height/CELL_SIZE && j+2 < Width/CELL_SIZE
                            && (Grid.get(i-1).get(j).hasPower
                            || Grid.get(i-1).get(j+1).hasPower
                            || Grid.get(i+1).get(j).hasPower
                            || Grid.get(i+1).get(j+1).hasPower
                            || Grid.get(i).get(j-1).hasPower
                            || Grid.get(i).get(j+2).hasPower)) {
                        Grid.get(i).get(j).setHasPower(true);
                    }
                } else {
                    Grid.get(i).set(j, new SchoolR(Grid.get(i).get(j), true));
                    Grid.get(i+1).set(j, new School(Grid.get(i+1).get(j), true));
                    if(i-1 >=0 && j-1 >=0 && i+2 < Height/CELL_SIZE && j+1 < Width/CELL_SIZE
                            && (Grid.get(i).get(j-1).hasPower
                            || Grid.get(i+1).get(j-1).hasPower
                            || Grid.get(i).get(j+1).hasPower
                            || Grid.get(i+1).get(j+1).hasPower
                            || Grid.get(i-1).get(j).hasPower
                            || Grid.get(i+2).get(j).hasPower)) {
                        Grid.get(i).get(j).setHasPower(true);
                    }
                }
                No_schoolExists++;
                log.setText("Iskola építése");
            }
            case 4 -> {
                Grid.get(i).set(j, new University(Grid.get(i).get(j)));
                Grid.get(i).set(j+1, new UniversityUR(Grid.get(i).get(j+1)));
                Grid.get(i+1).set(j, new UniversityLL(Grid.get(i+1).get(j)));
                Grid.get(i+1).set(j+1, new UniversityLR(Grid.get(i+1).get(j+1)));
                if (i-1 >=0 && j-1 >=0 && i+2 < Height/CELL_SIZE && j+2 < Width/CELL_SIZE
                        && (Grid.get(i-1).get(j).hasPower || Grid.get(i-1).get(j+1).hasPower
                        || Grid.get(i).get(j-1).hasPower || Grid.get(i+1).get(j-1).hasPower
                        || Grid.get(i+2).get(j).hasPower || Grid.get(i+2).get(j+1).hasPower
                        || Grid.get(i).get(j+2).hasPower || Grid.get(i+1).get(j+2).hasPower)) {
                    Grid.get(i).get(j).setHasPower(true);
                }
                No_universityExists++;
                log.setText("Egyetem építése");
            }
            case 5 -> {
                Grid.get(i).set(j, new Stadium(Grid.get(i).get(j)));
                Grid.get(i).set(j+1, new StadiumUR(Grid.get(i).get(j+1)));
                Grid.get(i+1).set(j, new StadiumLL(Grid.get(i+1).get(j)));
                Grid.get(i+1).set(j+1, new StadiumLR(Grid.get(i+1).get(j+1)));
                if (i-1 >=0 && j-1 >=0 && i+2 < Height/CELL_SIZE && j+2 < Width/CELL_SIZE
                        && (Grid.get(i-1).get(j).hasPower || Grid.get(i-1).get(j+1).hasPower
                        || Grid.get(i).get(j-1).hasPower || Grid.get(i+1).get(j-1).hasPower
                        || Grid.get(i+2).get(j).hasPower || Grid.get(i+2).get(j+1).hasPower
                        || Grid.get(i).get(j+2).hasPower || Grid.get(i+1).get(j+2).hasPower)) {
                    Grid.get(i).get(j).setHasPower(true);
                }
                stadiumExists = true;
                log.setText("Stadion építése");
            }
            case 6 -> {
                Grid.get(i).set(j,new PowerPlant(Grid.get(i).get(j),Grid));
                Grid.get(i).set(j+1, new PowerPlantUR(Grid.get(i).get(j+1)));
                Grid.get(i+1).set(j, new PowerPlantLL(Grid.get(i+1).get(j)));
                Grid.get(i+1).set(j+1, new PowerPlantLR(Grid.get(i+1).get(j+1)));
                log.setText("Erőmű építése");
            }
            case 7 -> {
                Grid.get(i).set(j, new ForestNew(Grid.get(i).get(j), year, month, day));
                log.setText("Erdő építése");
            }
            case 8 -> {
                Grid.get(i).set(j, new ForestGrown(Grid.get(i).get(j),false));
                return;
            }
        }
        this.Money -= Grid.get(i).get(j).getCost();
        log.setMoney(Grid.get(i).get(j).getCost());
        logs.add(log);
    }

    public void clickOnField(int building) {

        try {

            for (ArrayList<Field> rows : Grid) {
                for (Field cell : rows) {
                    if ((Pos_x >= cell.getX() && Pos_y >= cell.getY()) && (Pos_x <= cell.getX()+CELL_SIZE && Pos_y <= cell.getY()+CELL_SIZE)) { // a cellába esik a kattintás
                        if(!cell.getClass().equals(Field.class)) { // nem üres mező (üres, ha Field típusú, ha van rajta valami, akkor pl Road típust ad vissza)
                            return;
                        }
                    }
                }
            }

            for (Zone zone : zones) {
                if (zone.getX() < Pos_x && Pos_x < (zone.getX() + CELL_SIZE) && zone.getY() < Pos_y && Pos_y < (zone.getY() + CELL_SIZE)) {
                    return;
                }
            }

            for (int i = 0; i < Grid.size(); i++) {
                for (int j = 0; j < Grid.get(i).size(); j++) {
                    if (Grid.get(i).get(j).getPosX() <= Pos_x && Pos_x <= (Grid.get(i).get(j).getPosX() + CELL_SIZE) && Grid.get(i).get(j).getPosY() <= Pos_y && Pos_y <= (Grid.get(i).get(j).getPosY() + CELL_SIZE)) {
                        //erdőnél szerintem nem kéne, hogy csak út mellé lehessen építeni
                        if (building == 7) {
                            placeBuilding(i, j, building);
                        }
                        //iskola
                        else if (building == 3) {
                            if (!scrolled) {
                                for (Zone zone : zones) {
                                    if ((Pos_x >= zone.getX() && Pos_y >= zone.getY()) && (Pos_x <= zone.getX()+CELL_SIZE && Pos_y <= zone.getY()+CELL_SIZE)
                                            || (Pos_x +CELL_SIZE >= zone.getX() && Pos_y >= zone.getY()) && (Pos_x +CELL_SIZE <= zone.getX()+CELL_SIZE && Pos_y <= zone.getY()+CELL_SIZE)) {
                                        // van már ott zóna
                                        return;
                                    }
                                }
                                //megnézi, hogy üres-e mindkét mező
                                if (!Grid.get(i).get(j).getClass().equals(Field.class)
                                        || !Grid.get(i).get(j+1).getClass().equals(Field.class)
                                        //és hogy nem lóg-e ki a pályáról
                                        || j+1 >= Width/CELL_SIZE) {
                                    return;
                                } else if ((Grid.get(i-1).get(j).isFieldRoad() && Grid.get(i-1).get(j+1).isFieldRoad())
                                        || (Grid.get(i+1).get(j).isFieldRoad() && Grid.get(i+1).get(j+1).isFieldRoad())) {
                                    placeBuilding(i, j, building);
                                }
                            } else {
                                for (Zone zone : zones) {
                                    if ((Pos_x >= zone.getX() && Pos_y >= zone.getY()) && (Pos_x <= zone.getX()+CELL_SIZE && Pos_y <= zone.getY()+CELL_SIZE)
                                            || (Pos_x >= zone.getX() && Pos_y +CELL_SIZE >= zone.getY()) && (Pos_x <= zone.getX()+CELL_SIZE && Pos_y +CELL_SIZE <= zone.getY()+CELL_SIZE)) {
                                        // van már ott zóna
                                        return;
                                    }
                                }
                                //megnézi, hogy üres-e mindkét mező
                                if (!Grid.get(i).get(j).getClass().equals(Field.class)
                                        || !Grid.get(i+1).get(j).getClass().equals(Field.class)
                                        //és hogy nem lóg-e ki a pályáról
                                        || i+1 >= Height/CELL_SIZE) {
                                    return;
                                } else if ((Grid.get(i).get(j-1).isFieldRoad() && Grid.get(i+1).get(j-1).isFieldRoad())
                                        || (Grid.get(i).get(j+1).isFieldRoad() && Grid.get(i+1).get(j+1).isFieldRoad())) {
                                    placeBuilding(i, j, building);
                                }
                            }
                        }
                        else if (building == 4 || building == 5 || building == 6) {
                            //ha egyetemet, stadiont vagy erőművet építenénk, nézzük meg, hogy mind a 4 mezője üres-e
                            for (Zone zone : zones) {
                                if ((Pos_x >= zone.getX() && Pos_y >= zone.getY()) && (Pos_x <= zone.getX()+CELL_SIZE && Pos_y <= zone.getY()+CELL_SIZE)
                                        || (Pos_x +CELL_SIZE >= zone.getX() && Pos_y >= zone.getY()) && (Pos_x +CELL_SIZE <= zone.getX()+CELL_SIZE && Pos_y <= zone.getY()+CELL_SIZE)
                                        || (Pos_x >= zone.getX() && Pos_y +CELL_SIZE >= zone.getY()) && (Pos_x <= zone.getX()+CELL_SIZE && Pos_y +CELL_SIZE <= zone.getY()+CELL_SIZE)
                                        || (Pos_x +CELL_SIZE >= zone.getX() && Pos_y +CELL_SIZE >= zone.getY()) && (Pos_x +CELL_SIZE <= zone.getX()+CELL_SIZE && Pos_y +CELL_SIZE <= zone.getY()+CELL_SIZE)) {
                                    // van már ott zóna
                                    return;
                                }
                            }
                            if (!Grid.get(i).get(j).getClass().equals(Field.class)
                                    || !Grid.get(i+1).get(j).getClass().equals(Field.class)
                                    || !Grid.get(i).get(j+1).getClass().equals(Field.class)
                                    || !Grid.get(i+1).get(j+1).getClass().equals(Field.class)
                            //és hogy nem lóg-e ki a pályáról
                                    || i+2 >= Height/CELL_SIZE || j+2 >= Width/CELL_SIZE) {
                                return;
                            } else {
                                if ((i-1 >=0 && Grid.get(i-1).get(j).isFieldRoad() && Grid.get(i-1).get(j+1).isFieldRoad())
                                        || (j-1 >=0 && Grid.get(i).get(j-1).isFieldRoad() && Grid.get(i+1).get(j-1).isFieldRoad())
                                        || (i+2 < Height/CELL_SIZE && Grid.get(i+2).get(j).isFieldRoad() && Grid.get(i+2).get(j+1).isFieldRoad())
                                        || (j+2 < Width/CELL_SIZE && Grid.get(i).get(j+2).isFieldRoad() && Grid.get(i+1).get(j+2).isFieldRoad())) {
                                    placeBuilding(i, j, building);
                                }
                            }
                        }
                        else if(i-1 == -1 && j-1 == -1) {
                            if (Grid.get(i+1).get(j).isFieldRoad() || Grid.get(i).get(j+1).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        }
                        else if(i+1 == Grid.size() && j+1 == Grid.get(i).size()) {
                            if (Grid.get(i-1).get(j).isFieldRoad() || Grid.get(i).get(j-1).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        }
                        else if(i+1 == Grid.size() && j-1 == -1) {
                            if (Grid.get(i-1).get(j).isFieldRoad() || Grid.get(i).get(j+1).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        }
                        else if(i-1 == -1 && j+1 == Grid.get(i).size()) {
                            if (Grid.get(i+1).get(j).isFieldRoad() || Grid.get(i).get(j-1).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        }
                        else if(i+1 == Grid.size()) {
                            if (Grid.get(i-1).get(j).isFieldRoad() || Grid.get(i).get(j-1).isFieldRoad() || Grid.get(i).get(j+1).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        }
                        else if(j+1 == Grid.get(i).size()) {
                            if (Grid.get(i-1).get(j).isFieldRoad() || Grid.get(i).get(j-1).isFieldRoad()|| Grid.get(i+1).get(j).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        }
                        else if(i-1 == -1) {
                            if (Grid.get(i+1).get(j).isFieldRoad() || Grid.get(i).get(j+1).isFieldRoad() || Grid.get(i).get(j-1).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        }
                        else if(j-1 == -1) {
                            if (Grid.get(i+1).get(j).isFieldRoad() || Grid.get(i).get(j+1).isFieldRoad() || Grid.get(i-1).get(j).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        }
                        else {
                            if ((Grid.get(i-1).get(j).isFieldRoad() || Grid.get(i).get(j-1).isFieldRoad() || Grid.get(i+1).get(j).isFieldRoad() || Grid.get(i).get(j+1).isFieldRoad())) {
                                placeBuilding(i, j, building);
                            }
                        }
                        repaint();
                        break;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Indexelési hiba");
        }
    }

    public void setBuildingMode(int mode,int building) {
        buildingMode = mode;
        buildingSelected = building;

        spriteComponents.removeIf(sprite -> sprite.getImage().equals(Border));
        repaint();

        Timer timer2 = new Timer(0, e -> {
            if (buildingMode == 1 || buildingMode == 2 || buildingMode == 3 || buildingMode == 4) {

                spriteComponents.removeIf(sprite -> sprite.getImage().equals(Border));

                Pos_x = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
                Pos_y = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;

                for (ArrayList<Field> fields : Grid) {
                    for (Field field : fields) {
                        if (field.getPosX() < Pos_x && Pos_x < (field.getPosX() + CELL_SIZE) && field.getPosY() < Pos_y && Pos_y < (field.getPosY() + CELL_SIZE)) {
                            if (buildingSelected == 3 && buildingMode == 1) {
                                if (!scrolled) {
                                    addSpriteComponent(new Sprite(CELL_SIZE*2, CELL_SIZE, field.getPosX(), field.getPosY(), Border));
                                    repaint();
                                } else {
                                    addSpriteComponent(new Sprite(CELL_SIZE, CELL_SIZE*2, field.getPosX(), field.getPosY(), Border));
                                    repaint();
                                }
                            } else if (buildingSelected == 4 || buildingSelected == 5 || buildingSelected == 6) {
                                addSpriteComponent(new Sprite(CELL_SIZE*2, CELL_SIZE*2, field.getPosX(), field.getPosY(), Border));
                                repaint();
                            } else {
                                addSpriteComponent(new Sprite(CELL_SIZE, CELL_SIZE, field.getPosX(), field.getPosY(), Border));
                                repaint();
                            }

                        }
                    }
                }
            }
        });
        timer2.start();

        if (buildingMode == 0) {
            spriteComponents.removeIf(sprite -> sprite.getImage().equals(Border));
            repaint();
            timer2.stop();
            for (MouseListener l : this.getMouseListeners()) {
                this.removeMouseListener(l);
            }
            for (ActionListener l: timer2.getActionListeners()
                 ) {
                timer2.removeActionListener(l);
            }
            buildingSelected = 0;
        }

        this.addMouseWheelListener(new MouseAdapter(){
            public void mouseWheelMoved(MouseWheelEvent e) {
                scrolled = true;
            }
        });

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher((KeyEvent event) -> {
                    if (event.isAltDown()) {
                        scrolled = true;
                    }
                    return false;
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                spriteComponents.removeIf(sprite -> sprite.getImage().equals(Border));

                Pos_x = e.getX();
                Pos_y = e.getY();

                if(buildingMode == 1) {
                    clickOnField(building);
                }
                else if(buildingMode == 2) {
                    destroyField();
                }
                else if(buildingMode == 3) {
                    clickOnZone(building);
                }
                else if(buildingMode == 4) {
                    destroyZone();
                }
                scrolled = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                if (buildingMode == 1) {
                    timer2.start();
                }

            }
        });
    }

    public void destroyField() {
        boolean helper = false;
        for (int i = 0; i < Grid.size(); i++) {
            for (int j = 0; j < Grid.get(i).size(); j++) {
                if (Grid.get(i).get(j).getPosX() < Pos_x && Pos_x < (Grid.get(i).get(j).getPosX() + CELL_SIZE) && Grid.get(i).get(j).getPosY() < Pos_y && Pos_y < (Grid.get(i).get(j).getPosY() + CELL_SIZE)) {
                    if(i != starter || j != 0) {
                        if (Grid.get(i).get(j).getClass().equals(School.class) && !((School)Grid.get(i).get(j)).rotated){
                            this.Money += (Grid.get(i).get(j).getCost()/2);
                            Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i).set(j+1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j+1).getPosX(), Grid.get(i).get(j+1).getPosY(), 0, 0, false));
                        } else if (Grid.get(i).get(j).getClass().equals(School.class) && ((School)Grid.get(i).get(j)).rotated) {
                            this.Money += (Grid.get(i).get(j).getCost() / 2);
                            Grid.get(i).set(j, new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i-1).set(j, new Field(CELL_SIZE, CELL_SIZE, Grid.get(i-1).get(j).getPosX(), Grid.get(i-1).get(j).getPosY(), 0, 0, false));
                        } else if (Grid.get(i).get(j).getClass().equals(SchoolR.class) && !((SchoolR)Grid.get(i).get(j)).rotated){
                            this.Money += (Grid.get(i).get(j-1).getCost()/2);
                            Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i).set(j-1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j-1).getPosX(), Grid.get(i).get(j-1).getPosY(), 0, 0, false));
                        } else if (Grid.get(i).get(j).getClass().equals(SchoolR.class) && ((SchoolR)Grid.get(i).get(j)).rotated){
                            this.Money += (Grid.get(i+1).get(j).getCost()/2);
                            Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i+1).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i+1).get(j).getPosX(), Grid.get(i+1).get(j).getPosY(), 0, 0, false));
                        } else if (Grid.get(i).get(j).getClass().equals(University.class) || Grid.get(i).get(j).getClass().equals(Stadium.class) || Grid.get(i).get(j).getClass().equals(PowerPlant.class)) {
                            this.Money += (Grid.get(i).get(j).getCost()/2);
                            Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i).set(j+1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j+1).getPosX(), Grid.get(i).get(j+1).getPosY(), 0, 0, false));
                            Grid.get(i+1).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i+1).get(j).getPosX(), Grid.get(i+1).get(j).getPosY(), 0, 0, false));
                            Grid.get(i+1).set(j+1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i+1).get(j+1).getPosX(), Grid.get(i+1).get(j+1).getPosY(), 0, 0, false));
                            helper = true;
                            break;
                        } else if (Grid.get(i).get(j).getClass().equals(UniversityUR.class) || Grid.get(i).get(j).getClass().equals(StadiumUR.class) || Grid.get(i).get(j).getClass().equals(PowerPlantUR.class)) {
                            this.Money += (Grid.get(i).get(j-1).getCost()/2);
                            Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i).set(j-1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j-1).getPosX(), Grid.get(i).get(j-1).getPosY(), 0, 0, false));
                            Grid.get(i+1).set(j-1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i+1).get(j-1).getPosX(), Grid.get(i+1).get(j-1).getPosY(), 0, 0, false));
                            Grid.get(i+1).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i+1).get(j).getPosX(), Grid.get(i+1).get(j).getPosY(), 0, 0, false));
                            helper = true;
                            break;
                        } else if (Grid.get(i).get(j).getClass().equals(UniversityLL.class) || Grid.get(i).get(j).getClass().equals(StadiumLL.class) || Grid.get(i).get(j).getClass().equals(PowerPlantLL.class)) {
                            this.Money += (Grid.get(i-1).get(j).getCost()/2);
                            Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i-1).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i-1).get(j).getPosX(), Grid.get(i-1).get(j).getPosY(), 0, 0, false));
                            Grid.get(i-1).set(j+1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i-1).get(j+1).getPosX(), Grid.get(i-1).get(j+1).getPosY(), 0, 0, false));
                            Grid.get(i).set(j+1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j+1).getPosX(), Grid.get(i).get(j+1).getPosY(), 0, 0, false));
                            helper = true;
                            break;
                        } else if (Grid.get(i).get(j).getClass().equals(UniversityLR.class) || Grid.get(i).get(j).getClass().equals(StadiumLR.class) || Grid.get(i).get(j).getClass().equals(PowerPlantLR.class)) {
                            this.Money += (Grid.get(i-1).get(j-1).getCost()/2);
                            Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i-1).set(j-1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i-1).get(j-1).getPosX(), Grid.get(i-1).get(j-1).getPosY(), 0, 0, false));
                            Grid.get(i-1).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i-1).get(j).getPosX(), Grid.get(i-1).get(j).getPosY(), 0, 0, false));
                            Grid.get(i).set(j-1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j-1).getPosX(), Grid.get(i).get(j-1).getPosY(), 0, 0, false));
                            helper = true;
                            break;
                        } else {
                            if(Grid.get(i).get(j).getClass().equals(ForestGrown.class)) {
                                if(!(((ForestGrown)(Grid.get(i).get(j))).isStarted())) {
                                    this.Money += (Grid.get(i).get(j).getCost() / 2);
                                }
                            }
                            else{
                                this.Money += (Grid.get(i).get(j).getCost() / 2);
                            }
                            Grid.get(i).set(j, new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            if(Grid.get(i).get(j).getClass().equals(School.class)) {
                                No_schoolExists--;
                            }
                            else if(Grid.get(i).get(j).getClass().equals(University.class)) {
                                No_universityExists--;
                            }
                            helper = true;
                            break;
                        }
                    }
                }
            }
            if(helper) {
                break;
            }
        }
    }

    public void destroyZone() { //ide
        boolean helper = false;
        for (Zone zone : zones) {
            if (zone.getX() < Pos_x && Pos_x < (zone.getX() + CELL_SIZE) && zone.getY() < Pos_y && Pos_y < (zone.getY() + CELL_SIZE)) {
                if(Grid.get(cordinateToNum(Pos_y)).get(cordinateToNum(Pos_x)).getClass().equals(Field.class)) {
                    zones.remove(zone);
                    helper = true;
                    break;
                }else {
                    JOptionPane.showMessageDialog(null, "Nem lehet olyan zónát törölni, amire már épült valami.", "Hiba!",  JOptionPane.ERROR_MESSAGE);
                }
            }
            if(helper) {
                break;
            }
        }
    }
    
    public void clickOnZone(int zoneType) {
        Image mainBorder;
        ZoneType type;
        if(zoneType == 1) {
            mainBorder = GBorder;
            type = ResidentalArea;
        }
        else if (zoneType == 2) {
            mainBorder = OBorder;
            type = IndustrialArea;
        }
        else {
            mainBorder = BBorder;
            type = ServiceArea;
        }
        try {

            for (Zone zone : zones) {
                if ((Pos_x >= zone.getX() && Pos_y >= zone.getY()) && (Pos_x <= zone.getX()+CELL_SIZE && Pos_y <= zone.getY()+CELL_SIZE)) {
                    // van már ott zóna
                    return;
                }
            }

            for (int i = 0; i < Grid.size(); i++) {
                for (int j = 0; j < Grid.get(i).size(); j++) {
                    if (Grid.get(i).get(j).getPosX() < Pos_x && Pos_x < (Grid.get(i).get(j).getPosX() + CELL_SIZE) && Grid.get(i).get(j).getPosY() < Pos_y && Pos_y < (Grid.get(i).get(j).getPosY() + CELL_SIZE)) {
                        if(i-1 == -1 && j-1 == -1) {
                            if (Grid.get(i+1).get(j).isFieldRoad() || Grid.get(i).get(j+1).isFieldRoad()) {
                                addZone(new Zone(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), mainBorder, type));
                            }
                        }
                        else if(i+1 == Grid.size() && j+1 == Grid.get(i).size()) {
                            if (Grid.get(i-1).get(j).isFieldRoad() || Grid.get(i).get(j-1).isFieldRoad()) {
                                addZone(new Zone(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), mainBorder, type));
                            }
                        }
                        else if(i+1 == Grid.size() && j-1 == -1) {
                            if (Grid.get(i-1).get(j).isFieldRoad() || Grid.get(i).get(j+1).isFieldRoad()) {
                                addZone(new Zone(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), mainBorder, type));
                            }
                        }
                        else if(i-1 == -1 && j+1 == Grid.get(i).size()) {
                            if (Grid.get(i+1).get(j).isFieldRoad() || Grid.get(i).get(j-1).isFieldRoad()) {
                                addZone(new Zone(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), mainBorder, type));
                            }
                        }
                        else if(i+1 == Grid.size()) {
                            if (Grid.get(i-1).get(j).isFieldRoad() || Grid.get(i).get(j-1).isFieldRoad() || Grid.get(i).get(j+1).isFieldRoad()) {
                                addZone(new Zone(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), mainBorder, type));
                            }
                        }
                        else if(j+1 == Grid.get(i).size()) {
                            if (Grid.get(i-1).get(j).isFieldRoad() || Grid.get(i).get(j-1).isFieldRoad()|| Grid.get(i+1).get(j).isFieldRoad()) {
                                addZone(new Zone(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), mainBorder, type));
                            }
                        }
                        else if(i-1 == -1) {
                            if (Grid.get(i+1).get(j).isFieldRoad() || Grid.get(i).get(j+1).isFieldRoad() || Grid.get(i).get(j-1).isFieldRoad()) {
                                addZone(new Zone(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), mainBorder, type));
                            }
                        }
                        else if(j-1 == -1) {
                            if (Grid.get(i+1).get(j).isFieldRoad() || Grid.get(i).get(j+1).isFieldRoad() || Grid.get(i-1).get(j).isFieldRoad()) {
                                addZone(new Zone(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), mainBorder, type));
                            }
                        }
                        else {
                            if ((Grid.get(i-1).get(j).isFieldRoad() || Grid.get(i).get(j-1).isFieldRoad() || Grid.get(i+1).get(j).isFieldRoad() || Grid.get(i).get(j+1).isFieldRoad())) {
                                addZone(new Zone(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), mainBorder, type));
                            }
                        }
                        repaint();
                        break;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Indexelési hiba");
        }
    }

    public void setTaxMultiplier(int type) {
        switch (type) {
            case 1 -> this.taxMultiplier = 0.0;
            case 2 -> this.taxMultiplier = 0.5;
            case 3 -> this.taxMultiplier = 1.0;
            case 4 -> this.taxMultiplier = 1.5;
            case 5 -> this.taxMultiplier = 2.0;
            default -> {
            }
        }
    }

    public double getTaxMultiplier() {
        return this.taxMultiplier;
    }

    public void aYearPassed() {
        int giveOutQual2 = 0, giveOutQual3 = 0;
        if(No_schoolExists > 0) {
            giveOutQual2 = citizens.size()/10;
        }
        if(No_universityExists > 0) {
            giveOutQual3 = citizens.size()/20;
        }
        for(Citizen citizen : citizens) {
            if(citizen.isRetired()){
                this.Money -= citizen.getRetirementMoney();
            }
            if(giveOutQual2 > 0 && citizen.getQualification() == 1) {
                giveOutQual2--;
                citizen.setQualification(2);
            }
            if(giveOutQual3 > 0 && citizen.getQualification() == 2) {
                giveOutQual3--;
                citizen.setQualification(3);
            }
            citizen.addAge();
        }

        this.Money += this.monthly_tax;
        this.monthly_tax = 0;

        for (ArrayList<Field> rows : Grid) {
            for (Field cell : rows) {
                if(cell.getClass().equals(ForestNew.class)) {
                    int i = cell.getY()/CELL_SIZE;
                    int j = cell.getX()/CELL_SIZE;
                    ((ForestNew) cell).setGrowthLevel(((ForestNew) cell).getGrowthLevel() + 1);
                    if(((ForestNew) cell).getGrowthLevel() == 10) {
                        placeBuilding(i, j,8);
                    }
                    repaint();
                }
            }
        }
    }

    public void aMonthPassed() {
        for(Citizen citizen : citizens) {
            this.monthly_tax += this.taxMultiplier * citizen.getQualification() * 100;
            citizen.setAmountOfTax(this.taxMultiplier);
        }
    }

    public void aDayPassed() {
        switch ((int)(taxMultiplier*2.0)) {
            case 0 -> this.satisfactionMod = 1.0;
            case 1 -> this.satisfactionMod = 0.5;
            case 2 -> this.satisfactionMod = 0.0;
            case 3 -> this.satisfactionMod = -0.5;
            case 4 -> this.satisfactionMod = -1.0;
            default -> {
            }
        }
        //Bugfix
        for(Citizen citizen : citizens) {
            this.satisfactionExtra = 0.0;
            if(citizen.getHouse().getNearPark()) {
                this.satisfactionExtra += (0.1 * citizen.getHouse().getNearestForest().getGrowthLevel());
            }
            System.out.println(citizen.getHouse().getNearFactory());
            if(citizen.getHouse().getNearFactory()) {
                this.satisfactionExtra -= 0.1;
            }
            if(citizen.getHouse().getNearPolice()) {
                this.satisfactionExtra += 0.5;
            }
            else {
                this.satisfactionExtra -= 0.5;
            }
            if(stadiumExists) {
                this.satisfactionExtra += 1.0;
            }
            citizen.setSatisfaction(citizen.getSatisfaction() + this.satisfactionMod + this.satisfactionExtra);
        }

        if (day % 2 == 0) {
            for (Zone zone : zones) {
                if (zone.getImage().equals(GBorder)) {
                    //House
                    if(Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX())).getClass().equals(Field.class)) {
                        // random lerak pár házat, nem mindet egyből
                        int randomChance = rand.nextInt((20 - 1) + 1) + 1;
                        if((randomChance % 4) == 0) {
                            House currentHouse = new House(zone.getWidth(),zone.getHeight(),zone.getX(),zone.getY());
                            Grid.get(cordinateToNum(zone.getY())).set(cordinateToNum(zone.getX()), currentHouse);
                            randomRes = (int)(Math.random() * 5) + 1;
                            for(int i = 0; i < randomRes; i++) {
                                citizens.add(new Citizen(currentHouse));
                            }

                            for(int i = -3; i < 4;i++) {
                                for(int j = -3; j < 4;j++) {
                                    if(!(i == 0 && j == 0) && ((cordinateToNum(zone.getY()) + i) > 0 && (cordinateToNum(zone.getY()) + i) < Height/CELL_SIZE && (cordinateToNum(zone.getX()) + j) > 0 && (cordinateToNum(zone.getX()) + j) < Width/CELL_SIZE )) {
                                        if(Grid.get(cordinateToNum(zone.getY()) + i).get(cordinateToNum(zone.getX()) + j).getClass().getSuperclass().equals(Forest.class)) {
                                            currentHouse.setNearPark();
                                            currentHouse.setNearestForest((Forest)(Grid.get(cordinateToNum(zone.getY()) + i ).get(cordinateToNum(zone.getX() + j))));
                                            break;
                                        }
                                    }
                                }
                                if(currentHouse.getNearPark()){
                                    break;
                                }
                            }

                            for(int i = -5; i < 6;i++) {
                                for(int j = -5; j < 6;j++) {
                                    if(!(i == 0 && j == 0) && ((cordinateToNum(zone.getY()) + i) > 0 && (cordinateToNum(zone.getY()) + i) < Height/CELL_SIZE && (cordinateToNum(zone.getX()) + j) > 0 && (cordinateToNum(zone.getX()) + j) < Width/CELL_SIZE )) {
                                        if(Grid.get(cordinateToNum(zone.getY()) + i).get(cordinateToNum(zone.getX()) + j).getClass().equals(Police.class)) {
                                            currentHouse.setNearPolice();
                                            break;
                                        }
                                    }
                                }
                                if(currentHouse.getNearPolice()){
                                    break;
                                }
                            }

                            randomRes = (int)(Math.random() * 5) + 1;
                            for(int i = 0; i < randomRes; i++) {
                                citizens.add(new Citizen(currentHouse));
                            }
                        }
                    }
                    repaint();
                } else if (zone.getImage().equals(OBorder)) {
                    //Factory
                    if(Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX())).getClass().equals(Field.class)) {
                        // random lerak pár gyárat, nem mindet egyből
                        int randomChance = rand.nextInt((20 - 1) + 1) + 1;
                        if((randomChance % 4) == 0) {
                            Grid.get(cordinateToNum(zone.getY())).set(cordinateToNum(zone.getX()), new Job(CELL_SIZE, CELL_SIZE, zone.getX(), zone.getY(), Factory, 2));
                            /*if(Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX()) + 1).getClass().equals(House.class)) {
                                ((House)(Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX()) + 1))).setNearFactory(true);
                            }
                            if(Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX()) - 1).getClass().equals(House.class)) {
                                ((House)(Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX()) - 1))).setNearFactory(true);
                            }
                            if(Grid.get(cordinateToNum(zone.getY()) + 1).get(cordinateToNum(zone.getX())).getClass().equals(House.class)) {
                                ((House)(Grid.get(cordinateToNum(zone.getY()) + 1).get(cordinateToNum(zone.getX())))).setNearFactory(true);
                            }
                            if(Grid.get(cordinateToNum(zone.getY()) - 1).get(cordinateToNum(zone.getX())).getClass().equals(House.class)) {
                                ((House)(Grid.get(cordinateToNum(zone.getY()) - 1).get(cordinateToNum(zone.getX())))).setNearFactory(true);
                            }*/
                        }
                    }
                    repaint();
                } else if (zone.getImage().equals(BBorder)) {
                    //Office
                    if(Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX())).getClass().equals(Field.class)) {
                        // random lerak pár irodát, nem mindet egyből
                        int randomChance = rand.nextInt((20 - 1) + 1) + 1;
                        if((randomChance % 4) == 0) {
                            Grid.get(cordinateToNum(zone.getY())).set(cordinateToNum(zone.getX()), new Job(CELL_SIZE, CELL_SIZE, zone.getX(), zone.getY(), Office, 1));
                        }
                    }
                    repaint();
                }
            }
        }

        if(month == 1 && day == 31) {
            month++;
            day = 1;
            s_day = "01";
            s_month = "02";
            aMonthPassed();
        }
        else if(month == 2 && day == 28) {
            month++;
            day = 1;
            s_day = "01";
            s_month = "03";
            aMonthPassed();
        }
        else if(month == 3 && day == 31) {
            month++;
            day = 1;
            s_day = "01";
            s_month = "04";
            aMonthPassed();
        }
        else if(month == 4 && day == 30) {
            month++;
            day = 1;
            s_day = "01";
            s_month = "05";
            aMonthPassed();
        }
        else if(month == 5 && day == 31) {
            month++;
            day = 1;
            s_day = "01";
            s_month = "06";
            aMonthPassed();
        }
        else if(month == 6 && day == 30) {
            month++;
            day = 1;
            s_day = "01";
            s_month = "07";
            aMonthPassed();
        }
        else if(month == 7 && day == 31) {
            month++;
            day = 1;
            s_day = "01";
            s_month = "08";
            aMonthPassed();
        }
        else if(month == 8 && day == 31) {
            month++;
            day = 1;
            s_day = "01";
            s_month = "09";
            aMonthPassed();
        }
        else if(month == 9 && day == 30) {
            month++;
            day = 1;
            s_day = "01";
            s_month = "10";
            aMonthPassed();
        }
        else if(month == 10 && day == 31) {
            month++;
            day = 1;
            s_day = "01";
            s_month = "11";
            aMonthPassed();
        }
        else if(month == 11 && day == 30) {
            month++;
            day = 1;
            s_day = "01";
            s_month = "12";
            aMonthPassed();
        }
        else if(month == 12 && day == 31) {
            month++;
            day = 1;
            s_day = "01";
            s_month = "01";
            year++;
            aMonthPassed();
            aYearPassed();
        }
        else {
            day++;
            if(day < 10) {
                s_day = "0" + day;
            }
            else {
                s_day = String.valueOf(day);
            }
        }

    }

    public void Time() {
        timer = new Timer(1000, e -> {
            if(gameSpeed == 3) {
                time = time + 55;
            }
            else if(gameSpeed == 2) {
                time = time + 10;
            }
            else {
                time++;
            }
            int h = time / 60;
            int m = time % 60;
            if (h == 24) {
                time = time % 60;
                aDayPassed();
                if (time < 10) {
                    timetext = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + "00:0" + time;
                }
                else {
                    timetext = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + "00:" + time;
                }
            } else {
                if (h == 0) {
                    if (m < 10) {
                        timetext = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + "00:0" + m;
                    } else {
                        timetext = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + "00:" + m;
                    }
                }
                if (h < 10) {
                    if (m < 10) {
                        timetext = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + "0" + h + ":0" + m;
                    } else {
                        timetext = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + "0" + h + ":" + m;
                    }
                } else {
                    if (m < 10) {
                        timetext = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + h + ":0" + m;
                    } else {
                        timetext = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + h + ":" + m;
                    }
                }
            }
        });
        timer.start();
    }

    public String getTime() {
        return timetext;
    }

    public void addSpriteComponent(Sprite spriteComponent) {
        spriteComponents.add(spriteComponent);
    }

    public void addZone(Zone zone) {
        zones.add(zone);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        sizeHelper++;
        if(sizeHelper == 2) {
            this.Width = this.getWidth();
            this.Height = this.getHeight();
            setGrid();
        }

        // háttér
        for (int col = 0; col < Height/CELL_SIZE; col++) {
            for (int row = 0; row < Width / CELL_SIZE; row++) {
                int x = row * CELL_SIZE;
                int y = col * CELL_SIZE;
                grphcs.drawImage(Grass,x,y,CELL_SIZE,CELL_SIZE,null);
            }
        }

        for (Zone zone : zones) {
            grphcs.drawImage(zone.getImage(), zone.getX(), zone.getY(), zone.getWidth(), zone.getHeight(), null);
        }

        for (Sprite sprite : spriteComponents) {
            grphcs.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), null);
        }

        for (ArrayList<Field> rows : Grid) {
            for (Field cell : rows) {
                grphcs.drawImage(cell.getImage(), cell.getX(), cell.getY(), cell.getWidth(), cell.getHeight(), null);
            }
        }

    }
}