package de.richard.alex.flashbox;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView congrats;
    TextView congratstext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final Button button = findViewById(R.id.result_button);

        congrats = findViewById(R.id.congrats);
        congratstext = findViewById(R.id.congratstext);

        int result =  Integer.parseInt(getIntent().getStringExtra(HauptmenuActivity.EXTRA_STACK));

         //Spezifischer Text zu verschiedenen Prozentbeträgen korrekter Antworten
        if (result == 0) {
            congrats.setText("0 Punkte");
            congratstext.setText("Das war wohl Nichts");
            congrats.setTextColor(Color.RED);
        } if (result >= 1) {
            congrats.setText("");
            congratstext.setText("");
            congrats.setTextColor(Color.RED);
        } if (result >= 25) {
            congrats.setText("Naja");
            congratstext.setText("Da ist aber noch ganz klar Verbesserungsbedarf");
            congrats.setTextColor(Color.RED);
        }  if (result >= 50) {
            congrats.setText("Glückwunsch!");
            congratstext.setText("Du hast mehr als die Hälfte richtig beantwortet und somit erfolgreich bestanden");
            congrats.setTextColor(Color.rgb(0,70,0));
        } if (result >= 75) {
            congrats.setText("Hervorragend");
            congratstext.setText("Die meisten Fragen korrekt beantwortet");
            congrats.setTextColor(Color.GREEN);
        } if (result == 100) {
            congrats.setText("Perfekt!");
            congratstext.setText("Du konntest alle Fragen korrekt beantworten");
            congrats.setTextColor(Color.GREEN);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
