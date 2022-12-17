package team.diamond.kaizer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import team.diamond.kaizer.R;
import team.diamond.kaizer.NewActivity;
import team.diamond.kaizer.models.Upload;


//адаптер для отображение фото по очереди
//+
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;

    //+
    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }
//+


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item2, parent, false);
        return new ImageViewHolder(v);
    }

//+


    //  ОШИБКА В ЭТОМ КОДЕ !  НАЧАЛО
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        //     Picasso.with(mContext)
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.drawable.var2)
                .fit()
                // .centerCrop()  // первый вариант
                .centerInside()  // второй вариант
                .into(holder.imageView);

        //  ОШИБКА В ЭТОМ КОДЕ !  НАЧАЛО!!!!!    альтернатива
        //код перехода на новый лист
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewActivity.class);
                intent.putExtra("image@#", mUploads.get(position).getImageUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });



    }
    //  ОШИБКА В ЭТОМ КОДЕ !  КОНЕЦ


    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    // +
    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.txt_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }
        //+

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Выберите действие");
            //  MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Do whatever");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Удалить фото");

            //     doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);

        }
//+

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    //  mListener.onItemClick(position);  типо забыл удалить

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onWhatEverClick(position);
                            return true;

                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
        //+

    }


    public interface OnItemClickListener {
        void onItemClick(int position);

        void onWhatEverClick(int position);

        void onDeleteClick(int position);
    }

    //+
    public void setOnClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


}
