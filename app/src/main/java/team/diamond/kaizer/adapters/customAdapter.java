package team.diamond.kaizer.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import team.diamond.kaizer.R;
import team.diamond.kaizer.models.UploadCustom;


//адаптер для отображение фото по очереди
//+
public class customAdapter extends RecyclerView.Adapter<customAdapter.ImageViewHolder> {

    private Context mContext;
    private List<UploadCustom> mUploads;
    private OnItemClickListener mListener;

    //+
    public customAdapter(Context context, List<UploadCustom> uploads) {
        mContext = context;
        mUploads = uploads;
    }
//+


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.story_custom_item, parent, false);
        return new ImageViewHolder(v);
    }

//+


    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        UploadCustom uploadCurrent = mUploads.get(position);
        holder.storyName.setText(uploadCurrent.getName());
        holder.storyTxt.setText(uploadCurrent.getImageUrl());

    }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    // +
    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView storyName;
        public TextView storyTxt;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            storyName = itemView.findViewById(R.id.story_name);
            storyTxt = itemView.findViewById(R.id.story_txt);


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
