package de.techfak.gse.mbiyik.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import android.view.View.OnClickListener;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.techfak.gse.mbiyik.R;
import de.techfak.gse.mbiyik.model.Game;
import de.techfak.gse.mbiyik.model.GameApplication;
import de.techfak.gse.mbiyik.model.Turn;


public class GameActivity extends AppCompatActivity implements PropertyChangeListener {
    private GameApplication gameApplication;
    private GridLayout gridLayout;
    private final OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView textView = (TextView) view;
            if (textView.getText().equals("")) {
                textView.setText("X");
            } else {
                textView.setText("");
            }
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
        String pattern = input.replaceAll("\n", "").replaceAll("[^a-zA-Z\\\\s]", "").replaceAll(" ", "");
        String[] field = pattern.split("");

        for (int i = 1; i < field.length; i++) {

                TextView square = new TextView(this);
                GridLayout.LayoutParams param = new GridLayout.LayoutParams(GridLayout.spec(
                    GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f));
                param.height = 100;
                param.width = 100;
                square.setLayoutParams(param);
                square.setTextSize(20);
                square.setTextColor(Color.BLACK);
                square.setGravity(17);
                square.setId(i);
                square.setOnClickListener(onClickListener);

                char uppercase = field[i].charAt(0);
                if (Character.isUpperCase(uppercase)) {
                    square.setText("X");
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
                }
                gridLayout.addView(square);

        }

    }
    public void nextRound(View view) {
        int currentRound = gameApplication.getGame().getRound();
        Turn turns = new Turn(gameApplication.getGame(), gridLayout);
        if (turns.unmarked(gridLayout)) {
            if (turns.firstRound(gridLayout) && turns.validateTurn(gridLayout)) {
                gameApplication.getGame().markingFields(gridLayout);
                gameApplication.getGame().setRound(currentRound + 1);
            } else {
                Snackbar.make(view, "Der erste Zug ist nicht gültig!", Snackbar.LENGTH_SHORT).show();
            }

        } else if (!turns.unmarked(gridLayout)) {
            if (turns.validateTurn(gridLayout)) {
                if (turns.validateConnection(gridLayout)) {
                    gameApplication.getGame().markingFields(gridLayout);
                    gameApplication.getGame().setRound(currentRound + 1);
                }
            } else {
                Snackbar.make(view, "Der Zug ist nicht gültig!", Snackbar.LENGTH_SHORT).show();
            }
        }
        turns.deleteInvalid(gridLayout);

    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        TextView rundenAnzeige = findViewById(R.id.round);
        rundenAnzeige.setText(String.format("Runde: %s", evt.getNewValue()));
    }


}
