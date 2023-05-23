package notsimcity;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static java.lang.Math.floor;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static notsimcity.ZoneType.*;

public class Game extends JPanel {
    private JFrame frame6;
    private static final int CELL_SIZE = 50;
    private static final Random rand = new Random();
    private final ArrayList<ArrayList<Field>> Grid = new ArrayList<>();
    private final ArrayList<Zone> zones = new ArrayList<>();
    private final ArrayList<Citizen> citizens = new ArrayList<>();
    private final ArrayList<MoneyLog> logs = new ArrayList<>();
    private String s_day = "01", s_month = "01", timeText = "";
    private final java.util.List<Sprite> spriteComponents = new ArrayList<>();
    private int time = 0;
    private int day = 1;
    private int month = 1;
    private int year = 2023;
    private int gameSpeed = 1;
    private int Pos_y;
    private int Pos_x;
    private int Width;
    private int Height;
    private int buildingMode = 0;
    private int sizeHelper = 0;
    private int Money = 50000;
    private int starter;
    private int No_schoolExists = 0;
    private int No_universityExists = 0;
    private int numberOfOffices = 0;
    private int numberOfFactorys = 0;
    private int numberOfStadiums = 0;
    private int monthly_tax = 0;
    private int unsatistiedMonths = 0;
    private final int mapNum;
    private double taxMultiplier = 1.0;
    private double satisfactionMod = 0.0;
    private Timer timer;
    private MouseListener ml;
    private boolean scrolled = false;
    private final Image Border = new ImageIcon("yellow_border.png").getImage();
    private final Image GBorder = new ImageIcon("green_border.png").getImage();
    private final Image OBorder = new ImageIcon("orange_border.png").getImage();
    private final Image BBorder = new ImageIcon("blue_border.png").getImage();
    private final Image Office = new ImageIcon("office.png").getImage();
    private final Image Factory = new ImageIcon("factory.png").getImage();
    private final Image Grass = new ImageIcon("grass.jpg").getImage();
    private static int buildingSelected = 0;

    /**
     * Játék kezdetekori beállítások. (konstruktor)
     */
    public Game(int map_num) {
        super();
        mapNum = map_num;
        createML();
    }

    /**
     * A mezők statisztikáinak lekérdezése.
     */
    public void createML() {
        ml = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                spriteComponents.removeIf(sprite -> sprite.getImage().equals(Border));

                Pos_x = e.getX();
                Pos_y = e.getY();

                boolean show = false;
                boolean showPack = false;
                for (ArrayList<Field> fields : Grid) {
                    for (Field field : fields) {
                        if (field.getPosX() < Pos_x && Pos_x < (field.getPosX() + CELL_SIZE) && field.getPosY() < Pos_y && Pos_y < (field.getPosY() + CELL_SIZE)) {
                            JPanel panel = new JPanel();

                            if(frame6 != null && frame6.isVisible()) {
                                frame6.dispose();
                            }

                            if (field.getClass().equals(House.class)) {
                                frame6 = new JFrame("Ház adatai");
                                JLabel label1 = new JLabel();
                                if (field.hasPower) {
                                    label1.setIcon(new ImageIcon("villam.png"));
                                } else {
                                    label1.setIcon(new ImageIcon("villam2.png"));
                                }
                                label1.setBorder(new EmptyBorder(0, 0, 0, 50));
                                panel.add(label1);
                                JLabel label2 = new JLabel("Lakók: " + field.getCapacity() + "/5");
                                label2.setBorder(new EmptyBorder(0, 0, 0, 50));
                                panel.add(label2);
                                double ctrInHouse = 0.0;
                                double ctrInHouseSat = 0.0;
                                for (Citizen citizen : citizens) {
                                    if (citizen.getHouse() == field) {
                                        ctrInHouse++;
                                        ctrInHouseSat += citizen.getSatisfaction();
                                    }
                                }
                                JLabel label3 = new JLabel("Elégedettség: " + (ctrInHouseSat / ctrInHouse));
                                panel.add(label3);
                                show = true;
                            } else if (field.getClass().equals(Job.class)) {
                                String frameName;
                                if (((Job) field).getJobType() == 2) {
                                    frameName = "Gyár adatai";
                                } else {
                                    frameName = "Iroda adatai";
                                }
                                frame6 = new JFrame(frameName);
                                JLabel label1 = new JLabel();
                                if (field.hasPower) {
                                    label1.setIcon(new ImageIcon("villam.png"));
                                } else {
                                    label1.setIcon(new ImageIcon("villam2.png"));
                                }
                                label1.setBorder(new EmptyBorder(0, 0, 0, 50));
                                panel.add(label1);
                                JLabel label2 = new JLabel("Kapacitás: " + field.getCapacity());
                                label2.setBorder(new EmptyBorder(0, 0, 0, 50));
                                panel.add(label2);
                                JLabel label3 = new JLabel("Jelenlegi dolgozók: " + ((Job) field).getWorkers());
                                panel.add(label3);
                                show = true;
                            } else if (field.getClass().getSuperclass().equals(Forest.class)) {
                                frame6 = new JFrame("Erdő adatai");
                                JLabel label1 = new JLabel("Kor: " + ((Forest) field).getGrowthLevel());
                                label1.setFont(new Font("Courier", Font.PLAIN, 20));
                                panel.add(label1);
                                frame6.setSize(new Dimension(300, 150));
                                panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                                frame6.setLayout(new BoxLayout(frame6.getContentPane(), BoxLayout.Y_AXIS));
                                frame6.add(Box.createVerticalGlue());
                                showPack = true;
                            }
                            else if (field.getClass().equals(School.class) || field.getClass().equals(University.class) || field.getClass().equals(Stadium.class) ||
                                        field.getClass().equals(SchoolR.class) || field.getClass().equals(StadiumUR.class) || field.getClass().equals(StadiumLR.class) ||
                                        field.getClass().equals(StadiumLL.class) || field.getClass().equals(UniversityLL.class) || field.getClass().equals(UniversityLR.class) ||
                                        field.getClass().equals(UniversityUR.class) || field.getClass().equals(Police.class)) {
                                frame6 = new JFrame("Energiaellátás");
                                JLabel label1 = new JLabel();
                                if (field.hasPower) {
                                    label1.setIcon(new ImageIcon("villam.png"));
                                } else {
                                    label1.setIcon(new ImageIcon("villam2.png"));
                                }
                                panel.add(label1);
                                panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                                frame6.setLayout(new BoxLayout(frame6.getContentPane(), BoxLayout.Y_AXIS));
                                frame6.add(Box.createVerticalGlue());
                                frame6.setSize(new Dimension(300, 150));
                                showPack = true;
                            }
                            else if (field.getClass().equals(PowerPlant.class)) {
                                frame6 = new JFrame("Energiakapacitás");
                                JLabel label1 = new JLabel("Hátralévő energia: " + field.getCapacity());
                                panel.add(label1);
                                panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                                frame6.setLayout(new BoxLayout(frame6.getContentPane(), BoxLayout.Y_AXIS));
                                frame6.add(Box.createVerticalGlue());
                                frame6.setSize(new Dimension(300, 150));
                                showPack = true;
                            }
                            else if (field.getClass().equals(PowerPlantLL.class)) {
                                frame6 = new JFrame("Energiakapacitás");
                                JLabel label1 = new JLabel("Hátralévő energia: " + Grid.get((field.getPosY()-1)/field.height).get(field.getPosX()/field.width).getCapacity());
                                panel.add(label1);
                                panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                                frame6.setLayout(new BoxLayout(frame6.getContentPane(), BoxLayout.Y_AXIS));
                                frame6.add(Box.createVerticalGlue());
                                frame6.setSize(new Dimension(300, 150));
                                showPack = true;
                            }
                            else if (field.getClass().equals(PowerPlantLR.class)) {
                                frame6 = new JFrame("Energiakapacitás");
                                JLabel label1 = new JLabel("Hátralévő energia: " + Grid.get((field.getPosY()-1)/field.height).get((field.getPosX()-1)/field.width).getCapacity());
                                panel.add(label1);
                                panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                                frame6.setLayout(new BoxLayout(frame6.getContentPane(), BoxLayout.Y_AXIS));
                                frame6.add(Box.createVerticalGlue());
                                frame6.setSize(new Dimension(300, 150));
                                showPack = true;
                            }
                            else if (field.getClass().equals(PowerPlantUR.class)) {
                                frame6 = new JFrame("Energiakapacitás");
                                JLabel label1 = new JLabel("Hátralévő energia: " + Grid.get(field.getPosY()/field.height).get((field.getPosX()-1)/field.width).getCapacity());
                                panel.add(label1);
                                panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                                frame6.setLayout(new BoxLayout(frame6.getContentPane(), BoxLayout.Y_AXIS));
                                frame6.add(Box.createVerticalGlue());
                                frame6.setSize(new Dimension(300, 150));
                                showPack = true;
                            }

                            if(show || showPack) {
                                frame6.add(panel);
                                frame6.setResizable(false);
                                frame6.setLocationRelativeTo(null);
                                frame6.setVisible(true);
                                if(!showPack) {
                                    frame6.pack();
                                }
                                frame6.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                            }
                            break;
                        }
                    }
                    if(show || showPack) {
                        break;
                    }
                }
                scrolled = false;
            }
        };
        this.addMouseListener(ml);
    }

    public void removeML() {
        this.removeMouseListener(ml);
    }

    /**
     * A játék kezdetekori alap mezőszerkezet kialakítása.
     */
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
            Grid.get(randomNum).set(0, new Road(Grid.get(randomNum).get(0)));

            for (int i = 0; i < 10; i++) {
                int randX = ThreadLocalRandom.current().nextInt(1, a-1);
                int randY = ThreadLocalRandom.current().nextInt(1, b-1);

                Grid.get(randX).set(randY, new ForestGrown(Grid.get(randX).get(randY),true));
            }
        }
        else if(mapNum == 1){
            starter = 1;
            Grid.get(starter).set(0, new Road(Grid.get(starter).get(0)));
            Grid.get(4).set(5, new ForestGrown(Grid.get(4).get(5),true));
            Grid.get(7).set(10, new ForestGrown(Grid.get(7).get(10),true));
            Grid.get(10).set(16, new ForestGrown(Grid.get(10).get(16),true));
            Grid.get(15).set(8, new ForestGrown(Grid.get(15).get(8),true));
            Grid.get(9).set(15, new ForestGrown(Grid.get(9).get(15),true));
            Grid.get(12).set(12, new ForestGrown(Grid.get(12).get(12),true));
        }
        else if(mapNum == 2){
            starter = 5;
            Grid.get(starter).set(0, new Road(Grid.get(starter).get(0)));
            Grid.get(2).set(5, new ForestGrown(Grid.get(2).get(5),true));
            Grid.get(7).set(15, new ForestGrown(Grid.get(7).get(15),true));
            Grid.get(13).set(22, new ForestGrown(Grid.get(13).get(22),true));
            Grid.get(12).set(10, new ForestGrown(Grid.get(12).get(10),true));
            Grid.get(6).set(9, new ForestGrown(Grid.get(6).get(9),true));
        }
        else if(mapNum == 3){
            starter = 10;
            Grid.get(starter).set(0, new Road(Grid.get(starter).get(0)));
            Grid.get(13).set(20, new ForestGrown(Grid.get(13).get(20),true));
            Grid.get(4).set(4, new ForestGrown(Grid.get(4).get(4),true));
            Grid.get(3).set(11, new ForestGrown(Grid.get(3).get(11),true));
            Grid.get(15).set(5, new ForestGrown(Grid.get(15).get(5),true));
        }
    }

    /**
     * Egér-koordinátából tömb koordinátáva alakítás.
     */
    public static int cordinateToNum(int number) {
        return (int)(floor(number/CELL_SIZE));
    }

    /**
     * A játék sebességének beállítása
     */
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

    /**
     * A játékos költségvetésének lekérdezése.
     */
    public ArrayList<MoneyLog> getLogs() {return this.logs;}

    /**
     * A városban lakók számának lekérdezése.
     */
    public int getCitizens() {return this.citizens.size();}

    /**
     * A lakók elégedettségének lekérése.
     */
    public double getSatisfaction() {
        double avgSatisfaction = 0.0;
        for (Citizen citizen : this.citizens) {
            avgSatisfaction += citizen.getSatisfaction();
        }
        return Math.round((avgSatisfaction/this.citizens.size()) * 100.0) / 100.0;
    }

    /**
     * A játékos jelenlegi egyenlegének lekérése.
     */
    public int getMoney() {
        return this.Money;
    }

    /**
     * A mezőre minden típusú épület lerakása.
     */
    public void placeBuilding(int i, int j, int building) {
        MoneyLog log = new MoneyLog(0, 0, "", timeText);
        switch (building) {
            case 1 -> {
                Grid.get(i).set(j, new Road(Grid.get(i).get(j)));
                log.setText("Út építése");
            }
            case 2 -> {
                Grid.get(i).set(j, new Police(Grid.get(i).get(j)));
                /*if(i-1 >=0 && j-1 >=0 && i+1 < Height/CELL_SIZE && j+1 < Width/CELL_SIZE && (Grid.get(i).get(j+1).hasPower || Grid.get(i).get(j-1).hasPower || Grid.get(i+1).get(j).hasPower || Grid.get(i-1).get(j).hasPower)) {
                    Grid.get(i).get(j).setHasPower(true);
                }*/
                log.setText("Rendőrség építése");
            }
            case 3 -> {
                if (!scrolled) {
                    Grid.get(i).set(j, new School(Grid.get(i).get(j)));
                    Grid.get(i).set(j+1, new SchoolR(Grid.get(i).get(j+1)));
                    /*if(i-1 >=0 && j-1 >=0 && i+1 < Height/CELL_SIZE && j+2 < Width/CELL_SIZE
                            && (Grid.get(i-1).get(j).hasPower
                            || Grid.get(i-1).get(j+1).hasPower
                            || Grid.get(i+1).get(j).hasPower
                            || Grid.get(i+1).get(j+1).hasPower
                            || Grid.get(i).get(j-1).hasPower
                            || Grid.get(i).get(j+2).hasPower)) {
                        Grid.get(i).get(j).setHasPower(true);
                    }*/
                } else {
                    Grid.get(i).set(j, new SchoolR(Grid.get(i).get(j), true));
                    Grid.get(i+1).set(j, new School(Grid.get(i+1).get(j), true));
                    /*if(i-1 >=0 && j-1 >=0 && i+2 < Height/CELL_SIZE && j+1 < Width/CELL_SIZE
                            && (Grid.get(i).get(j-1).hasPower
                            || Grid.get(i+1).get(j-1).hasPower
                            || Grid.get(i).get(j+1).hasPower
                            || Grid.get(i+1).get(j+1).hasPower
                            || Grid.get(i-1).get(j).hasPower
                            || Grid.get(i+2).get(j).hasPower)) {
                        Grid.get(i).get(j).setHasPower(true);
                    }*/
                }
                No_schoolExists++;
                log.setText("Iskola építése");
            }
            case 4 -> {
                Grid.get(i).set(j, new University(Grid.get(i).get(j)));
                Grid.get(i).set(j+1, new UniversityUR(Grid.get(i).get(j+1)));
                Grid.get(i+1).set(j, new UniversityLL(Grid.get(i+1).get(j)));
                Grid.get(i+1).set(j+1, new UniversityLR(Grid.get(i+1).get(j+1)));
                /*if (i-1 >=0 && j-1 >=0 && i+2 < Height/CELL_SIZE && j+2 < Width/CELL_SIZE
                        && (Grid.get(i-1).get(j).hasPower || Grid.get(i-1).get(j+1).hasPower
                        || Grid.get(i).get(j-1).hasPower || Grid.get(i+1).get(j-1).hasPower
                        || Grid.get(i+2).get(j).hasPower || Grid.get(i+2).get(j+1).hasPower
                        || Grid.get(i).get(j+2).hasPower || Grid.get(i+1).get(j+2).hasPower)) {
                    Grid.get(i).get(j).setHasPower(true);
                }*/
                No_universityExists++;
                log.setText("Egyetem építése");
            }
            case 5 -> {
                Grid.get(i).set(j, new Stadium(Grid.get(i).get(j)));
                Grid.get(i).set(j+1, new StadiumUR(Grid.get(i).get(j+1)));
                Grid.get(i+1).set(j, new StadiumLL(Grid.get(i+1).get(j)));
                Grid.get(i+1).set(j+1, new StadiumLR(Grid.get(i+1).get(j+1)));
                /*if (i-1 >=0 && j-1 >=0 && i+2 < Height/CELL_SIZE && j+2 < Width/CELL_SIZE
                        && (Grid.get(i-1).get(j).hasPower || Grid.get(i-1).get(j+1).hasPower
                        || Grid.get(i).get(j-1).hasPower || Grid.get(i+1).get(j-1).hasPower
                        || Grid.get(i+2).get(j).hasPower || Grid.get(i+2).get(j+1).hasPower
                        || Grid.get(i).get(j+2).hasPower || Grid.get(i+1).get(j+2).hasPower)) {
                    Grid.get(i).get(j).setHasPower(true);
                }*/
                numberOfStadiums++;
                log.setText("Stadion építése");
            }
            case 6 -> {
                Grid.get(i).set(j,new PowerPlant(Grid.get(i).get(j)));
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
            case 9 -> {
                Grid.get(i).set(j, new Pole(Grid.get(i).get(j)));
                log.setText("Vezeték építése");
            }
        }
        this.Money -= Grid.get(i).get(j).getCost();
        log.setMoney(Grid.get(i).get(j).getCost());
        logs.add(log);
    }

    /**
     * A jelenlegi idő lekérdezése.
     */
    public String getTime() {
        return timeText;
    }

    /**
     * Egérmutató hozzáadása.
     */
    public void addSpriteComponent(Sprite spriteComponent) {
        spriteComponents.add(spriteComponent);
    }

    /**
     * Zóna hozzáadása.
     */
    public void addZone(Zone zone) {
        zones.add(zone);
    }

    /**
     * Építőmódban a játékos építési funkciói.
     */
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
                        if (building == 7 || building == 9) {
                            placeBuilding(i, j, building);
                        }
                        //iskola
                        else if (building == 3) {
                            if (!scrolled) {
                                for (Zone zone : zones) {
                                    if ((Pos_x >= zone.getX() && Pos_y >= zone.getY()) && (Pos_x <= zone.getX() + CELL_SIZE && Pos_y <= zone.getY() + CELL_SIZE)
                                            || (Pos_x + CELL_SIZE >= zone.getX() && Pos_y >= zone.getY()) && (Pos_x + CELL_SIZE <= zone.getX() + CELL_SIZE && Pos_y <= zone.getY() + CELL_SIZE)) {
                                        // van már ott zóna
                                        return;
                                    }
                                }
                                //megnézi, hogy üres-e mindkét mező
                                if (!Grid.get(i).get(j).getClass().equals(Field.class)
                                        || !Grid.get(i).get(j + 1).getClass().equals(Field.class)
                                        //és hogy nem lóg-e ki a pályáról
                                        || j + 1 >= Width / CELL_SIZE) {
                                    return;
                                } else if ((Grid.get(i - 1).get(j).isFieldRoad() && Grid.get(i - 1).get(j + 1).isFieldRoad())
                                        || (Grid.get(i + 1).get(j).isFieldRoad() && Grid.get(i + 1).get(j + 1).isFieldRoad())) {
                                    placeBuilding(i, j, building);
                                }
                            } else {
                                for (Zone zone : zones) {
                                    if ((Pos_x >= zone.getX() && Pos_y >= zone.getY()) && (Pos_x <= zone.getX() + CELL_SIZE && Pos_y <= zone.getY() + CELL_SIZE)
                                            || (Pos_x >= zone.getX() && Pos_y + CELL_SIZE >= zone.getY()) && (Pos_x <= zone.getX() + CELL_SIZE && Pos_y + CELL_SIZE <= zone.getY() + CELL_SIZE)) {
                                        // van már ott zóna
                                        return;
                                    }
                                }
                                //megnézi, hogy üres-e mindkét mező
                                if (!Grid.get(i).get(j).getClass().equals(Field.class)
                                        || !Grid.get(i + 1).get(j).getClass().equals(Field.class)
                                        //és hogy nem lóg-e ki a pályáról
                                        || i + 1 >= Height / CELL_SIZE) {
                                    return;
                                } else if ((Grid.get(i).get(j - 1).isFieldRoad() && Grid.get(i + 1).get(j - 1).isFieldRoad())
                                        || (Grid.get(i).get(j + 1).isFieldRoad() && Grid.get(i + 1).get(j + 1).isFieldRoad())) {
                                    placeBuilding(i, j, building);
                                }
                            }
                        } else if (building == 4 || building == 5 || building == 6) {
                            //ha egyetemet, stadiont vagy erőművet építenénk, nézzük meg, hogy mind a 4 mezője üres-e
                            for (Zone zone : zones) {
                                if ((Pos_x >= zone.getX() && Pos_y >= zone.getY()) && (Pos_x <= zone.getX() + CELL_SIZE && Pos_y <= zone.getY() + CELL_SIZE)
                                        || (Pos_x + CELL_SIZE >= zone.getX() && Pos_y >= zone.getY()) && (Pos_x + CELL_SIZE <= zone.getX() + CELL_SIZE && Pos_y <= zone.getY() + CELL_SIZE)
                                        || (Pos_x >= zone.getX() && Pos_y + CELL_SIZE >= zone.getY()) && (Pos_x <= zone.getX() + CELL_SIZE && Pos_y + CELL_SIZE <= zone.getY() + CELL_SIZE)
                                        || (Pos_x + CELL_SIZE >= zone.getX() && Pos_y + CELL_SIZE >= zone.getY()) && (Pos_x + CELL_SIZE <= zone.getX() + CELL_SIZE && Pos_y + CELL_SIZE <= zone.getY() + CELL_SIZE)) {
                                    // van már ott zóna
                                    return;
                                }
                            }
                            if (!Grid.get(i).get(j).getClass().equals(Field.class)
                                    || !Grid.get(i + 1).get(j).getClass().equals(Field.class)
                                    || !Grid.get(i).get(j + 1).getClass().equals(Field.class)
                                    || !Grid.get(i + 1).get(j + 1).getClass().equals(Field.class)
                                    //és hogy nem lóg-e ki a pályáról
                                    || i + 2 >= Height / CELL_SIZE || j + 2 >= Width / CELL_SIZE) {
                                return;
                            } else {
                                if ((i - 1 >= 0 && Grid.get(i - 1).get(j).isFieldRoad() && Grid.get(i - 1).get(j + 1).isFieldRoad())
                                        || (j - 1 >= 0 && Grid.get(i).get(j - 1).isFieldRoad() && Grid.get(i + 1).get(j - 1).isFieldRoad())
                                        || (i + 2 < Height / CELL_SIZE && Grid.get(i + 2).get(j).isFieldRoad() && Grid.get(i + 2).get(j + 1).isFieldRoad())
                                        || (j + 2 < Width / CELL_SIZE && Grid.get(i).get(j + 2).isFieldRoad() && Grid.get(i + 1).get(j + 2).isFieldRoad())) {
                                    placeBuilding(i, j, building);
                                }
                            }
                        } else if (i - 1 == -1 && j - 1 == -1) {
                            if (Grid.get(i + 1).get(j).isFieldRoad() || Grid.get(i).get(j + 1).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        } else if (i + 1 == Grid.size() && j + 1 == Grid.get(i).size()) {
                            if (Grid.get(i - 1).get(j).isFieldRoad() || Grid.get(i).get(j - 1).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        } else if (i + 1 == Grid.size() && j - 1 == -1) {
                            if (Grid.get(i - 1).get(j).isFieldRoad() || Grid.get(i).get(j + 1).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        } else if (i - 1 == -1 && j + 1 == Grid.get(i).size()) {
                            if (Grid.get(i + 1).get(j).isFieldRoad() || Grid.get(i).get(j - 1).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        } else if (i + 1 == Grid.size()) {
                            if (Grid.get(i - 1).get(j).isFieldRoad() || Grid.get(i).get(j - 1).isFieldRoad() || Grid.get(i).get(j + 1).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        } else if (j + 1 == Grid.get(i).size()) {
                            if (Grid.get(i - 1).get(j).isFieldRoad() || Grid.get(i).get(j - 1).isFieldRoad() || Grid.get(i + 1).get(j).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        } else if (i - 1 == -1) {
                            if (Grid.get(i + 1).get(j).isFieldRoad() || Grid.get(i).get(j + 1).isFieldRoad() || Grid.get(i).get(j - 1).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        } else if (j - 1 == -1) {
                            if (Grid.get(i + 1).get(j).isFieldRoad() || Grid.get(i).get(j + 1).isFieldRoad() || Grid.get(i - 1).get(j).isFieldRoad()) {
                                placeBuilding(i, j, building);
                            }
                        } else {
                            if ((Grid.get(i - 1).get(j).isFieldRoad() || Grid.get(i).get(j - 1).isFieldRoad() || Grid.get(i + 1).get(j).isFieldRoad() || Grid.get(i).get(j + 1).isFieldRoad())) {
                                placeBuilding(i, j, building);
                            }
                        }
                        repaint();
                        return;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Indexelési hiba");
        }
    }

    /**
     * Építői mód állítása
     */
    public void setBuildingMode(int mode,int building) {
        this.buildingMode = mode;
        buildingSelected = building;
        removeML();

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

    /**
     * Mező törlése
     */
    public void destroyField() {
        for (int i = 0; i < Grid.size(); i++) {
            for (int j = 0; j < Grid.get(i).size(); j++) {
                if (Grid.get(i).get(j).getPosX() < Pos_x && Pos_x < (Grid.get(i).get(j).getPosX() + CELL_SIZE) && Grid.get(i).get(j).getPosY() < Pos_y && Pos_y < (Grid.get(i).get(j).getPosY() + CELL_SIZE)) {
                    if(i != starter || j != 0) {
                        if((Grid.get(i).get(j).getClass().equals(Field.class)) || (Grid.get(i).get(j).getClass().equals(House.class)) || (Grid.get(i).get(j).getClass().equals(Job.class))) {
                            return;
                        }
                        for(ArrayList<Field> arr : Grid) {
                            for(Field f : arr) {
                                f.setHasPower(false);
                            }
                        }
                        MoneyLog log = new MoneyLog(1, 0, "", timeText);
                        if (Grid.get(i).get(j).getClass().equals(School.class) && !((School)Grid.get(i).get(j)).rotated){
                            this.Money += (Grid.get(i).get(j).getCost()/2);
                            log.setMoney((Grid.get(i).get(j).getCost()/2));
                            log.setText("Iskola lebontás");
                            No_schoolExists--;
                            Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i).set(j+1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j+1).getPosX(), Grid.get(i).get(j+1).getPosY(), 0, 0, false));
                        } else if (Grid.get(i).get(j).getClass().equals(School.class) && ((School)Grid.get(i).get(j)).rotated) {
                            this.Money += (Grid.get(i).get(j).getCost() / 2);
                            log.setMoney((Grid.get(i).get(j).getCost() / 2));
                            log.setText("Iskola lebontás");
                            No_schoolExists--;
                            Grid.get(i).set(j, new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i-1).set(j, new Field(CELL_SIZE, CELL_SIZE, Grid.get(i-1).get(j).getPosX(), Grid.get(i-1).get(j).getPosY(), 0, 0, false));
                        } else if (Grid.get(i).get(j).getClass().equals(Police.class)) {
                            this.Money += (Grid.get(i).get(j).getCost() / 2);
                            log.setMoney((Grid.get(i).get(j).getCost() / 2));
                            log.setText("Rendőrség lebontás");
                            Grid.get(i).set(j, new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                        }
                        else if (Grid.get(i).get(j).getClass().equals(SchoolR.class) && !((SchoolR)Grid.get(i).get(j)).rotated){
                            this.Money += (Grid.get(i).get(j-1).getCost()/2);
                            log.setMoney((Grid.get(i).get(j-1).getCost()/2));
                            log.setText("Iskola lebontás");
                            No_schoolExists--;
                            Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i).set(j-1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j-1).getPosX(), Grid.get(i).get(j-1).getPosY(), 0, 0, false));
                        } else if (Grid.get(i).get(j).getClass().equals(SchoolR.class) && ((SchoolR)Grid.get(i).get(j)).rotated){
                            this.Money += (Grid.get(i+1).get(j).getCost()/2);
                            log.setMoney((Grid.get(i+1).get(j).getCost()/2));
                            log.setText("Iskola lebontás");
                            No_schoolExists--;
                            Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i+1).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i+1).get(j).getPosX(), Grid.get(i+1).get(j).getPosY(), 0, 0, false));
                        } else if (Grid.get(i).get(j).getClass().equals(University.class) || Grid.get(i).get(j).getClass().equals(Stadium.class) || Grid.get(i).get(j).getClass().equals(PowerPlant.class)) {
                            this.Money += (Grid.get(i).get(j).getCost()/2);
                            log.setMoney((Grid.get(i).get(j).getCost()/2));
                            if(Grid.get(i).get(j).getClass().equals(University.class)) {
                                log.setText("Egyetem lebontás");
                                No_universityExists--;
                            }
                            else if(Grid.get(i).get(j).getClass().equals(Stadium.class)){
                                log.setText("Stadion lebontás");
                                numberOfStadiums--;
                            }
                            else {
                                log.setText("Erőmű lebontás");
                            }
                            Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i).set(j+1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j+1).getPosX(), Grid.get(i).get(j+1).getPosY(), 0, 0, false));
                            Grid.get(i+1).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i+1).get(j).getPosX(), Grid.get(i+1).get(j).getPosY(), 0, 0, false));
                            Grid.get(i+1).set(j+1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i+1).get(j+1).getPosX(), Grid.get(i+1).get(j+1).getPosY(), 0, 0, false));
                        } else if (Grid.get(i).get(j).getClass().equals(UniversityUR.class) || Grid.get(i).get(j).getClass().equals(StadiumUR.class) || Grid.get(i).get(j).getClass().equals(PowerPlantUR.class)) {
                            this.Money += (Grid.get(i).get(j-1).getCost()/2);
                            log.setMoney((Grid.get(i).get(j-1).getCost()/2));
                            if(Grid.get(i).get(j).getClass().equals(UniversityUR.class)) {
                                log.setText("Egyetem lebontás");
                                No_universityExists--;
                            }
                            else if(Grid.get(i).get(j).getClass().equals(StadiumUR.class)){
                                log.setText("Stadion lebontás");
                                numberOfStadiums--;
                            }
                            else {
                                log.setText("Erőmű lebontás");
                            }
                            Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i).set(j-1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j-1).getPosX(), Grid.get(i).get(j-1).getPosY(), 0, 0, false));
                            Grid.get(i+1).set(j-1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i+1).get(j-1).getPosX(), Grid.get(i+1).get(j-1).getPosY(), 0, 0, false));
                            Grid.get(i+1).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i+1).get(j).getPosX(), Grid.get(i+1).get(j).getPosY(), 0, 0, false));
                        } else if (Grid.get(i).get(j).getClass().equals(UniversityLL.class) || Grid.get(i).get(j).getClass().equals(StadiumLL.class) || Grid.get(i).get(j).getClass().equals(PowerPlantLL.class)) {
                            this.Money += (Grid.get(i-1).get(j).getCost()/2);
                            log.setMoney((Grid.get(i-1).get(j).getCost()/2));
                            if(Grid.get(i).get(j).getClass().equals(UniversityLL.class)) {
                                log.setText("Egyetem lebontás");
                                No_universityExists--;
                            }
                            else if(Grid.get(i).get(j).getClass().equals(StadiumLL.class)){
                                log.setText("Stadion lebontás");
                                numberOfStadiums--;
                            }
                            else {
                                log.setText("Erőmű lebontás");
                            }
                            Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i-1).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i-1).get(j).getPosX(), Grid.get(i-1).get(j).getPosY(), 0, 0, false));
                            Grid.get(i-1).set(j+1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i-1).get(j+1).getPosX(), Grid.get(i-1).get(j+1).getPosY(), 0, 0, false));
                            Grid.get(i).set(j+1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j+1).getPosX(), Grid.get(i).get(j+1).getPosY(), 0, 0, false));
                        } else if (Grid.get(i).get(j).getClass().equals(UniversityLR.class) || Grid.get(i).get(j).getClass().equals(StadiumLR.class) || Grid.get(i).get(j).getClass().equals(PowerPlantLR.class)) {
                            this.Money += (Grid.get(i-1).get(j-1).getCost()/2);
                            log.setMoney((Grid.get(i-1).get(j-1).getCost()/2));
                            if(Grid.get(i).get(j).getClass().equals(UniversityLR.class)) {
                                log.setText("Egyetem lebontás");
                                No_universityExists--;
                            }
                            else if(Grid.get(i).get(j).getClass().equals(StadiumLR.class)){
                                log.setText("Stadion lebontás");
                                numberOfStadiums--;
                            }
                            else {
                                log.setText("Erőmű lebontás");
                            }
                            Grid.get(i).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            Grid.get(i-1).set(j-1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i-1).get(j-1).getPosX(), Grid.get(i-1).get(j-1).getPosY(), 0, 0, false));
                            Grid.get(i-1).set(j,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i-1).get(j).getPosX(), Grid.get(i-1).get(j).getPosY(), 0, 0, false));
                            Grid.get(i).set(j-1,new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j-1).getPosX(), Grid.get(i).get(j-1).getPosY(), 0, 0, false));
                        }
                        else if(Grid.get(i).get(j).getClass().equals(Road.class)) {
                            if(Grid.get(i+1).get(j).getCapacity() > 0 || Grid.get(i-1).get(j).getCapacity() > 0 || Grid.get(i).get(j+1).getCapacity() > 0|| Grid.get(i).get(j-1).getCapacity() > 0) {
                                JOptionPane.showMessageDialog(null, "Nem lehet olyan utat elbontani ami összeköt más épületet.", "Hiba!",  JOptionPane.ERROR_MESSAGE);
                            }
                            else if((Grid.get(i+1).get(j).isFieldRoad() && Grid.get(i).get(j+1).isFieldRoad()) || (Grid.get(i+1).get(j).isFieldRoad() && Grid.get(i).get(j-1).isFieldRoad()) || (Grid.get(i+1).get(j).isFieldRoad() && Grid.get(i-1).get(j).isFieldRoad()) ||
                                    (Grid.get(i-1).get(j).isFieldRoad() && Grid.get(i).get(j+1).isFieldRoad()) || (Grid.get(i-1).get(j).isFieldRoad() && Grid.get(i).get(j-1).isFieldRoad()) || (Grid.get(i).get(j+1).isFieldRoad() && Grid.get(i).get(j-1).isFieldRoad())) {
                                JOptionPane.showMessageDialog(null, "Az út nem a végén van.", "Hiba!",  JOptionPane.ERROR_MESSAGE);
                            }
                            else {
                                Grid.get(i).set(j, new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            }


                            /*else {
                                int index = j+1;
                                boolean hasNext = true;
                                while(hasNext) {
                                    if(Grid.get(i).get(index).getClass().equals(Road.class)) {
                                        index++;
                                        if(Grid.get(i+1).get(index).getCapacity() > 0 || Grid.get(i-1).get(index).getCapacity() > 0 || Grid.get(i).get(index+1).getCapacity() > 0|| Grid.get(i).get(index-1).getCapacity() > 0) {
                                            JOptionPane.showMessageDialog(null, "Nem lehet olyan utat elbontani ami összeköt más épületet.", "Hiba!",  JOptionPane.ERROR_MESSAGE);
                                            break;
                                        }
                                    }
                                    else {
                                        hasNext = false;
                                        Grid.get(i).set(j, new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                                    }
                                }

                            }*/
                        } else {
                            if (Grid.get(i).get(j).getClass().equals(ForestGrown.class)) {
                                if (!(((ForestGrown) (Grid.get(i).get(j))).isStarted())) {
                                    this.Money += (Grid.get(i).get(j).getCost() / 2);
                                    log.setMoney((Grid.get(i).get(j).getCost() / 2));
                                    log.setText("Erdő lebontás");
                                }
                            } else if(Grid.get(i).get(j).getClass().equals(ForestNew.class)) {
                                this.Money += (Grid.get(i).get(j).getCost() / 2);
                                log.setMoney((Grid.get(i).get(j).getCost() / 2));
                                log.setText("Erdő lebontás");
                            }
                            else if(Grid.get(i).get(j).getClass().equals(Pole.class)) {
                                this.Money += (Grid.get(i).get(j).getCost() / 2);
                                log.setMoney((Grid.get(i).get(j).getCost() / 2));
                                log.setText("Vezeték lebontás");
                            }
                            Grid.get(i).set(j, new Field(CELL_SIZE, CELL_SIZE, Grid.get(i).get(j).getPosX(), Grid.get(i).get(j).getPosY(), 0, 0, false));
                            if (Grid.get(i).get(j).getClass().equals(School.class)) {
                                No_schoolExists--;
                            } else if (Grid.get(i).get(j).getClass().equals(University.class)) {
                                No_universityExists--;
                            }
                        }
                        if(log.getText().equals("")) {
                            logs.add(log);
                        }
                        for(ArrayList<Field> arr : Grid) {
                            for(Field f : arr) {
                                if(f.getClass().equals(PowerPlant.class)) {
                                    f.setCapacity(100);
                                    int i2 = f.getPosY()/f.height;
                                    int j2 = f.getPosX()/f.width;
                                    ((PowerPlant) f).checkPowerNeed(i2,j2,Grid);
                                }
                            }
                        }
                    }
                    return;
                }
            }
        }
    }

    /**
     * Zóna törlése
     */
    public void destroyZone() { //ide
        for (Zone zone : zones) {
            if (zone.getX() < Pos_x && Pos_x < (zone.getX() + CELL_SIZE) && zone.getY() < Pos_y && Pos_y < (zone.getY() + CELL_SIZE)) {
                if(Grid.get(cordinateToNum(Pos_y)).get(cordinateToNum(Pos_x)).getClass().equals(Field.class)) {
                    zones.remove(zone);
                }else {
                    JOptionPane.showMessageDialog(null, "Nem lehet olyan zónát törölni, amire már épült valami.", "Hiba!",  JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
        }
    }

    /**
     * Különböző típusú zónák lerakása.
     */
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
                        return;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Indexelési hiba");
        }
    }

    /**
     * Adómennyiség beállítása
     */
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

    /**
     * Adómennyiség lekérdezése.
     */
    public double getTaxMultiplier() {
        return this.taxMultiplier;
    }

    /**
     * 1 év elteltével történő műveletek
     */
    public void aYearPassed() {
        int giveOutQual2 = 0, giveOutQual3 = 0;
        if(No_universityExists > 0) {
            giveOutQual3 = citizens.size()/20;
            for(Citizen citizen : citizens) {
                if(citizen.getQualification() == 3) {
                    giveOutQual3--;
                }
            }
        }
        if(No_schoolExists > 0) {
            giveOutQual2 = citizens.size()/10 + giveOutQual3;
            for(Citizen citizen : citizens) {
                if(citizen.getQualification() == 2) {
                    giveOutQual2--;
                }
            }
        }
        for(Citizen citizen : citizens) {
            if(citizen.isRetired()){
                this.Money -= citizen.getRetirementMoney();
                logs.add(new MoneyLog(0,citizen.getRetirementMoney(),"Nyugdíj kifizetés",timeText));
                if(citizen.getJob() != null) {
                    citizen.setJob(null);
                }
            }
            if(giveOutQual2 > 0 && citizen.getQualification() == 1) {
                giveOutQual2--;
                citizen.setQualification(2);
            }
            if(giveOutQual3 > 0 && citizen.getQualification() == 2) {
                giveOutQual3--;
                citizen.setQualification(3);
            }
            if(citizen.addAge()) {
                if(citizen.getHouse().capacity == 1) {
                    Citizen newCitizen = new Citizen(citizen.getHouse());
                    citizens.add(newCitizen);
                }
                else {
                    int randomHouse = -1;
                    while (randomHouse == -1) {
                        randomHouse = (int) (Math.random() * citizens.size());
                        if (citizens.get(randomHouse).getHouse().capacity != 5) {
                            Citizen newCitizen = new Citizen(citizens.get(randomHouse).getHouse());
                            citizens.get(randomHouse).getHouse().capacity++;
                            citizens.add(newCitizen);
                        } else {
                            randomHouse = -1;
                        }
                    }
                    citizen.getHouse().capacity--;
                }
                citizens.remove(citizen);
            }
        }

        this.Money += this.monthly_tax;
        logs.add(new MoneyLog(1,this.monthly_tax,"Adók beszedése", timeText));
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
        if(No_schoolExists != 0) {
            this.Money -= No_schoolExists * 400;
            MoneyLog log = new MoneyLog(0, No_schoolExists * 200, "Iskola fenntartási költsége", timeText);
            logs.add(log);
        }
        if(No_universityExists != 0) {
            this.Money -= No_universityExists * 800;
            MoneyLog log = new MoneyLog(0, No_universityExists * 500, "Egyetem fenntartási költsége", timeText);
            logs.add(log);
        }
        int roads = 0;
        int police_C = 0;
        int stadium_C = 0;
        for(ArrayList<Field> arr : Grid) {
            for(Field f : arr) {
                if(f.isFieldRoad()) {
                    roads++;
                }
                else if(f.getClass().equals(Police.class)) {
                    police_C++;
                }
                else if(f.getClass().equals(Stadium.class)) {
                    stadium_C++;
                }
            }
        }
        if(roads > 0) {
            this.Money -= roads * 20;
            MoneyLog log = new MoneyLog(0, roads * 20, "Utak fenntartási költsége", timeText);
            logs.add(log);
        }
        if(police_C > 0) {
            this.Money -= police_C * 100;
            MoneyLog log = new MoneyLog(0, police_C * 100, "Rendőrség fenntartási költsége", timeText);
            logs.add(log);
        }
        if(stadium_C > 0) {
            this.Money -= stadium_C * 2000;
            MoneyLog log = new MoneyLog(0, stadium_C * 2000, "Stadion fenntartási költsége", timeText);
            logs.add(log);
        }
    }

    /**
     * A játék elbukását lejátszó függvény
     */
    void gameOver() {
        JOptionPane.showMessageDialog(null, "Mivel a népesség 6 hónapon át elégedetlen volt, ezért szavazást indítottak, és leváltottak téged!\n" +
                "A játék véget ért!\n\n" +
                "Néhány információ:\n" +
                "Lakosok száma: "+citizens.size() + "\n" +
                "Dátum: "+ timeText +"\n", "Leváltottak!",  JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    /**
     * 1 hónap elteltével történő műveletek
     */
    public void aMonthPassed() {
        for(Citizen citizen : citizens) {
            if(!citizen.isRetired()) {
                this.monthly_tax += this.taxMultiplier * citizen.getQualification() * 100;
                citizen.setAmountOfTax(this.taxMultiplier);
            }
            if(citizen.getPreferredJobType() == 1 && citizen.getJob() == null && !citizen.isRetired()) {
                double closest = 1000.0;
                Zone the_zone = new Zone(0,0,0,0,null, ServiceArea);
                for (Zone zone : zones) {
                    if (zone.getType() == ServiceArea) {
                        double dist = distance(zone.getX(),zone.getY(),citizen.getHouse().getPosX(),citizen.getHouse().getPosY());
                        if(closest > dist && ((Job) Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX()))).getWorkers() != 20) {
                            closest = dist;
                            the_zone = zone;
                        }
                    }
                }
                if (closest != 1000.0) {
                    citizen.setJob((Job) Grid.get(cordinateToNum(the_zone.getY())).get(cordinateToNum(the_zone.getX())));
                    ((Job) Grid.get(cordinateToNum(the_zone.getY())).get(cordinateToNum(the_zone.getX()))).setWorkers();
                }
            }
            else if (citizen.getPreferredJobType() == 2 && citizen.getJob() == null && !citizen.isRetired()) {
                double closest = 1000.0;
                Zone the_zone = new Zone(0,0,0,0,null,IndustrialArea);
                for (Zone zone : zones) {
                    if (zone.getType() == IndustrialArea) {
                        double dist = distance(zone.getX(),zone.getY(),citizen.getHouse().getPosX(),citizen.getHouse().getPosY());
                        if(closest > dist && ((Job) Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX()))).getWorkers() != 40) {
                            closest = dist;
                            the_zone = zone;
                        }
                    }
                }
                if (closest != 1000.0){
                    citizen.setJob((Job) Grid.get(cordinateToNum(the_zone.getY())).get(cordinateToNum(the_zone.getX())));
                    ((Job) Grid.get(cordinateToNum(the_zone.getY())).get(cordinateToNum(the_zone.getX()))).setWorkers();
                }
            }

        }

        double satisfaction = getSatisfaction();
        if(satisfaction <= 20.0) {
            unsatistiedMonths++;
            if(unsatistiedMonths >= 6) {
                gameOver();
            }
        }
        else {
            if(unsatistiedMonths >= 4) {
                unsatistiedMonths = 2;
            }
            else {
                unsatistiedMonths = 0;
            }
        }
    }

    public double distance(int x1,int y1,int x2,int y2) {
        return Math.sqrt(Math.pow(Math.abs(x1-x2),2) + Math.pow(Math.abs(y1-y2),2));
    }

    /**
     * 1 nap elteltével történő műveletek
     */
    public void aDayPassed() {
        switch ((int)(taxMultiplier*2.0)) {
            case 0 -> this.satisfactionMod = 2.0;
            case 1 -> this.satisfactionMod = 1.0;
            case 2 -> this.satisfactionMod = 0.0;
            case 3 -> this.satisfactionMod = -1.0;
            case 4 -> this.satisfactionMod = -2.0;
            default -> {
            }
        }
        //Bugfix
        for(Citizen citizen : citizens) {
            /* Nézzük meg, hogy még mindig ott van-e az a mező */
            for(int i = -3; i < 4;i++) {
                for(int j = -3; j < 4;j++) {
                    if(!(i == 0 && j == 0) && ((cordinateToNum(citizen.getHouse().getY()) + i) > 0 && (cordinateToNum(citizen.getHouse().getY()) + i) < Height/CELL_SIZE && (cordinateToNum(citizen.getHouse().getX()) + j) > 0 && (cordinateToNum(citizen.getHouse().getX()) + j) < Width/CELL_SIZE )) {
                        if(Grid.get(cordinateToNum(citizen.getHouse().getY()) + i).get(cordinateToNum(citizen.getHouse().getX()) + j).getClass().equals(ForestGrown.class) || Grid.get(cordinateToNum(citizen.getHouse().getY()) + i).get(cordinateToNum(citizen.getHouse().getX()) + j).getClass().equals(ForestNew.class)) {
                            citizen.getHouse().setNearPark(true);
                            citizen.getHouse().setNearestForest((Forest)(Grid.get(cordinateToNum(citizen.getHouse().getY()) + i).get(cordinateToNum(citizen.getHouse().getX()) + j)));
                            break;
                        }
                        else {
                            citizen.getHouse().setNearPark(false);
                        }

                        if((Grid.get(cordinateToNum(citizen.getHouse().getY()) + i).get(cordinateToNum(citizen.getHouse().getX()) + j).getClass().equals(Job.class)) && ((Job)Grid.get(cordinateToNum(citizen.getHouse().getY()) + i).get(cordinateToNum(citizen.getHouse().getX()) + j)).getJobType() == 2) {
                            citizen.getHouse().setNearFactory(true);
                            break;
                        }
                        else {
                            citizen.getHouse().setNearFactory(false);
                        }
                    }
                }
                if(citizen.getHouse().getNearPark()){
                    break;
                }
            }

            for(int i = -5; i < 6;i++) {
                for(int j = -5; j < 6;j++) {
                    if(!(i == 0 && j == 0) && ((cordinateToNum(citizen.getHouse().getY()) + i) > 0 && (cordinateToNum(citizen.getHouse().getY()) + i) < Height/CELL_SIZE && (cordinateToNum(citizen.getHouse().getX()) + j) > 0 && (cordinateToNum(citizen.getHouse().getX()) + j) < Width/CELL_SIZE )) {
                        if((Grid.get(cordinateToNum(citizen.getHouse().getY()) + i).get(cordinateToNum(citizen.getHouse().getX()) + j).getClass().equals(Police.class)) && (Grid.get(cordinateToNum(citizen.getHouse().getY()) + i).get(cordinateToNum(citizen.getHouse().getX()) + j)).hasPower) {
                            citizen.getHouse().setNearPolice(true);
                            break;
                        }
                        else {
                            citizen.getHouse().setNearPolice(false);
                        }
                    }
                }
                if(citizen.getHouse().getNearPolice()){
                    break;
                }
            }

            double satisfactionExtra = 0.0;
            if(citizen.getHouse().getNearPark()) {
                satisfactionExtra += (0.05 * citizen.getHouse().getNearestForest().getGrowthLevel());
            }

            if(citizen.getHouse().getNearFactory()) {
                if(citizen.getHouse().getNearPark()) {
                    satisfactionExtra -= 0.1;
                }
                else {
                    satisfactionExtra -= 0.2;
                }
            }

            if(citizen.getHouse().getNearPolice()) {
                satisfactionExtra += 0.3;
            }
            else {
                satisfactionExtra -= 0.5;
            }

            if(numberOfStadiums > 0) {
                satisfactionExtra += 1.0;
            }

            if(this.Money < 0) {
                satisfactionExtra -= Math.round((this.Money/100000.0) * 100) / 100.0;
            }

            if(!citizen.getHouse().hasPower) {
                satisfactionExtra -= 0.5; // ha nincs áram a házába
            }

            if(citizen.getJob() != null) {
                if(!citizen.getJob().hasPower) {
                    satisfactionExtra -= 0.2;
                }
            }

            int averageOfJobPlaces = (numberOfFactorys+numberOfOffices)/2;
            if(numberOfFactorys<averageOfJobPlaces || numberOfOffices<averageOfJobPlaces) {
                satisfactionExtra -= 0.1;
            }

            if(citizen.isRetired()) {
                citizen.setSatisfaction(citizen.getSatisfaction() + satisfactionExtra);
            }
            else {
                citizen.setSatisfaction(citizen.getSatisfaction() + this.satisfactionMod + satisfactionExtra);
            }
        }

        if (day % 2 == 0) {
            for (Zone zone : zones) {
                if (zone.getImage().equals(GBorder)) {
                    //House
                    if(Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX())).getClass().equals(Field.class)) {
                        // random lerak pár házat, nem mindet egyből
                        boolean placeHouse = false;
                        int randomChance = rand.nextInt((20 - 1) + 1) + 1;
                        if((randomChance % 4) == 0) {
                            if(citizens.size() > 0 && getSatisfaction() <= 50.0) {
                                randomChance = rand.nextInt((20 - 1) + 1) + 1;
                                if((randomChance % 4) == 0) {
                                    placeHouse = true;
                                }
                            }
                            else {
                                placeHouse = true;
                            }
                            if(placeHouse) {
                                int randomRes = (int)(Math.random() * 5) + 1;
                                House currentHouse = new House(zone.getWidth(), zone.getHeight(), zone.getX(), zone.getY(), randomRes);
                                Grid.get(cordinateToNum(zone.getY())).set(cordinateToNum(zone.getX()), currentHouse);
                                for(int i = 0; i < randomRes; i++) {
                                    citizens.add(new Citizen(currentHouse));
                                }

                                for(int i = -3; i < 4;i++) {
                                    for(int j = -3; j < 4;j++) {
                                        if(!(i == 0 && j == 0) && ((cordinateToNum(zone.getY()) + i) > 0 && (cordinateToNum(zone.getY()) + i) < Height/CELL_SIZE && (cordinateToNum(zone.getX()) + j) > 0 && (cordinateToNum(zone.getX()) + j) < Width/CELL_SIZE )) {
                                            if(Grid.get(cordinateToNum(zone.getY()) + i).get(cordinateToNum(zone.getX()) + j).getClass().equals(ForestGrown.class) || Grid.get(cordinateToNum(zone.getY()) + i).get(cordinateToNum(zone.getX()) + j).getClass().equals(ForestNew.class)) {
                                                currentHouse.setNearPark();
                                                currentHouse.setNearestForest((Forest)(Grid.get(cordinateToNum(zone.getY()) + i).get(cordinateToNum(zone.getX()) + j)));
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
                            if(Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX()) + 1).getClass().equals(House.class)) {
                                ((House)(Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX()) + 1))).setNearFactory(true);
                            }
                            if(cordinateToNum(zone.getX())-1 >= 0) {
                                if(Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX()) - 1).getClass().equals(House.class)) {
                                    ((House)(Grid.get(cordinateToNum(zone.getY())).get(cordinateToNum(zone.getX()) - 1))).setNearFactory(true);
                                }
                            }
                            if(cordinateToNum(zone.getY()) + 1 >= 0) {
                                if(Grid.get(cordinateToNum(zone.getY()) + 1).get(cordinateToNum(zone.getX())).getClass().equals(House.class)) {
                                    ((House)(Grid.get(cordinateToNum(zone.getY()) + 1).get(cordinateToNum(zone.getX())))).setNearFactory(true);
                                }
                            }
                            if(cordinateToNum(zone.getY()) - 1 >= 0) {
                                if(Grid.get(cordinateToNum(zone.getY()) - 1).get(cordinateToNum(zone.getX())).getClass().equals(House.class)) {
                                    ((House)(Grid.get(cordinateToNum(zone.getY()) - 1).get(cordinateToNum(zone.getX())))).setNearFactory(true);
                                }
                            }
                            numberOfFactorys++;
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
                            numberOfOffices++;
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

        for(ArrayList<Field> arr : Grid) {
            for(Field f : arr) {
                f.setHasPower(false);
            }
        }

        for(ArrayList<Field> arr : Grid) {
            for(Field f : arr) {
                if(f.getClass().equals(PowerPlant.class)) {
                    f.setCapacity(100);
                    int i = f.getPosY()/f.height;
                    int j = f.getPosX()/f.width;
                    ((PowerPlant) f).checkPowerNeed(i,j,Grid);
                }
            }
        }
    }

    /**
     * A játékbeli idő múlásának függvénye.
     */
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
                    timeText = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + "00:0" + time;
                }
                else {
                    timeText = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + "00:" + time;
                }
            } else {
                if (h == 0) {
                    if (m < 10) {
                        timeText = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + "00:0" + m;
                    } else {
                        timeText = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + "00:" + m;
                    }
                }
                if (h < 10) {
                    if (m < 10) {
                        timeText = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + "0" + h + ":0" + m;
                    } else {
                        timeText = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + "0" + h + ":" + m;
                    }
                } else {
                    if (m < 10) {
                        timeText = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + h + ":0" + m;
                    } else {
                        timeText = String.valueOf(year) + '.' + s_month + '.' + s_day + ". " + h + ":" + m;
                    }
                }
            }
        });
        timer.start();
    }

    /**
     * A grafika kirajzolása, a teljes játék kinézetének grafikai megoldásai.
     */

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

    /**
     * Teszteléshez kell.
     */
    public void setWidth(int width){
        this.Width = width;
    }
    public void setHeight(int height){
        this.Height = height;
    }
    public ArrayList<ArrayList<Field>> getGrid() {
        return this.Grid;
    }
    public void setPos(int x, int y) {
        this.Pos_x = x;
        this.Pos_y = y;
    }
    public ArrayList<Zone> getZones() {
        return zones;
    }
}