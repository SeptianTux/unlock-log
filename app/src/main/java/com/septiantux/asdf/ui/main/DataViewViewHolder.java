package com.septiantux.asdf.ui.main;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.septiantux.asdf.R;

public class DataViewViewHolder extends RecyclerView.ViewHolder {
    View view;
    ImageView icon;
    TextView textHeader, textFooterLeft;
    RelativeTimeTextView textFooterRight;

    public DataViewViewHolder(View v) {
        super(v);

        this.view = v;
        this.icon = this.view.findViewById(R.id.cardViewIcon);
        this.textHeader = this.view.findViewById(R.id.cardViewTextHeader);
        this.textFooterLeft = this.view.findViewById(R.id.cardViewFooterLeft);
        this.textFooterRight = this.view.findViewById(R.id.cardViewFooterRitght);
    }

    public void bind(final DataLog item, final DataViewOnItemClickListener listener) {
        this.view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(view, item);
            }
        });
    }
}