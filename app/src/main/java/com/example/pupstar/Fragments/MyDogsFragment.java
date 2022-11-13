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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pupstar.API;
import com.example.pupstar.AddDogDetailsActivity;
import com.example.pupstar.PreferencesData;
import com.example.pupstar.PreviousBehaviorsActivity;
import com.example.pupstar.R;
import com.example.pupstar.VetShopDetailsActivity;
import com.example.pupstar.VeterinariansActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyDogsFragment extends Fragment {

    private ImageView btnAddDogDetails;
    private ListView listView;
    private ArrayList<MyDogItem> arrayList = new ArrayList<>();

    private BottomSheetDialog dialogBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_dogs, container, false);

//        Dialog box
        dialogBox = new BottomSheetDialog(getContext(), R.style.BottomSheetTheme);
        dialogBox.setContentView(R.layout.dialog_my_dog_item);

        listView = (ListView) view.findViewById(R.id.listView);
        btnAddDogDetails = (ImageView) view.findViewById(R.id.btnAddDog);

        showMyDogs();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                String selected = String.valueOf(arrayList.get(i).getId());
                ImageView dialogImage;
                TextView name, breed, foods, vitamins;

                dialogImage = dialogBox.findViewById(R.id.image);
                name = dialogBox.findViewById(R.id.name);
                breed = dialogBox.findViewById(R.id.breed);
                foods = dialogBox.findViewById(R.id.foods);
                vitamins = dialogBox.findViewById(R.id.vitamins);

                Uri imgUri = Uri.parse(arrayList.get(i).getImage());
                Picasso.get().load(imgUri).into(dialogImage);

                name.setText(arrayList.get(i).getName());
                breed.setText(arrayList.get(i).getBreed());
                foods.setText(arrayList.get(i).getFoods());
                vitamins.setText(arrayList.get(i).getVitamins());

                dialogBox.show();

            }
        });

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



        //arrayList.add(new MyDogItem("pet1", "Shadow", "German Shepherd", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg", "Meet, Fruits", "Omega, Calcium"));
        //arrayList.add(new MyDogItem("pet2", "Puppy", "German Shepherd", "https://i.pinimg.com/736x/13/44/a9/1344a98c1a7bd9788838922836d813c0.jpg", "Meet, Fruits, Royal Canin", "Omega, Calcium, C"));
        //myDogAdapter.notifyDataSetChanged();

        arrayList.clear();
        //arrayList2.clear();
        listView.setAdapter(null);

        //PreBehaviorAdapter preBehaviorAdapter = new PreBehaviorAdapter(PreviousBehaviorsActivity.this, R.layout.row_pre_behaviors_item, arrayList);
        //listView.setAdapter(preBehaviorAdapter);

        String URL = API.BASE_URL + "/getEntireDogList";
        //String URL_2 = API.BASE_URL + "/clinic_images";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //arrayList.add(new VetItem("2", "Funa Vet Clinic", "Veterinary clinics", "1.5Km", "75%", "25%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
                        MyDogAdapter myDogAdapter = new MyDogAdapter(getContext(), R.layout.row_my_dogs_item, arrayList);
                        listView.setAdapter(myDogAdapter);

                        String[] glarr = {};
                        //System.out.println("Testing");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray arr = jsonObject.getJSONArray("message");
                            //String data = jsonObject.getString("message");
                            System.out.println(arr);

                            for (int i = 0; i < arr.length(); i++) {
                                int t = 1;

                                String fullname = arr.getJSONArray(i).getString(0);
                                String breed = arr.getJSONArray(i).getString(1);
                                //String date = arr.getJSONArray(i).getString(2);
                                String imgref = arr.getJSONArray(i).getString(2);

                                String imagurl = API.BASE_URL + "/static/uploads/breed/" + imgref + "_breed.png";

                                String tt = String.valueOf(t);
                                //arrayList.add(new PreBehaviorItem(castedClinicId, ClinicName, adress, "1.5Km", castedgoodcommentpercentage + "%", castedbadcommentpercentage + "%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", imagurl));

                                //arrayList.add(new VetItem("1", "Funa Vet Clinic", "Veterinary clinics", "1.5Km", "75%", "25%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
                                arrayList.add(new MyDogItem(tt, fullname, breed, imagurl, "Meet, Fruits", "Omega, Calcium"));


                                myDogAdapter.notifyDataSetChanged();
                                /*System.out.println(arr.getJSONArray(i).getInt(2));
                                System.out.println(arr.getJSONArray(i).getInt(3));
                                System.out.println(arr.getJSONArray(i).getInt(4));*/


                                //int tempArray[] = new int[5];
                                //tempArray = arr.get(i);


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }

}

class MyDogItem {

    String id, name, breed, image, foods, vitamins;

    public MyDogItem(String id, String name, String breed, String image, String foods, String vitamins) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.image = image;
        this.foods = foods;
        this.vitamins = vitamins;
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

    public String getFoods() {
        return foods;
    }

    public String getVitamins() {
        return vitamins;
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