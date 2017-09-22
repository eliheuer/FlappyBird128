package flappybird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bird {
    public float xPosition, yPosition, xVelocity, yVelocity;
    public static final int BIRD_RADIUS = 16;
    private Image img;
    public Bird() {
        xPosition = FlappyBird.WIDTH/2;
        yPosition = FlappyBird.HEIGHT/2;
        try {
            img = ImageIO.read(new File("img/bird.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void physics() {
        xPosition += xVelocity;
        yPosition += yVelocity;
        yVelocity += 0.4f;
    }
    public void update(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawImage(img,
            Math.round(xPosition - BIRD_RADIUS),
            Math.round(yPosition - BIRD_RADIUS),
            2 * BIRD_RADIUS,
            2 * BIRD_RADIUS,
            null
        );
    }
    public void jump() {
        yVelocity = -5;
    }

    public void reset() {
        xPosition = 256/2;
        yPosition = 256/2;
        xVelocity = yVelocity = 0;
    }
}
