package com.example.pupstar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PreviousDiseasesActivity extends AppCompatActivity {

    private ListView listView;
    private EditText search;
    private ImageView btnBack;

    private ArrayList<PreDiseaseItem> arrayList = new ArrayList<>();
    private ArrayList<PreDiseaseItem> arrayList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_diseases);

        listView = (ListView) this.findViewById(R.id.listView);

        search = (EditText) this.findViewById(R.id.search);

        btnBack = (ImageView) findViewById(R.id.btnBack);

        showPreDiseases();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                String selected = String.valueOf(arrayList.get(i).getId());

                Intent intent = new Intent(PreviousDiseasesActivity.this, ViewDiseaseDetectedResultActivity.class);
                intent.putExtra("id", selected);
                intent.putExtra("isCreated", "no");
                startActivity(intent);

            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String value = charSequence.toString().toLowerCase();

                arrayList.clear();
                listView.setAdapter(null);

                PreDiseaseAdapter preDiseaseAdapter = new PreDiseaseAdapter(PreviousDiseasesActivity.this, R.layout.row_pre_disease_item, arrayList);
                listView.setAdapter(preDiseaseAdapter);

                if (!value.equals("")) {
                    for (int l = 0; l < arrayList2.size(); l++) {
                        if (arrayList2.get(l).getTitle().toLowerCase().contains(value)) {
                            arrayList.add(arrayList2.get(l));
                        }
                    }
                } else {
                    for (int l = 0; l < arrayList2.size(); l++) {
                        arrayList.add(arrayList2.get(l));
                    }
                }

                preDiseaseAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void showPreDiseases()
    {
        arrayList.clear();
        listView.setAdapter(null);

        PreDiseaseAdapter preDiseaseAdapter = new PreDiseaseAdapter(PreviousDiseasesActivity.this, R.layout.row_pre_disease_item, arrayList);
        listView.setAdapter(preDiseaseAdapter);

        arrayList.add(new PreDiseaseItem("1", "Dry Skin", "German Shepherd", "Need good health care", "https://img.freepik.com/premium-vector/bowl-food-dog-cat-pet-flat-style-vector-illustration-animal-bowl-silhouette-print_501826-267.jpg?w=2000"));
        arrayList.add(new PreDiseaseItem("2", "Worm", "Lion Shepherd", "Need vet treatments",  "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRcIygi3-IUvIW0VeGSI45FU_OSutWHJVRhOW9oKX934Q&s"));

        arrayList2.add(new PreDiseaseItem("1", "Dry Skin", "German Shepherd", "Need good health care", "https://img.freepik.com/premium-vector/bowl-food-dog-cat-pet-flat-style-vector-illustration-animal-bowl-silhouette-print_501826-267.jpg?w=2000"));
        arrayList2.add(new PreDiseaseItem("2", "Worm", "Lion Shepherd", "Need vet treatments",  "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRcIygi3-IUvIW0VeGSI45FU_OSutWHJVRhOW9oKX934Q&s"));

        preDiseaseAdapter.notifyDataSetChanged();
    }
}

class PreDiseaseItem {

    String id, title, breed, description, image;

    public PreDiseaseItem(String id, String title, String breed, String description, String image) {
        this.id = id;
        this.title = title;
        this.breed = breed;
        this.description = description;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBreed() {
        return breed;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}

class PreDiseaseAdapter extends ArrayAdapter<PreDiseaseItem> {

    private Context mContext;
    private int mResource;

    public PreDiseaseAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PreDiseaseItem> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mResource = resource;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView breed = (TextView) convertView.findViewById(R.id.breed);

        title.setText(getItem(position).getTitle());
        breed.setText(getItem(position).getBreed());

        Uri imgUri = Uri.parse(getItem(position).getImage());
        Picasso.get().load(imgUri).into(image);

        return convertView;
    }

}