package team.diamond.kaizer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import team.diamond.kaizer.R;
import team.diamond.kaizer.models.AllUserModel;
import team.diamond.kaizer.view.StreamAllHere;

public class allUserAdapter extends RecyclerView.Adapter<allUserAdapter.MyViewHolder> {


    Context context;


    ArrayList<AllUserModel> list2;


    public allUserAdapter(Context context, ArrayList<AllUserModel> list2) {
        this.context = context;
        this.list2 = list2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.all_user_adapterver2, parent, false); // указываем что родитель  Layout который создавали
        return new MyViewHolder(v);

    }

    //а тут можно забиндить нажатие
    //т.е. провалиться   - например посмотреть полную инфу о белке
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AllUserModel Reply_shema = list2.get(position);
        holder.user.setText(Reply_shema.getName());  //устанавливаем соответсвие  текста

        holder.privateVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start PostDetailActivity
                Intent intent = new Intent(context, StreamAllHere.class);
            //    intent.putExtra("postId", user); // will be detail of post this id, is id of the post clicked
                context.startActivity(intent);

            }
        });


        //  + можно добавить все что угодно     нажал и провалился

    }

    @Override
    public int getItemCount() {
        return list2.size();

    }

    //сюда записываем все !!!
    // то что нужно найти на макете о белке  - возраст / рейтинг / размер пизды / фото / полная инфа и прочее
    //придаем  значение к примеру получить полную инфу
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user, privateVideo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            user = itemView.findViewById(R.id.user);   // находим где оно находится физически
            privateVideo = itemView.findViewById(R.id.privateVideo);   // находим где оно находится физически


        }
    }


}
