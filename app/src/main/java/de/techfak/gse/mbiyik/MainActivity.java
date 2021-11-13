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
        String[] configs = input.split("\n");
        if (!(valideLayout(configs,view))) {
            editText.setText("");
            editText.setHint("Bitte versuchen Sie es erneut.");

        }

    }
    public boolean valideLayout(String[] input,View view) {
        int count = 0;
        if (input.length==7) {
            for (int i=0;i<7;i++) {
                if (input[i].length()==15) {
                    count++;
                }
            }
        }
        try {
            if (count != 7) {
                throw new Exception("InvalidBoardLayout");
            }

        } catch (Exception e) {
            Snackbar.make(view,"Das eingegebene Spielfeld ist nicht gültig!", Snackbar.LENGTH_SHORT).show();
            Log.e("InvalidBoardLayout", "Die Eingabe für die Spielfeldkonfiguration ist größer oder kleiner als 7x15");
        }
        return count == 7;
    }

}
