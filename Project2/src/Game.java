import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame {
    final int LOCATION = 200;
    final String TITLE_OF_GAME = "Game";
    public static void main(String[] args) {
        new Game();
    }
    Game() {
        setTitle(TITLE_OF_GAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(LOCATION, LOCATION);
        setSize(400, 250);
        setResizable(false);
        add(new Paint());
        setVisible(true);
    }
    class Paint extends JPanel {
        private int xDelta = 0;
        private int yDelta = 0;
        private Timer repaintTimerX;
        private Timer repaintTimerY;
        private int xPos = 1;
        private int yPos = 1;

        Paint() {
            InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
            ActionMap am = getActionMap();
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "pressed.down");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "released.down");
            am.put("pressed.down", new MoveActionY(2, true));
            am.put("released.down", new MoveActionY(0, false));

            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "pressed.up");
            im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "released.up");
            am.put("pressed.up", new MoveActionY(-2, true));
            am.put("released.up", new MoveActionY(0, false));


        repaintTimerY = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yPos += yDelta;
                if (yPos < 0) {
                    yPos = 0;
                } else if (yPos + 40 > getHeight()) {
                    yPos = getHeight() - 40;
                }
                repaint();
            }
        });
            repaintTimerY.setInitialDelay(0);
            repaintTimerY.setRepeats(true);
            repaintTimerY.setCoalesce(true);
    }
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            draw1(g);
        }
        private void draw1(Graphics g) {
            Graphics2D paint2 = (Graphics2D) g;
            paint2.setPaint(Color.black);
            paint2.fillRoundRect(xPos, yPos, 15, 40, 0, 0);
        }
        public class MoveActionY extends AbstractAction {

            private int direction;
            private boolean keyDown;

            public MoveActionY(int direction, boolean down) {
                this.direction = direction;
                keyDown = down;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                yDelta = direction;
                if (keyDown) {
                    if (!repaintTimerY.isRunning()) {
                        repaintTimerY.start();
                    }
                } else {
                    repaintTimerY.stop();

                }
            }
        }
    }
}
