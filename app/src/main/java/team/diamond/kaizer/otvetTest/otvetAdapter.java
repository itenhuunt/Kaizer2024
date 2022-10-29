package team.diamond.kaizer.otvetTest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import team.diamond.kaizer.R;

public class otvetAdapter extends RecyclerView.Adapter<otvetAdapter.MyViewHolder> {


    Context context;


    ArrayList<otvetModel> list2;


    public otvetAdapter(Context context, ArrayList<otvetModel> list2) {
        this.context = context;
        this.list2 = list2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.belka_full_infa_card_shema,parent,false); // указываем что родитель  Layout который создавали
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        otvetModel Reply_shema = list2.get(position);
        holder.quest1.setText(Reply_shema.getQuest1());  //устанавливаем соответсвие  текста
        holder.reply1.setText(Reply_shema.getReply1()+" $");

    }

    @Override
    public int getItemCount() {
        return list2.size();

    }

    public static class  MyViewHolder extends  RecyclerView.ViewHolder{

        TextView quest1, reply1;
        Button btndelete;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            quest1 = itemView.findViewById(R.id.question1);   // находим где оно находится физически
            reply1 = itemView.findViewById(R.id.reply1);   //  ПЕРЕБИТЬ НА НОВОМ МАКЕТЕ !!!
            btndelete = (Button) itemView.findViewById(R.id.btndeleteanswertest);


        }
    }


}
