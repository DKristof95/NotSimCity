package notsimcity;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
public class Menu extends JPanel{
    private Image Logo;
    public Menu() {
        super();
        Logo = new ImageIcon("Logo.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(Logo, 150, 10, 384, 303, null);
    }
}
