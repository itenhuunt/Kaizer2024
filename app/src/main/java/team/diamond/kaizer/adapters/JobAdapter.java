package team.diamond.kaizer.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import team.diamond.kaizer.R;
import team.diamond.kaizer.models.jobModel;

public class JobAdapter extends FirebaseRecyclerAdapter<jobModel, JobAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public JobAdapter(@NonNull FirebaseRecyclerOptions<jobModel> options) {
        super(options);


    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull jobModel model) {
        holder.job.setText(model.getJob());//  присваем то что есть в модели или firebase   / что одно и то же
        holder.jobdescription.setText(model.getDescriptionjob());//  присваем то что есть в модели или firebase   / что одно и то же
        holder.jobreward.setText(model.getReward());//  присваем то что есть в модели или firebase   / что одно и то же
        holder.nameboss.setText(model.getNameboss());//  присваем то что есть в модели или firebase   / что одно и то же




//        Glide.with(holder.img.getContext())
//                .load(model.getTurl())
//                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
//                .circleCrop()
//                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
//                .into(holder.img);

        holder.btndeletejob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.job.getContext());
                builder.setTitle("ты уверен ?");
                builder.setMessage(" ты точно сделал этот пунк ? ");

                builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("job")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });

                builder.setNegativeButton("cancale", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.job.getContext(), "cancelled", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();

            }
        });


    }


    // загружается hooks =  model или  firebase  без разницы особо


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false);
        return new myViewHolder(view);
    }


    // тут прописываем все что есть в адаптере
    class myViewHolder extends RecyclerView.ViewHolder {

//        CircularImageView img;
        TextView job,jobreward,jobdescription,nameboss;
        Button btndeletejob;




        // тут прописываем hooks()
        public myViewHolder(@NonNull View itemView) {
            super(itemView);


//            img = (CircularImageView) itemView.findViewById(R.id.profileImage);
            job = (TextView) itemView.findViewById(R.id.job);
            jobreward = (TextView) itemView.findViewById(R.id.jobreward);
            jobdescription = (TextView) itemView.findViewById(R.id.jobdescription);
            nameboss = (TextView) itemView.findViewById(R.id.nameboss);
            btndeletejob = (Button) itemView.findViewById(R.id.btndeletejob);

        }
    }


}
