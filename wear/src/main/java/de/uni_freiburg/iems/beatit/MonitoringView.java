package de.uni_freiburg.iems.beatit;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MonitoringView extends Fragment {

    private MonitoringViewModel monitoringViewModel;
    private ImageButton startStopButton;

    public static MonitoringView newInstance() {
        return new MonitoringView();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.monitoring_view, container, false);
        startStopButton = rootView.findViewById(R.id.monitoring_start_stop_button);
        Spinner spinner = rootView.findViewById(R.id.monitoring_model_spinner);
        List<String> list = new ArrayList<>();
        list.add("Model1");
        list.add("Model2");
        list.add("Model3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_spinner_item, list.toArray(new String[0]));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //todo implement choice of model
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setAdapter(dataAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        monitoringViewModel = ViewModelProviders.of(this).get(MonitoringViewModel.class);
        startStopButton.setOnClickListener(view -> onStartStopButtonClicked());
        monitoringViewModel.getIsMonitoringStarted().observe(this, isMonitoringStarted -> {
            if (isMonitoringStarted == null) return;
            startStopButton.setImageResource(chooseImageResource(isMonitoringStarted));
        });
    }

    private void onStartStopButtonClicked() {
        Boolean isStarted = monitoringViewModel.getIsMonitoringStarted().getValue();
        if (isStarted == null) return;
        if (isStarted)
            monitoringViewModel.stopMonitoring();
        else
            monitoringViewModel.startMonitoring();
    }

    private int chooseImageResource(Boolean isStarted) {
        if (isStarted)
            return R.drawable.ic_stop_white_24dp;
        else
            return R.drawable.ic_monitoring_white_24dp;
    }


}
