package materialtest.example.user.firebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 10/31/2016.
 */

public class Pre extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Name = "nameKey";
    //public static final String Location = "locationKey";
    public static final String First = "firstKey";
    // ed1, ed2;
    Button b1;
    Intent in;
    ArrayList<String> filteredLocationList = new ArrayList<>();
    ArrayList<String> filteredLocationList2 = new ArrayList<>();
    private SharedPreferences filtersharedpreferences;
    private String all_location;
    private String all_location2;
    private SharedPreferences shared;
    private TextView selectjobzone;
    private TextView selectidentity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre);

        //Shared preferences
        shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        filtersharedpreferences = getSharedPreferences("filter", MODE_PRIVATE);

        //check if logged in before
        Boolean logged = shared.getBoolean(First, false);

        if (logged) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(this, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            startActivity(i);
            finish();
        }

        //ed1 = (EditText) findViewById(R.id.editText);
        b1 = (Button) findViewById(R.id.button);
        selectjobzone = (TextView) findViewById(R.id.tv_selectZone);
        selectidentity = (TextView) findViewById(R.id.tv_selectIdentity);

        filteredLocationList.add("AIR ITAM");
        filteredLocationList.add("BAYAN LEPAS");
        filteredLocationList.add("BUKIT MERTAJAM");
        filteredLocationList.add("GEORGE TOWN");
        filteredLocationList.add("MAK MANDIN AND PERMATANG PAUH");
        filteredLocationList.add("PERAI AND SEBERANG JAYA");
        filteredLocationList.add("TANJUNG BUNGAH");

        filteredLocationList2.add("isExperienced");
        filteredLocationList2.add("isYoungAdult");
        filteredLocationList2.add("isHighSchoolCollegeStudent");

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //String n = ed1.getText().toString();
                //String ph = ed2.getText().toString();

                //if(filteredLocationList.size() == 0 || n.equals("")){
                if (filteredLocationList.size() == 0 || filteredLocationList2.size() == 0) {
                    Toast.makeText(Pre.this, "Please select filer(s)", Toast.LENGTH_SHORT).show();
                } else {

                    SharedPreferences.Editor editor = shared.edit();

                    //editor.putString(Name, n);
                    //editor.putString(Location, ph);

                    //indicates first time log in
                    editor.putBoolean(First, true);
                    editor.apply();

                    SharedPreferences.Editor filter_sp_editor = filtersharedpreferences.edit();

                    //convert your List into a HashSet
                    Set<String> filter_location_set = new HashSet<>();
                    filter_location_set.addAll(filteredLocationList);
                    filter_sp_editor.putStringSet("filterkey", filter_location_set);

                    Set<String> filter_job_target_set = new HashSet<>();
                    filter_job_target_set.addAll(filteredLocationList2);
                    filter_sp_editor.putStringSet("filterkey2", filter_job_target_set);

                    filter_sp_editor.apply();

                    in = new Intent(Pre.this, MainActivity.class);
                    startActivity(in);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void selectZone1(View view) {

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
        boolean[] checkedValues = new boolean[7];

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
                    Toast.makeText(Pre.this, "Please select filer(s)", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(Pre.this, "selected item is " + item, Toast.LENGTH_SHORT).show();
                        filteredLocationList.add(item);
                    }

                    if (filteredLocationList.size() < 7) {
                        all_location = filteredLocationList.size() + " location(s) selected";
                    } else {
                        all_location = "all locations selected";
                    }
                    selectjobzone.setText(all_location);

                    //location.setText(all_location);
                    wantToCloseDialog = true;
                }
                //Do stuff, possibly set wantToCloseDialog to true then...
                if (wantToCloseDialog)
                    dialog.dismiss();
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });
    }

    //select who you are
    public void selectIdentity(View view) {

        final CharSequence[] items = {
                "Experienced",
                "Young Adults",
                "High School/College Students"
        };

        //filter ticked box
        boolean[] checkedValues2 = new boolean[7];

        //pre-ticked those selected filters
        //pre-selected those selected filters
        final ArrayList<Integer> selectedLocations2 = new ArrayList<>();

        for (int i = 0; i < filteredLocationList2.size(); i++) {

            switch (filteredLocationList2.get(i)) {
                case "isExperienced":
                    checkedValues2[0] = true;//add pre-filter
                    selectedLocations2.add(0);//add pre-filter into selected
                    break;
                case "isYoungAdult":
                    checkedValues2[1] = true;
                    selectedLocations2.add(1);
                    break;
                case "isHighSchoolCollegeStudent":
                    checkedValues2[2] = true;
                    selectedLocations2.add(2);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid filter selected: " + filteredLocationList2.get(i));
            }
        }

        //show dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this/*, R.style.MyAlertDialogStyle*/);
        builder.setTitle("Select Who You Are");
        builder.setMultiChoiceItems(items, checkedValues2,
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
                    Toast.makeText(Pre.this, "Please select filer(s)", Toast.LENGTH_SHORT).show();

                } else {

                    filteredLocationList2.clear();
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
                        Toast.makeText(Pre.this, "selected item is " + item, Toast.LENGTH_SHORT).show();
                        filteredLocationList2.add(item);
                    }

                    if (filteredLocationList2.size() < 3) {
                        all_location2 = filteredLocationList2.size() + "selected";
                    } else {
                        all_location2 = "all selected";
                    }
                    selectidentity.setText(all_location2);

                    //location.setText(all_location2);
                    wantToCloseDialog = true;
                }
                //Do stuff, possibly set wantToCloseDialog to true then...
                if (wantToCloseDialog)
                    dialog.dismiss();
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });
    }
}
