import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class PlaneP extends JPanel{
    public int x = 0;
    public int y = 200;
    public int speed;
    private BufferedImage planeImage;

    //Constructor used when all the fields are defined/filled
    //Sets the speed of the plane and finds the correct png image
    public PlaneP(int speeds) {
        speed = speeds;
        try {
            planeImage = ImageIO.read(getClass().getClassLoader().getResource("plane.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //moves the plane to the right
    public void update() {
        x += speed;
    }

    //if the plane goes over the right side of the screen, it teleports it back to the left
    public void resetIfNeeded(int width) {
        if (x > width) {
            x = -50;
        }
    }

    //When called, it draws the plane png at the defined position
    public void draw(Graphics g) {

        g.drawImage(planeImage, x, y, 100, 50, null);
    }


  /*  public static BufferedImage png()
    {
        try {
            File file = new File("C:\\Users\\massgiu26\\OneDrive - issaquah.wednet.edu\12th Grade\\Computer science HL\\IA Proejct\src");
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    } */
}

