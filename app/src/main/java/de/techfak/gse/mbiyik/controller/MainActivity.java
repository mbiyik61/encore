package de.techfak.gse.mbiyik.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import de.techfak.gse.mbiyik.model.InvalidBoardLayout;
import de.techfak.gse.mbiyik.model.InvalidField;
import de.techfak.gse.mbiyik.R;
import de.techfak.gse.mbiyik.model.Game;
import de.techfak.gse.mbiyik.model.GameApplication;


/**
 * Entry point activity for the app.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {
        EditText editText = findViewById(R.id.config);

        Log.i("log", String.valueOf(editText.getText()));
        String input = editText.getText().toString();
        GameApplication gameApplication = (GameApplication) getApplication();
        Game game = new Game(input);
        if ((!valideLayout(input, view, game.getZeilen(), game.getSpalten())) || (!valideField(input, view))) {
            editText.setText("");
            editText.setHint("Bitte versuchen Sie es erneut.");
        } else {
            gameApplication.setGame(game);
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);

        }
    }

    public boolean valideLayout(String input, View view, int zeilen, int spalten) {
        int count = 0;
        String[] configs = input.split("\n");
        if (configs.length == zeilen) {
            for (String s : configs) {
                if (s.length() == spalten) {
                    count++;
                }
            }
        }
        try {
            checkLayout(zeilen, count);

        } catch (InvalidBoardLayout e) {
            Snackbar.make(view, "Das eingegebene Spielfeld ist nicht gültig!", Snackbar.LENGTH_LONG).show();
        }
        return count == zeilen;
    }
    private void checkLayout(int zeilen, int count) throws InvalidBoardLayout {
        if (count != zeilen) {
            throw new InvalidBoardLayout();
        }
    }

    //Alle Elemente des Inputs (ausser "\n" und " ") werden mit den vorgegebenen Farben verglichen
    // und falls ein Buchstabe nicht vorkommt, wird die Exception ausgegeben
    public boolean valideField(String input, View view) {
        String pure = input.replaceAll("[^a-zA-Z]", "");
        boolean contains = true;
        try {
            checkField(pure);
        } catch (InvalidField e) {
            contains = false;
            Snackbar.make(view, "Das eingegebene Spielfeld enthält mindestens ein "
                    + "ungültiges Zeichen!", Snackbar.LENGTH_LONG).show();
        }

        return contains;
    }

    private void checkField(String pure) throws InvalidField {
        if (!(pure.matches("^[bBgGoOrRyY]+$"))) {
            throw new InvalidField();
        }
    }
}
