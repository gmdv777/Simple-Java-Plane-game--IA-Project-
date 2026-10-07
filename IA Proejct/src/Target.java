import javax.swing.*;
import java.awt.*;
public class Target extends JPanel{
    public int width;
    public int height;
    public int xplace;
    public int yplace;

    //Constructor used when all the fields are defined/filled
    public Target(int width, int height, int xplace, int yplace) {
        this.width = width;
        this.height = height;
        this.xplace = xplace;
        this.yplace = yplace;
    }

    ////Constructor used when not all the fields are defined/filled
    public Target(){
        repaint();
        this.width = 50;
        this.height = 25;
        this.xplace = 200;
    }

    //When called, it draws the target in the location and size defined from it's variables
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.drawOval(xplace, yplace, width, height);
        g.fillOval(xplace,yplace , width, height);

    }
}