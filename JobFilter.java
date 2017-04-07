package materialtest.example.user.firebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class JobFilter extends AppCompatActivity {

    TextView type;
    TextView location;
    String all_location;
    ArrayList<String> filteredLocationList = new ArrayList<>();
    private boolean[] checkedValues;
    ArrayList<String> filteredLocationList2 = new ArrayList<>();
    String all_location2;
    TextView selectidentity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.job_filter);

        //Set Up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.job_filter_toolbar);
        setSupportActionBar(toolbar);

        type = (TextView) findViewById(R.id.job_filter_tv_job_type_selected);
        location = (TextView) findViewById(R.id.job_filter_tv_job_location_selected);
        selectidentity = (TextView) findViewById(R.id.job_filter_tv_job_industry_selected);

        //Shared Preference to store filters
        SharedPreferences shared = getSharedPreferences("filter", MODE_PRIVATE);
        Set<String> set = shared.getStringSet("filterkey", null);
        if (set != null) {
            filteredLocationList = new ArrayList<>(set);
        }

        Set<String> set2 = shared.getStringSet("filterkey2", null);
        if (set2 != null) {
            filteredLocationList2 = new ArrayList<>(set2);
        }

        type.setText("ALL");

        if (filteredLocationList.size() < 7) {
            all_location = filteredLocationList.size() + " location(s) selected";
        } else {
            all_location = "all locations selected";
        }
        location.setText(all_location);

        if (filteredLocationList2.size() < 3) {
            all_location2 = filteredLocationList2.size() + " selected";
        } else {
            all_location2 = "all selected";
        }
        selectidentity.setText(all_location2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void selectJobType(View view) {

        final CharSequence[] items = {
                "Full Time", "Part Time", "Freelance"
        };

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");
        alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selected) {
                type.setText(items[selected]);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void selectIndustry(View view) {

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

        for (int i = 0; i < filteredLocationList2.size(); i++) {

            switch (filteredLocationList2.get(i)) {
                case "isExperienced":
                    checkedValues[0] = true;//add pre-filter
                    selectedLocations2.add(0);//add pre-filter into selected
                    break;
                case "isYoungAdult":
                    checkedValues[1] = true;
                    selectedLocations2.add(1);
                    break;
                case "isHighSchoolCollegeStudent":
                    checkedValues[2] = true;
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
                    Toast.makeText(JobFilter.this, "Please select filer(s)", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(JobFilter.this, "selected item is " + item, Toast.LENGTH_SHORT).show();
                        filteredLocationList2.add(item);
                    }

                    if (filteredLocationList2.size() < 7) {
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

    //Filter location
    public void selectLocation(View view) {

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
                    Toast.makeText(JobFilter.this, "Please select filer(s)", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(JobFilter.this, "selected item is " + item, Toast.LENGTH_SHORT).show();
                        filteredLocationList.add(item);
                    }

                    if (filteredLocationList.size() < 7) {
                        all_location = filteredLocationList.size() + " location(s) selected";
                    } else {
                        all_location = "all locations selected";
                    }

                    location.setText(all_location);
                    wantToCloseDialog = true;
                }
                //Do stuff, possibly set wantToCloseDialog to true then...
                if (wantToCloseDialog)
                    dialog.dismiss();
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });
    }

    //update filters
    public void update(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences("filter", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        //convert your List into a HashSet
        Set<String> set = new HashSet<>();
        set.addAll(filteredLocationList);
        editor.putStringSet("filterkey", set);

        Set <String> set2= new HashSet<>();
        set2.addAll(filteredLocationList2);
        editor.putStringSet("filterkey2", set2);

        editor.apply();

        Intent in = new Intent(this, MainActivity.class);
        // Closing all the Activities
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
        finish();
    }

    public void reset(View view) {
    }
}
