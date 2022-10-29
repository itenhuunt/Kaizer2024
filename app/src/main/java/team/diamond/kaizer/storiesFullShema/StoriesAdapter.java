package team.diamond.kaizer.storiesFullShema;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;

import team.diamond.kaizer.R;

public class StoriesAdapter extends FirebaseRecyclerAdapter<StoriesModel, StoriesAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public StoriesAdapter(@NonNull FirebaseRecyclerOptions<StoriesModel> options) {
        super(options);


    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull StoriesModel model) {
        holder.stories.setText(model.getStories());//  присваем то что есть в модели или firebase   / что одно и то же




//        Glide.with(holder.img.getContext())
//                .load(model.getTurl())
//                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
//                .circleCrop()
//                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
//                .into(holder.img);

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.stories.getContext());
                builder.setTitle("ты уверен ?");
                builder.setMessage(" ты точно сделал этот пунк ? ");

                builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("рассказы маленьких сучек2").child("кайзер")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });

                builder.setNegativeButton("cancale", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.stories.getContext(), "cancelled", Toast.LENGTH_SHORT).show();

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stories_item, parent, false);
        return new myViewHolder(view);
    }


    // тут прописываем все что есть в адаптере
    class myViewHolder extends RecyclerView.ViewHolder {

//        CircularImageView img;
        TextView stories;
        Button btndelete;




        // тут прописываем hooks()
        public myViewHolder(@NonNull View itemView) {
            super(itemView);


//            img = (CircularImageView) itemView.findViewById(R.id.profileImage);
            stories = (TextView) itemView.findViewById(R.id.storiesread);
            btndelete = (Button) itemView.findViewById(R.id.btndelete);

        }
    }


}
