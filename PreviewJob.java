package materialtest.example.user.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PreviewJob extends AppCompatActivity {

    String jobType;
    String jobTarget;
    String jobIndustry;
    String jobCompany;
    String jobTitle;
    String jobResponsibilities;
    String jobPay;
    String jobBenefits;
    String jobZone;
    String jobVenue;
    String jobDate;
    String jobTime;
    String jobGender;
    String jobRequirement;
    String jobContactPerson;
    String jobContactNumber;
    String jobAddedAt;

    String jobZoneEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_preview);

        //Set Up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.job_details_toolbar);
        setSupportActionBar(toolbar);

        TextView tv_jobTitle = (TextView) findViewById(R.id.job_details_tv_jobTitle);
        TextView tv_jobCompany = (TextView) findViewById(R.id.job_details_tv_jobCompany);
        TextView tv_jobType = (TextView) findViewById(R.id.job_details_tv_jobType);
        //TextView tv_jobTarget = (TextView) findViewById(R.id.job_details_tv_jobTarget);
        TextView tv_jobPay = (TextView) findViewById(R.id.job_details_tv_jobPay);
        TextView tv_jobBenefits = (TextView) findViewById(R.id.job_details_tv_jobBenefits);
        TextView tv_jobTime = (TextView) findViewById(R.id.job_details_tv_jobTime);
        TextView tv_jobDate = (TextView) findViewById(R.id.job_details_tv_jobDate);
        TextView tv_jobVenue = (TextView) findViewById(R.id.job_details_tv_jobVenue);
        TextView tv_jobResponsibilities = (TextView) findViewById(R.id.job_details_tv_jobResponsibilities);
        TextView tv_jobGender = (TextView) findViewById(R.id.job_details_tv_jobGender);
        TextView tv_jobRequirement = (TextView) findViewById(R.id.job_details_tv_jobRequirement);
        TextView tv_jobAddedAt = (TextView) findViewById(R.id.job_details_tv_jobAddedAt);

        Bundle bundle = getIntent().getExtras();

        //SET DATA
        jobType = bundle.getString("jobType");
        jobTarget = bundle.getString("jobTarget");
        jobIndustry = bundle.getString("jobIndustry");
        jobCompany = bundle.getString("jobCompany");
        jobTitle = bundle.getString("jobTitle");
        jobResponsibilities = bundle.getString("jobResponsibilities");
        jobPay = bundle.getString("jobPay");
        jobBenefits = bundle.getString("jobBenefits");
        jobZone = bundle.getString("jobZone");
        jobVenue = bundle.getString("jobVenue");
        jobDate = bundle.getString("jobDate");
        jobTime = bundle.getString("jobTime");
        jobRequirement = bundle.getString("jobRequirement");
        jobGender = bundle.getString("jobGender");
        jobContactPerson = bundle.getString("jobContactPerson");
        jobContactNumber = bundle.getString("jobContactNumber");
        jobAddedAt = bundle.getString("jobAddedAt");

        //Change Job Zone (tag with "Penang, " and remove "zone")
        jobZoneEdited = "Penang, " + jobZone.replace(" Zone", "");

        tv_jobTitle.setText(jobTitle);
        tv_jobCompany.setText(jobCompany);
        tv_jobType.setText(jobType);
        //tv_jobTarget.setText(jobTarget);
        tv_jobPay.setText(jobPay);
        tv_jobBenefits.setText(jobBenefits);
        tv_jobTime.setText(jobTime);
        tv_jobDate.setText(jobDate);
        tv_jobVenue.setText(jobVenue);
        tv_jobResponsibilities.setText(jobResponsibilities);
        tv_jobGender.setText(jobGender);
        tv_jobRequirement.setText(jobRequirement);
        tv_jobAddedAt.setText(jobAddedAt);

        //Toast.makeText(this, jobTarget, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void post(View view) {

        Job job = new Job();

        //SET DATA

        switch (jobTarget) {
            case "Experienced":
                job.setIsExperienced("true");
                job.setIsHighSchoolCollegeStudent("false");
                job.setIsYoungAdult("false");
                break;

            case "Young Adults":
                job.setIsExperienced("false");
                job.setIsHighSchoolCollegeStudent("false");
                job.setIsYoungAdult("true");
                break;

            case "High School/College Students":
                job.setIsExperienced("false");
                job.setIsHighSchoolCollegeStudent("true");
                job.setIsYoungAdult("false");
                break;

            default:
                job.setIsExperienced("false");
                job.setIsHighSchoolCollegeStudent("false");
                job.setIsYoungAdult("false");
        }

        job.setJobType(jobType);
        job.setJobIndustry(jobIndustry);
        job.setJobCompany(jobCompany);
        job.setJobTitle(jobTitle);
        job.setJobResponsibilities(jobResponsibilities);
        job.setJobPay(jobPay);
        job.setJobBenefits(jobBenefits);
        job.setJobZone(jobZoneEdited);
        job.setJobVenue(jobVenue);
        job.setJobDate(jobDate);
        job.setJobTime(jobTime);
        job.setJobRequirement(jobRequirement);
        job.setJobGender(jobGender);
        job.setJobContactPerson(jobContactPerson);
        job.setJobContactNumber(jobContactNumber);
        job.setJobAddedAt(jobAddedAt);

        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MALAYSIA/PENANG");
            jobZone = jobZone.replace(" Zone", "");//remove "zone"
            jobZone = jobZone.toUpperCase();//to upper case
            databaseReference.child(jobZone).push().setValue(job);
            Toast.makeText(this, "posted", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
