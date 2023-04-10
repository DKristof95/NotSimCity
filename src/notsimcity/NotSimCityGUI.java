package notsimcity;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseListener;

import static javax.swing.JOptionPane.showMessageDialog;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;

public class NotSimCityGUI {
    private JFrame frame;
    private JFrame frame2;
    private JFrame frame3;
    private Game gameArea;

    private JLabel timetext;

    private Menu menuArea;
    private MapSelect mapSelectArea;
    private JMenuBar menuBar;
    private JMenu menu, subMenu;
    private JMenuItem menuItem;

    private JTextField cityNameField;
    private String cityName;
    public NotSimCityGUI() {
        frame = new JFrame("NotSimCity");
        JButton b1 = new JButton("Új Játék");
        b1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b1.setFocusPainted(false);
        b1.setBounds(250,330,200,50);
        b1.addActionListener(e -> {
            frame.setVisible(false);
            frame2 = new JFrame("NotSimCity");
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame2.setResizable(false);
            mapSelectArea = new MapSelect();

            JLabel cityNameLabel = new JLabel("Város neve:");
            cityNameLabel.setBounds(195,50,100,30);
            frame2.add(cityNameLabel);
            cityNameField = new JTextField();
            cityNameField.setBounds(275,50,200,30);
            frame2.add(cityNameField);


            ButtonGroup buttonGroup = new ButtonGroup();
            JRadioButton radio1 = new JRadioButton("Véletlenszerű pálya",true);
            radio1.setBounds(100,110,20,20);
            JLabel radio1_label = new JLabel("Véletlenszerű pálya");
            radio1_label.setBounds(120,110,200,20);
            buttonGroup.add(radio1);
            JRadioButton radio2 = new JRadioButton("Előre megadott pálya választása");
            radio2.setBounds(100,150,20,20);
            JLabel radio2_label = new JLabel("Előre megadott pálya választása");
            radio2_label.setBounds(120,150,200,20);
            buttonGroup.add(radio2);
            frame2.add(radio1);
            frame2.add(radio2);
            frame2.add(radio1_label);
            frame2.add(radio2_label);

            JButton BalB = new BasicArrowButton(BasicArrowButton.WEST);
            BalB.setBounds(100,400,64,64);
            BalB.addActionListener(e1 -> {
                    frame.setVisible(true);
                    frame2.dispose();
            });
            BalB.setBorder(BorderFactory.createEmptyBorder());
            BalB.setOpaque(false);
            BalB.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            JButton JobbB = new BasicArrowButton(BasicArrowButton.EAST);
            JobbB.setBounds(536,400,64,64);
            JobbB.addActionListener(e1 -> {
                Border greenBorder = BorderFactory.createLineBorder(Color.GREEN, 2, true);

                cityName = cityNameField.getText();
                frame.dispose();
                frame2.dispose();
                frame3 = new JFrame("NotSimCity | Város: " + cityName );
                frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame3.setPreferredSize(new Dimension(1616, 930));
                frame3.setResizable(false);
                gameArea = new Game();
                frame3.getContentPane().add(gameArea);
                frame3.pack();
                frame3.setLocationRelativeTo(null);
                frame3.setVisible(true);

                JButton stopBuild = new JButton("Építőmód kikapcsolása");
                stopBuild.setVisible(false);
                stopBuild.addActionListener(e1113 -> {
                    gameArea.setBuildingMode(0,0);
                    stopBuild.setVisible(false);
                    for (MouseListener l : gameArea.getMouseListeners()) {
                        gameArea.removeMouseListener(l);
                    }
                });

                menuBar = new JMenuBar();
                menuBar.setPreferredSize(new Dimension(1616,41));
                menu = new JMenu("Városvezetés");
                menu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuBar.add(menu);

                subMenu = new JMenu("Zóna kijelölés");
                subMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                menuItem = new JMenuItem("Lakossági");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e11 -> {
                    gameArea.setBuildingMode(3,1);
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);
                menuItem = new JMenuItem("Ipari");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e11 -> {
                    gameArea.setBuildingMode(3,2);
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);
                menuItem = new JMenuItem("Szolgáltatási");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e11 -> {
                    gameArea.setBuildingMode(3,3);
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);

                menu.add(subMenu);

                menu.addSeparator();

                subMenu = new JMenu("Építkezés");
                subMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                menuItem = new JMenuItem("Út");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e11 -> {
                    //gameArea.clickOnField(1);
                    gameArea.setBuildingMode(1,1);
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);
                menuItem = new JMenuItem("Rendőrség");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e112 -> {
                    //gameArea.clickOnField(1);
                    gameArea.setBuildingMode(1,2);
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);
                menuItem = new JMenuItem("Iskola");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e113 -> {
                    //gameArea.clickOnField(1);
                    gameArea.setBuildingMode(1,3);
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);
                menuItem = new JMenuItem("Egyetem");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e114 -> {
                    //gameArea.clickOnField(1);
                    gameArea.setBuildingMode(1,4);
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);
                menuItem = new JMenuItem("Stadion");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e115 -> {
                    //gameArea.clickOnField(1);
                    gameArea.setBuildingMode(1,5);
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);
                menuItem = new JMenuItem("Erőmű");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e116 -> {
                    //gameArea.clickOnField(1);
                    gameArea.setBuildingMode(1,6);
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);

                menu.add(subMenu);

                menu.addSeparator();

                menuItem = new JMenuItem("Bontás");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e117 -> {
                    gameArea.setBuildingMode(2,0);
                    stopBuild.setVisible(true);
                });
                menu.add(menuItem);

                timetext = new JLabel("2023.01.01. 00:00");
                timetext.setFont(timetext.getFont().deriveFont(24.0f));
                gameArea.Time();

                Timer timer = new Timer(1000, e117 -> timetext.setText(gameArea.getTime()));
                timer.start();
                timetext.setBorder(new EmptyBorder(0, 450, 0, 566));

                JButton saveButton = new JButton("Mentés");
                saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                saveButton.setBorderPainted(false);
                saveButton.setContentAreaFilled(false);
                saveButton.setFocusPainted(false);
                saveButton.addActionListener(e1114 -> showMessageDialog(null, "Jelenleg nem funkcionál.", "Hiba", JOptionPane.ERROR_MESSAGE));
                menuBar.add(saveButton);

                JButton exitButton = new JButton("Kilépés");
                exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                exitButton.setBorderPainted(false);
                exitButton.setContentAreaFilled(false);
                exitButton.setFocusPainted(false);
                exitButton.addActionListener(e118 -> {
                    Object[] options = {"Igen", "Nem"};
                    int confirm = JOptionPane.showOptionDialog(
                            frame, "Biztosan be szeretnéd zárni a játékot?\nMentés nélkül az összes mentetlen adat elvész.", "Megerősítés",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (confirm == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                });
                menuBar.add(exitButton);

                menuBar.add(stopBuild);

                menuBar.add(timetext);

                JButton pauseButton = new JButton();
                JButton playButton = new JButton();
                JButton fastForwardButton = new JButton();
                JButton fasterForwardButton = new JButton();

                pauseButton.setIcon(new ImageIcon("pause.png"));
                pauseButton.setBorderPainted(false);
                pauseButton.setContentAreaFilled(false);
                pauseButton.setFocusPainted(false);
                pauseButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                pauseButton.setBorder(greenBorder);
                pauseButton.addActionListener(e119 -> {
                    gameArea.setGameSpeed(0);
                    pauseButton.setBorderPainted(true);
                    playButton.setBorderPainted(false);
                    fastForwardButton.setBorderPainted(false);
                    fasterForwardButton.setBorderPainted(false);
                    timer.stop();
                });
                menuBar.add(pauseButton);

                playButton.setIcon(new ImageIcon("play.png"));
                playButton.setContentAreaFilled(false);
                playButton.setFocusPainted(false);
                playButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                playButton.setBorderPainted(true);
                playButton.setBorder(greenBorder);
                playButton.addActionListener(e1110 -> {
                    gameArea.setGameSpeed(1);
                    pauseButton.setBorderPainted(false);
                    playButton.setBorderPainted(true);
                    fastForwardButton.setBorderPainted(false);
                    fasterForwardButton.setBorderPainted(false);
                    timer.setDelay(1000);
                    timer.start();
                });
                menuBar.add(playButton);

                fastForwardButton.setIcon(new ImageIcon("fast_forward.png"));
                fastForwardButton.setBorderPainted(false);
                fastForwardButton.setContentAreaFilled(false);
                fastForwardButton.setFocusPainted(false);
                fastForwardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                fastForwardButton.setBorder(greenBorder);
                fastForwardButton.addActionListener(e1111 -> {
                    gameArea.setGameSpeed(2);
                    pauseButton.setBorderPainted(false);
                    playButton.setBorderPainted(false);
                    fastForwardButton.setBorderPainted(true);
                    fasterForwardButton.setBorderPainted(false);
                    timer.setDelay(10);
                    timer.start();
                });
                menuBar.add(fastForwardButton);

                fasterForwardButton.setIcon(new ImageIcon("fast_forward_3.png"));
                fasterForwardButton.setBorderPainted(false);
                fasterForwardButton.setContentAreaFilled(false);
                fasterForwardButton.setFocusPainted(false);
                fasterForwardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                fasterForwardButton.setBorder(greenBorder);
                fasterForwardButton.addActionListener(e1112 -> {
                    gameArea.setGameSpeed(3);
                    pauseButton.setBorderPainted(false);
                    playButton.setBorderPainted(false);
                    fastForwardButton.setBorderPainted(false);
                    fasterForwardButton.setBorderPainted(true);
                    timer.setDelay(1);
                    timer.start();
                });
                menuBar.add(fasterForwardButton);

                frame3.setJMenuBar(menuBar);
            });
            JobbB.setBorder(BorderFactory.createEmptyBorder());
            JobbB.setOpaque(false);
            JobbB.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            frame2.add(BalB);
            frame2.add(JobbB);
            frame2.getContentPane().add(mapSelectArea);
            frame2.pack();
            frame2.setSize(new Dimension(700, 600));
            frame2.setLocationRelativeTo(null);
            frame2.setVisible(true);


        });
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.YELLOW);
        JButton b2 = new JButton("Betöltés");
        b2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b2.setFocusPainted(false);
        b2.setBounds(250,405,200,50);
        b2.addActionListener(e -> showMessageDialog(null, "Jelenleg nem funkcionál.", "Hiba", JOptionPane.ERROR_MESSAGE));
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.GREEN);
        JButton b3 = new JButton("Kilépés");
        b3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b3.setFocusPainted(false);
        b3.setBounds(250,480,200,50);
        b3.addActionListener(e -> System.exit(0));
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.RED);
        frame.add(b1);
        frame.add(b2);
        frame.add(b3);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        menuArea = new Menu();
        frame.getContentPane().add(menuArea);
        frame.pack();
        frame.setSize(new Dimension(700, 600));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}