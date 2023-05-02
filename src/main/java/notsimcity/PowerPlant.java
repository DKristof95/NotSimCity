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

        try {
            neighbors.add(Grid.get(i).get(j));
            neighbors.add(Grid.get(i-1).get(j));
            neighbors.add(Grid.get(i-1).get(j+1));
            neighbors.add(Grid.get(i+2).get(j));
            neighbors.add(Grid.get(i+2).get(j+1));
            neighbors.add(Grid.get(i).get(j-1));
            neighbors.add(Grid.get(i+1).get(j-1));
            neighbors.add(Grid.get(i).get(j+2));
            neighbors.add(Grid.get(i+1).get(j+2));
        } catch (IndexOutOfBoundsException e) {

        }

        while(!empty) {
            int loop = neighbors.size();
            Field previous = neighbors.get(0);
            for(int l = 1;l < loop;l++) {
                if(neighbors.get(l).getPowerDemand() > 0){
                    neighbors.get(l).hasPower = true;
                    System.out.println(neighbors.get(l).y + " " + neighbors.get(l).x);
                    i = neighbors.get(l).y/field.height;
                    j = neighbors.get(l).x/field.width;
                    neighbors.add(Grid.get(i).get(j));
                    if(i <= 12) {
                        if(!previous.equals(Grid.get(i+2).get(j))) {neighbors.add(Grid.get(i+2).get(j));}
                        if(!previous.equals(Grid.get(i+2).get(j+1))) {neighbors.add(Grid.get(i+2).get(j+1));}
                    }
                    if(i > 0) {
                        if(!previous.equals(Grid.get(i-1).get(j))) {neighbors.add(Grid.get(i-1).get(j));}
                        if(!previous.equals(Grid.get(i-1).get(j+1))) {neighbors.add(Grid.get(i-1).get(j+1));}
                    }
                    if(j <= 27) {
                        if(!previous.equals(Grid.get(i).get(j+2))) {neighbors.add(Grid.get(i).get(j+2));}
                        if(!previous.equals(Grid.get(i+1).get(j+2))) {neighbors.add(Grid.get(i+1).get(j+2));}
                    }
                    if(j > 0) {
                        if(!previous.equals(Grid.get(i).get(j-1))) {neighbors.add(Grid.get(i).get(j-1));}
                        if(!previous.equals(Grid.get(i+1).get(j-1))) {neighbors.add(Grid.get(i+1).get(j-1));}
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