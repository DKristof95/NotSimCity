package notsimcity;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

import static java.lang.Math.floor;
import static notsimcity.ZoneType.*;

public class Game extends JPanel {
    private static final int CELL_SIZE = 50;
    private ArrayList<ArrayList<Field>> Grid = new ArrayList<ArrayList<Field>>();
    private Player player;
    private String cityName;
    private java.util.List<Sprite> spriteComponents;
    private String s_day = "01";
    private String s_month = "01";
    private int time = 0;
    private int day = 1;
    private int month = 1;
    private int year = 2023;
    private int gameSpeed = 1;
    private int zoom;
    private ArrayList<Zone> zones;
    private ArrayList<Citizen> citizens;
    private final int FPS = 240;
    private Timer timer;
    private String timetext = "";
    private int Pos_y;
    private int Pos_x;
    private int buildingMode = 0;
    private int sizeHelper = 0;
    private int Width;
    private int Height;
    private int Money = 50000;
    private int Power = 0;
    private boolean scrolled = false;
    private final Image Ut1 = new ImageIcon("ut_viz.png").getImage();
    private final Image Ut2 = new ImageIcon("ut_fugg.png").getImage();
    private final Image Border = new ImageIcon("yellow_border.png").getImage();
    private final Image GBorder = new ImageIcon("green_border.png").getImage();
    private final Image OBorder = new ImageIcon("orange_border.png").getImage();
    private final Image BBorder = new ImageIcon("blue_border.png").getImage();
    private final Image House = new ImageIcon("house.png").getImage();
    private final Image Office = new ImageIcon("office.png").getImage();
    private final Image Factory = new ImageIcon("factory.png").getImage();
    private final Image Grass = new ImageIcon("grass.jpg").getImage();
    private int starter;
    private float taxMultiplier = 1.0f;

    public Game() {
        super();
        spriteComponents = new ArrayList<>();
        zones = new ArrayList<>();
        citizens = new ArrayList<>();
    }

    public void setGrid() {
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
        int randomNum = ThreadLocalRandom.current().nextInt(1, a-1);
        starter = randomNum;
        Grid.get(randomNum).set(0, new Road(Grid.get(randomNum).get(0), Ut1));
    }

    public static int cordinateToNum(int x) {
        return (int)(floor(x/CELL_SIZE));
    }

    public void loadSave(int saveID) {
        
    }
    
    public void saveGame() {
        
    }
    
    public void zoomIn() {
        
    }
    
    public void zoomOut() {
        
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

    public int getCitizens() {return this.citizens.size();}

    public int getMoney() {
        return this.Money;
    }

    public void placeBuilding(int i, int j, int building) {
        switch (building) {
            case 1 -> {
                if (scrolled) {
                    Grid.get(i).set(j, new Road(Grid.get(i).get(j), Ut2));
                } else {
                    Grid.get(i).set(j, new Road(Grid.get(i).get(j), Ut1));
                }
                this.Money -= Grid.get(i).get(j).getCost();
            }
            case 2 -> {
                Grid.get(i).set(j, new Police(Grid.get(i).get(j)));
                this.Money -= Grid.get(i).get(j).getCost();
                if(Grid.get(i).get(j+1).hasPower || Grid.get(i).get(j-1).hasPower || Grid.get(i+1).get(j).hasPower || Grid.get(i-1).get(j).hasPower) {
                    Grid.get(i).get(j).setHasPower(true);
                }
            }
            case 3 -> {
                Grid.get(i).set(j, new School(Grid.get(i).get(j)));
                this.Money -= Grid.get(i).get(j).getCost();
                if(Grid.get(i).get(j+1).hasPower || Grid.get(i).get(j-1).hasPower || Grid.get(i+1).get(j).hasPower || Grid.get(i-1).get(j).hasPower) {
                    Grid.get(i).get(j).setHasPower(true);
                }
            }
            case 4 -> {
                Grid.get(i).set(j, new University(Grid.get(i).get(j)));
                this.Money -= Grid.get(i).get(j).getCost();
                if(Grid.get(i).get(j+1).hasPower || Grid.get(i).get(j-1).hasPower || Grid.get(i+1).get(j).hasPower || Grid.get(i-1).get(j).hasPower) {
                    Grid.get(i).get(j).setHasPower(true);
                }
            }
            case 5 -> {
                Grid.get(i).set(j, new Stadium(Grid.get(i).get(j)));
                this.Money -= Grid.get(i).get(j).getCost();
                if(Grid.get(i).get(j+1).hasPower || Grid.get(i).get(j-1).hasPower || Grid.get(i+1).get(j).hasPower || Grid.get(i-1).get(j).hasPower) {
                    Grid.get(i).get(j).setHasPower(true);
                }
            }
            case 6 -> {
                Grid.get(i).set(j,new PowerPlant(Grid.get(i).get(j),Grid));
                this.Money -= Grid.get(i).get(j).getCost();
            }
        }
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

            for (int i = 0; i < Grid.size(); i++) {
                for (int j = 0; j < Grid.get(i).size(); j++) {
                    if (Grid.get(i).get(j).getPosX() <= Pos_x && Pos_x <= (Grid.get(i).get(j).getPosX() + CELL_SIZE) && Grid.get(i).get(j).getPosY() <= Pos_y && Pos_y <= (Grid.get(i).get(j).getPosY() + CELL_SIZE)) {
                        if(i-1 == -1 && j-1 == -1) {
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
        this.buildingMode = mode;

        spriteComponents.removeIf(sprite -> sprite.getImage().equals(Border));
        repaint();

        Timer timer2 = new Timer(0, e -> {
            if (buildingMode == 1 || buildingMode == 2 || buildingMode == 3) {

                spriteComponents.removeIf(sprite -> sprite.getImage().equals(Border));

                Pos_x = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
                Pos_y = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;

                for (ArrayList<Field> fields : Grid) {
                    for (Field field : fields) {
                        if (field.getPosX() < Pos_x && Pos_x < (field.getPosX() + CELL_SIZE) && field.getPosY() < Pos_y && Pos_y < (field.getPosY() + CELL_SIZE)) {
                            addSpriteComponent(new Sprite(CELL_SIZE, CELL_SIZE, field.getPosX(), field.getPosY(), Border));
                            repaint();
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
                        this.Money += (Grid.get(i).get(j).getCost()/2);
                        Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                        helper = true;
                        break;
                    }
                }
            }
            if(helper) {
                break;
            }
        }
    }
    
    public void clickOnZone(int zoneType) { //ide
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
                    System.out.println("Van már ott zóna.");
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
    
    public void aYearPassed() {
        
    }
    
    public void aMonthPassed() {
        this.Money += this.taxMultiplier * 10000;
    }

    public void setTaxMultiplier(int type) {
        switch (type) {
            case 1 -> this.taxMultiplier = 0.0f;
            case 2 -> this.taxMultiplier = 0.5f;
            case 3 -> this.taxMultiplier = 1.0f;
            case 4 -> this.taxMultiplier = 1.5f;
            case 5 -> this.taxMultiplier = 2.0f;
            default -> {
            }
        }
    }

    public float getTaxMultiplier() {
        return this.taxMultiplier;
    }

    public int getPower() {return this.Power;}

    public void aDayPassed() { //itt dolgozok (Bence)
        if (day % 2 == 0) {
            for (Zone zone : zones) {
                if (zone.getImage().equals(GBorder)) {
                    //House
                    if(Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX())).getClass().equals(Field.class)) {
                        Grid.get(cordinateToNum(zone.getY())).set(cordinateToNum(zone.getX()), new House(CELL_SIZE, CELL_SIZE, zone.getX(), zone.getY()));
                        citizens.add(new Citizen(new House(CELL_SIZE, CELL_SIZE, zone.getX(), zone.getY()),1)); // még nincs kész
                    }
                    repaint();
                } else if (zone.getImage().equals(OBorder)) {
                    //Factory
                    repaint();
                } else if (zone.getImage().equals(BBorder)) {
                    //Office
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
                time = time + 11;
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