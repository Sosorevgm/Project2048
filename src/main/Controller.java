package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {

    private final static int WINNING_TILE = 2048;
    private Model model;
    private View view;

    public Controller(Model model) {
        this.model = model;
        this.view = new View(this);
    }

    public View getView() {
        return view;
    }

    public Tile[][] getGameTiles() {
        return model.getGameTiles();
    }

    public int getScore() {
        return model.score;
    }

    public void resetGame() {
        model.resetGameTiles();
        model.score = 0;
        view.isGameWon = false;
        view.isGameLost = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.resetGame();
        }

        if (!model.canMove()){
            view.isGameLost = true;
        }

        if (!(view.isGameLost && view.isGameWon)){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    model.left();
                    break;
                case KeyEvent.VK_RIGHT:
                    model.right();
                    break;
                case KeyEvent.VK_UP:
                    model.up();
                    break;
                case KeyEvent.VK_DOWN:
                    model.down();
                    break;
            }
        }
        view.repaint();
        if (model.maxTile == WINNING_TILE){
            view.isGameWon = true;
        }
        super.keyPressed(e);
    }
}
