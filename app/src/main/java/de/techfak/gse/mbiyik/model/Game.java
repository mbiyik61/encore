package de.techfak.gse.mbiyik.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Game {
    private static final int[] BOARD_SIZE = {7, 15};
    private final String board;
    private final PropertyChangeSupport support;
    private int round;

    public Game(String board) {
        this.board = board;
        this.round = 1;
        this.support = new PropertyChangeSupport(this);
    }
    public String getBoard() {
        return board;
    }
    public int getRound() {
        return round; }
    public void setRound(int round) {
        this.support.firePropertyChange("setRound", this.round, round);
        this.round = round;
    }
    public int getZeilen() {
        return BOARD_SIZE[0];
    }
    public int getSpalten() {
        return BOARD_SIZE[1];
    }
    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    public void removeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
