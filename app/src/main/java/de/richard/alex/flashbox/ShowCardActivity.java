package de.richard.alex.flashbox;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class ShowCardActivity extends AppCompatActivity {

    Card currentCard;

    //layout
    private TextView question;
    private TextView answer1;
    private TextView answer2;
    private TextView answer3;
    private TextView explanation;
    private Button remove;
    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);

        //get Layout
        question = findViewById(R.id.show_question);
        answer1 = findViewById(R.id.show_answer1);
        answer2 = findViewById(R.id.show_answer2);
        answer3 = findViewById(R.id.show_answer3);
        explanation = findViewById(R.id.show_explanation);
        remove = findViewById(R.id.show_remove);
        back = findViewById(R.id.show_back);

        currentCard = EditActivity.getClickedCard();

        question.setText(currentCard.getQuestion());
        answer1.setText(currentCard.getAnswers()[0]);
        answer2.setText(currentCard.getAnswers()[1]);
        answer3.setText(currentCard.getAnswers()[2]);
        explanation.setText(currentCard.getExplanation());

        switch (currentCard.getCorrect_answer()) {
            case 0: answer1.setTextColor(Color.GREEN);
            break;
            case 1: answer2.setTextColor(Color.GREEN);
            break;
            case 2: answer3.setTextColor(Color.GREEN);
            break;
            default:
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardStack stack = EditActivity.getStackbox();
                stack.removeCard(currentCard);
                SPBrowseActivity.setStack(EditActivity.getStacknumber(),stack);
                finish();
            }
        });
    }
}
