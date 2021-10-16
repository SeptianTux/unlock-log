package com.septiantux.asdf.ui.main;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
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
    private DataLog dataLog;
    private ArrayList<DataLog> dataLogList;
    private DataViewAdapter dataViewAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private DataViewMarkUnmarkObject dataViewMarkUnmarkObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataViewMarkUnmarkObject = new DataViewMarkUnmarkObject();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(40);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.addItemDecoration(
                new DataViewGridSpacingItemDecoration(
                        1
                        , dpToPx(5)
                        , true)
        );

        final Observer<List<Data>> listObserver = new Observer<List<Data>>() {
            @SuppressLint({"SimpleDateFormat", "ResourceType"})
            @Override
            public void onChanged(@Nullable final List<Data> data) {
                dataLogList = new ArrayList<>();

                dataViewAdapter = new DataViewAdapter(
                        root.getContext()
                        , dataLogList
                        , new DataViewOnItemClickListener() {
                            @Override
                            public void onItemClick(View view, DataLog logData) {
                                View c = view.findViewById(R.id.cardViewBg);

                                dataViewMarkUnmarkObject.markUnmark(logData);

                                c.setBackgroundResource(
                                        dataViewMarkUnmarkObject.getMarkById(logData.getId())
                                        ? R.color.cardImageBgMarked : R.color.cardImageBg
                                );
                            }
                        }
                );

                mLayoutManager = new GridLayoutManager(root.getContext(), 1);

                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(dataViewAdapter);

                int ic, data_size=0;

                if (data != null) {
                    data_size = data.size();
                }

                for(int i=0; i<data_size; i++) {
                    dataLog = new DataLog();
                    dataLog.setId(data.get(i).id);
                    dataLog.setTimestamp(data.get(i).timestamp);
                    dataLog.setType(data.get(i).type);
                    dataLog.setMark(data.get(i).mark);

                    switch (data.get(i).type) {
                        case 0  : ic = R.drawable.ic_lock; break;
                        //case 1  : ic = R.drawable.ic_unlock; break;
                        case 2  : ic = R.drawable.ic_switch_on; break;
                        case 3  : ic = R.drawable.ic_switch_off; break;
                        default : ic = R.drawable.ic_unlock;
                    }

                    dataLog.setIcon(ic);
                    dataLogList.add(dataLog);

                    dataViewAdapter.notifyItemRemoved(i);
                    dataViewAdapter.notifyItemChanged(i);
                    dataViewAdapter.notifyItemInserted(i);
                }
            }
        };

        viewModel.getAllLog().observe(getViewLifecycleOwner(), listObserver);

        return root;
    }

    @Override
    public void onDestroy() {
        int size = -1;
        size = dataViewMarkUnmarkObject.getSize();

        for(int i=0; i<size; i++) {
            viewModel.mark(dataViewMarkUnmarkObject.getId(i), dataViewMarkUnmarkObject.getMark(i));
        }

        super.onDestroy();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics())
        );
    }
}
