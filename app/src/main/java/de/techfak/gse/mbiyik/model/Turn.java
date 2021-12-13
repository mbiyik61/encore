package de.techfak.gse.mbiyik.model;


import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Turn {
    Game game;
    GridLayout gridLayout;
    private final Set<Integer> columnH = new HashSet<>(Arrays.asList(7, 22, 37, 52, 67, 82, 97));

    public Turn(Game game, GridLayout gridLayout) {
        this.game = game;
        this.gridLayout = gridLayout;
    }

    public boolean validateTurn(GridLayout gridLayout) {
        boolean validate = true;
        for (int i = 1; i < gridLayout.getChildCount(); i++) {
            TextView field = (TextView) gridLayout.getChildAt(i);
            if (field.getText().equals("X") && field.isEnabled()) {
                if (i < 15) {
                    boolean left = ((TextView) gridLayout.getChildAt(i - 1)).getText().equals("X");
                    boolean right = ((TextView) gridLayout.getChildAt(i + 1)).getText().equals("X");
                    boolean below = ((TextView) gridLayout.getChildAt(i + 15)).getText().equals("X");
                    if (!left && !right && !below) {
                        validate = false;
                        break;
                    }
                } else if (i > 82) {
                    boolean left = ((TextView) gridLayout.getChildAt(i - 1)).getText().equals("X");
                    boolean right = ((TextView) gridLayout.getChildAt(i + 1)).getText().equals("X");
                    boolean above = ((TextView) gridLayout.getChildAt(i - 15)).getText().equals("X");
                    if (!left && !right && !above) {
                        validate = false;
                        break;
                    }
                } else {
                    boolean left = ((TextView) gridLayout.getChildAt(i - 1)).getText().equals("X");
                    boolean right = ((TextView) gridLayout.getChildAt(i + 1)).getText().equals("X");
                    boolean above = ((TextView) gridLayout.getChildAt(i - 15)).getText().equals("X");
                    boolean below = ((TextView) gridLayout.getChildAt(i + 15)).getText().equals("X");

                    if (!left && !right && !above && !below) {
                        validate = false;
                        break;
                    }
                }

            }
        }

        return validate;

    }

    public boolean firstRound(GridLayout gridLayout) {
        boolean validate = true;

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            TextView firstField = (TextView) gridLayout.getChildAt(i);
            if (firstField.getText().equals("X")) {
                if (columnH.contains(i)) {
                    validate = true;
                    break;
                } else {
                    validate = false;
                    break;
                }
            }
        }
        return validate;
   }
    public boolean unmarked(GridLayout gridLayout) {
        int marked = 0;
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            TextView field = (TextView) gridLayout.getChildAt(i);
            if (field.getText().equals("X") && !field.isEnabled()) {
                marked++;
                break;
            }
        }
        return marked == 0;
    }
    public void deleteInvalid(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            TextView field = (TextView) gridLayout.getChildAt(i);
            if (field.isEnabled()) {
                field.setText("");
            }
        }
    }
    public boolean validateConnection(GridLayout gridLayout) {
        boolean validate = true;
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            TextView field = (TextView) gridLayout.getChildAt(i);
            if (field.getText().equals("X") && field.isEnabled()) {
                TextView left = (TextView) gridLayout.getChildAt(i - 1);
                TextView right = (TextView) gridLayout.getChildAt(i + 1);
                TextView above = (TextView) gridLayout.getChildAt(i - 15);
                TextView below = (TextView) gridLayout.getChildAt(i + 15);

                boolean leftConnection = !left.isEnabled();
                boolean rightConnection = !right.isEnabled();
                boolean aboveConnection = !above.isEnabled();
                boolean belowConnection = !below.isEnabled();


                if (!leftConnection && !rightConnection && !aboveConnection && !belowConnection) {
                    validate = false;
                    break;
                } else {
                    field.setEnabled(false);
                    validate = true;
                }
            }
        }
        return validate;


    }
}
