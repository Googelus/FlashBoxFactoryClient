package de.richard.alex.flashbox;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    private static CardStack stackbox;
    private static Card clickedCard;
    private static int stacknumber;

    public static CardStack getStackbox() {
        return stackbox;
    }

    public static int getStacknumber() {
        return stacknumber;
    }

    public static Card getClickedCard() {
        return clickedCard;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        // Get stack
        stacknumber =  Integer.parseInt(getIntent().getStringExtra(HauptmenuActivity.EXTRA_STACK));
        stackbox = SPBrowseActivity.getStack(stacknumber);
        List<String> items = new LinkedList<String>();

        for (Card i : stackbox.getCards()) {
            items.add(i.getQuestion());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.cardlist,items);


        ListView listView = (ListView) findViewById(R.id.cardeditlist);
        listView.setAdapter(adapter);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCard();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                clickedCard = stackbox.getCard(position);
                Intent i = new Intent(EditActivity.this, ShowCardActivity.class);
                startActivity(i);
                recreate();

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void makeCard() {
        Intent i = new Intent(EditActivity.this, MakeCardActivity.class);
        i.putExtra(HauptmenuActivity.EXTRA_STACK, stacknumber + "");
        startActivityForResult(i,1);
    }


}
