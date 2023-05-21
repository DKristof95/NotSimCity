package notsimcity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PowerPlant extends Field {
    private final Image PowerPlant_im = new ImageIcon("power_plant_upper_left.png").getImage();
    public PowerPlant(Field field, ArrayList<ArrayList<Field>> Grid) {
        super(field.width, field.height, field.x,field.y,100,0,false);
        this.cost = 20000;
        this.image = PowerPlant_im;
        this.hasPower = true;
        boolean empty = false;
        int i = field.y/field.height;
        int j = field.x/field.width;
        ArrayList<Field> neighbors = new ArrayList<>();
        neighbors.add(Grid.get(i).get(j));
        if(i >= 1 && i <= 12) {
            neighbors.add(Grid.get(i-1).get(j));
            if(j < 27) {
                neighbors.add(Grid.get(i-1).get(j+1));
            }
        }
        if(j >= 1 && j <= 27) {
            neighbors.add(Grid.get(i).get(j-1));
            if(i < 12) {
                neighbors.add(Grid.get(i+1).get(j-1));
            }
        }
        if(i < 11) {
            neighbors.add(Grid.get(i+2).get(j));
            if(j < 27) {
                neighbors.add(Grid.get(i+2).get(j+1));
            }
        }
        if(j < 26) {
            neighbors.add(Grid.get(i).get(j+2));
            if(i < 12) {
                neighbors.add(Grid.get(i+1).get(j+2));
            }
        }

        while(!empty) {
            int loop = neighbors.size();
            for(int l = 0;l < loop; l++) {
                if(neighbors.get(l).getPowerDemand() > 0) {
                    neighbors.get(l).hasPower = true;
                    System.out.println(neighbors.get(l).y + " " + neighbors.get(l).x);
                    i = neighbors.get(l).y/field.height;
                    j = neighbors.get(l).x/field.width;
                    if(i >= 0 && i < 12) {
                        if(!Grid.get(i+1).get(j).hasPower) {neighbors.add(Grid.get(i+1).get(j));}
                    }
                    if(j > 0 && j <= 27) {
                        if(!Grid.get(i).get(j-1).hasPower) {neighbors.add(Grid.get(i).get(j-1));}
                    }
                    if(j < 27 && j >= 0) {
                        if(!Grid.get(i).get(j+1).hasPower) {neighbors.add(Grid.get(i).get(j+1));}
                    }
                    if(i > 0 && i <= 12) {
                        if(!Grid.get(i-1).get(j).hasPower) {neighbors.add(Grid.get(i-1).get(j));}
                    }
                }
            }
            int g = 0;
            for (Field f : new ArrayList<>(neighbors)) {
                neighbors.remove(f);
                g++;
                if (g == loop) {
                    break;
                }
            }
            if(neighbors.isEmpty()){empty = true;}
            }
        }
    }