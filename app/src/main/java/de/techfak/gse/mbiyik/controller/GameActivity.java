package de.techfak.gse.mbiyik.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import android.view.View.OnClickListener;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.techfak.gse.mbiyik.model.InvalidTurn;
import de.techfak.gse.mbiyik.R;
import de.techfak.gse.mbiyik.model.Dice;
import de.techfak.gse.mbiyik.model.CheckColor;
import de.techfak.gse.mbiyik.model.Game;
import de.techfak.gse.mbiyik.model.GameApplication;
import de.techfak.gse.mbiyik.model.Turn;


public class GameActivity extends AppCompatActivity implements PropertyChangeListener {
    private static final String MARK = "X";
    private GameApplication gameApplication;
    private GridLayout gridLayout;
    private final OnClickListener onClickListener = view -> {
        TextView textView = (TextView) view;
        if (textView.getText().equals("")) {
            textView.setText(MARK);
        } else {
            textView.setText("");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        gameApplication = (GameApplication) getApplication();
        Game game = gameApplication.getGame();
        game.addListener(this);
        String playerBoard = game.getBoard();
        gridLayout = findViewById(R.id.gridlayout);
        gridLayout.setColumnCount(game.getSpalten());
        gridLayout.setRowCount(game.getZeilen());
        gridColors(gridLayout, playerBoard);
        Button endTurn = findViewById(R.id.zugbeenden);
        endTurn.setEnabled(false);

    }

    @Override
    protected void onDestroy() {
        gameApplication.getGame().removeListener(this);
        super.onDestroy();
    }


    //Alertdialog wird erstellt
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Möchten Sie das Spiel verlassen?");
        alert.setMessage("Der Spielfortschritt wird gelöscht und Sie landen wieder im Hauptmenü.");
        alert.setPositiveButton("Ja", (dialog, which) -> super.onBackPressed());
        alert.setNegativeButton("Nein", (dialog, which) -> {});
        alert.show();
    }
    //Erstellt ein TextView und füllt den Background mit der entsprechenden Farbe aus und
    // gegebenenfalls werden Großbuchstaben angekreuzt
    public void gridColors(GridLayout gridLayout, String input) {
        final int layoutParams = 100;
        final int textSize = 20;
        final int gravity = 17;
        String pattern = input.replaceAll("\n", "").replaceAll("[^a-zA-Z\\\\s]", "").replaceAll(" ", "");
        String[] field = pattern.split("");

        for (int i = 1; i < field.length; i++) {

                TextView square = new TextView(this);
                GridLayout.LayoutParams param = new GridLayout.LayoutParams(GridLayout.spec(
                    GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f));
                param.height = layoutParams;
                param.width = layoutParams;
                square.setLayoutParams(param);
                square.setTextSize(textSize);
                square.setTextColor(Color.BLACK);
                square.setGravity(gravity);
                square.setId(i);
                square.setOnClickListener(onClickListener);

                char uppercase = field[i].charAt(0);
                if (Character.isUpperCase(uppercase)) {
                    square.setText(MARK);
                }
                switch (field[i]) {
                    case "b":
                    case "B":
                        square.setTag("blue");
                        square.setBackgroundResource(R.drawable.field_blue);
                        break;
                    case "g":
                    case "G":
                        square.setTag("green");
                        square.setBackgroundResource(R.drawable.field_green);
                        break;
                    case "o":
                    case "O":
                        square.setTag("orange");
                        square.setBackgroundResource(R.drawable.field_orange);
                        break;
                    case "r":
                    case "R":
                        square.setTag("red");
                        square.setBackgroundResource(R.drawable.field_red);
                        break;
                    case "y":
                    case "Y":
                        square.setTag("yellow");
                        square.setBackgroundResource(R.drawable.field_yellow);
                        break;
                    default:
                }
                gridLayout.addView(square);

        }

    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        switch (propertyName) {
            case "setRound":
                TextView rundenAnzeige = findViewById(R.id.round);
                rundenAnzeige.setText(String.format("Runde: %s", evt.getNewValue()));
                break;
            case "setNumbers":
                int[] numbers = (int[]) evt.getNewValue();
                changeNumbers(numbers);
                break;
            case "setColors":
                char[] colors = (char[]) evt.getNewValue();
                changeColors(colors);
                break;
            default:
        }
    }
    public void nextRound(View view) {
        int currentRound = gameApplication.getGame().getRound();
        Button wuerfel = findViewById(R.id.wuerfeln);
        Button endTurn = findViewById(R.id.zugbeenden);
        Game game = gameApplication.getGame();
        Turn turns = new Turn(gridLayout);
        CheckColor colors = new CheckColor();
        turns.layoutToArray(gridLayout);
        int[][] field = turns.layoutToArray(gridLayout);
        colors.colorArray(gridLayout);
        try {
            checkTurn(turns, gridLayout, colors);
            if (turns.fieldEmpty(field)) {
                if (turns.pass(field)) {
                    game.setRound(currentRound + 1);
                } else if (turns.validateFirstRound() && turns.validate(field) && colors.check(field)) {
                    turns.markingFields(gridLayout);
                    game.setRound(currentRound + 1);
                }
            } else {
                if (turns.pass(field)) {
                    game.setRound(currentRound + 1);
                } else if (turns.validate(field) && colors.check(field)) {
                    turns.markingFields(gridLayout);
                    game.setRound(currentRound + 1);
                }
            }
            endTurn.setEnabled(false);
            wuerfel.setEnabled(true);
        } catch (InvalidTurn e) {
            Snackbar.make(view, "Der Zug ist nicht gültig!", Snackbar.LENGTH_SHORT).show();
        }
    }
    public void checkTurn(Turn turn, GridLayout gridLayout, CheckColor colors) throws InvalidTurn {
        int[][] field = turn.layoutToArray(gridLayout);
        if (turn.fieldEmpty(field) && !turn.pass(field)) {
            if (!colors.check(field) || !turn.validate(field) || !turn.validateFirstRound()) {
                throw new InvalidTurn();
            }
        } else if (!turn.pass(field) && (!colors.check(field) || !turn.validate(field))) {
            throw new InvalidTurn();
        }
    }
    //onClick-Methode für den "Würfeln"-Button
    public void wuerfeln(View view) {
        Dice dice = new Dice();
        dice.addListener(this);
        dice.rollDice();
        int[] numbers = dice.getNumbers();
        char[] colors = dice.getColors();
        dice.setNumbers(numbers);
        dice.setColors(colors);
        Button wuerfeln = findViewById(R.id.wuerfeln);
        Button endTurn = findViewById(R.id.zugbeenden);
        wuerfeln.setEnabled(false);
        endTurn.setEnabled(true);
    }
    public void changeNumbers(int[] numbers) {
        final int three = 3;
        final int four = 4;
        final int five = 5;
        ImageView[] views = {findViewById(R.id.firstNr), findViewById(R.id.secondNr),  findViewById(R.id.thirdNr)};
        for (int i = 0; i < numbers.length; i++) {
            ImageView imageView = views[i];
            switch (numbers[i]) {
                case 1:
                    imageView.setImageResource(R.drawable.one);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.two);
                    break;
                case three:
                    imageView.setImageResource(R.drawable.three);
                    break;
                case four:
                    imageView.setImageResource(R.drawable.four);
                    break;
                case five:
                    imageView.setImageResource(R.drawable.five);
                    break;
                default:
            }
        }
    }
    public void changeColors(char[] colors) {
        ImageView[] views = {findViewById(R.id.firstC), findViewById(R.id.secondC), findViewById(R.id.thirdC)};
        for (int i = 0; i < colors.length; i++) {
            ImageView imageView = views[i];
            switch (colors[i]) {
                case 'b':
                    imageView.setImageResource(R.drawable.field_blue);
                    break;
                case 'g':
                    imageView.setImageResource(R.drawable.field_green);
                    break;
                case 'o':
                    imageView.setImageResource(R.drawable.field_orange);
                    break;
                case 'r':
                    imageView.setImageResource(R.drawable.field_red);
                    break;
                case 'y':
                    imageView.setImageResource(R.drawable.field_yellow);
                    break;
                default:
            }
        }
    }
}
