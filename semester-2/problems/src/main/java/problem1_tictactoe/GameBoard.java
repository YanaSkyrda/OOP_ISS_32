package problem1_tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameBoard extends JFrame {
    private static Integer player;
    private static Communicator com;
    private static Thread oppcheck;
    private static GameManager game;
    private static final Button[][] buttons = new Button[3][3];

    private static final AtomicBoolean continues = new AtomicBoolean(true);
    private static AtomicBoolean move;

    private static JFrame frame;

    public GameBoard(int player) {
        super("TicTacToe " + getLabel(player));
        GameBoard.player = player;
        game = new GameManager();
        move = player == GameManager.X ? new AtomicBoolean(true) : new AtomicBoolean(false);
        com = new Communicator(GameBoard.player);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame = this;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                frame.dispose();
                oppcheck.interrupt();
                try {
                    oppcheck.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                com.close();
                System.exit(0);
            }
        });

        setSize(500, 500);

        JPanel boardPanel = new JPanel();
        add(boardPanel);
        boardPanel.setLayout(new GridLayout(3, 3, 6, 6));
        boardPanel.setBounds(100, 100, 400, 400);
        boardPanel.setOpaque(false);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                boardPanel.add(buttons[i][j]);
                buttons[i][j].addMouseListener(
                        new MouseAdapter() {
                            public void mouseClicked(MouseEvent event) {
                                Thread t = new Thread(new MoveHandler(event));
                                t.setDaemon(true);
                                t.start();
                            }
                        });
            }
        oppcheck = new Thread(new OpponentChecker());
        oppcheck.start();

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private static String getLabel(int p) {
        if (p == GameManager.X)
            return "X";
        else
            return "O";
    }

    private static class MoveHandler implements Runnable {
        private final MouseEvent e;

        public MoveHandler(MouseEvent e) {
            this.e = e;
        }

        @Override
        public void run() {
            if (move.get() && continues.get()) {
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++) {
                        if (e.getSource().equals(buttons[i][j])) {
                            if (game.move(player, i, j)) {
                                move.compareAndSet(true, false);
                                buttons[i][j].setLabel(getLabel(player));

                                if (!game.checkWin(player)) {

                                    if (!game.hasMoves(player)) {
                                        com.sendTie(i, j);
                                        continues.compareAndSet(true, false);
                                        JOptionPane.showMessageDialog(null, "It's a Tie!");
                                        return;
                                    }

                                    com.sendMove(i, j);

                                } else {
                                    continues.compareAndSet(true, false);
                                    com.sendWin(i, j);
                                    JOptionPane.showMessageDialog(null, "You won!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Move is impossible!");
                            }
                        }
                    }
            } else {
                if (!continues.get())
                    JOptionPane.showMessageDialog(null, "Game is over!");
                else
                    JOptionPane.showMessageDialog(null, "Waiting for another player!");
            }
        }

    }

    private static class OpponentChecker implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                var response = com.receiveMessage();
                if (response != null) {
                    game.move(-player, response[1], response[2]);
                    buttons[response[1]][response[2]].setLabel(getLabel(-player));

                    if (response[0] != 0) {
                        continues.compareAndSet(true, false);
                        if (response[0] == -1)
                            JOptionPane.showMessageDialog(null, "You lost!");
                        return;
                    }
                    move.compareAndSet(false, true);
                }
            }

        }

    }
}
