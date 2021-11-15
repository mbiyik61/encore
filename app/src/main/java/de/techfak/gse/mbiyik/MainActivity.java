package de.techfak.gse.mbiyik;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;



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
        if ((!valideLayout(input,view,7,15)) || (!valideField(input, view))) {
            editText.setText("");
            editText.setHint("Bitte versuchen Sie es erneut.");

        }



    }

    public boolean valideLayout(String input,View view, int zeilen,int spalten) {
        int count = 0;
        String[] configs = input.split("\n");
        if (configs.length==zeilen) {
            for (int i=0;i<zeilen;i++) {
                if (configs[i].length()==spalten) {
                    count++;
                }
            }
        }
        try {
            if (count != zeilen) {
                throw new Exception("InvalidBoardLayout");
            }

        } catch (Exception e) {
            Snackbar.make(view,"Das eingegebene Spielfeld ist nicht gültig!", Snackbar.LENGTH_LONG).show();
            Log.e("InvalidBoardLayout", "Die Eingabe für die Spielfeldkonfiguration ist größer oder kleiner als 7x15");
        }
        return count == zeilen;
    }

    public boolean valideField(String input, View view) {
        String[] colors = {"b", "B", "g", "G", "o", "O", "r", "R", "y", "Y"};
        String[] seperate = input.split("");
        boolean contains = true;

        try {
            loop:
            for (int i = 1; i < seperate.length; i++) {
                if (!seperate[i].equals("\n") && !seperate[i].equals(" ")) {
                    for (String color : colors) {
                        if (seperate[i].matches(color)) {
                            break;
                        } else if ((!seperate[i].matches(color) && (color.equals("Y")))) {
                            contains = false;
                            break loop;

                        }

                    }
                }
            }
            if (!contains){
                throw new Exception("InvalidField");
            }
        } catch (Exception e) {
            Snackbar.make(view,"Das eingegebene Spielfeld enthält mindestens ein ungültiges Zeichen!", Snackbar.LENGTH_LONG).show();
            Log.e("InvalidField", "Die Eingabe für das Spielfeld enthält mindestens ein ungültiges Zeichen.");
        }
        return contains;
    }

}
