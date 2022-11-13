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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PreviousBehaviorsActivity extends AppCompatActivity {

    private ListView listView;
    private EditText search;
    private ImageView btnBack;

    private ArrayList<PreBehaviorItem> arrayList = new ArrayList<>();
    private ArrayList<PreBehaviorItem> arrayList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_behaviors);

        listView = (ListView) this.findViewById(R.id.listView);

        search = (EditText) this.findViewById(R.id.search);
        btnBack = (ImageView) findViewById(R.id.btnBack);

        showPreBehaviors();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                String selected = String.valueOf(arrayList.get(i).getImage());
                //String imgval = String.valueOf(arrayList.get(3));
                Intent intent = new Intent(PreviousBehaviorsActivity.this, ViewBehaviorDetectedResultActivity.class);
                intent.putExtra("id", selected);
                //intent.putExtra("imageRef", imgval);
                intent.putExtra("isCreated", "no");
                //intent.putExtra("behimgref", imgval);
                //intent.putExtra("imgref", )
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

                PreBehaviorAdapter preBehaviorAdapter = new PreBehaviorAdapter(PreviousBehaviorsActivity.this, R.layout.row_pre_behaviors_item, arrayList);
                listView.setAdapter(preBehaviorAdapter);

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

                preBehaviorAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void showPreBehaviors()
    {


        arrayList.clear();
        arrayList2.clear();
        listView.setAdapter(null);

        PreBehaviorAdapter preBehaviorAdapter = new PreBehaviorAdapter(PreviousBehaviorsActivity.this, R.layout.row_pre_behaviors_item, arrayList);
        listView.setAdapter(preBehaviorAdapter);

        String URL = API.BASE_URL + "/getPreviousBehaviors";
        //String URL_2 = API.BASE_URL + "/clinic_images";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //arrayList.add(new VetItem("2", "Funa Vet Clinic", "Veterinary clinics", "1.5Km", "75%", "25%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));


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
                                String behavior = arr.getJSONArray(i).getString(1);
                                //String date = arr.getJSONArray(i).getString(2);
                                String imgref = arr.getJSONArray(i).getString(3);

                                String imagurl = API.BASE_URL + "/static/uploads/behavior/" + imgref + "_breed.png";

                                String tt = String.valueOf(t);
                                //arrayList.add(new PreBehaviorItem(castedClinicId, ClinicName, adress, "1.5Km", castedgoodcommentpercentage + "%", castedbadcommentpercentage + "%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", imagurl));

                                //arrayList.add(new VetItem("1", "Funa Vet Clinic", "Veterinary clinics", "1.5Km", "75%", "25%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
                                arrayList.add(new PreBehaviorItem( tt, behavior, fullname, imagurl));

                                preBehaviorAdapter.notifyDataSetChanged();
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
                        Toast.makeText(PreviousBehaviorsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


        };
        RequestQueue queue = Volley.newRequestQueue(PreviousBehaviorsActivity.this);
        queue.add(stringRequest);

        //arrayList.add(new VetItem("1", "Funa Vet Clinic", "Veterinary clinics", "1.5Km", "75%", "25%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
        //arrayList.add(new VetItem("2", "Pet Care", "Veterinary clinics", "2Km", "65%", "35%", "https://goo.gl/maps/DERVxZwdDYxAwyUe8", "https://i.pinimg.com/736x/13/44/a9/1344a98c1a7bd9788838922836d813c0.jpg"));

        //arrayList2.add(new VetItem("1", "Funa Vet Clinic", "Veterinary clinics", "1.5Km", "75%", "25%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
        //arrayList2.add(new VetItem("2", "Pet Care", "Veterinary clinics", "2Km", "65%", "35%", "https://goo.gl/maps/DERVxZwdDYxAwyUe8", "https://i.pinimg.com/736x/13/44/a9/1344a98c1a7bd9788838922836d813c0.jpg"));

        //preBehaviorAdapter.notifyDataSetChanged();
       // arrayList.clear();
        //listView.setAdapter(null);

        /*PreBehaviorAdapter preBehaviorAdapter = new PreBehaviorAdapter(PreviousBehaviorsActivity.this, R.layout.row_pre_behaviors_item, arrayList);
        listView.setAdapter(preBehaviorAdapter);

        arrayList.add(new PreBehaviorItem("1", "Happy Mood", "German Shepherd", "Need good diet", "https://img.freepik.com/premium-vector/bowl-food-dog-cat-pet-flat-style-vector-illustration-animal-bowl-silhouette-print_501826-267.jpg?w=2000"));
        arrayList.add(new PreBehaviorItem("2", "Angry Mood", "Lion Shepherd", "Need play",  "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRcIygi3-IUvIW0VeGSI45FU_OSutWHJVRhOW9oKX934Q&s"));

        arrayList2.add(new PreBehaviorItem("1", "Happy Mood", "German Shepherd", "Need good diet", "https://img.freepik.com/premium-vector/bowl-food-dog-cat-pet-flat-style-vector-illustration-animal-bowl-silhouette-print_501826-267.jpg?w=2000"));
        arrayList2.add(new PreBehaviorItem("2", "Angry Mood", "Lion Shepherd", "Need play",  "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRcIygi3-IUvIW0VeGSI45FU_OSutWHJVRhOW9oKX934Q&s"));
*/
        //preBehaviorAdapter.notifyDataSetChanged();
    }
}

class PreBehaviorItem {

    String id, title, breed, description, image;

    public PreBehaviorItem(String id, String title, String breed, String image) {
        this.id = id;
        this.title = title;
        this.breed = breed;
        //this.description = description;
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

class PreBehaviorAdapter extends ArrayAdapter<PreBehaviorItem> {

    private Context mContext;
    private int mResource;

    public PreBehaviorAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PreBehaviorItem> objects) {
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