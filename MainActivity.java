package materialtest.example.user.firebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

//import com.google.firebase.database.ChildEventListener;

/**
 * MainActivity of Application
 * Display a list of Jobs in RecyclerView using data from Firebase
 */

public class MainActivity extends AppCompatActivity {

    private MyAdapter myAdapter;
    private ArrayList<Job> jobArrayList = new ArrayList<>();
    //private ArrayList<String> keysArrayList = new ArrayList<>();
    private ArrayList<String> filteredLocationList = new ArrayList<>();
    private ArrayList<String> filteredJobTargetList = new ArrayList<>();
    private ValueEventListener valueEventListener;
    //private ChildEventListener[] childEventListener = new ChildEventListener[7];
    private DatabaseReference[] databaseReferences = new DatabaseReference[7];
    private FrameLayout spinner;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DrawerLayout mDrawerLayout;
    private SharedPreferences filtersharedpreferences;
    //ArrayList<String> filteredLocationList = new ArrayList<>();
    //ArrayList<String> filteredLocationList2 = new ArrayList<>();
    private boolean[] checkedValues;
    private String total; // string for textview above recyclerview
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set Up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            setSupportActionBar(toolbar);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //TextView and ImageView
        textView = (TextView) findViewById(R.id.textView);
        //imageView = (ImageView) findViewById(R.id.imageView);

        //Set Up RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);

        //Instantiate and bind adapter to recyclerview
        myAdapter = new MyAdapter(jobArrayList);
        recyclerView.setAdapter(myAdapter);

        spinner = (FrameLayout) findViewById(R.id.progressBarContainer);
        spinner.setVisibility(View.INVISIBLE);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        SharedPreferences shared = getSharedPreferences("filter", MODE_PRIVATE);
        Set<String> filter_location_set = shared.getStringSet("filterkey", null);
        Set<String> filter_job_target_set = shared.getStringSet("filterkey2", null);

        //Future Usage
        //SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        //String name = preferences.getString("nameKey", "DEFAULT");

        //retrieve filtered location set and cast it into an array list
        if (filter_location_set != null) {
            filteredLocationList = new ArrayList<>(filter_location_set);
        } else {
            // add elements to the filtered list
            filteredLocationList.add("AIR ITAM");
            filteredLocationList.add("BAYAN LEPAS");
            filteredLocationList.add("BUKIT MERTAJAM");
            filteredLocationList.add("GEORGE TOWN");
            filteredLocationList.add("MAK MANDIN AND PERMATANG PAUH");
            filteredLocationList.add("PERAI AND SEBERANG JAYA");
            filteredLocationList.add("TANJUNG BUNGAH");
        }

        //retrieve filtered job target set and cast it into an array list
        if (filter_job_target_set != null) {
            filteredJobTargetList = new ArrayList<>(filter_job_target_set);
        } else {
            // add elements to the filtered list
            filteredJobTargetList.add("isExperienced");
            filteredJobTargetList.add("isYoungAdult");
            filteredJobTargetList.add("isHighSchoolCollegeStudent");
        }

        //swipe to refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        //Navigation Drawer
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        NavigationView mDrawer = (NavigationView) findViewById(R.id.mainActivity_drawer);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //future plug
        //View header = mDrawer.getHeaderView(0);
        //TextView username = (TextView) header.findViewById(R.id.header_username);
        //username.setText(name);

        //TextView userEmail = (TextView) findViewById(R.id.header_email);
        //userEmail.setText(email);

        // start of temporary menu
        //Shared Preference to store filters
        Set<String> set = shared.getStringSet("filterkey", null);
        if (set != null) {
            filteredLocationList = new ArrayList<>(set);
        }

        Set<String> set2 = shared.getStringSet("filterkey2", null);
        if (set2 != null) {
            filteredJobTargetList = new ArrayList<>(set2);
        }

        mDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.navigation_item_1) {//filter job type
                    //mDrawerLayout.closeDrawer(GravityCompat.START);
                    selectIndustry();
                    //mDrawerLayout.closeDrawer(GravityCompat.START);
                    //Intent i = new Intent(MainActivity.this, JobFilter.class);
                    //startActivity(i);
                    return true;
                }
                if (menuItem.getItemId() == R.id.navigation_item_2) {//filter job zone
                    //mDrawerLayout.closeDrawer(GravityCompat.START);
                    selectLocation();
                    //mDrawerLayout.closeDrawer(GravityCompat.START);
                    //intent = new Intent(this, SavedJobsActivity.class);
                    //startActivity(intent);
                    return true;
                }
                if (menuItem.getItemId() == R.id.navigation_item_3) {//about us
                    //mDrawerLayout.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(MainActivity.this, AboutUs.class);
                    startActivity(intent);
                    return true;
                }
                /*future useage
                if (menuItem.getItemId() == R.id.navigation_item_4) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    //intent = new Intent(this, AboutActivity.class);
                    //startActivity(intent);
                    return true;
                }
                if (menuItem.getItemId() == R.id.navigation_item_5) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);

                    //clear login status
                    SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();

                    //clear filter
                    filtersharedpreferences = getSharedPreferences("filter", MODE_PRIVATE);
                    SharedPreferences.Editor filter_sp_editor = filtersharedpreferences.edit();
                    filter_sp_editor.clear();
                    filter_sp_editor.apply();

                    Toast.makeText(MainActivity.this, "LogOut", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, Pre.class);
                    startActivity(i);
                    finish();
                }*/

                return false;
            }
        });

        spinner.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        //display database reference's data on recyclerView
        displayData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.post_job:
                Intent i = new Intent(MainActivity.this, PostJob.class);
                startActivity(i);
                return true;
            case R.id.maps:
                Intent j = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(j);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void displayData() {

        //update textview
        total = "";
        String string = "";

        for (int i = 0; i < filteredJobTargetList.size(); i++) {
            switch (filteredJobTargetList.get(i)) {
                case "isExperienced":
                    string = "Experienced";
                    break;
                case "isYoungAdult":
                    string = "Young Adults";
                    break;
                case "isHighSchoolCollegeStudent":
                    string = "Students";
                    break;
                default:

            }
            if (i == 0){
                total = string;
            }else if (i == filteredJobTargetList.size()-1) {
                total = total + " & " + string;
            }else{
                total = total + ", " + string;
            }
        }

        for (int i = 0; i < filteredLocationList.size(); i++) {
            String filteredLocation = filteredLocationList.get(i);
            //Toast.makeText(MainActivity.this, "i is " + i + "filter is " + filteredLocation, Toast.LENGTH_SHORT).show();
            databaseReferences[i] = FirebaseDatabase.getInstance().getReference("MALAYSIA/PENANG/" + filteredLocation);

            //Query recentPostsQuery = databaseReferences[i].orderByChild("isExperienced").equalTo("false");

            //Listen Single Time
            valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Job job = postSnapshot.getValue(Job.class);

                        int condition1 = 0;
                        int condition2 = 0;
                        int condition3 = 0;

                        for (int i = 0; i < filteredJobTargetList.size(); i++) {

                            switch (filteredJobTargetList.get(i)) {
                                case "isExperienced":
                                    if (job.getIsExperienced().equals("true")) {
                                        condition1 = 1;
                                    } else {
                                        condition1 = 0;
                                    }
                                    break;
                                case "isYoungAdult":
                                    if (job.getIsYoungAdult().equals("true")) {
                                        condition2 = 1;
                                    } else {
                                        condition2 = 0;
                                    }
                                    break;
                                case "isHighSchoolCollegeStudent":
                                    if (job.getIsHighSchoolCollegeStudent().equals("true")) {
                                        condition3 = 1;
                                    } else {
                                        condition3 = 0;
                                    }
                                    break;
                                default:
                                    condition1 = 0;
                                    condition2 = 0;
                                    condition3 = 0;
                            }
                        }

                        if (condition1 + condition2 + condition3 > 0) {
                            jobArrayList.add(job);//store Job object in jobArrayList
                            Collections.sort(jobArrayList);
                            Collections.reverse(jobArrayList);
                            //keysArrayList.add(postSnapshot.getKey());//store key in keysArrayList
                            //Collections.sort(keysArrayList);
                            //Collections.reverse(keysArrayList);
                            //myAdapter = new MyAdapter(jobArrayList);//Instantiate Adapter Class
                            myAdapter.notifyDataSetChanged();
                            //recyclerView.setAdapter(myAdapter);//Bind adapter to RecyclerView
                            spinner.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            mSwipeRefreshLayout.setRefreshing(false);

                            //update textView
                            textView.setText(myAdapter.getItemCount() + " jobs shared for\n" + total);
                            //imageView.setImageResource(R.drawable.logo);
                            textView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.secondary));
                            //imageView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.secondary));
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    // ...
                }
            };

            databaseReferences[i].addListenerForSingleValueEvent(valueEventListener);
            //recentPostsQuery.addListenerForSingleValueEvent(valueEventListener);
/*
            //child event
            childEventListener[i] = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //Toast.makeText(MainActivity.this, "loop", Toast.LENGTH_SHORT).show();
                    Job job = dataSnapshot.getValue(Job.class);
                    jobArrayList.add(job);//store Job object in jobArrayList
                    Collections.sort(jobArrayList);
                    Collections.reverse(jobArrayList);
                    keysArrayList.add(dataSnapshot.getKey());//store key in keysArrayList
                    Collections.sort(keysArrayList);
                    Collections.reverse(keysArrayList);
                    //myAdapter = new MyAdapter(jobArrayList);//Instantiate Adapter Class
                    myAdapter.notifyDataSetChanged();
                    //recyclerView.setAdapter(myAdapter);//Bind adapter to RecyclerView
                    spinner.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    String changedKey = dataSnapshot.getKey();
                    int changedIndex = keysArrayList.indexOf(changedKey);
                    if (changedIndex != -1) {
                        Job job = dataSnapshot.getValue(Job.class);
                        jobArrayList.set(changedIndex, job);
                        myAdapter.notifyDataSetChanged();
                        //myAdapter = new MyAdapter(jobArrayList);
                        //recyclerView.setAdapter(myAdapter);
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    String deletedKey = dataSnapshot.getKey();
                    int removedIndex = keysArrayList.indexOf(deletedKey);
                    if (removedIndex != -1) {
                        keysArrayList.remove(removedIndex);
                        jobArrayList.remove(removedIndex);
                        myAdapter.notifyDataSetChanged();
                        //myAdapter = new MyAdapter(jobArrayList);
                        //recyclerView.setAdapter(myAdapter);
                    }
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            if (childEventListener == null) {
                Toast.makeText(MainActivity.this, "null", Toast.LENGTH_SHORT).show();
            }
            databaseReferences[i].addChildEventListener(childEventListener[i]);
*/
        }
    }

    public void refresh() {

        spinner.setVisibility(View.VISIBLE);

        for (DatabaseReference databaseReference : databaseReferences) {
            if (databaseReference != null) {
                databaseReference.removeEventListener(valueEventListener);
            }
        }

        jobArrayList.clear();
        //myAdapter.notifyDataSetChanged();

        //keysArrayList.clear();
        displayData();
    }

    //TOBE removed in future
    public void selectIndustry() {

        final CharSequence[] items = {
                "Experienced",
                "Young Adults",
                "High School/College Students"
        };

        //filter ticked box
        boolean[] checkedValues = new boolean[7];

        //pre-ticked those selected filters
        //pre-selected those selected filters
        final ArrayList<Integer> selectedLocations2 = new ArrayList<>();

        for (int i = 0; i < filteredJobTargetList.size(); i++) {

            switch (filteredJobTargetList.get(i)) {
                case "isExperienced":
                    if (checkedValues != null) {
                        checkedValues[0] = true;//add pre-filter
                    }
                    selectedLocations2.add(0);//add pre-filter into selected
                    break;
                case "isYoungAdult":
                    if (checkedValues != null) {
                        checkedValues[1] = true;
                    }
                    selectedLocations2.add(1);
                    break;
                case "isHighSchoolCollegeStudent":
                    if (checkedValues != null) {
                        checkedValues[2] = true;
                    }
                    selectedLocations2.add(2);
                    break;
                default:
                    checkedValues = null;
            }
        }

        //show dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this/*, R.style.MyAlertDialogStyle*/);
        builder.setTitle("Select Who You Are");
        builder.setMultiChoiceItems(items, checkedValues,
                new DialogInterface.OnMultiChoiceClickListener() {
                    // indexSelected contains the index of item (of which checkbox checked)
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected,
                                        boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectedLocations2.add(indexSelected);
                        } else if (selectedLocations2.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            selectedLocations2.remove(Integer.valueOf(indexSelected));
                        }
                    }
                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //overriding this to prevent dialog from closing if no filter is selected
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = false;

                if (selectedLocations2.size() == 0) {
                    Toast.makeText(MainActivity.this, "Please select filer(s)", Toast.LENGTH_SHORT).show();

                } else {

                    filteredJobTargetList.clear();
                    //  Your code when user clicked on OK
                    for (int i = 0; i < selectedLocations2.size(); i++) {
                        String item = (String) items[selectedLocations2.get(i)];

                        switch (item) {
                            case "Experienced":
                                item = "isExperienced";
                                break;
                            case "Young Adults":
                                item = "isYoungAdult";
                                break;
                            case "High School/College Students":
                                item = "isHighSchoolCollegeStudent";
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid filter selected: " + item);
                        }
                        //Toast.makeText(MainActivity.this, "selected item is " + item, Toast.LENGTH_SHORT).show();
                        filteredJobTargetList.add(item);
                    }

                    //location.setText(all_location2);
                    wantToCloseDialog = true;
                }
                //Do stuff, possibly set wantToCloseDialog to true then...
                if (wantToCloseDialog)
                    dialog.dismiss();
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.

                jobArrayList.clear();
                update();

                spinner.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                displayData();
            }
        });
    }

    //Filter location
    public void selectLocation() {

        final CharSequence[] items = {
                "Penang, Air Itam",
                "Penang, Bayan Lepas",
                "Penang, Bukit Mertajam",
                "Penang, George Town",
                "Penang, Mak Mandin And Permatang Pauh",
                "Penang, Perai And Seberang Jaya",
                "Penang, Tanjung Bungah"
        };

        //filter ticked box
        checkedValues = new boolean[7];

        //pre-ticked those selected filters
        //pre-selected those selected filters
        final ArrayList<Integer> selectedLocations = new ArrayList<>();

        for (int i = 0; i < filteredLocationList.size(); i++) {
            String item = filteredLocationList.get(i);

            switch (item) {
                case "AIR ITAM":
                    checkedValues[0] = true;
                    selectedLocations.add(0);
                    break;
                case "BAYAN LEPAS":
                    checkedValues[1] = true;
                    selectedLocations.add(1);
                    break;
                case "BUKIT MERTAJAM":
                    checkedValues[2] = true;
                    selectedLocations.add(2);
                    break;
                case "GEORGE TOWN":
                    checkedValues[3] = true;
                    selectedLocations.add(3);
                    break;
                case "MAK MANDIN AND PERMATANG PAUH":
                    checkedValues[4] = true;
                    selectedLocations.add(4);
                    break;
                case "PERAI AND SEBERANG JAYA":
                    checkedValues[5] = true;
                    selectedLocations.add(5);
                    break;
                case "TANJUNG BUNGAH":
                    checkedValues[6] = true;
                    selectedLocations.add(6);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid filter selected: " + item);
            }
        }

        //show dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this/*, R.style.MyAlertDialogStyle*/);
        builder.setTitle("Select zone to filter");
        builder.setMultiChoiceItems(items, checkedValues,
                new DialogInterface.OnMultiChoiceClickListener() {
                    // indexSelected contains the index of item (of which checkbox checked)
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected,
                                        boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectedLocations.add(indexSelected);
                        } else if (selectedLocations.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            selectedLocations.remove(Integer.valueOf(indexSelected));
                        }
                    }
                })
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //overriding this to prevent dialog from closing if no filter is selected
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = false;

                if (selectedLocations.size() == 0) {
                    Toast.makeText(MainActivity.this, "Please select filer(s)", Toast.LENGTH_SHORT).show();

                } else {

                    filteredLocationList.clear();
                    //  Your code when user clicked on OK
                    for (int i = 0; i < selectedLocations.size(); i++) {
                        String item = (String) items[selectedLocations.get(i)];

                        switch (item) {
                            case "Penang, Air Itam":
                                item = "AIR ITAM";
                                break;
                            case "Penang, Bayan Lepas":
                                item = "BAYAN LEPAS";
                                break;
                            case "Penang, Bukit Mertajam":
                                item = "BUKIT MERTAJAM";
                                break;
                            case "Penang, George Town":
                                item = "GEORGE TOWN";
                                break;
                            case "Penang, Mak Mandin And Permatang Pauh":
                                item = "MAK MANDIN AND PERMATANG PAUH";
                                break;
                            case "Penang, Perai And Seberang Jaya":
                                item = "PERAI AND SEBERANG JAYA";
                                break;
                            case "Penang, Tanjung Bungah":
                                item = "TANJUNG BUNGAH";
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid filter selected: " + item);
                        }
                        //Toast.makeText(MainActivity.this, "selected item is " + item, Toast.LENGTH_SHORT).show();
                        filteredLocationList.add(item);
                    }

                    wantToCloseDialog = true;
                }
                //Do stuff, possibly set wantToCloseDialog to true then...
                if (wantToCloseDialog)
                    dialog.dismiss();
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.

                jobArrayList.clear();

                update();
                spinner.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                displayData();
            }
        });
    }

    public void update() {
        SharedPreferences sharedpreferences = getSharedPreferences("filter", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        //convert your List into a HashSet
        Set<String> set = new HashSet<>();
        set.addAll(filteredLocationList);
        editor.putStringSet("filterkey", set);

        Set <String> set2= new HashSet<>();
        set2.addAll(filteredJobTargetList);
        editor.putStringSet("filterkey2", set2);
        editor.apply();
    }
}