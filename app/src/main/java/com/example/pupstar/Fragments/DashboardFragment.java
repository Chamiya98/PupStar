package com.example.pupstar.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.pupstar.AddDogDetailsActivity;
import com.example.pupstar.DetectBehaviorActivity;
import com.example.pupstar.DetectDiseaseActivity;
import com.example.pupstar.DiseaseDetectionDashboardActivity;
import com.example.pupstar.DogsBehaviorDashboardActivity;
import com.example.pupstar.R;
import com.example.pupstar.VeterinariansActivity;

public class DashboardFragment extends Fragment {

    private CardView breed, veterinary, behaviour, disease;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        breed = (CardView) view.findViewById(R.id.breed);
        veterinary = (CardView) view.findViewById(R.id.veterinary);
        behaviour = (CardView) view.findViewById(R.id.behaviour);
        disease = (CardView) view.findViewById(R.id.disease);

        breed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AddDogDetailsActivity.class);
                startActivity(intent);

            }
        });

        behaviour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), DogsBehaviorDashboardActivity.class);
                startActivity(intent);

            }
        });

        disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), DiseaseDetectionDashboardActivity.class);
                startActivity(intent);

            }
        });

        veterinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), VeterinariansActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }
}