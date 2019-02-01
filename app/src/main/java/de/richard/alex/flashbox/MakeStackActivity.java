package de.richard.alex.flashbox;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MakeStackActivity extends AppCompatActivity {

    private Button finish;
    private EditText name;
    private EditText tags;
    private EditText info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_stack);

        //Get Layout
        finish = findViewById(R.id.make_stack_finish);
        name = findViewById(R.id.make_stack_name);
        tags = findViewById(R.id.tags);
        info = findViewById(R.id.make_stack_description);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String author = sharedPreferences.getString("author","Micheal Knight");

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPBrowseActivity.addStack(new CardStack(author,name.getText().toString(),info.getText().toString(),tags.getText().toString()));
                finish();
            }
        });


    }

}
