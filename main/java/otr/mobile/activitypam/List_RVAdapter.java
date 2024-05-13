package otr.mobile.activitypam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class List_RVAdapter extends RecyclerView.Adapter<List_RVAdapter.MyViewHolder> {
    private final RVInterface rvinterface;
    Context context;
    ArrayList<input> inputs;
    public List_RVAdapter(Context context, ArrayList<input> inputs, RVInterface rvinterface) {
        this.context = context;
        this.inputs = inputs;
        this.rvinterface = rvinterface;
    }

    @NonNull
    @Override
    public List_RVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row,parent,false);
        return new List_RVAdapter.MyViewHolder(view, rvinterface);
    }

    @Override
    public void onBindViewHolder(@NonNull List_RVAdapter.MyViewHolder holder, int position) {
        holder.tvNama.setText(inputs.get(position).getNama());
        holder.tvSubs.setText(inputs.get(position).getStatus());
        holder.ivProfil.setImageResource(inputs.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return inputs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvNama,tvSubs;
        ImageView ivProfil;

        public MyViewHolder(@NonNull View itemView, RVInterface rvinterface) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tvNama);
            tvSubs = itemView.findViewById(R.id.tvSubs);
            ivProfil = itemView.findViewById(R.id.ivProfil);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rvinterface != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            rvinterface.onItemClick(position);
                        }
                    }

                }
            });
        }
    }
}
