package materialtest.example.user.firebase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Adapter class to help in binding view of custom row to recycler view
 * Strings of Job info is read and displayed on the custom row textView
 */

class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private ArrayList<Job> jobArrayList;

    //Constructor
    MyAdapter(ArrayList<Job> jobArrayList) {
        this.jobArrayList = jobArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.custom_row_card,parent,false);
        return new MyViewHolder(view, jobArrayList, context);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Job job = jobArrayList.get(position);

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cal = Calendar.getInstance();
        String localtime = sdf.format(cal.getTime());

        Date d1;
        Date d2;
        String date = null;

        try {
            d1 = sdf.parse(localtime);
            d2 = sdf.parse(job.getJobAddedAt());

            //in milliseconds
            long diff = d1.getTime() - d2.getTime();
            //long diffSeconds = diff / 1000 % 60;
            //long diffMinutes = diff / (60 * 1000) % 60;
            //long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if (diffDays == 0){
                date = "Today";
            }else if (diffDays <= 7){
                date = diffDays+" day(s) ago";
            }else if (diffDays > 7){
                date = "> 1 week ago";
            }else{
                date = "not available";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String type = job.getJobType();
        if (type.equals("Freelance")){
            type = "Free\nLance";
        }else {
            type = type.replace(" ", "\n");
        }

        holder.jobTitle.setText(job.getJobTitle());
        holder.jobCompany.setText(job.getJobCompany());
        //holder.jobDate.setText(job.getJobDate());
        holder.jobPay.setText(job.getJobPay());
        holder.jobZone.setText(job.getJobZone());
        holder.jobAddedAt.setText(date);
        holder.jobType.setText(type);
    }

    @Override
    public int getItemCount() {
        return jobArrayList.size();
    }

    //Removes the row (TOBE fixed)
    void delete(int position, ArrayList jobArrayList) {
        jobArrayList.remove(position);
        notifyItemRemoved(position);
    }
}
