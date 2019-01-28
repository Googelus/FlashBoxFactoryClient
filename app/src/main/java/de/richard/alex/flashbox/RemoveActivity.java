package de.richard.alex.flashbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RemoveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        final int stacknumber =  Integer.parseInt(getIntent().getStringExtra(HauptmenuActivity.EXTRA_STACK));

        final Button yes = findViewById(R.id.remove_yes);
        final Button no = findViewById(R.id.remove_no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPBrowseActivity.removeStack(stacknumber);
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
