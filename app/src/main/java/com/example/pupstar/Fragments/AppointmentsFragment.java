package com.example.pupstar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pupstar.AddAppointmentActivity;
import com.example.pupstar.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AppointmentsFragment extends Fragment {

    private ChipGroup chipGroup;
    private TextView addAppointment;

    private ListView listView;
    private ArrayList<AppointmentItem> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointments, container, false);

        chipGroup = (ChipGroup) view.findViewById(R.id.chipGroup);
        addAppointment = (TextView) view.findViewById(R.id.addAppointment);
        listView = (ListView) view.findViewById(R.id.listView);

        showUpcoming();

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {

                Chip chip = chipGroup.findViewById(i);
                if (chip != null) {
                    String selected = chip.getText().toString();

                    if (selected.equals("Upcoming")) {
                        showUpcoming();
                    } else {
                        showPrevious();
                    }

                }
            }
        });

        addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddAppointmentActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void showUpcoming()
    {

        arrayList.clear();
        listView.setAdapter(null);

        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(getContext(), R.layout.row_appointment_item, arrayList);
        listView.setAdapter(appointmentAdapter);

        arrayList.add(new AppointmentItem("1", "2022-10-02", "10.00"));
        arrayList.add(new AppointmentItem("2", "2022-12-12", "11.00"));
        appointmentAdapter.notifyDataSetChanged();

    }

    private void showPrevious()
    {
        arrayList.clear();
        listView.setAdapter(null);

        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(getContext(), R.layout.row_appointment_item, arrayList);
        listView.setAdapter(appointmentAdapter);

        arrayList.add(new AppointmentItem("1", "2022-5-02", "10.00"));
        arrayList.add(new AppointmentItem("2", "2022-6-12", "11.00"));
        appointmentAdapter.notifyDataSetChanged();
    }
}

class AppointmentItem {

    String id, date, time;

    public AppointmentItem(String id, String date, String time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}

class AppointmentAdapter extends ArrayAdapter<AppointmentItem> {

    private Context mContext;
    private int mResource;

    public AppointmentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<AppointmentItem> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
        TextView txtTime = (TextView) convertView.findViewById(R.id.txtTime);

        txtDate.setText(getItem(position).getDate());
        txtTime.setText(getItem(position).getTime());

        return convertView;
    }

}