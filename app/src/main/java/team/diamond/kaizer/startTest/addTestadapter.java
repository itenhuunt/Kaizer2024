package team.diamond.kaizer.startTest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import team.diamond.kaizer.R;
import team.diamond.kaizer.creatTest.testModel;

public class addTestadapter extends RecyclerView.Adapter<addTestadapter.MyViewHolder> {


    Context context;

    ArrayList<testModel> list2;


    public addTestadapter(Context context, ArrayList<testModel> list2) {
        this.context = context;
        this.list2 = list2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.add_test_card_shema,parent,false); // указываем что родитель  Layout который создавали
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        testModel add_test_shema = list2.get(position);
        holder.nameCustomTest.setText(add_test_shema.getCustomTest());  //устанавливаем соответсвие  текста

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return list2.size();

    }

    public static class  MyViewHolder extends  RecyclerView.ViewHolder{

        TextView nameCustomTest;
        Button btndelete;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameCustomTest = itemView.findViewById(R.id.nameCustomTest);
            btndelete = (Button) itemView.findViewById(R.id.btndelete);


        }
    }


}
