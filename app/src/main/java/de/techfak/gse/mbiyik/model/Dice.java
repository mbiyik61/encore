package de.techfak.gse.mbiyik.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Dice {
    private static final char[] ALL_COLORS = {'b', 'g', 'o', 'r', 'y'};
    private static final double NUMBER_OF_CHANCES = 5.0;
    private final PropertyChangeSupport support;
    private int[] numbers;
    private char[] colors;

    public Dice() {
        this.support = new PropertyChangeSupport(this);
    }

    public void setNumbers(int[] numbers) {
        this.support.firePropertyChange("setNumbers", this.numbers, numbers);
        this.numbers = numbers;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public void setColors(char[] colors) {
        this.support.firePropertyChange("setColors", this.colors, colors);
        this.colors = colors;
    }

    public char[] getColors() {
        return colors;
    }
    public void rollDice() {
        final int numberOfDices = 3;
        int[] newNumbers = new int[numberOfDices];
        char[] newColors = new char[numberOfDices];
        for (int i = 0; i < numberOfDices; i++) {
            newNumbers[i] = rollNumbers();
            newColors[i] = rollColors();
        }
        setNumbers(newNumbers);
        setColors(newColors);
    }
    public int rollNumbers() {
        return (int) (NUMBER_OF_CHANCES * Math.random()) + 1;
    }
    public char rollColors() {
        final int three = 3;
        final int four = 4;
        final int five = 5;
        int random = (int) (NUMBER_OF_CHANCES * Math.random()) + 1;
        char color = 0;
        switch (random) {
            case 1:
                color = ALL_COLORS[0];
                break;
            case 2:
                color = ALL_COLORS[1];
                break;
            case three:
                color = ALL_COLORS[2];
                break;
            case four:
                color = ALL_COLORS[three];
                break;
            case five:
                color = ALL_COLORS[four];
                break;
            default:
        }
        return color;
    }
    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
