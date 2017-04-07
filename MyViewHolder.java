package materialtest.example.user.firebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * View Holder class to initialize textViews from custom row
 */

class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ArrayList<Job> jobArrayList;
    private Context context;
    TextView jobTitle;
    TextView jobCompany;
    //TextView jobDate;
    TextView jobPay;
    TextView jobZone;
    TextView jobAddedAt;
    TextView jobType;

    MyViewHolder(View itemView, ArrayList<Job> jobArrayList, Context context) {
        super(itemView);
        jobTitle = (TextView) itemView.findViewById(R.id.custom_row_card_tv_jobTitle);
        jobCompany = (TextView) itemView.findViewById(R.id.custom_row_card_tv_jobCompany);
        //jobDate = (TextView) itemView.findViewById(R.id.tv_jobDate);
        jobPay = (TextView) itemView.findViewById(R.id.custom_row_card_tv_jobPay);
        jobZone = (TextView) itemView.findViewById(R.id.custom_row_card_tv_jobZone);
        jobAddedAt = (TextView) itemView.findViewById(R.id.custom_row_card_tv_jobAddedAt);
        jobType = (TextView)itemView.findViewById(R.id.custom_row_card_tv_jobType);

        // Attach a click listener to the entire row view
        itemView.setOnClickListener(this);
        this.jobArrayList = jobArrayList;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        int position = getLayoutPosition(); // gets item position
        Job job = jobArrayList.get(position);

        //get all job details
        String jobTitle =job.getJobTitle();
        String jobCompany =job.getJobCompany();
        String jobPay =job.getJobPay();
        String jobBenefits =job.getJobBenefits();
        String jobDate =job.getJobDate();
        String jobTime =job.getJobTime();
        String jobVenue =job.getJobVenue();
        String jobResponsibilities =job.getJobResponsibilities();
        //String jobTimeBreak =job.getJobTimeBreak();
        String jobType =job.getJobType();
        //String jobStatus=job.getJobStatus();
        String jobGender =job.getJobGender();
        String jobRequirement =job.getJobRequirement();
        //String jobClosingDate =job.getJobClosingDate();
        //String jobAddedBy =job.getJobAddedBy();
        String jobAddedAt =job.getJobAddedAt();
        String jobContactPerson =job.getJobContactPerson();
        String jobContactNumber =job.getJobContactNumber();
        //String jobContactEmail =job.getJobContactEmail();

        //create a bundle object to store the values
        Bundle bundle = new Bundle();
        bundle.putString("jobTitle", jobTitle);
        bundle.putString("jobCompany", jobCompany);
        bundle.putString("jobPay", jobPay);
        bundle.putString("jobBenefits", jobBenefits);
        bundle.putString("jobDate", jobDate);
        bundle.putString("jobTime", jobTime);
        bundle.putString("jobVenue", jobVenue);
        bundle.putString("jobResponsibilities", jobResponsibilities);
        //bundle.putString("jobTimeBreak", jobTimeBreak);
        bundle.putString("jobType", jobType);
        //bundle.putString("jobStatus", jobStatus);
        bundle.putString("jobGender", jobGender);
        bundle.putString("jobRequirement", jobRequirement);
        //bundle.putString("jobClosingDate", jobClosingDate);
        //bundle.putString("jobAddedBy", jobAddedBy);
        bundle.putString("jobAddedAt", jobAddedAt);
        bundle.putString("jobContactPerson", jobContactPerson);
        bundle.putString("jobContactNumber", jobContactNumber);
        //bundle.putString("jobContactEmail", jobContactEmail);

        //Start JobDetails class
        //Intent intent = new Intent(context, JobDetails2.class);
        Intent intent = new Intent(context, JobDetails.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }
}
