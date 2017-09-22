package flappybird;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Flappy Bird 128
 */

public class FlappyBird implements ActionListener, KeyListener {

    public static final int FPS = 60, WIDTH = 256, HEIGHT = 256;

    private Bird bird; 
    private JFrame frame;
    private JPanel panel;
    private ArrayList<Rectangle> rects;
    private int time, scroll;
    private Timer timer;
    private boolean paused;

    public void go() {
        frame = new JFrame("Flappy Bird 128");
        bird = new Bird();
        rects = new ArrayList<Rectangle>();
        panel = new Ui(this, bird, rects);
        frame.add(panel);

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);

        paused = true;

        timer = new Timer(1000/FPS, this);
        timer.start();
    }

    public static void main(String[] args) {
        new FlappyBird().go();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
        if(!paused) {
            bird.physics();
            if(scroll % 90 == 0) {
                Rectangle topPipeHitBox = new Rectangle(WIDTH, 0, Ui.PIPE_W,
                    (int) ((Math.random()*HEIGHT)/4f + (0.2f)*HEIGHT));
                int pipeSpacing = (int) ((Math.random()*HEIGHT)/4f + (0.2f)*HEIGHT);
                Rectangle BottomPipeHitBox = new Rectangle(WIDTH, HEIGHT - pipeSpacing, Ui.PIPE_W, pipeSpacing);
                rects.add(topPipeHitBox);
                rects.add(BottomPipeHitBox);
            }
            ArrayList<Rectangle> toRemove = new ArrayList<Rectangle>();
            boolean game = true;
            for(Rectangle r : rects) {
                r.x-=3;
                if(r.x + r.width <= 0) {
                    toRemove.add(r);
                }
                if(r.contains(bird.xPosition, bird.yPosition)) {
                    game = false;
                }
            }
            rects.removeAll(toRemove);
            time++;
            scroll++;
            if(bird.yPosition > HEIGHT || bird.yPosition+bird.BIRD_RADIUS < 0) {
                game = false;
            }
            if(!game) {
                rects.clear();
                bird.reset();
                time = 0;
                scroll = 0;
                paused = true;
            }
        }
        else {
        }
    }

    public int getScore() {
        return time;
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP) {
            bird.jump();
        }
        else if(e.getKeyCode()==KeyEvent.VK_SPACE) {
            paused = false;
        }
    }
    public void keyReleased(KeyEvent e) {
    }
    public void keyTyped(KeyEvent e) {
    }
    public boolean paused() {
        return paused;
    }
}
