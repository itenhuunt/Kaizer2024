package team.diamond.kaizer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import team.diamond.kaizer.R;
import team.diamond.kaizer.models.AllUserModel;

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

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AllUserModel Reply_shema = list2.get(position);
        holder.user.setText(Reply_shema.getName());  //устанавливаем соответсвие  текста

    }

    @Override
    public int getItemCount() {
        return list2.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            user = itemView.findViewById(R.id.user);   // находим где оно находится физически


        }
    }


}
