package de.techfak.gse.mbiyik.model;

import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CheckColor {
    private final int zeilen = 7;
    private final int spalten = 15;
    private final char[][] colors = new char[zeilen][spalten];
    private final ArrayList<Character> currentColors = new ArrayList<>();

    public CheckColor() {

    }

    public void colorArray(GridLayout gridLayout) {
        for (int i = 0; i < zeilen; i++) {
            for (int j = 0; j < spalten; j++) {
                TextView feld = (TextView) gridLayout.getChildAt(i * spalten + j);
                switch (feld.getTag().toString()) {
                    case "blue":
                        colors[i][j] = 'b';
                        break;
                    case "green":
                        colors[i][j] = 'g';
                        break;
                    case "orange":
                        colors[i][j] = 'o';
                        break;
                    case "red":
                        colors[i][j] = 'r';
                        break;
                    case "yellow":
                        colors[i][j] = 'y';
                        break;
                    default:
                }

            }
        }
    }
    public boolean check(int[][] field) {
        for (int i = 0; i < zeilen; i++) {
            for (int j = 0; j < spalten; j++) {
                if (field[i][j] == 1) {
                    currentColors.add(colors[i][j]);
                }

            }
        }
        Set<Character> colors = new HashSet<>(currentColors);
        return colors.size() == 1;
    }
}
