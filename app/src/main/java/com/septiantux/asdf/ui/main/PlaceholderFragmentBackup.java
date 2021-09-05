package com.septiantux.asdf.ui.main;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.septiantux.asdf.R;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class PlaceholderFragmentBackup extends Fragment {
    private ViewModel viewModel;

    private View root;
    private TimelineRow timelineRow;
    private ArrayList<TimelineRow> timelineRowsList;
    private ArrayAdapter<TimelineRow> myAdapter;
    private List<Data> allData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this)
                .get(ViewModel.class);

        final Observer<List<Data>> listObserver = new Observer<List<Data>>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onChanged(@Nullable final List<Data> data) {
                Date date;
                DateFormat format;
                Data tmp;
                String typ;

                TimelineRow timelineRow;
                ArrayList<TimelineRow> timelineRowsList;
                ArrayAdapter<TimelineRow> myAdapter;

                format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                format.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));

                timelineRowsList = new ArrayList<>();

                Log.w("PlaceholderFragment", String.valueOf(data.size()));

                for(int i=0; i<data.size(); i++) {
                    tmp = data.get(i);

                    switch (tmp.type) {
                        case 0  : typ = "Lock"; break;
                        case 1  : typ = "Unlock"; break;
                        case 2  : typ = "Boot"; break;
                        case 3  : typ = "Shutdown"; break;
                        default : typ = "Unknown";
                    }

                    date = new Date(tmp.timestamp*1000);
                    timelineRow = new TimelineRow(i);
                    timelineRow.setDate(new Date(tmp.timestamp*1000));
                    timelineRow.setTitle(typ);
                    timelineRow.setDescription(format.format(date));
                    //timelineRow.setDateColor();
                    timelineRow.setBellowLineColor(getResources().getColor(R.color.timelineRowBellowLineColor));
                    timelineRow.setBellowLineSize(2);
                    timelineRow.setDateColor(Color.argb(255, 0, 0, 0));
                    timelineRow.setTitleColor(Color.argb(255, 0, 0, 0));
                    timelineRow.setDescriptionColor(Color.argb(255, 0, 0, 0));

                    if(i==3) {
                        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_play_button);
                        timelineRow.setImage(b);
                        timelineRow.setBackgroundColor(getResources().getColor(R.color.timelineRowBackgroundSelected));
                    } else {
                        timelineRow.setBackgroundColor(getResources().getColor(R.color.timelineRowBackground));
                    }

                    timelineRowsList.add(timelineRow);

                    timelineRow = null;
                    tmp = null;
                    date = null;
                }

                myAdapter = new TimelineViewAdapter(getContext(), 0, timelineRowsList,
                        //if true, list will be sorted by date
                        false);
            }
        };
        viewModel.getAllLog().observe(this, listObserver);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_main, container, false);

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
