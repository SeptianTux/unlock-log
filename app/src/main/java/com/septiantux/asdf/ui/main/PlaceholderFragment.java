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

    private class MarkDataObject {
        private ArrayList<Integer> id;
        private ArrayList<Boolean> mark;
        private ArrayList<Boolean> dbMarkValue;

        MarkDataObject() {
            this.id = new ArrayList<>();
            this.mark = new ArrayList<>();
            this.dbMarkValue = new ArrayList<>();
        }

        public void markUnmark(LogData logData) {
            int index = -1;

            index=this.id.indexOf(logData.getId());
            if(index >= 0) {
                this.mark.set(index, !this.mark.get(index));
            } else {
                this.id.add(logData.getId());
                this.mark.add(!logData.getMark());
                this.dbMarkValue.add(logData.getMark());
            }
        }

        public boolean getMarkById(int id) {
            return this.mark.get(this.id.indexOf(id));
        }
    }

    private MarkDataObject markDataObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        markDataObject = new MarkDataObject();

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        viewModel = ViewModelProviders.of(this)
                .get(ViewModel.class);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(40);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
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
                                                    public void onItemClick(View view, LogData logData) {
                                                        View c = view.findViewById(R.id.cardViewBg);

                                                        markDataObject.markUnmark(logData);

                                                        c.setBackgroundResource(
                                                                markDataObject.getMarkById(logData.getId())
                                                                ?
                                                                        R.color.cardImageBgMarked
                                                                        :
                                                                        R.color.cardImageBg
                                                        );
                                                    }
                                                }
                                        );
                //logDataAdapter.notifyItemRangeInserted(rangeStart, rangeEnd);


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
                    logData.setMark(data.get(i).mark);

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

                    logDataAdapter.notifyItemRemoved(i);
                    logDataAdapter.notifyItemChanged(i);
                    logDataAdapter.notifyItemInserted(i);
                }

                Log.w("PlaceholderFragment", String.valueOf(logDataList.size()));

                //logDataAdapter.notifyDataSetChanged();

            }
        };

        viewModel.getAllLog().observe(getViewLifecycleOwner(), listObserver);

        return root;
    }

    @Override
    public void onDestroy() {
        int size = -1;
        size = markDataObject.id.size();

        for(int i=0; i<size; i++) {
            viewModel.mark(markDataObject.id.get(i), markDataObject.mark.get(i));
        }

        super.onDestroy();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
