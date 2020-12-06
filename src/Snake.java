import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;


public class Snake extends JPanel implements ActionListener {
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 6;
    private static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    private static final int DELAY = 75;
    private final int x[] = new int[GAME_UNITS];
    private final int y[] = new int[GAME_UNITS];
	
    private int bodyParts = 5;
    private int applesEaten;
    private int appleX;
    private int appleY; 
    private char direction = 'R';
    private static boolean gameOn = false; 
    private boolean running = false;
    private Timer timer;
    private Random random;
    private int awal = 0;

    Snake(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        newApple();
        timer = new Timer(DELAY,this);
        timer.start();

    }
    public void pause() {
		Snake.gameOn = true;
		timer.stop();
	}

	private void resume() {
		Snake.gameOn = false;
		timer.start();
	}
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g){

        if(running) {
            /*
        for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        }
        */

        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
        if (awal == 0){
            for(int i=0; i < bodyParts; i++){
                x[0]=300;
                x[1]=294;
                x[2]=288;
                x[3]=282;
                x[4]=276;

                y[0]=300;
                y[1]=300;
                y[2]=300;
                y[3]=300;
                y[4]=300;
            }

        }
        for(int i = 0; i < bodyParts; i++){
            awal++;
            if (i==0){
		pause();
                g.setColor(Color.MAGENTA);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            } else {
		resume();
                g.setColor(Color.white);
                g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " +applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " +applesEaten))/2, g.getFont().getSize());

        }

        else {
            gameOver(g);
        }
    }

    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move(){
        for(int i =  bodyParts;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction) {
            case 'U':
            y[0] = y[0] - UNIT_SIZE;
            break;

            case 'D':
            y[0] = y[0] + UNIT_SIZE;
            break;

            case 'L':
            x[0] = x[0] - UNIT_SIZE;
            break;

            case 'R':
            x[0] = x[0] + UNIT_SIZE;
            break;
        }

    }

    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }

    public void checkCollisions(){
        //check if head collides with body
        for(int i=bodyParts;i>0;i--){
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        //check if head touches left border
        if(x[0] < 0){
            running = false;
        }

        //if head touches right border
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }

        //if head touches top border
        if(y[0] < 0){
            running = false;
        }

        //if head touches bottom border
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }

        if(running == false){
            timer.stop();
        }

    }
    
     public void gameOver(Graphics g){
        //Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 70));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("!! Game Over !!", (SCREEN_WIDTH - metrics1.stringWidth("!! Game Over !!"))/2, SCREEN_HEIGHT/2);
        
        //Start
        g.setColor(Color.black);
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Space to Start", (SCREEN_WIDTH - metrics3.stringWidth("Enter to Restart"))/2, SCREEN_HEIGHT/3);
        
        //Restart
        g.setColor(Color.orange);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        FontMetrics metrics7 = getFontMetrics(g.getFont());
        g.drawString("Enter to Restart", (SCREEN_WIDTH - metrics7.stringWidth("Enter to Restart"))/2, SCREEN_HEIGHT/4);
        

        //Score
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Score: " +applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " +applesEaten))/2, g.getFont().getSize()); 

    }
    
        public void reset(){
        this.add(new Snake());
        running =true;
        startGame();
        repaint();
        pause();
        resume();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                if (direction != 'R') {
                    direction = 'L';
                }
                break;

                case KeyEvent.VK_RIGHT:
                if (direction != 'L') {
                    direction = 'R';
                }
                break;

                case KeyEvent.VK_UP:
                if (direction != 'D') {
                    direction = 'U';
                }
                break;

                case KeyEvent.VK_DOWN:
                if (direction != 'U') {
                    direction = 'D';
                }
                break;
		
		case KeyEvent.VK_ENTER:
		if(Snake.gameOn) {
			resume();
		} else {
			pause();
		}
                break;
		
		case KeyEvent.VK_SPACE:
                running = true;
                repaint();
                break;
		
		case KeyEvent.VK_SHIFT:
                reset();
                break;
	
            }

        }
    }
}
