package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Ui extends JPanel {

    private Bird bird;
    private ArrayList<Rectangle> rects;
    private FlappyBird fb;
    private Font scoreFont, pauseFont;
    public static final Color bg = new Color(150, 200, 200);
    public static final int PIPE_W = 32, PIPE_H = 32;
    private Image pipeEnd, pipe;

    public Ui(FlappyBird fb, Bird bird, ArrayList<Rectangle> rects) {
        this.fb = fb;
        this.bird = bird;
        this.rects = rects;
        scoreFont = new Font("Monospace", Font.BOLD, 12);
        pauseFont = new Font("Monospace", Font.BOLD, 12 );

        try {
            pipeEnd = ImageIO.read(new File("img/pipeEnd.png"));
            pipe = ImageIO.read(new File("img/pipe.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(bg);
        g.fillRect(0,0,FlappyBird.WIDTH,FlappyBird.HEIGHT);
        bird.update(g);
        g.setColor(Color.RED);
        for(Rectangle r : rects) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.GREEN);
            // g2d.fillRect(r.x, r.y, r.width, r.height);  // Show pipe hit box.
            AffineTransform old = g2d.getTransform();
            g2d.translate(r.x+PIPE_W/2, r.y+PIPE_H/2);
            if(r.y < FlappyBird.HEIGHT/2) {
                g2d.translate(0, r.height);
                g2d.rotate(Math.PI);
            }
            g2d.drawImage(pipeEnd, -PIPE_W/2, -PIPE_H/2, Ui.PIPE_W, Ui.PIPE_H, null);
            g2d.drawImage(pipe, -PIPE_W/2, PIPE_H/2, Ui.PIPE_W, r.height, null);
            g2d.setTransform(old);
        }
        g.setFont(scoreFont);
        g.setColor(new Color(0,0,255,170));
        g.drawString("Score: "+fb.getScore(), 10, 20);

        if(fb.paused()) {
            g.setFont(pauseFont);
            g.setColor(new Color(0,0,255,170));
            g.drawString("PAUSED", FlappyBird.WIDTH/2 - 27, FlappyBird.HEIGHT/2-40);
            g.drawString("PRESS SPACE TO BEGIN", FlappyBird.WIDTH/2-75, FlappyBird.HEIGHT/2+50);
        }
    }
}
