package de.techfak.gse.mbiyik.model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Game {
    private String board;
    private int points;
    private int round;
    private int zeilen = 7;
    private int spalten = 15;
    private final PropertyChangeSupport support;

    public Game(String board) {
        this.board = board;
        this.points = 0;
        this.round = 1;
        this.support = new PropertyChangeSupport(this);
    }
    public String getBoard() {
        return board;
    }
    public void setBoard(String board) {
        this.support.firePropertyChange("setBoard", this.board, board);
        this.board = board;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public int getRound() {
        return round; }
    public void setRound(int round) {
        int lastRound = this.round;
        this.round = round;
        this.support.firePropertyChange("setRound", lastRound, round);
    }

    public int getZeilen() {
        return zeilen;
    }

    public void setZeilen(int zeilen) {
        this.zeilen = zeilen;
    }

    public int getSpalten() {
        return spalten;
    }

    public void setSpalten(int spalten) {
        this.spalten = spalten;
    }

    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
