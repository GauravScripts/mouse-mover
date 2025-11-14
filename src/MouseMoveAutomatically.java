import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class MouseMoveAutomatically {
    private static boolean running = true;
    private static Point lastLocation;
    private static long lastMoveTime;

    public static void main(String[] args) throws AWTException {
        Robot robot = new Robot();
        JFrame frame = new JFrame();
        frame.setSize(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    running = false;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        lastLocation = MouseInfo.getPointerInfo().getLocation();
        lastMoveTime = System.currentTimeMillis();

        Thread mouseMoverThread = new Thread(() -> {
            try {
                while (running) {
                    Point currentLocation = MouseInfo.getPointerInfo().getLocation();
                    if (currentLocation.equals(lastLocation)) {
                        if (System.currentTimeMillis() - lastMoveTime >= 3000) { // 5 minutes
                            robot.mouseMove(currentLocation.x + 1, currentLocation.y + 1);
                            Thread.sleep(100);
                            robot.mouseMove(currentLocation.x - 1, currentLocation.y - 1);
                            lastMoveTime = System.currentTimeMillis();
                        }
                    } else {
                        lastLocation = currentLocation;
                        lastMoveTime = System.currentTimeMillis();
                    }
                    Thread.sleep(1000); // Check every second
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        mouseMoverThread.start();
    }
}
