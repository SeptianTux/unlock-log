package com.septiantux.asdf.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.septiantux.asdf.R;

import java.util.List;

public class DataViewAdapter extends RecyclerView.Adapter<DataViewViewHolder> {
    private Context mContext;
    private List<DataLog> dataLogList;
    private DataViewOnItemClickListener listener;

    public DataViewAdapter(Context mContext, List<DataLog> dataLogList, DataViewOnItemClickListener listener) {
        this.mContext = mContext;
        this.dataLogList = dataLogList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DataViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new DataViewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewViewHolder holder, int position) {
        DataLog dataLog = dataLogList.get(position);

        holder.textHeader.setText(dataLog.getTypeString());
        holder.textFooterLeft.setText(dataLog.getDate());
        holder.textFooterRight.setReferenceTime(dataLog.getTimestamp()*1000);

        if(dataLog.getMark()) {
            View cardBg;
            cardBg = holder.view.findViewById(R.id.cardViewBg);
            cardBg.setBackgroundResource(R.color.cardImageBgMarked);
        }

        Glide.with(mContext).load(dataLog.getIcon()).into(holder.icon);
        holder.bind(dataLogList.get(position), this.listener);
    }

    @Override
    public int getItemCount() {
        return dataLogList.size();
    }
}
