package arie.kamus.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import arie.kamus.DetailKata;
import arie.kamus.R;
import arie.kamus.db.IndonesiaModel;

public class IndonesiaAdapter extends RecyclerView.Adapter<IndonesiaAdapter.MyViewHolder> {
    private ArrayList<IndonesiaModel> arrayList = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public IndonesiaAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kata, parent, false);
        return new IndonesiaAdapter.MyViewHolder(view, parent.getContext(), arrayList);
    }

    public void addItem(ArrayList<IndonesiaModel> list) {
        this.arrayList = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.txtKata.setText(arrayList.get(position).getKata());

        holder.txtKata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailKata.class);
                intent.putExtra("id", arrayList.get(position).getId());
                intent.putExtra("kata", arrayList.get(position).getKata());
                intent.putExtra("keterangan", arrayList.get(position).getKeterangan());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView txtKata;
        public ArrayList<IndonesiaModel> list = new ArrayList<>();
        public Context ctx;

        public MyViewHolder(View itemView, Context context, ArrayList<IndonesiaModel> list) {
            super(itemView);
            this.list = list;
            this.ctx = context;

            txtKata = itemView.findViewById(R.id.txtKata);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            IndonesiaModel data = this.list.get(position);

            Intent intent = new Intent(ctx, DetailKata.class);
            intent.putExtra("id", data.getId());
            intent.putExtra("kata", data.getKata());
            intent.putExtra("keterangan", data.getKeterangan());
            ctx.startActivity(intent);
        }
    }
}
