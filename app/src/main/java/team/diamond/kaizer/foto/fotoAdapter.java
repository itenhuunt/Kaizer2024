package team.diamond.kaizer.foto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import team.diamond.kaizer.R;

public class fotoAdapter extends RecyclerView.Adapter<fotoAdapter.ViewHolder> {


    private Context context;
    private ArrayList<fotoModel> imageModelArrayList;



    public fotoAdapter(Context context, ArrayList<fotoModel> imageModelArrayList) {
        this.context = context;          //  "связь"
        this.imageModelArrayList = imageModelArrayList;       // список
    }


    //зависимость   image_item    для recycler view  -----  родитель
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate
                //     (R.layout.single_image_layout,parent,false);
                        (R.layout.image_item, parent, false);

        return new ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //сама загрузка
        Glide.with(context)
                .load(imageModelArrayList.get(position).getImageurl())
                .into(holder.imageView);


        //код перехода на новый лист
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewActivity.class);
                intent.putExtra("image@#", imageModelArrayList.get(position).getImageurl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageview);

        }
    }
}
