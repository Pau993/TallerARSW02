package snakepackage;

import enums.GridSize;
import snakepackage.Cell;
import snakepackage.Snake;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class SnakeApp {

    private static SnakeApp app;
    public static final int MAX_THREADS = 8;
    final Snake[] snakes = new Snake[MAX_THREADS];
    private static final Cell[] spawn = {
            new Cell(1, (GridSize.GRID_HEIGHT / 2) / 2),
            new Cell(GridSize.GRID_WIDTH - 2, 3 * (GridSize.GRID_HEIGHT / 2) / 2),
            new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2, 1),
            new Cell((GridSize.GRID_WIDTH / 2) / 2, GridSize.GRID_HEIGHT - 2),
            new Cell(1, 3 * (GridSize.GRID_HEIGHT / 2) / 2),
            new Cell(GridSize.GRID_WIDTH - 2, (GridSize.GRID_HEIGHT / 2) / 2),
            new Cell((GridSize.GRID_WIDTH / 2) / 2, 1),
            new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2, GridSize.GRID_HEIGHT - 2)
    };
    private JFrame frame;
    private static Board board;
    private Thread[] threads = new Thread[MAX_THREADS];
    private CountDownLatch latch = new CountDownLatch(MAX_THREADS); // Añadir CountDownLatch

    public SnakeApp() {
        setupFrame();
    }

    public static void main(String[] args) {
        app = new SnakeApp();
        app.init();
    }

    private void setupFrame() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("The Snake Race");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GridSize.GRID_WIDTH * GridSize.WIDTH_BOX + 17,
                GridSize.GRID_HEIGHT * GridSize.HEIGHT_BOX + 40);
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2,
                dimension.height / 2 - frame.getHeight() / 2);
        board = new Board();
        frame.add(board, BorderLayout.CENTER);

        JPanel actionsPanel = new JPanel(new FlowLayout());
        JButton actionButton = new JButton("Action");
        actionButton.addActionListener(e -> handleAction());
        actionsPanel.add(actionButton);
        frame.add(actionsPanel, BorderLayout.SOUTH);
    }

    private void handleAction() {
        // Implementar la funcionalidad del botón aquí
    }

    private void init() {
        for (int i = 0; i < MAX_THREADS; i++) {
            snakes[i] = new Snake(i + 1, spawn[i], i + 1, latch);
            snakes[i].addObserver(board);
            threads[i] = new Thread(snakes[i]);
            threads[i].start();
        }

        frame.setVisible(true);

        try {
            // Espera a que todas las serpientes terminen
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted: " + e.getMessage());
        }

        System.out.println("Thread (snake) status:");
        for (int i = 0; i < MAX_THREADS; i++) {
            System.out.println("[" + i + "] :" + threads[i].getState());
        }
    }

    public static SnakeApp getApp() {
        return app;
    }
}