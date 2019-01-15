package de.richard.alex.flashbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MakeCardActivity extends AppCompatActivity {

    EditText question;
    EditText answer1;
    EditText answer2;
    EditText answer3;
    EditText description;
    Button submitButton;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private static int stackNumber;
    private static CardStack stack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_card);

        question = findViewById(R.id.makequestion);
        answer1 = findViewById(R.id.makeAnswer1);
        answer2 = findViewById(R.id.makeAnswer2);
        answer3 = findViewById(R.id.makeAnswer3);
        description = findViewById(R.id.makeDescription);

        submitButton = findViewById(R.id.makesubmit);

        radioGroup = findViewById(R.id.radioGroup);

        stackNumber =  Integer.parseInt(getIntent().getStringExtra(HauptmenuActivity.EXTRA_STACK));
        stack = SPBrowseActivity.getStack(stackNumber);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

    }

    private void submit() {
        stack.addCard(new Card(question.getText().toString(),
                answer1.getText().toString(),
                answer2.getText().toString(),
                answer3.getText().toString(),
                description.getText().toString(),
                Integer.parseInt(radioButton.getText().toString())-1));
        SPBrowseActivity.setStack(stackNumber,stack);
        Toast.makeText(this, "Card added", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void rbClick(View view) {

        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioButtonId);

    }

}
