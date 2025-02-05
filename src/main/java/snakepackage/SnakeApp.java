package snakepackage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import enums.Direction;
import enums.GridSize;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author jd-
 *
 */
public class SnakeApp {

    private JFrame frame;
    private static Board board;
    private volatile boolean pauseGame = false;
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton startButton;
    private static SnakeApp app;
    public static final int MAX_THREADS = 8;
    Snake[] snakes = new Snake[MAX_THREADS];
    Thread[] thread = new Thread[MAX_THREADS];
    private static final Cell[] spawn = {
        new Cell(1, (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(GridSize.GRID_WIDTH - 2,
        3 * (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2, 1),
        new Cell((GridSize.GRID_WIDTH / 2) / 2, GridSize.GRID_HEIGHT - 2),
        new Cell(1, 3 * (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(GridSize.GRID_WIDTH - 2, (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell((GridSize.GRID_WIDTH / 2) / 2, 1),
        new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2,
        GridSize.GRID_HEIGHT - 2)};

    int nr_selected = 0;
    


    
    public SnakeApp() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("The Snake Race");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(618, 640);
        frame.setSize(GridSize.GRID_WIDTH * GridSize.WIDTH_BOX + 17,
                GridSize.GRID_HEIGHT * GridSize.HEIGH_BOX + 40);
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2,
                dimension.height / 2 - frame.getHeight() / 2);
        board = new Board();
        
        
        frame.add(board, BorderLayout.CENTER);
        
        JPanel actionsBPabel=new JPanel();
        actionsBPabel.setLayout(new FlowLayout());

        startButton = new JButton("Iniciar");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        actionsBPabel.add(startButton);
        
        pauseButton = new JButton("Pausar");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseGame();
            }
        });
        actionsBPabel.add(pauseButton);

        resumeButton = new JButton("Reanudar");
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeGame();
            }
        });

        actionsBPabel.add(resumeButton);
        frame.add(actionsBPabel,BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public synchronized void startGame() {
        snakes = new Snake[MAX_THREADS];
        thread = new Thread[MAX_THREADS];
        for (int i = 0; i < MAX_THREADS; i++) {
            Cell head = new Cell(0, 0);
            int direction = Direction.NO_DIRECTION;
            snakes[i] = new Snake(i, head, direction);
            thread[i] = new Thread(snakes[i]);
            thread[i].start();
        }
    }

    public synchronized void pauseGame() {
        pauseGame = true;
        for (int i = 0; i < MAX_THREADS; i++) {
            snakes[i].pause();
        }
    }

    public synchronized void resumeGame() {
        pauseGame = false;
        for (int i = 0; i < MAX_THREADS; i++) {
            snakes[i].resume();
        }
        notifyAll();
    }

    public void gameLoop() {
        while (true) {
            synchronized (this) {
                while (pauseGame) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
    
            int x = 0;
            for (int i = 0; i != MAX_THREADS; i++) {
                if (snakes[i].isSnakeEnd() == true) {
                    x++;
                }
            }
            if (x == MAX_THREADS) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    

    private void init() {
        snakes = new Snake[MAX_THREADS];
        thread = new Thread[MAX_THREADS];
        for (int i = 0; i < MAX_THREADS; i++) {
            Cell head = new Cell(0, 0);
            int direction = Direction.NO_DIRECTION;
            snakes[i] = new Snake(i, head, direction);
            snakes[i].addObserver(board);
            thread[i] = new Thread(snakes[i]);
            thread[i].start();
        }

        frame.setVisible(true);
    }

    public static SnakeApp getApp() {
        return app;
    }

    
    public static void main(String[] args) {
        app = new SnakeApp();
        app.init();
    }

}
