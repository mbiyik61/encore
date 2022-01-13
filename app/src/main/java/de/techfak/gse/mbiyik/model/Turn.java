package de.techfak.gse.mbiyik.model;


import android.graphics.Color;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Stack;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Turn {
    private static final int ZEILEN = 7;
    private static final int SPALTEN = 15;
    private static final String MARK = "X";
    private static final Set<Integer> COLUMN_H = new HashSet<>(Arrays.asList(7, 22, 37, 52, 67, 82, 97));
    private final GridLayout gridLayout;
    private final int[][] twodArray = new int[ZEILEN][SPALTEN];

    public Turn(GridLayout gridLayout) {
        this.gridLayout = gridLayout;
    }
    public boolean validateFirstRound() {
        boolean validate = false;
        for (int i = 0; i < ZEILEN; i++) {
            for (int j = 0; j < SPALTEN; j++) {
                int index = i * SPALTEN + j;
                TextView feld = (TextView) gridLayout.getChildAt(index);
                if (feld.getText().equals(MARK) && feld.isEnabled() && COLUMN_H.contains(index)) {
                    validate = true;
                }
            }
        }
        return validate;
    }
    public boolean pass(int[][] field) {
        int twosZeros = 0;
        for (int i = 0; i < ZEILEN; i++) {
            for (int j = 0; j < SPALTEN; j++) {
                if (field[i][j] == 0 || field[i][j] == 2) {
                    twosZeros++;
                }
            }
        }
        return twosZeros == ZEILEN * SPALTEN;
    }
    public boolean fieldEmpty(int[][] field) {
        boolean empty = true;
        for (int i = 0; i < ZEILEN; i++) {
            for (int j = 0; j < SPALTEN; j++) {
                if (field[i][j] == 2) {
                    empty = false;
                    break;
                }
            }
        }
        return empty;
    }
    public int[][] layoutToArray(GridLayout gridLayout) {
        for (int i = 0; i < ZEILEN; i++) {
            for (int j = 0; j < SPALTEN; j++) {
                TextView feld = (TextView) gridLayout.getChildAt(i * SPALTEN + j);
                if (feld.getText().equals(MARK) && !feld.isEnabled()) {
                    twodArray[i][j] = 2;
                } else if (feld.getText().equals(MARK) && feld.isEnabled()) {
                    twodArray[i][j] = 1;
                }

            }
        }
        return twodArray;
    }

    public void markingFields(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View view = gridLayout.getChildAt(i);
            if (view instanceof TextView && ((TextView) view).getText().equals(MARK)) {
                ((TextView) view).setTextColor(Color.parseColor("#808080"));
                view.setEnabled(false);
            }
        }
    }
    //Using stack with depth first search to find all neighbors and validate them
    public boolean validate(int[][] field) {
        int countTwos = 0;
        int countMarked = 0;
        boolean validRound = false;
        int rows = field.length;
        int cols = field[0].length;
        for (int[] ints : field) {
            for (int j = 0; j < cols; j++) {
                if (ints[j] == 1) {
                    countMarked++;
                } else if (ints[j] == 2) {
                    countTwos++;
                }
            }
        }

        loop:
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (field[i][j] == 1) {
                    validRound = dfs(field, i, j, countMarked, countTwos);
                    break loop;
                }
            }
        }
        return validRound;
    }
    private boolean dfs(int[][] field, int row, int col, int countMarked, int countTwos) {
        int size = 0;
        boolean[][] visited = new boolean[field.length][field[0].length];
        Stack<Coordinate> stack = new Stack<>();
        Coordinate coord = new Coordinate(row, col);
        stack.push(coord);
        boolean neighborFound = false;
        if (countTwos == 0) {
            neighborFound = true;
        }

        while (!stack.empty()) {
            Coordinate current = stack.pop();
            int xCord = current.getX();
            int yCord = current.getY();
            if (xCord < 0 || yCord < 0 || xCord >= field.length || yCord >= field[0].length || visited[xCord][yCord]) {
                continue;
            }
            if (field[xCord][yCord] == 2) {
                neighborFound = true;
            }
            if (field[xCord][yCord] == 1) {
                visited[xCord][yCord] = true;
                size += 1;
                Coordinate above = new Coordinate(current.getX() - 1, current.getY());
                Coordinate below = new Coordinate(current.getX() + 1, current.getY());
                Coordinate right = new Coordinate(current.getX(), current.getY() + 1);
                Coordinate left = new Coordinate(current.getX(), current.getY() - 1);
                stack.push(above);
                stack.push(below);
                stack.push(right);
                stack.push(left);
            }
        }
        return (countMarked == size) && neighborFound;
    }
}
