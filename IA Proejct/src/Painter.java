import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;
import java.util.Random;

public class Painter extends JPanel {
    private Timer timer;
    private PlaneP plane;
    private Target target;
    public Opponent opponent;
    public boolean droplow;
    public int yPos;
    public int xPos;
    public double speeddown =10;
    public double speedright;
    public boolean dropping = false;
    public int PlayerScore =0;
    public boolean PlayerTurn = true;
    public int CheckScore=0;
    public int rand;
    public int BotScore =0;
    private JPanel parentCard;
    private CardLayout parentLayout;
    Random dev = new Random();

    //Constructor used when all the fields are defined/filled
    Painter(PlaneP plane, Target target, Opponent opponent, JPanel card, CardLayout layout) {
        this.plane = plane;
        this.target = target;
        this.opponent = opponent;
        speedright = this.plane.speed;
        this.parentCard = card;
        this.parentLayout = layout;
        timer = new Timer(10, e -> {
            if(PlayerTurn == true) {
                plane.update();
                plane.resetIfNeeded(getWidth());
            }
            if(PlayerTurn == false) {
                opponent.update();
                opponent.resetIfNeeded(getWidth());
            }
            if (opponent.x > target.xplace + target.width) { rand = dev.nextInt(100); }
            if(PlayerTurn == false && !dropping) {
                if(opponent.diffculty.equals("hard")) {

                    double landingX = (opponent.x + opponent.speed * Math.sqrt((2 * (1080 - (opponent.y + 20))) / 4.5)) % getWidth();
                    if (landingX >= target.xplace && landingX <= target.xplace + target.width) {drop();}
                }
                if(opponent.diffculty.equals("easy")) {
                    if (opponent.x - target.xplace >= -500 && rand>20) {
                        drop();

                    } else if (rand<=20) {
                        drop();
                        System.out.println("Bot missing easy ");
                    }
                }
                if(opponent.diffculty.equals("medium")) {
                    if((opponent.x + opponent.speed * Math.sqrt((2 * (1080 - (opponent.y + 20))) / 4.5)
                            >= target.xplace && opponent.x + opponent.speed * Math.sqrt((2 * (1080 - (opponent.y + 20))) / 4.5)
                            <= target.xplace + target.width) && rand>10)
                    {
                        drop();

                    }
                    else if (rand<=10) {
                        drop();
                        System.out.println("Bot missing easy ");
                    }
                }

            }
            repaint();
            if(droplow){
                speeddown += 4.5;
                speedright *= 0.98;
                yPos += (int) speeddown;
                xPos += (int) speedright;
                double Targetmiddlex = xPos + 32.5;
                double Targetmiddley = yPos + 12.5;
                double Loadmiddlex = target.xplace + target.width/2.0;
                double Loadmiddley = target.yplace + target.height/2.0;
                double distanceBettwenX = Targetmiddlex - Loadmiddlex;
                double distanceBettwenY = Targetmiddley - Loadmiddley;
                double ElipseX = (65 + target.width) / 2.0;
                double ElipseY = (25 + target.height) / 2.0;

                if ((distanceBettwenX*distanceBettwenX)/(ElipseX*ElipseX) + (distanceBettwenY*distanceBettwenY)/(ElipseY*ElipseY) <= 1) {
                    System.out.println("hit");
                    if(PlayerTurn){PlayerScore++;}
                    else if(!PlayerTurn){BotScore++;}
                    System.out.println(PlayerScore);
                    System.out.println(BotScore);
                    dropping = false;
                    droplow= false;
                    if(PlayerTurn) {Main.finalPlayerScore += 1;CheckScore++;PlayerTurn = false;opponent.x=0;}
                    else{CheckScore++;PlayerTurn = true;plane.x=0;}
                    System.out.println(CheckScore);
                    if(CheckScore == 2) {
                        if(PlayerScore > BotScore) {
                            stopTheGame();
                            parentLayout.show(parentCard, "Win");
                        } else if (BotScore > PlayerScore) {
                            stopTheGame();
                            parentLayout.show(parentCard, "Lose");
                        } else {
                            CheckScore = 0;
                            PlayerScore = 0;
                            BotScore = 0;
                        }
                    }
                }
                else if((yPos >= 1080) &&(distanceBettwenX*distanceBettwenX)/(ElipseX*ElipseX) + (distanceBettwenY*distanceBettwenY)/(ElipseY*ElipseY) > 1)
                {
                    System.out.println("Miss");
                    droplow= false;
                    dropping = false;
                    if(PlayerTurn) {
                        CheckScore++;
                        PlayerTurn = false;
                        opponent.x=0;
                    }
                    else{
                        CheckScore++;
                        PlayerTurn = true;
                        plane.x=0;
                    }

                    System.out.println(CheckScore);
                    if(CheckScore == 2) {
                        if(PlayerScore > BotScore) {
                            stopTheGame();
                            parentLayout.show(parentCard, "Win");
                        } else if (BotScore > PlayerScore) {
                            stopTheGame();
                            parentLayout.show(parentCard, "Lose");
                        } else {
                            CheckScore = 0;
                            PlayerScore = 0;
                            BotScore = 0;
                        }
                    }
                }
            }

            resetIfNeeded(getWidth());
            repaint();
        });

        timer.start();



    }

    //called when repaint() is used within and in other classes
    //it either draws the player's plane or opponent's plane while also, after space is clicked it draws the payload and sets the starting speeds
    protected void paintComponent(Graphics g) {
        setBackground(Color.WHITE);
        super.paintComponent(g);
        if(PlayerTurn) {
            plane.draw(g);
        }
        else{
            opponent.draw(g);
        }
        target.draw(g);
        if(droplow)
        {
            speeddown =1;
            speedright = plane.speed;
            g.setColor(Color.BLACK);
            g.drawOval(xPos, yPos, 65, 25);
            g.fillOval(xPos, yPos, 65, 25);
        }
    }

    //After it's called when the player clicks space, it stops the player from dorpping multiple, and sets the location of drop and then repaints
    public void drop(){

        //System.out.println(opponent.x - target.xplace);
        rand = dev.nextInt(100);
        droplow = true;
        if(dropping){return;}
        dropping = true;
        if(PlayerTurn) {
            xPos = plane.x;
            yPos = plane.y + 20;
        }
        if(!PlayerTurn) {
            xPos = opponent.x;
            yPos = opponent.y + 20;
        }
        repaint();

    }

    //if the plane goes over the right side of the screen, it teleports it back to the left
    public void resetIfNeeded(int width) {
        if (xPos > width) {
            xPos = -50;
        }
    }

    //returns whose turn it is
    public boolean getPlayerTurn() {
        return PlayerTurn;
    }

    //Stops the timer when called, thus stopping the game
    public void stopTheGame() {
        if (timer != null) {
            timer.stop();
        }
    }
}
