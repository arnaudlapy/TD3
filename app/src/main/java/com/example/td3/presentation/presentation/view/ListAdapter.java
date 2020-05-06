package com.example.td3.presentation.presentation.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.td3.R;
import com.example.td3.presentation.presentation.model.Discographie;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Discographie> values;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtHeader;
        TextView txtFooter;
        ImageView imageView;
        RelativeLayout parentLayout;

        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            imageView = (ImageView) v.findViewById(R.id.icon);
            parentLayout = v.findViewById(R.id.parent_layout);

        }
    }

    public void add(int position, Discographie item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    private void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public ListAdapter(List<Discographie> myDataset, Context context) {
        this.values = myDataset;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final Discographie currentDisc;

        if(position < values.size()){

           currentDisc = values.get(position);

            holder.txtHeader.setText(currentDisc.getName());
            holder.txtFooter.setText(currentDisc.getType());

            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, GalleryActivity.class);
                    intent.putExtra("Disc_image_URL", currentDisc.getImgURL());
                    intent.putExtra("Disc_Title", currentDisc.getName());
                    intent.putExtra("Disc_Date", currentDisc.getDate());
                    intent.putExtra("Disc_Type", currentDisc.getType());
                    intent.putExtra("Disc_Duree", currentDisc.getDuree());
                    intent.putExtra("Disc_Genre", currentDisc.getGenre());
                    intent.putExtra("Disc_Description", currentDisc.getDescription());
                    intent.putExtra("Disc_StyleImg", currentDisc.getImgStyleURL());
                    context.startActivity(intent);
                }
            });

            Picasso.with(context)
                .load(currentDisc.getImgURL())
                .into(holder.imageView);


        }else Log.i("ListAdapter", "erreur size");
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
