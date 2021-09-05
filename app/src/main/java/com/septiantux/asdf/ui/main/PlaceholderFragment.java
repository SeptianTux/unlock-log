package com.septiantux.asdf.ui.main;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.septiantux.asdf.R;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderFragment extends Fragment {
    private ViewModel viewModel;

    private View root;
    private LogData logData;
    private ArrayList<LogData> logDataList;
    private LogDataAdapter logDataAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);


        viewModel = ViewModelProviders.of(this)
                .get(ViewModel.class);

        recyclerView.addItemDecoration(
                new LogDataAdapter.GridSpacingItemDecoration(
                        1
                        , dpToPx(5)
                        , true)
        );



        final Observer<List<Data>> listObserver = new Observer<List<Data>>() {
            @SuppressLint({"SimpleDateFormat", "ResourceType"})
            @Override
            public void onChanged(@Nullable final List<Data> data) {
                logDataList = new ArrayList<>();

                logDataAdapter = new LogDataAdapter(
                                                root.getContext()
                                                , logDataList
                                                , new LogDataAdapter.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(LogData item) {
                                                        Log.w("test", "dan test lagi");
                                                    }
                                                }
                                        );

                mLayoutManager = new GridLayoutManager(root.getContext(), 1);


                recyclerView.setLayoutManager(mLayoutManager);

                recyclerView.setItemAnimator(new DefaultItemAnimator());
                //recyclerView.setAdapter(logDataAdapter);
                recyclerView.setAdapter(logDataAdapter);

                int ic;
                for(int i=0; i<data.size(); i++) {
                    logData = new LogData();
                    logData.setId(data.get(i).id);
                    logData.setTimestamp(data.get(i).timestamp);
                    logData.setType(data.get(i).type);

                    switch (data.get(i).type) {
                        case 0  : ic = R.drawable.ic_lock; break;
                        case 1  : ic = R.drawable.ic_unlock; break;
                        case 2  : ic = R.drawable.ic_switch_on; break;
                        case 3  : ic = R.drawable.ic_switch_off; break;
                        default : ic = R.drawable.ic_unlock;
                    }

                    Log.w("PlaceholderFragment", String.valueOf(data.get(i).timestamp));

                    logData.setIcon(ic);
                    logDataList.add(logData);
                }

                Log.w("PlaceholderFragment", String.valueOf(logDataList.size()));

                logDataAdapter.notifyDataSetChanged();

            }
        };

        viewModel.getAllLog().observe(getViewLifecycleOwner(), listObserver);

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
