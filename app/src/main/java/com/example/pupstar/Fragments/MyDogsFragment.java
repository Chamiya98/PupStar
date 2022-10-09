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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pupstar.AddDogDetailsActivity;
import com.example.pupstar.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyDogsFragment extends Fragment {

    private ImageView btnAddDogDetails;
    private ListView listView;
    private ArrayList<MyDogItem> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_dogs, container, false);

        listView = (ListView) view.findViewById(R.id.listView);
        btnAddDogDetails = (ImageView) view.findViewById(R.id.btnAddDog);

        showMyDogs();

        btnAddDogDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AddDogDetailsActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }

    private void showMyDogs()
    {
        arrayList.clear();
        listView.setAdapter(null);

        MyDogAdapter myDogAdapter = new MyDogAdapter(getContext(), R.layout.row_my_dogs_item, arrayList);
        listView.setAdapter(myDogAdapter);

        arrayList.add(new MyDogItem("pet1", "Shadow", "German Shepherd", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
        arrayList.add(new MyDogItem("pet2", "Puppy", "German Shepherd", "https://i.pinimg.com/736x/13/44/a9/1344a98c1a7bd9788838922836d813c0.jpg"));
        myDogAdapter.notifyDataSetChanged();
    }

}

class MyDogItem {

    String id, name, breed, image;

    public MyDogItem(String id, String name, String breed, String image) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public String getImage() {
        return image;
    }
}

class MyDogAdapter extends ArrayAdapter<MyDogItem> {

    private Context mContext;
    private int mResource;

    public MyDogAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MyDogItem> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView breed = (TextView) convertView.findViewById(R.id.breed);

        name.setText(getItem(position).getName());
        breed.setText(getItem(position).getBreed());

        Uri imgUri = Uri.parse(getItem(position).getImage());
        Picasso.get().load(imgUri).into(image);

        return convertView;
    }

}