package materialtest.example.user.firebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PostJob extends AppCompatActivity {

    private EditText jobCompany;
    private EditText jobTitle;
    private EditText jobResponsibilities;
    private EditText jobPay;
    private EditText jobBenefits;
    private EditText jobVenue;
    private EditText jobDate;
    private EditText jobTime;
    private EditText jobRequirement;
    private EditText jobContactPerson;
    private EditText jobContactNumber;
    //private EditText jobContactEmail;
    private TextView selectJobType;
    private TextView selectIndustry;
    private TextView selectZone;
    private TextView selectJobGender;
    private TextView selectJobTarget;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_job);

        //hide keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Initialize Edit Texts
        jobCompany = (EditText) findViewById(R.id.et_jobCompany);
        jobTitle = (EditText) findViewById(R.id.et_jobTitle);
        jobResponsibilities = (EditText) findViewById(R.id.et_jobResponsibilities);
        jobPay = (EditText) findViewById(R.id.et_jobPay);
        jobBenefits = (EditText) findViewById(R.id.et_jobBenefits);
        jobVenue = (EditText) findViewById(R.id.et_jobVenue);
        jobDate = (EditText) findViewById(R.id.et_jobDate);
        jobTime = (EditText) findViewById(R.id.et_jobTime);
        jobRequirement = (EditText) findViewById(R.id.et_jobRequirement);
        jobContactPerson = (EditText) findViewById(R.id.et_jobContactPerson);
        jobContactNumber = (EditText) findViewById(R.id.et_jobContactNumber);
        //jobContactEmail = (EditText) findViewById(R.id.et_jobContactEmail);
        selectJobType = (TextView) findViewById(R.id.tv_selectJobType);
        selectJobTarget = (TextView) findViewById(R.id.tv_selectJobTarget);
        selectIndustry = (TextView) findViewById(R.id.tv_selectIndustry);
        selectZone = (TextView) findViewById(R.id.tv_selectZone);
        selectJobGender = (TextView) findViewById(R.id.tv_selectJobGender);

        //Set Up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.post_job_toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public void preview(View view) {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        String setJobType = selectJobType.getText().toString();//from dialog
        String setJobTarget = selectJobTarget.getText().toString();//from dialog
        String setJobIndustry = selectIndustry.getText().toString();//from dialog
        String setJobCompany = jobCompany.getText().toString();
        String setJobTitle = jobTitle.getText().toString();
        String setJobResponsibilities = jobResponsibilities.getText().toString();
        String setJobPay = jobPay.getText().toString();
        String setJobBenefits = jobBenefits.getText().toString();
        String setJobZone = selectZone.getText().toString();// from dialog
        String setJobVenue = jobVenue.getText().toString();
        String setJobDate = jobDate.getText().toString();
        String setJobTime = jobTime.getText().toString();
        String setJobRequirement = jobRequirement.getText().toString();
        String setJobGender = selectJobGender.getText().toString();// from dialog
        String setJobContactPerson = jobContactPerson.getText().toString();
        String setJobContactNumber = jobContactNumber.getText().toString();
        //String setJobContactEmail = jobContactEmail.getText().toString();
        String setJobAddedAt = sdf.format(cal.getTime());

        //Bundle
        Bundle bundle = new Bundle();
        bundle.putString("jobType", setJobType);
        bundle.putString("jobTarget", setJobTarget);
        bundle.putString("jobIndustry", setJobIndustry);
        bundle.putString("jobCompany", setJobCompany);
        bundle.putString("jobTitle", setJobTitle);
        bundle.putString("jobResponsibilities", setJobResponsibilities);
        bundle.putString("jobPay", setJobPay);
        bundle.putString("jobBenefits", setJobBenefits);
        bundle.putString("jobZone", setJobZone);
        bundle.putString("jobVenue", setJobVenue);
        bundle.putString("jobDate", setJobDate);
        bundle.putString("jobTime", setJobTime);
        bundle.putString("jobRequirement", setJobRequirement);
        bundle.putString("jobGender", setJobGender);
        bundle.putString("jobContactPerson", setJobContactPerson);
        bundle.putString("jobContactNumber", setJobContactNumber);
        bundle.putString("jobAddedAt", setJobAddedAt);

        //VALIDATE
        if (setJobType.length() > 0 && setJobTarget.length() > 0 && setJobIndustry.length() > 0 && setJobIndustry.length() > 0 && setJobCompany.length() > 0
                && setJobTitle.length() > 0 && setJobResponsibilities.length() > 0 && setJobPay.length() > 0 && setJobBenefits.length() > 0
                && setJobZone.length() > 0 && setJobVenue.length() > 0 && setJobDate.length() > 0 && setJobTime.length() > 0
                && setJobRequirement.length() > 0 && setJobContactPerson.length() > 0 && setJobContactNumber.length() > 0
                && setJobAddedAt.length() > 0) {//temp removed email

            Intent i = new Intent(this, PreviewJob.class);
            i.putExtras(bundle);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

        } else {
            Toast.makeText(this, "Please complete the form", Toast.LENGTH_SHORT).show();
        }
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
                selectJobType.setText(items[selected]);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void selectJobTarget(View view) {

        final CharSequence[] items = {
                "Experienced",
                "Young Adults",
                "High School/College Students"
        };

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");
        alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selected) {
                selectJobTarget.setText(items[selected]);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void selectIndustry(View view) {

        final CharSequence[] items = {
                "Food & Beverages",
                "Accommodation & Hospitality",
                "Others",
                "Transportation & Logistics",
                "Health Care",
                "Manufacturing & Production",
                "Entertainment",
                "Construction & Development",
                "Sales & Marketing",
                "Fashion Retail",
                "Finance & Insurance",
                "Retail",
                "Technology",
                "Education",
                "Business & Management"
        };

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");
        alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selected) {
                selectIndustry.setText(items[selected]);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void selectZone(View view) {

        final CharSequence[] items = {
                "George Town Zone",
                "Bayan Lepas Zone",
                "Air Itam Zone",
                "Tanjung Bungah Zone",
                "Perai and Seberang Jaya Zone",
                "Bukit Mertajam Zone",
                "Mak Mandin and Permatang Pauh Zone"
        };

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");
        alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selected) {
                selectZone.setText(items[selected]);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void selectGender(View view) {

        final CharSequence[] items = {
                "Male Only", "Female Only", "Both Male and Female"
        };

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");
        alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int selected) {
                selectJobGender.setText(items[selected]);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
