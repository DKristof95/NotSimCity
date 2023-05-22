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
        }

        public void checkPowerNeed(int oi, int oj, ArrayList<ArrayList<Field>> Grid) {
            boolean empty = false;
            int prev_size = -1;
            ArrayList<Field> neighbors = new ArrayList<>();
            neighbors.add(Grid.get(oi).get(oj));
            if(oi >= 1) {
                neighbors.add(Grid.get(oi-1).get(oj));
                neighbors.add(Grid.get(oi-1).get(oj+1));
            }
            if(oj >= 1) {
                neighbors.add(Grid.get(oi).get(oj-1));
                neighbors.add(Grid.get(oi+1).get(oj-1));
            }
            if(oi < 11) {
                neighbors.add(Grid.get(oi+2).get(oj));
                neighbors.add(Grid.get(oi+2).get(oj+1));
            }
            if(oj < 26) {
                neighbors.add(Grid.get(oi).get(oj+2));
                neighbors.add(Grid.get(oi+1).get(oj+2));
            }

            while(!empty) {
                int loop = neighbors.size();
                for(int l = 0;l < loop; l++) {
                    //System.out.println(neighbors.get(l).y + " " + neighbors.get(l).x);
                    if(neighbors.get(l).getClass().equals(Pole.class)) {
                        int i = neighbors.get(l).y/neighbors.get(l).height;
                        int j = neighbors.get(l).x/neighbors.get(l).width;
                        if(i >= 0 && i < 12) {
                            if(!neighbors.contains(Grid.get(i+1).get(j))) {neighbors.add(Grid.get(i+1).get(j));}
                        }
                        if(j > 0 && j <= 27) {
                            if(!neighbors.contains(Grid.get(i).get(j-1))) {neighbors.add(Grid.get(i).get(j-1));}
                        }
                        if(j < 27 && j >= 0) {
                            if(!neighbors.contains(Grid.get(i).get(j+1))) {neighbors.add(Grid.get(i).get(j+1));}
                        }
                        if(i > 0 && i <= 12) {
                            if(!neighbors.contains(Grid.get(i-1).get(j))) {neighbors.add(Grid.get(i-1).get(j));}
                        }
                    }
                    else if((neighbors.get(l).getClass().equals(Stadium.class) || neighbors.get(l).getClass().equals(University.class)) && !neighbors.get(l).hasPower) {
                        int i = neighbors.get(l).y/neighbors.get(l).height;
                        int j = neighbors.get(l).x/neighbors.get(l).width;
                        if((Grid.get(oi).get(oj).getCapacity() - (neighbors.get(l).getPowerDemand()*4)) > 0) {
                            neighbors.get(l).hasPower = true;
                            Grid.get(i+1).get(j).hasPower = true;
                            Grid.get(i).get(j+1).hasPower = true;
                            Grid.get(i+1).get(j+1).hasPower = true;
                            Grid.get(oi).get(oj).setCapacity(-(neighbors.get(l).getPowerDemand()*4));
                            neighbors.get(l).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            Grid.get(i+1).get(j).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            Grid.get(i).get(j+1).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            Grid.get(i+1).get(j+1).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            if(!neighbors.contains(Grid.get(i+1).get(j))) {neighbors.add(Grid.get(i+1).get(j));}
                            if(!neighbors.contains(Grid.get(i).get(j+1))) {neighbors.add(Grid.get(i).get(j+1));}
                            if(!neighbors.contains(Grid.get(i+1).get(j+1))) {neighbors.add(Grid.get(i+1).get(j+1));}
                        }
                        if(i >= 1) {
                            if(!neighbors.contains(Grid.get(i-1).get(j))) {neighbors.add(Grid.get(i-1).get(j));}
                            if(!neighbors.contains(Grid.get(i-1).get(j+1))) {neighbors.add(Grid.get(i-1).get(j+1));}
                        }
                        if(j >= 1) {
                            if(!neighbors.contains(Grid.get(i).get(j-1))) {neighbors.add(Grid.get(i).get(j-1));}
                            if(!neighbors.contains(Grid.get(i+1).get(j-1))) {neighbors.add(Grid.get(i+1).get(j-1));}
                        }
                        if(i < 11) {
                            if(!neighbors.contains(Grid.get(i+2).get(j))) {neighbors.add(Grid.get(i+2).get(j));}
                            if(!neighbors.contains(Grid.get(i+2).get(j+1))) {neighbors.add(Grid.get(i+2).get(j+1));}
                        }
                        if(j < 26) {
                            if(!neighbors.contains(Grid.get(i).get(j+2))) {neighbors.add(Grid.get(i).get(j+2));}
                            if(!neighbors.contains(Grid.get(i+1).get(j+2))) {neighbors.add(Grid.get(i+1).get(j+2));}
                        }
                    }
                    else if((neighbors.get(l).getClass().equals(StadiumLL.class) || neighbors.get(l).getClass().equals(UniversityLL.class)) && !neighbors.get(l).hasPower) {
                        int i = neighbors.get(l).y/neighbors.get(l).height;
                        int j = neighbors.get(l).x/neighbors.get(l).width;
                        if((Grid.get(oi).get(oj).getCapacity() - (neighbors.get(l).getPowerDemand()*4)) > 0) {
                            neighbors.get(l).hasPower = true;
                            Grid.get(i-1).get(j).hasPower = true;
                            Grid.get(i).get(j+1).hasPower = true;
                            Grid.get(i-1).get(j+1).hasPower = true;
                            Grid.get(oi).get(oj).setCapacity(-(neighbors.get(l).getPowerDemand()*4));
                            neighbors.get(l).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            Grid.get(i-1).get(j).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            Grid.get(i).get(j+1).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            Grid.get(i-1).get(j+1).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            if(!neighbors.contains(Grid.get(i-1).get(j))) {neighbors.add(Grid.get(i-1).get(j));}
                            if(!neighbors.contains(Grid.get(i).get(j+1))) {neighbors.add(Grid.get(i).get(j+1));}
                            if(!neighbors.contains(Grid.get(i-1).get(j+1))) {neighbors.add(Grid.get(i-1).get(j+1));}
                        }
                        if(i >= 2) {
                            if(!neighbors.contains(Grid.get(i-2).get(j))) {neighbors.add(Grid.get(i-2).get(j));}
                            if(!neighbors.contains(Grid.get(i-2).get(j+1))) {neighbors.add(Grid.get(i-2).get(j+1));}
                        }
                        if(j >= 1) {
                            if(!neighbors.contains(Grid.get(i).get(j-1))) {neighbors.add(Grid.get(i).get(j-1));}
                            if(!neighbors.contains(Grid.get(i-1).get(j-1))) {neighbors.add(Grid.get(i-1).get(j-1));}
                        }
                        if(i < 12) {
                            if(!neighbors.contains(Grid.get(i+1).get(j))) {neighbors.add(Grid.get(i+1).get(j));}
                            if(!neighbors.contains(Grid.get(i+1).get(j+1))) {neighbors.add(Grid.get(i+1).get(j+1));}
                        }
                        if(j < 26) {
                            if(!neighbors.contains(Grid.get(i).get(j+2))) {neighbors.add(Grid.get(i).get(j+2));}
                            if(!neighbors.contains(Grid.get(i-1).get(j+2))) {neighbors.add(Grid.get(i-1).get(j+2));}
                        }
                    }
                    else if((neighbors.get(l).getClass().equals(StadiumLR.class) || neighbors.get(l).getClass().equals(UniversityLR.class)) && !neighbors.get(l).hasPower) {
                        int i = neighbors.get(l).y/neighbors.get(l).height;
                        int j = neighbors.get(l).x/neighbors.get(l).width;
                        if((Grid.get(oi).get(oj).getCapacity() - (neighbors.get(l).getPowerDemand()*4)) > 0) {
                            neighbors.get(l).hasPower = true;
                            Grid.get(i-1).get(j).hasPower = true;
                            Grid.get(i).get(j-1).hasPower = true;
                            Grid.get(i-1).get(j-1).hasPower = true;
                            Grid.get(oi).get(oj).setCapacity(-(neighbors.get(l).getPowerDemand()*4));
                            neighbors.get(l).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            Grid.get(i-1).get(j).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            Grid.get(i).get(j-1).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            Grid.get(i-1).get(j-1).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            if(!neighbors.contains(Grid.get(i-1).get(j))) {neighbors.add(Grid.get(i-1).get(j));}
                            if(!neighbors.contains(Grid.get(i).get(j-1))) {neighbors.add(Grid.get(i).get(j-1));}
                            if(!neighbors.contains(Grid.get(i-1).get(j-1))) {neighbors.add(Grid.get(i-1).get(j-1));}
                        }
                        if(i >= 2) {
                            if(!neighbors.contains(Grid.get(i-2).get(j))) {neighbors.add(Grid.get(i-2).get(j));}
                            if(!neighbors.contains(Grid.get(i-2).get(j-1))) {neighbors.add(Grid.get(i-2).get(j-1));}
                        }
                        if(j >= 2) {
                            if(!neighbors.contains(Grid.get(i).get(j-2))) {neighbors.add(Grid.get(i).get(j-2));}
                            if(!neighbors.contains(Grid.get(i-1).get(j-2))) {neighbors.add(Grid.get(i-1).get(j-2));}
                        }
                        if(i < 12) {
                            if(!neighbors.contains(Grid.get(i+1).get(j))) {neighbors.add(Grid.get(i+1).get(j));}
                            if(!neighbors.contains(Grid.get(i+1).get(j-1))) {neighbors.add(Grid.get(i+1).get(j-1));}
                        }
                        if(j < 27) {
                            if(!neighbors.contains(Grid.get(i).get(j+1))) {neighbors.add(Grid.get(i).get(j+1));}
                            if(!neighbors.contains(Grid.get(i-1).get(j+1))) {neighbors.add(Grid.get(i-1).get(j+1));}
                        }
                    }
                    else if((neighbors.get(l).getClass().equals(StadiumUR.class) || neighbors.get(l).getClass().equals(UniversityUR.class)) && !neighbors.get(l).hasPower) {
                        int i = neighbors.get(l).y/neighbors.get(l).height;
                        int j = neighbors.get(l).x/neighbors.get(l).width;
                        if((Grid.get(oi).get(oj).getCapacity() - (neighbors.get(l).getPowerDemand()*4)) > 0) {
                            neighbors.get(l).hasPower = true;
                            Grid.get(i+1).get(j).hasPower = true;
                            Grid.get(i).get(j-1).hasPower = true;
                            Grid.get(i+1).get(j-1).hasPower = true;
                            Grid.get(oi).get(oj).setCapacity(-(neighbors.get(l).getPowerDemand()*4));
                            neighbors.get(l).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            Grid.get(i+1).get(j).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            Grid.get(i).get(j-1).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            Grid.get(i+1).get(j-1).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                            if(!neighbors.contains(Grid.get(i+1).get(j))) {neighbors.add(Grid.get(i+1).get(j));}
                            if(!neighbors.contains(Grid.get(i).get(j-1))) {neighbors.add(Grid.get(i).get(j-1));}
                            if(!neighbors.contains(Grid.get(i+1).get(j-1))) {neighbors.add(Grid.get(i+1).get(j-1));}
                        }
                        if(i >= 1) {
                            if(!neighbors.contains(Grid.get(i-1).get(j))) {neighbors.add(Grid.get(i-1).get(j));}
                            if(!neighbors.contains(Grid.get(i-1).get(j-1))) {neighbors.add(Grid.get(i-1).get(j-1));}
                        }
                        if(j >= 2) {
                            if(!neighbors.contains(Grid.get(i).get(j-2))) {neighbors.add(Grid.get(i).get(j-2));}
                            if(!neighbors.contains(Grid.get(i+1).get(j-2))) {neighbors.add(Grid.get(i+1).get(j-2));}
                        }
                        if(i < 11) {
                            if(!neighbors.contains(Grid.get(i+2).get(j))) {neighbors.add(Grid.get(i+2).get(j));}
                            if(!neighbors.contains(Grid.get(i+2).get(j-1))) {neighbors.add(Grid.get(i+2).get(j-1));}
                        }
                        if(j < 27) {
                            if(!neighbors.contains(Grid.get(i).get(j+1))) {neighbors.add(Grid.get(i).get(j+1));}
                            if(!neighbors.contains(Grid.get(i+1).get(j+1))) {neighbors.add(Grid.get(i+1).get(j+1));}
                        }
                    }
                    else if(neighbors.get(l).getPowerDemand() > 0 && (Grid.get(oi).get(oj).getCapacity() - neighbors.get(l).getPowerDemand()) > 0) {
                        if(!neighbors.get(l).hasPower) {
                            neighbors.get(l).hasPower = true;
                            Grid.get(oi).get(oj).setCapacity(-neighbors.get(l).getPowerDemand());
                            neighbors.get(l).setPowerSource((PowerPlant) Grid.get(oi).get(oj));
                        }
                        int i = neighbors.get(l).y/neighbors.get(l).height;
                        int j = neighbors.get(l).x/neighbors.get(l).width;
                        if(i >= 0 && i < 12) {
                            if(!neighbors.contains(Grid.get(i+1).get(j))) {neighbors.add(Grid.get(i+1).get(j));}
                        }
                        if(j > 0 && j <= 27) {
                            if(!neighbors.contains(Grid.get(i).get(j-1))) {neighbors.add(Grid.get(i).get(j-1));}
                        }
                        if(j < 27 && j >= 0) {
                            if(!neighbors.contains(Grid.get(i).get(j+1))) {neighbors.add(Grid.get(i).get(j+1));}
                        }
                        if(i > 0 && i <= 12) {
                            if(!neighbors.contains(Grid.get(i-1).get(j))) {neighbors.add(Grid.get(i-1).get(j));}
                        }
                    }
                }
                if(neighbors.size() == prev_size){empty = true;}
                prev_size = neighbors.size();
            }
        }

        @Override
        public void setCapacity(int cap) {
            this.capacity += cap;
        }
    }