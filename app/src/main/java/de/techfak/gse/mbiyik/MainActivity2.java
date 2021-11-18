package de.techfak.gse.mbiyik;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        String[] field = getIntent().getExtras().get("input").toString().split("");
        int zeilen = getIntent().getIntExtra("zeilen",0);
        int spalten = getIntent().getIntExtra("spalten",0);
        GridLayout gridLayout = findViewById(R.id.gridlayout);
        gridLayout.setColumnCount(spalten);
        gridLayout.setRowCount(zeilen);
        GridColors(gridLayout,field);
    }
    //Erstellt ein TextView und füllt den Background mit der entsprechenden Farbe aus und gegebenenfalls werden Großbuchstaben angekreuzt
    public void GridColors(GridLayout gridLayout,String[] input) {
        int total = input.length;
        for (int i=1;i<total;i++) {
            if (!input[i].equals("\n") && !input[i].equals(" ")) {
                TextView square = new TextView(this);
                square.setText("");
                square.setWidth(110);
                square.setHeight(110);
                square.setTextSize(30);
                square.setTextColor(Color.BLACK);
                square.setGravity(17);
                square.setId(i);
                char uppercase = input[i].charAt(0);
                if (Character.isUpperCase(uppercase)) {
                    square.setText("X");
                }
                switch (input[i]) {
                    case "b":
                        square.setBackgroundResource(R.drawable.field_blue);
                    case "B":
                        square.setBackgroundResource(R.drawable.field_blue);
                        break;
                    case "g":
                        square.setBackgroundResource(R.drawable.field_green);
                    case "G":
                        square.setBackgroundResource(R.drawable.field_green);
                        break;
                    case "o":
                        square.setBackgroundResource(R.drawable.field_orange);
                    case "O":
                        square.setBackgroundResource(R.drawable.field_orange);
                        break;
                    case "r":
                        square.setBackgroundResource(R.drawable.field_red);
                    case "R":
                        square.setBackgroundResource(R.drawable.field_red);
                        break;
                    case "y":
                        square.setBackgroundResource(R.drawable.field_yellow);
                    case "Y":
                        square.setBackgroundResource(R.drawable.field_yellow);
                        break;
                }
                gridLayout.addView(square);
            }
        }
    }
}