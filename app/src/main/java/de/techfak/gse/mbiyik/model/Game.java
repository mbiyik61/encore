package de.techfak.gse.mbiyik.model;


import android.graphics.Color;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Game {
    private String board;
    private int points;
    private int round;
    private final int zeilen = 7;
    private final int spalten = 15;
    private final PropertyChangeSupport support;
    private Turn turn;

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



    public int getSpalten() {
        return spalten;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
    public void markingFields(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View view = gridLayout.getChildAt(i);
            if (view instanceof TextView && ((TextView) view).getText().equals("X")) {
                ((TextView) view).setTextColor(Color.parseColor("#808080"));
                view.setEnabled(false);
            }
        }
    }
}
