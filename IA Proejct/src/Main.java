
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;

public class Main {
    static String difficulty;
    static boolean gameOn = false;
    static JPanel cards;
    static CardLayout cardLayout;
    static Painter game;
    static int finalPlayerScore;
    static boolean PlayeTurn = true;


    //Main method that runs when the program starts
    public static void main(String[] args) {

        System.setProperty("java.awt.headless", "false");
        //Basic window show
        JFrame frame = new JFrame("Drop game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        //Scence chose cars
         cardLayout = new CardLayout();
         cards = new JPanel(cardLayout);

        //Main menu
        JPanel mainMenu = CreateMenu();
        cards.add(mainMenu, "Menu");
        frame.add(cards);
        frame.setVisible(true);

        // Show main menu first
        cardLayout.show(cards, "Menu");

        //EndScence win
        JPanel EndWin = CreateWin();
        cards.add(EndWin, "Win");
        frame.add(cards);
        frame.setVisible(true);

        //EndScene lose
        JPanel EndLose = CreateLose();
        cards.add(EndLose, "Lose");
        frame.add(cards);
        frame.setVisible(true);
    }

    //Creates the main menu layout and objects.
    //it retruns the JPanel components that are added to mainMenu
    static JPanel CreateMenu() {
        JPanel mainMenu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.setFont(new Font("Arial", Font.BOLD, 72));
                String text = "DROP GAME";

                FontMetrics m = g.getFontMetrics();
                int x = (getWidth() - m.stringWidth(text)) / 2;
                g.drawString(text, x, 100);
            }
        };
        mainMenu.setLayout(null);
        JButton easyButton = new JButton("Easy Difficulty");
        easyButton.setBounds(300, 500, 200, 300);
        easyButton.setBackground(Color.GREEN);
        easyButton.addActionListener(e -> {
            startGame("easy");});
        mainMenu.add(easyButton);

        mainMenu.setLayout(null);
        JButton mediumButton = new JButton("Medium Difficulty");
        mediumButton.setBounds(850, 500, 200, 300);
        mediumButton.setBackground(Color.ORANGE);
        mediumButton.addActionListener(e -> {
            startGame("medium");});
        mainMenu.add(mediumButton);

        mainMenu.setLayout(null);
        JButton hardButton = new JButton("Hard Difficulty");
        hardButton.setBounds(1400, 500, 200, 300);
        hardButton.setBackground(Color.RED);
        hardButton.addActionListener(e -> {
            startGame("hard");});
        mainMenu.add(hardButton);

        mainMenu.setLayout(null);
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(850, 900, 200, 100);
        exitButton.setBackground(Color.CYAN);
        exitButton.addActionListener(e -> System.exit(0));
        mainMenu.add(exitButton);
        return mainMenu;
    }

    //Creates the Win menu layout and objects.

    //it retruns the JPanel components that are added to EndWin
    static JPanel CreateWin(){
        JPanel EndWin = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.setFont(new Font("Arial", Font.BOLD, 72));
                String text = "You Win";

                FontMetrics m = g.getFontMetrics();
                int x = (getWidth() - m.stringWidth(text)) / 2;
                g.drawString(text, x, 100);

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 36));
                g.drawString("Your score: " + finalPlayerScore, x, 700);
            }
        };

        EndWin.setLayout(null);
        JButton easyButton = new JButton("Play Again");
        easyButton.setBounds(650, 300, 600, 100);
        easyButton.setBackground(Color.GREEN);
        easyButton.addActionListener(e -> {
            cardLayout.show(cards, "Menu");
        });
        EndWin.add(easyButton);
        return EndWin;
    }

    //Creates the Lose menu layout and objects.
    //it retruns the JPanel components that are added to EndLose
    static JPanel CreateLose(){
        JPanel EndLose = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.setFont(new Font("Arial", Font.BOLD, 72));
                String text = "You Lost";

                FontMetrics m = g.getFontMetrics();
                int x = (getWidth() - m.stringWidth(text)) / 2;
                g.drawString(text, x, 100);

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 36));
                g.drawString("Your score: " + finalPlayerScore, x, 700);
            }
        };

        EndLose.setLayout(null);
        JButton easyButton = new JButton("Try Again");
        easyButton.setBounds(650, 300, 600, 100);
        easyButton.setBackground(Color.GREEN);
        easyButton.addActionListener(e -> {
            cardLayout.show(cards, "Menu");
        });
        EndLose.add(easyButton);
        return EndLose;
    }

    //When called after the main menu buttons are hit, it creates an instance of the other  classes, which renders the game in
    // the dififculty put into the parameter of this method.
    static void startGame(String dif) {
        Random rand = new Random();

        Target target;
        PlaneP plane;
        Opponent opponent;

        if (dif.equals("easy")) {

            target = new Target(200, 100, rand.nextInt(1500 - 700 + 1) + 700, 990);
             plane = new PlaneP(5);
             opponent = new Opponent(5);
        } else if (dif.equals("medium")) {
            target = new Target(150, 75, rand.nextInt(1800 - 300 + 1) + 300, 990);
             plane = new PlaneP(15);
            opponent = new Opponent(15);
        } else {
            target = new Target(100, 50, rand.nextInt(1920 - 100 + 1) + 100, 990);
             plane = new PlaneP(30);
            opponent = new Opponent(30);
        }
        finalPlayerScore = 0;
        game = new Painter(plane, target, opponent, cards, cardLayout);
        cards.add(game, "Game");
        cardLayout.show(cards, "Game");

        game.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "dropPayload");
        game.getActionMap().put("dropPayload", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(game.getPlayerTurn()){
                    game.drop();
                }

            }
        });

    }

}



