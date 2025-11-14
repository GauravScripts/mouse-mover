import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 * MouseMoveAutomatically - Auto Mouse Movement Application
 *
 * <p>This application automatically moves the mouse cursor to prevent the system
 * from locking during idle periods. It's designed for users on Microsoft Teams
 * or similar applications that change status to "Away" after inactivity.</p>
 *
 * <h2>How it works:</h2>
 * <ol>
 *   <li>Monitors the current mouse position continuously</li>
 *   <li>If no movement detected for 5 minutes (300,000 ms), moves mouse by 1 pixel</li>
 *   <li>Press ESC key to stop the application gracefully</li>
 *   <li>Detects user activity and resets the timer on manual mouse movement</li>
 * </ol>
 *
 * <h2>Features:</h2>
 * <ul>
 *   <li>Automatic mouse movement on inactivity (5 minutes)</li>
 *   <li>Respects user input - resets timer on manual mouse movement</li>
 *   <li>Minimal movement (1 pixel) to avoid interfering with work</li>
 *   <li>Easy to stop with ESC key</li>
 * </ul>
 *
 * @author gauravscripts
 * @version 1.0
 * @since 2024
 */
public class MouseMoveAutomatically {
    /**
     * Flag to control whether the application is currently running.
     * Set to false when ESC key is pressed to gracefully stop the application.
     */
    private static boolean running = true;

    /**
     * Stores the last known position of the mouse cursor.
     * Used to detect if the user has manually moved the mouse.
     */
    private static Point lastLocation;

    /**
     * Timestamp (in milliseconds) of the last mouse movement.
     * Used to calculate the inactivity duration and trigger automatic mouse movement
     * when the 5-minute (300,000 ms) threshold is reached.
     */
    private static long lastMoveTime;

    /**
     * Main method - Entry point of the application.
     *
     * <p>Initializes the Robot for mouse control, creates a JFrame to capture keyboard input,
     * and starts a background thread to monitor and control mouse movements.</p>
     *
     * <h3>Initialization Steps:</h3>
     * <ol>
     *   <li>Create Robot instance for mouse control</li>
     *   <li>Create JFrame window to receive keyboard focus</li>
     *   <li>Register KeyListener for ESC key detection</li>
     *   <li>Initialize last location and last move time</li>
     *   <li>Start background thread for mouse monitoring</li>
     * </ol>
     *
     * @param args command line arguments (not used)
     * @throws AWTException if the Robot class cannot be instantiated
     *
     * @see Robot
     * @see JFrame
     * @see KeyListener
     */
    public static void main(String[] args) throws AWTException {
        /**
         * Robot instance for controlling mouse movements.
         * Used to programmatically move the mouse cursor to prevent system lock.
         */
        Robot robot = new Robot();

        /**
         * JFrame window for capturing keyboard events.
         * Required to listen for the ESC key press to stop the application.
         * Window size is 200x200 pixels (minimal footprint).
         */
        JFrame frame = new JFrame();
        frame.setSize(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        /**
         * KeyListener implementation to detect ESC key press.
         * When ESC is pressed, sets running flag to false to gracefully stop the application.
         */
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not used - required by KeyListener interface
            }

            @Override
            public void keyPressed(KeyEvent e) {
                /**
                 * Detects when a key is pressed.
                 * If ESC key (KeyEvent.VK_ESCAPE) is pressed, terminates the application.
                 *
                 * @param e the KeyEvent containing key information
                 */
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    running = false;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not used - required by KeyListener interface
            }
        });

        /**
         * Initialization block: Capture current mouse position and current time.
         * These serve as baseline values for detecting inactivity.
         */
        lastLocation = MouseInfo.getPointerInfo().getLocation();
        lastMoveTime = System.currentTimeMillis();

        /**
         * Background thread for mouse monitoring and automatic movement.
         *
         * <p><b>Thread Logic:</b></p>
         * <ol>
         *   <li>Continuously monitors the current mouse position</li>
         *   <li>Compares it with the last known position to detect user activity</li>
         *   <li>If mouse hasn't moved for 5 minutes, automatically moves it 1 pixel</li>
         *   <li>If user moves mouse manually, resets the inactivity timer</li>
         *   <li>Checks every 1 second to balance responsiveness and CPU usage</li>
         * </ol>
         *
         * <p><b>Inactivity Threshold:</b> 300,000 milliseconds (5 minutes)</p>
         *
         * <p><b>Why separate thread?</b> Prevents UI from blocking while monitoring mouse position.</p>
         */
        Thread mouseMoverThread = new Thread(() -> {
            try {
                /**
                 * Main monitoring loop.
                 * Continues while the running flag is true.
                 * Exits when user presses ESC key (running set to false).
                 */
                while (running) {
                    /**
                     * Retrieves current mouse position from the system.
                     * MouseInfo.getPointerInfo() provides real-time cursor location.
                     */
                    Point currentLocation = MouseInfo.getPointerInfo().getLocation();

                    /**
                     * Checks if the current mouse position equals the last recorded position.
                     * If true, the user has not manually moved the mouse since last check.
                     */
                    if (currentLocation.equals(lastLocation)) {
                        /**
                         * Mouse position unchanged - Check if inactivity threshold reached.
                         *
                         * <p><b>Calculation:</b></p>
                         * <code>System.currentTimeMillis() - lastMoveTime >= 300000</code>
                         * <ul>
                         *   <li>Current time minus last movement time gives elapsed time</li>
                         *   <li>300,000 ms equals 5 minutes</li>
                         *   <li>If elapsed time >= 5 minutes, inactivity is detected</li>
                         * </ul>
                         */
                        if (System.currentTimeMillis() - lastMoveTime >= 300000) { // 5 minutes
                            /**
                             * Inactivity detected! Perform automatic mouse movement.
                             * Movement is minimal (1 pixel) to avoid interfering with user work.
                             *
                             * <p><b>Movement sequence:</b></p>
                             * <ol>
                             *   <li>Move mouse 1 pixel right and 1 pixel down (x+1, y+1)</li>
                             *   <li>Wait 100 milliseconds (makes movement visible to system)</li>
                             *   <li>Move mouse back to original position (x-1, y-1)</li>
                             *   <li>Reset the inactivity timer</li>
                             * </ol>
                             */
                            robot.mouseMove(currentLocation.x + 1, currentLocation.y + 1);
                            Thread.sleep(100);
                            robot.mouseMove(currentLocation.x - 1, currentLocation.y - 1);
                            lastMoveTime = System.currentTimeMillis();
                        }
                    } else {
                        /**
                         * User activity detected! Mouse position has changed.
                         * This means the user manually moved the mouse.
                         *
                         * <p><b>Action:</b> Reset the inactivity timer to restart the 5-minute countdown.</p>
                         */
                        lastLocation = currentLocation;
                        lastMoveTime = System.currentTimeMillis();
                    }

                    /**
                     * Polling interval of 1 second.
                     * Balances between:
                     * <ul>
                     *   <li><b>Responsiveness:</b> 1 second is quick enough to detect movement</li>
                     *   <li><b>CPU Efficiency:</b> 1 second prevents excessive CPU usage</li>
                     * </ul>
                     *
                     * @throws InterruptedException if thread is interrupted during sleep
                     */
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                /**
                 * Exception handling for InterruptedException.
                 * Prints stack trace if thread is interrupted unexpectedly.
                 */
                e.printStackTrace();
            }
        });

        /**
         * Start the background thread.
         * The mouse monitoring now runs independently in the background
         * while keeping the main thread alive for the GUI window.
         *
         * @see Thread#start()
         */
        mouseMoverThread.start();
    }
}
