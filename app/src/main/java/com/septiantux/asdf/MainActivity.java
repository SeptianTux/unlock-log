package com.septiantux.asdf;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.septiantux.asdf.ui.main.PlaceholderFragment;
import com.septiantux.asdf.ui.main.ViewModel;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        activity = this;

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, PlaceholderFragment.class, null)
                    .commit();
        }

        fab = findViewById(R.id.fab);
        fab.setImageResource(
                isMyServiceRunning(MyService.class) ?
                R.drawable.ic_stop_button : R.drawable.ic_play_button
        );

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message;

                if(isMyServiceRunning(MyService.class)) {
                    serviceStopperHandler();

                    if(isMyServiceRunning(MyService.class)) {
                        message = "Stop service: failed.";
                    } else {
                        message = "Stopped";
                    }
                } else {
                    serviceStarterHandler();

                    if(isMyServiceRunning(MyService.class)) {
                        message = "Started";
                    } else {
                        message = "Start service: failed.";
                    }
                }

                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                fab.setImageResource(
                        isMyServiceRunning(MyService.class) ?
                                R.drawable.ic_stop_button : R.drawable.ic_play_button
                );
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.topAppBar);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    ViewModel viewModel;
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);
                        viewModel.deleteAll();

                        Toast.makeText(context, "Ok", Toast.LENGTH_LONG).show();

                        return false;
                    }
                }
        );
    }

    public void serviceStarterHandler()
    {
        try {
            context.startService(new Intent(context, MyService.class));
        } catch (Exception e) {
            Log.w("MainActivity", e.getMessage());
        }
    }

    public void serviceStopperHandler()
    {
        try {
            context.stopService(new Intent(context, MyService.class));
        } catch (Exception e) {
            Log.w("MainActivity", e.getMessage());
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}