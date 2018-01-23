import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Таня on 21.01.2018.
 */
public class Ball extends JPanel {

    static int r, d, dx, dy, dt, t, t0, i, width, height;
    static double x, y, k, g;

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(new File("input.txt"));
        ArrayList<String> lines = new ArrayList<String>();
        for (int j = 0; j < 8; j++) {
            lines.add(sc.nextLine());
        }
        r = Integer.valueOf(lines.get(0));
        d = r * 2;

        x = Double.valueOf(lines.get(1));
        y = Double.valueOf(lines.get(2));

        dx = Integer.valueOf(lines.get(3));
        dy = Integer.valueOf(lines.get(4));

        dt = Integer.valueOf(lines.get(5));

        t = Integer.valueOf(lines.get(6)) * 1000;
        t0 = t;

        k = Double.valueOf(lines.get(7));

        g = 9.80665;
        i = 0;

        JFrame frame = new JFrame("Ball");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setResizable(false);
        frame.setContentPane(new Ball());
        frame.setVisible(true);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.ORANGE);
        g.fillOval((int)(x - r), (int)(y - r), (int)d, (int)d);
    }

    public Ball() {
        Thread thread = new Thread() {
            public void run() {
                while (t > 0) {
                    width = 794;
                    height = 471;
                    //System.out.println(getWidth());
                    //System.out.println(getHeight());

                    double x0 = x;
                    double y0 = y;

                    x += dx;
                    y += dy;

                    if (x - r < 0) {
                        dx = -dx;
                        x = r;
                        dy *= k;
                    }
                    if (x + r > width) {;
                        dx = -dx;
                        x = width - r;
                        dy *= k;
                    }
                    if (y - r < 0) {
                        dy = -dy;
                        y = r;
                        dy *= k;
                    }
                    if (y + r > height) {
                        dy = -dy;
                        y = height - r;
                        dy *= k;
                    }

                   /* Не знаю, верна ли такая правка dx с точки зрения физики,
                    но я так решила уменьшать dx, когда шар прекратит прыгать.
                    Иначе он катается бесконечно туда-сюда, что не очень реалистично.*/
                    if (dy == 0){
                        //System.out.println(dx);
                        dx *= k;
                        //System.out.println(dx);
                    }

                    repaint();
                    t -= dt;
                    dy += g/3;
                    System.out.println("t" + i + ": (" + x0 + "," + y0 + ") -> " + "(" + x + "," + y + ")");
                    i++;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                    }

                }
            }
        };
        thread.start();
    }

}
