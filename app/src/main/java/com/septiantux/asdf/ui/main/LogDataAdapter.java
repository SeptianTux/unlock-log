package com.septiantux.asdf.ui.main;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.septiantux.asdf.R;

import java.util.List;

public class LogDataAdapter extends RecyclerView.Adapter<LogDataAdapter.MyViewHolder> {
    private Context mContext;
    private List<LogData> logDataList;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(LogData item);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView textHeader, textFooterLeft, textFooterRight;
        public View view;

        public MyViewHolder(View v) {
            super(v);

            this.view = v;

            icon = view.findViewById(R.id.cardViewIcon);
            textHeader = view.findViewById(R.id.cardViewTextHeader);
            textFooterLeft = view.findViewById(R.id.cardViewFooterLeft);
            textFooterRight = view.findViewById(R.id.cardViewFooterRitght);
        }

        public void bind(final LogData item, final OnItemClickListener listener) {
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public LogDataAdapter(Context mContext, List<LogData> logDataList, OnItemClickListener listener) {
        this.mContext = mContext;
        this.logDataList = logDataList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LogData logData = logDataList.get(position);
        holder.textHeader.setText(logData.getTypeString());
        holder.textFooterLeft.setText(logData.getDate());
        holder.textFooterRight.setText(String.valueOf(logData.getTimestamp()));

        Glide.with(mContext).load(logData.getIcon()).into(holder.icon);

        holder.bind(logDataList.get(position), listener);
    }



    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing+215;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return logDataList.size();
    }
}
