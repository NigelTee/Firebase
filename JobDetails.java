package materialtest.example.user.firebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JobDetails of Application
 * Display a list of Jobs Details using data from Firebase
 */

public class JobDetails extends AppCompatActivity{

    String jobTitle;
    String jobCompany;
    String jobPay;
    String jobBenefits;
    String jobDate;
    String jobTime;
    String jobVenue;
    String jobResponsibilities;
    String jobTimeBreak;
    String jobType;
    String jobStatus;
    String jobGender;
    String jobRequirement;
    String jobClosingDate;
    String jobAddedBy;
    String jobAddedAt;
    String jobContactPerson;
    String jobContactNumber;
    String jobContactEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.job_details);

        //Set Up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.job_details_toolbar);
        setSupportActionBar(toolbar);

        TextView tv_jobTitle = (TextView) findViewById(R.id.job_details_tv_jobTitle);
        TextView tv_jobCompany = (TextView) findViewById(R.id.job_details_tv_jobCompany);
        TextView tv_jobType = (TextView) findViewById(R.id.job_details_tv_jobType);
        TextView tv_jobPay = (TextView) findViewById(R.id.job_details_tv_jobPay);
        TextView tv_jobBenefits = (TextView) findViewById(R.id.job_details_tv_jobBenefits);
        TextView tv_jobTime = (TextView) findViewById(R.id.job_details_tv_jobTime);
        TextView tv_jobDate = (TextView) findViewById(R.id.job_details_tv_jobDate);
        TextView tv_jobVenue = (TextView) findViewById(R.id.job_details_tv_jobVenue);
        TextView tv_jobResponsibilities = (TextView) findViewById(R.id.job_details_tv_jobResponsibilities);
        TextView tv_jobGender = (TextView) findViewById(R.id.job_details_tv_jobGender);
        TextView tv_jobRequirement = (TextView) findViewById(R.id.job_details_tv_jobRequirement);
        TextView tv_jobAddedAt = (TextView) findViewById(R.id.job_details_tv_jobAddedAt);
        //TextView tv_jobContactPerson = (TextView) findViewById(R.id.job_details_tv_jobContactPerson);

        Bundle bundle = getIntent().getExtras();

        //modify company
        String modified_company_name = bundle.getString("jobCompany");
        modified_company_name = "By: " + modified_company_name;

        //modify date
        final String OLD_FORMAT = "yyyyMMddHHmmss";
        final String NEW_FORMAT = "dd/MM/yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = null;
        try {
            d = sdf.parse(bundle.getString("jobAddedAt"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern(NEW_FORMAT);
        String modified_date = bundle.getString("jobContactPerson") + " shared on " + sdf.format(d);

        tv_jobTitle.setText(bundle.getString("jobTitle"));
        tv_jobCompany.setText(modified_company_name);
        tv_jobType.setText(bundle.getString("jobType"));
        tv_jobPay.setText(bundle.getString("jobPay"));
        tv_jobBenefits.setText(bundle.getString("jobBenefits"));
        tv_jobTime.setText(bundle.getString("jobTime"));
        tv_jobDate.setText(bundle.getString("jobDate"));
        tv_jobVenue.setText(bundle.getString("jobVenue"));
        tv_jobResponsibilities.setText(bundle.getString("jobResponsibilities"));
        tv_jobGender.setText(bundle.getString("jobGender"));
        tv_jobRequirement.setText(bundle.getString("jobRequirement"));
        tv_jobAddedAt.setText(modified_date);
        //tv_jobContactPerson.setText("Contact "+bundle.getString("jobContactPerson")+" via");
        jobContactNumber = bundle.getString("jobContactNumber");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void call(View view) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+ jobContactNumber));
        startActivity(callIntent);
    }

    public void text(View view) {
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + jobContactNumber));
        startActivity(sendIntent);
    }
}
