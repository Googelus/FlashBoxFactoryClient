package de.richard.alex.flashbox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class SPBrowseActivity extends AppCompatActivity {

    private static File save;
    private static Context thisContext;
    private static List<CardStack> stacks;

    public static CardStack getStack(int id) {
        return(stacks.get(id));
    }

    public static void setStack(int id, CardStack stack) {
        stacks.remove(id);
        stacks.add(id,stack);
    }

    public  static void addStack(CardStack stack) {
        stacks.add(stack);
    }

    public static void removeStack(int id) {
        stacks.remove(id);
    }

    public static void save() {
        try {
            List<CardStack> cardstacks = stacks;
            FileOutputStream data = thisContext.openFileOutput("Flashboxsave",MODE_PRIVATE);
            Gson gson = new Gson();
            String message = "";
            while (!(cardstacks.isEmpty())) {
                message = message + gson.toJson(cardstacks.get(0)) + "\n";
                cardstacks.remove(0);
            }
            data.write(message.getBytes("UTF-8"));
            data.close();
            Log.d("Save","Success");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private void refreshView(){
        mViewPager.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spbrowse);

        save = new File(getFilesDir(), "Flashboxsave");

        // get stacks
        if (stacks != null) save();
        stacks = new LinkedList<CardStack>();
        //stacks = HauptmenuActivity.getExampleStacks();
        // Load Stacks from File
        try {
            FileInputStream inputstream = openFileInput("Flashboxsave");
            InputStreamReader inputreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputreader);
            String line;
            Gson gson = new Gson();
            CardStack stack;
            int i = 0;
            while ((line = bufferedreader.readLine()) != null) {
                stack = gson.fromJson(line, CardStack.class);
                stacks.add(stack);
                i++;
            }
            Log.d("Load",i + " Stacks loaded");
        } catch (Exception e) {
            e.printStackTrace();
        }


        thisContext = SPBrowseActivity.this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newStack();
            }
        });

    }

    private void newStack() {
        Intent ii = new Intent(thisContext, MakeStackActivity.class);
        startActivityForResult(ii,1);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spbrowse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_spbrowse, container, false);
            // TODO: hier Fragment editieren


            TextView cardNumber = rootView.findViewById(R.id.cardnumber);
            TextView nameView = rootView.findViewById(R.id.stackname);
            TextView authorView = rootView.findViewById(R.id.author);
            TextView info = rootView.findViewById(R.id.info);
            TextView tags = rootView.findViewById(R.id.tags);
            final Button play = rootView.findViewById(R.id.Play);
            final Button edit = rootView.findViewById(R.id.edit_stack);
            final Button upload = rootView.findViewById(R.id.upload);
            final Button remove = rootView.findViewById(R.id.stack_remove);

            nameView.setText(SPBrowseActivity.stacks.get(getArguments().getInt(ARG_SECTION_NUMBER)-1).getName());
            authorView.setText(SPBrowseActivity.stacks.get(getArguments().getInt(ARG_SECTION_NUMBER)-1).getAuthor());
            cardNumber.setText("number of cards: " + SPBrowseActivity.stacks.get(getArguments().getInt(ARG_SECTION_NUMBER)-1).getSize());
            info.setText(SPBrowseActivity.stacks.get(getArguments().getInt(ARG_SECTION_NUMBER)-1).getInfo());
            tags.setText(SPBrowseActivity.stacks.get(getArguments().getInt(ARG_SECTION_NUMBER)-1).getTags());

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playStack(getArguments().getInt(ARG_SECTION_NUMBER)-1,PlaceholderFragment.super.getActivity());
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editStack(getArguments().getInt(ARG_SECTION_NUMBER)-1,PlaceholderFragment.super.getActivity());
                }
            });

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    upload(getArguments().getInt(ARG_SECTION_NUMBER)-1,PlaceholderFragment.super.getActivity());
                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removestack(getArguments().getInt(ARG_SECTION_NUMBER)-1,PlaceholderFragment.super.getActivity());
                }
            });

            return rootView;
        }

    }

    private static void playStack(int stacknumber,Activity activity) {
        Intent i = new Intent(thisContext, PlayActivity.class);
        i.putExtra(HauptmenuActivity.EXTRA_STACK, stacknumber + "");
        activity.startActivityForResult(i,1);
    }

    private static void editStack(int stacknumber, Activity activity) {
        Intent i = new Intent(thisContext, EditActivity.class);
        i.putExtra(HauptmenuActivity.EXTRA_STACK, stacknumber + "");
        activity.startActivityForResult(i,1);
    }

    private static void upload(int stacknumber, Activity activity) {
        Intent i = new Intent(thisContext, UploadActivity.class);
        i.putExtra(HauptmenuActivity.EXTRA_STACK,stacknumber + "");
        activity.startActivityForResult(i,1);
    }

    private static void removestack(int stacknumber, Activity activity) {
        Intent i = new Intent(thisContext, RemoveActivity.class);
        i.putExtra(HauptmenuActivity.EXTRA_STACK,stacknumber + "");
        activity.startActivityForResult(i,1);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return stacks.size();
        }
    }

}
