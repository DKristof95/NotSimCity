package notsimcity;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;

public class NotSimCityGUI {
    private JFrame frame, frame2, frame3, frame4, frame5;
    private Game gameArea;
    private JPanel panel;
    private JLabel timetext, money, satisfaction, population;
    private Menu menuArea;
    private MapSelect mapSelectArea;
    private JMenuBar menuBar, menuBar2;
    private JMenu menu, subMenu;
    private JMenuItem menuItem;
    private JTextField cityNameField;
    private String cityName;
    private int active = 0;
    private boolean showMoneyLogs = false, showEditTaxes = false;
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

            Border redBorder = BorderFactory.createLineBorder(Color.RED, 2, true);
            ButtonGroup buttonGroup = new ButtonGroup();

            JRadioButton radio1 = new JRadioButton("Véletlenszerű pálya",true);
            radio1.setBounds(100,110,20,20);
            JLabel radio1_label = new JLabel("Véletlenszerű pálya");
            radio1_label.setBounds(120,110,200,20);
            buttonGroup.add(radio1);
            JRadioButton radio2 = new JRadioButton("Előre megadott pálya választása");
            radio2.setBounds(100,150,20,20);
            JLabel radio2_label = new JLabel("Előre megadott pálya választása");
            radio2_label.setBounds(120,150,300,20);
            buttonGroup.add(radio2);
            ImageIcon bg1 = new ImageIcon("chosenpic1.png");
            JButton option1 = new JButton(bg1);
            option1.setBounds(50,220,160,160);
            option1.setVisible(false);
            option1.setFocusPainted(false);
            option1.setBorder(redBorder);
            option1.setBorderPainted(true);
            ImageIcon bg2 = new ImageIcon("chosenpic2.png");
            JButton option2 = new JButton(bg2);
            option2.setBounds(260,220,160,160);
            option2.setVisible(false);
            option2.setFocusPainted(false);
            option2.setBorder(redBorder);
            option2.setBorderPainted(false);
            ImageIcon bg3 = new ImageIcon("chosenpic3.png");
            JButton option3 = new JButton(bg3);
            option3.setBounds(470,220,160,160);
            option3.setVisible(false);
            option3.setFocusPainted(false);
            option3.setBorder(redBorder);
            option3.setBorderPainted(false);
            option1.addActionListener(o1 -> {
                option1.setBorderPainted(true);
                option2.setBorderPainted(false);
                option3.setBorderPainted(false);
                active = 1;
            });
            option2.addActionListener(o1 -> {
                option1.setBorderPainted(false);
                option2.setBorderPainted(true);
                option3.setBorderPainted(false);
                active = 2;
            });
            option3.addActionListener(o1 -> {
                option1.setBorderPainted(false);
                option2.setBorderPainted(false);
                option3.setBorderPainted(true);
                active = 3;
            });
            radio1.addActionListener(er2 -> {
                option1.setVisible(false);
                option2.setVisible(false);
                option3.setVisible(false);
                active = 0;
            });
            radio2.addActionListener(er2 -> {
                option1.setVisible(true);
                option2.setVisible(true);
                option3.setVisible(true);
                active = 1;
            });
            frame2.add(option1);
            frame2.add(option2);
            frame2.add(option3);
            frame2.add(radio1);
            frame2.add(radio2);
            frame2.add(radio1_label);
            frame2.add(radio2_label);

            JButton BalB = new BasicArrowButton(BasicArrowButton.WEST);
            BalB.setBounds(80,430,64,64);
            BalB.addActionListener(e1 -> {
                frame.setVisible(true);
                frame2.dispose();
            });
            BalB.setBorder(BorderFactory.createEmptyBorder());
            BalB.setOpaque(false);
            BalB.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            JButton JobbB = new BasicArrowButton(BasicArrowButton.EAST);
            JobbB.setBounds(536,430,64,64);
            JobbB.addActionListener(e1 -> {
                Border greenBorder = BorderFactory.createLineBorder(Color.GREEN, 2, true);

                cityName = cityNameField.getText();
                frame.dispose();
                frame2.dispose();
                frame3 = new JFrame("NotSimCity | Város: " + cityName );
                frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame3.setPreferredSize(new Dimension(1616, 930));
                frame3.setResizable(false);
                gameArea = new Game(active);
                frame3.getContentPane().add(gameArea);
                frame3.pack();
                frame3.setLocationRelativeTo(null);
                frame3.setVisible(true);

                JButton stopBuild = new JButton("Építőmód kikapcsolása");
                stopBuild.setVisible(false);
                stopBuild.addActionListener(e1113 -> {
                    gameArea.setBuildingMode(0,0);
                    stopBuild.setVisible(false);
                    timetext.setBorder(new EmptyBorder(0, 450, 0, 630));
                    for (MouseListener l : gameArea.getMouseListeners()) {
                        gameArea.removeMouseListener(l);
                    }
                    gameArea.createML();
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
                    timetext.setBorder(new EmptyBorder(0, 284, 0, 566));
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);
                menuItem = new JMenuItem("Ipari");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e11 -> {
                    gameArea.setBuildingMode(3,2);
                    timetext.setBorder(new EmptyBorder(0, 284, 0, 566));
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);
                menuItem = new JMenuItem("Szolgáltatási");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e11 -> {
                    gameArea.setBuildingMode(3,3);
                    timetext.setBorder(new EmptyBorder(0, 284, 0, 566));
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
                    timetext.setBorder(new EmptyBorder(0, 284, 0, 630));
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);
                menuItem = new JMenuItem("Rendőrség");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e112 -> {
                    //gameArea.clickOnField(1);
                    gameArea.setBuildingMode(1,2);
                    timetext.setBorder(new EmptyBorder(0, 284, 0, 630));
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);
                menuItem = new JMenuItem("Iskola");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e113 -> {
                    //gameArea.clickOnField(1);
                    gameArea.setBuildingMode(1,3);
                    timetext.setBorder(new EmptyBorder(0, 284, 0, 630));
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);
                menuItem = new JMenuItem("Egyetem");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e114 -> {
                    //gameArea.clickOnField(1);
                    gameArea.setBuildingMode(1,4);
                    timetext.setBorder(new EmptyBorder(0, 284, 0, 630));
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);
                menuItem = new JMenuItem("Stadion");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e115 -> {
                    //gameArea.clickOnField(1);
                    gameArea.setBuildingMode(1,5);
                    timetext.setBorder(new EmptyBorder(0, 284, 0, 630));
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);
                menuItem = new JMenuItem("Erőmű");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e116 -> {
                    //gameArea.clickOnField(1);
                    gameArea.setBuildingMode(1,6);
                    timetext.setBorder(new EmptyBorder(0, 284, 0, 630));
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);

                menuItem = new JMenuItem("Vezeték");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e116 -> {
                    gameArea.setBuildingMode(1,9);
                    timetext.setBorder(new EmptyBorder(0, 284, 0, 630));
                    stopBuild.setVisible(true);
                });
                subMenu.add(menuItem);

                menu.add(subMenu);

                menu.addSeparator();

                menuItem = new JMenuItem("Bontás");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e117 -> {
                    gameArea.setBuildingMode(2,0);
                    timetext.setBorder(new EmptyBorder(0, 284, 0, 630));
                    stopBuild.setVisible(true);
                });
                menu.add(menuItem);

                menuItem = new JMenuItem("Zóna törlés");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e1000 -> {
                    gameArea.setBuildingMode(4,0);
                    timetext.setBorder(new EmptyBorder(0, 284, 0, 630));
                    stopBuild.setVisible(true);
                });
                menu.add(menuItem);

                menu.addSeparator();

                menuItem = new JMenuItem("Erdősítés");
                menuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.addActionListener(e118 -> {
                    gameArea.setBuildingMode(1,7);
                    timetext.setBorder(new EmptyBorder(0, 284, 0, 630));
                    stopBuild.setVisible(true);
                });
                menu.add(menuItem);

                timetext = new JLabel("2023.01.01. 00:00");
                timetext.setFont(timetext.getFont().deriveFont(24.0f));
                timetext.setBorder(new EmptyBorder(0, 450, 0, 630));

                JButton exitButton = new JButton("Kilépés");
                exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                exitButton.setBorderPainted(false);
                exitButton.setContentAreaFilled(false);
                exitButton.setFocusPainted(false);
                exitButton.addActionListener(e118 -> {
                    Object[] options = {"Igen", "Nem"};
                    int confirm = JOptionPane.showOptionDialog(
                            frame, "Biztosan be szeretnéd zárni a játékot?\nAz összes adat elvész!", "Megerősítés",
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
                menuBar.add(pauseButton);

                playButton.setIcon(new ImageIcon("play.png"));
                playButton.setContentAreaFilled(false);
                playButton.setFocusPainted(false);
                playButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                playButton.setBorderPainted(true);
                playButton.setBorder(greenBorder);
                menuBar.add(playButton);

                fastForwardButton.setIcon(new ImageIcon("fast_forward.png"));
                fastForwardButton.setBorderPainted(false);
                fastForwardButton.setContentAreaFilled(false);
                fastForwardButton.setFocusPainted(false);
                fastForwardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                fastForwardButton.setBorder(greenBorder);
                menuBar.add(fastForwardButton);

                fasterForwardButton.setIcon(new ImageIcon("fast_forward_3.png"));
                fasterForwardButton.setBorderPainted(false);
                fasterForwardButton.setContentAreaFilled(false);
                fasterForwardButton.setFocusPainted(false);
                fasterForwardButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                fasterForwardButton.setBorder(greenBorder);
                menuBar.add(fasterForwardButton);

                frame3.setJMenuBar(menuBar);

                menuBar2 = new JMenuBar();
                menuBar2.setPreferredSize(new Dimension(1616,50));
                ImageIcon icon = new ImageIcon("money.png");
                JButton moneyButton = new JButton(icon);
                moneyButton.setHorizontalAlignment(SwingConstants.LEFT);
                moneyButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                moneyButton.setBorderPainted(false);
                moneyButton.setContentAreaFilled(false);
                moneyButton.setFocusPainted(false);
                moneyButton.addActionListener(e2 -> {
                    if(frame4 != null && frame4.isVisible()) {
                        frame4.dispose();
                    }
                    else {
                        frame4 = new JFrame("Adók");
                        frame4.setResizable(false);
                        panel = new JPanel();
                        panel.setPreferredSize(new Dimension(400,250));
                        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
                        JButton zeroButton = new JButton("0%    ++Elégedettség");
                        JButton halfButton = new JButton("25%    +Elégedettség");
                        JButton oneButton = new JButton("50%");
                        JButton onehalfButton = new JButton("75%    -Elégedettség");
                        JButton twoButton = new JButton("100%  --Elégedettség");
                        switch ((int)(gameArea.getTaxMultiplier() * 2)) {
                            case 0 -> zeroButton.setForeground(new Color(112, 78, 148));
                            case 1 -> halfButton.setForeground(new Color(112, 78, 148));
                            case 2 -> oneButton.setForeground(new Color(112, 78, 148));
                            case 3 -> onehalfButton.setForeground(new Color(112, 78, 148));
                            case 4 -> twoButton.setForeground(new Color(112, 78, 148));
                            default -> {
                            }
                        }
                        zeroButton.setMaximumSize(new Dimension(400,50));
                        zeroButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        zeroButton.setBorderPainted(false);
                        zeroButton.setContentAreaFilled(false);
                        zeroButton.setFocusPainted(false);
                        zeroButton.addActionListener(e21 -> {
                            gameArea.setTaxMultiplier(1);
                            frame4.dispose();
                        });
                        panel.add(zeroButton);
                        halfButton.setMaximumSize(new Dimension(400,50));
                        halfButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        halfButton.setBorderPainted(false);
                        halfButton.setContentAreaFilled(false);
                        halfButton.setFocusPainted(false);
                        halfButton.addActionListener(e21 -> {
                            gameArea.setTaxMultiplier(2);
                            frame4.dispose();
                        });
                        panel.add(halfButton);
                        oneButton.setMaximumSize(new Dimension(400,50));
                        oneButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        oneButton.setBorderPainted(false);
                        oneButton.setContentAreaFilled(false);
                        oneButton.setFocusPainted(false);
                        oneButton.addActionListener(e21 -> {
                            gameArea.setTaxMultiplier(3);
                            frame4.dispose();
                        });
                        panel.add(oneButton);
                        onehalfButton.setMaximumSize(new Dimension(400,50));
                        onehalfButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        onehalfButton.setBorderPainted(false);
                        onehalfButton.setContentAreaFilled(false);
                        onehalfButton.setFocusPainted(false);
                        onehalfButton.addActionListener(e21 -> {
                            gameArea.setTaxMultiplier(4);
                            frame4.dispose();
                        });
                        panel.add(onehalfButton);
                        twoButton.setMaximumSize(new Dimension(400,50));
                        twoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        twoButton.setBorderPainted(false);
                        twoButton.setContentAreaFilled(false);
                        twoButton.setFocusPainted(false);
                        twoButton.addActionListener(e21 -> {
                            gameArea.setTaxMultiplier(5);
                            frame4.dispose();
                        });
                        panel.add(twoButton);
                        frame4.getContentPane().add(panel);
                        frame4.setSize(new Dimension(400, 250));
                        frame4.pack();
                        frame4.setLocationRelativeTo(null);
                        frame4.setVisible(true);
                    }
                });
                money = new JLabel("50000");
                money.setBorder(new EmptyBorder(25, 100, 25, 0));
                moneyButton.add(money);
                menuBar2.add(moneyButton);

                icon = new ImageIcon("smile.png");
                menuItem = new JMenuItem(icon);
                satisfaction = new JLabel("N/A");
                satisfaction.setBorder(new EmptyBorder(25, 100, 25, 0));
                menuItem.add(satisfaction);
                menuBar2.add(menuItem);

                icon = new ImageIcon("village.png");
                menuItem = new JMenuItem(icon);
                population = new JLabel("0");
                population.setBorder(new EmptyBorder(25, 100, 25, 0));
                menuItem.add(population);
                menuBar2.add(menuItem);

                gameArea.Time();
                Timer timer = new Timer(1000, e117 -> {
                    timetext.setText(gameArea.getTime());
                });
                timer.start();

                Timer outputTimer = new Timer(500, e117 -> {
                    money.setText(String.valueOf(gameArea.getMoney()));
                    population.setText(String.valueOf(gameArea.getCitizens()));
                    if(gameArea.getCitizens() > 0){
                        satisfaction.setText(gameArea.getSatisfaction() + "%");
                    }
                });
                outputTimer.start();

                JButton logs = new JButton("Költségvetés");
                logs.setHorizontalAlignment(SwingConstants.LEFT);
                logs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                logs.setBorderPainted(false);
                logs.setContentAreaFilled(false);
                logs.setFocusPainted(false);
                menuBar2.add(logs);

                frame5 = new JFrame("Költségvetés");
                frame5.setResizable(false);
                JPanel panel2 = new JPanel();
                panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
                JScrollPane scrollPane = new JScrollPane(panel2);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                frame5.add(scrollPane);
                frame5.setSize(800,400);
                frame5.setLocationRelativeTo(null);
                frame5.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                logs.addActionListener(el -> {
                    if (showMoneyLogs) {
                        frame5.setVisible(false);
                        showMoneyLogs = false;
                    }
                    else {
                        panel2.removeAll();
                        ArrayList<MoneyLog> Logs = gameArea.getLogs();
                        for(MoneyLog log : Logs) {
                            final JLabel newLabel = new JLabel(log.getOutput());
                            newLabel.setFont(new Font("Courier", Font.PLAIN, 14));
                            panel2.add(newLabel);
                        }
                        frame5.setVisible(true);
                        showMoneyLogs = true;
                    }
                });

                frame5.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        showMoneyLogs = false;
                    }
                });

                frame3.add(menuBar2,BorderLayout.SOUTH);

                pauseButton.addActionListener(e119 -> {
                    gameArea.setGameSpeed(0);
                    pauseButton.setBorderPainted(true);
                    playButton.setBorderPainted(false);
                    fastForwardButton.setBorderPainted(false);
                    fasterForwardButton.setBorderPainted(false);
                    timer.stop();
                });

                playButton.addActionListener(e1110 -> {
                    gameArea.setGameSpeed(1);
                    pauseButton.setBorderPainted(false);
                    playButton.setBorderPainted(true);
                    fastForwardButton.setBorderPainted(false);
                    fasterForwardButton.setBorderPainted(false);
                    timer.setDelay(1000);
                    timer.start();
                });

                fastForwardButton.addActionListener(e1111 -> {
                    gameArea.setGameSpeed(2);
                    pauseButton.setBorderPainted(false);
                    playButton.setBorderPainted(false);
                    fastForwardButton.setBorderPainted(true);
                    fasterForwardButton.setBorderPainted(false);
                    timer.setDelay(10);
                    timer.start();
                });

                fasterForwardButton.addActionListener(e1112 -> {
                    gameArea.setGameSpeed(3);
                    pauseButton.setBorderPainted(false);
                    playButton.setBorderPainted(false);
                    fastForwardButton.setBorderPainted(false);
                    fasterForwardButton.setBorderPainted(true);
                    timer.setDelay(1);
                    timer.start();
                });
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
        JButton b3 = new JButton("Kilépés");
        b3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b3.setFocusPainted(false);
        b3.setBounds(250,405,200,50);
        b3.addActionListener(e -> System.exit(0));
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.RED);
        frame.add(b1);
        frame.add(b3);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        menuArea = new Menu();
        frame.getContentPane().add(menuArea);
        frame.pack();
        frame.setSize(new Dimension(700, 525));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    /**
     * Teszteléshez kell.
     */
    public Game getGameArea(){
        return gameArea;
    }
}