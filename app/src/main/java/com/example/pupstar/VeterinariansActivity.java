package com.example.pupstar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class VeterinariansActivity extends AppCompatActivity {

    private ChipGroup chipGroup;
//    private EditText search;
    private ImageView btnBack;

    private ListView listView;
    private ArrayList<VetItem> arrayList = new ArrayList<>();
    private ArrayList<VetItem> arrayList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterinarians);

        chipGroup = (ChipGroup) this.findViewById(R.id.chipGroup);

        listView = (ListView) this.findViewById(R.id.listView);
//        search = (EditText) this.findViewById(R.id.search);
        btnBack = (ImageView) this.findViewById(R.id.btnBack);

        showClinics();

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
                System.out.println("This is slected Id:" +selected);
                Intent intent = new Intent(VeterinariansActivity.this, VetShopDetailsActivity.class);
                intent.putExtra("id", selected);
                startActivity(intent);

            }
        });

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {

                Chip chip = chipGroup.findViewById(i);
                if (chip != null) {
                    String selected = chip.getText().toString();

                    if (selected.equals("Clinics")) {
                        showClinics();
                    } else {
                        showShops();
                    }

                }
            }
        });

//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                String value = charSequence.toString().toLowerCase();
//
//                arrayList.clear();
//                listView.setAdapter(null);
//
//                VetAdapter vetAdapter = new VetAdapter(VeterinariansActivity.this, R.layout.row_vet_item, arrayList);
//                listView.setAdapter(vetAdapter);
//
//                if (!value.equals("")) {
//                    for (int l = 0; l < arrayList2.size(); l++) {
//                        if (arrayList2.get(l).getTitle().toLowerCase().contains(value)) {
//                            arrayList.add(arrayList2.get(l));
//                        }
//                    }
//                } else {
//                    for (int l = 0; l < arrayList2.size(); l++) {
//                        arrayList.add(arrayList2.get(l));
//                    }
//                }
//
//                vetAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        this.initSearchWidget();

    }

    private void initSearchWidget()
    {
        SearchView searchView = (SearchView) findViewById(R.id.searchViewMain);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<VetItem> filteredVets = new ArrayList<VetItem>();

                for(VetItem vet : arrayList)
                {
                    if(vet.getTitle().toLowerCase().contains(s.toLowerCase()))
                    {
                        filteredVets.add(vet);
                    }
                }
                VetAdapter va = new VetAdapter(VeterinariansActivity.this, R.layout.row_vet_item, filteredVets);
                listView.setAdapter(va);
                return false;
            }
        });
    }

    private void showClinics() {

        arrayList.clear();
        arrayList2.clear();
        listView.setAdapter(null);

        VetAdapter vetAdapter = new VetAdapter(VeterinariansActivity.this, R.layout.row_vet_item, arrayList);
        listView.setAdapter(vetAdapter);

        String URL = API.BASE_URL + "/getCinicList";
        String URL_2 = API.BASE_URL + "/clinic_images";
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
                                int ClinicId = arr.getJSONArray(i).getInt(0);
                                String ClinicName = arr.getJSONArray(i).getString(1);
                                String adress = arr.getJSONArray(i).getString(2);
                                String imageref = arr.getJSONArray(i).getString(3);
                                int goodComment = arr.getJSONArray(i).getInt(4);
                                int badCommentCount = arr.getJSONArray(i).getInt(5);
                                int unknowncm = arr.getJSONArray(i).getInt(6);
                                int all_count = arr.getJSONArray(i).getInt(7);
                                //String ClinicName = arr.getJSONArray(i).getString(5);
                                //String ClinicAddress = arr.getJSONArray(i).getString(6);

                                float doublegm = Float.valueOf(goodComment);
                                float doublebm = Float.valueOf(badCommentCount);
                                float doubleum = Float.valueOf(unknowncm);
                                float doubleall = Float.valueOf(all_count);


                                double goodcommentpercentage = ((doublegm) / (doubleall)) * 100;
                                //System.out.println(goodcommentpercentage);
                                double badcommentpercentage = ((doublebm) / (doubleall)) * 100;
                                double unknowncommentpercentage = ((doubleum) / (doubleall)) * 100;
                                //System.out.println("clinic_ID"+ClinicId_FK);
                                //Casting Var
                                String castedClinicId = String.valueOf(ClinicId);
                                //String castedGoodCommentCount = String.valueOf(GoodCommentCount);
                                //String castedBadCommentCount = String.valueOf(BadCommentCount);
                                //String castedUnknownCommentCount = String.valueOf(UnknownCommentCount);
                                //String castedClinicId_FK = String.valueOf(ClinicId_FK);
                                String castedgoodcommentpercentage = String.valueOf(new DecimalFormat("##.##").format(goodcommentpercentage));
                                //System.out.println("This is good comment"+castedgoodcommentpercentage);
                                String castedbadcommentpercentage = String.valueOf(new DecimalFormat("##.##").format(badcommentpercentage));
                                String castedunknowncommentpercentage = String.valueOf(unknowncommentpercentage);
                                String imagurl = API.BASE_URL + "/static/images/images/team/" + String.valueOf(imageref);
                                System.out.println("image URL:" +imagurl);
                                arrayList.add(new VetItem(castedClinicId, ClinicName, adress, "1.5Km", castedgoodcommentpercentage + "%", castedbadcommentpercentage + "%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", imagurl));
                                //arrayList.add(new VetItem("1", "Funa Vet Clinic", "Veterinary clinics", "1.5Km", "75%", "25%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
                                vetAdapter.notifyDataSetChanged();
                                /*System.out.println(arr.getJSONArray(i).getInt(2));
                                System.out.println(arr.getJSONArray(i).getInt(3));
                                System.out.println(arr.getJSONArray(i).getInt(4));*/


                                //int tempArray[] = new int[5];
                                //tempArray = arr.get(i);


                            }
                           /* for(int i = 0; i< arr.length();i++){
                                System.out.println(arr.get(i));
                                int tempArray[] = new int[5];
                                tempArray = arr.get(i);

                                for(int j = 0; j< 5 ;j++){
                                    System.out.println("A");
                                    System.out.println(tempArray[2]);
                                }

//                                String tempArray[] = new String[5];
                               *//* if (arr != null) {
                                    int len = arr.length();
                                    for (int r=0;r<5;r++){
                                        tempArray[r] =arr.get(r).toString();

                                    }
                                    System.out.println("B");
                                    System.out.println(tempArray[0]);
                                    for(int j = 0; j< 5 ;j++){
                                        System.out.println("A");
                                        System.out.println(tempArray[2]);
                                    }

                                }*//*

                                //arrayList.add(new VetItem("1", "Funa Vet Clinic", "Veterinary clinics", "1.5Km", "75%", "25%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));

//                                for(int j = 0; j< 5;j++){
//                                    System.out.println(arr[i][j]);
//                                }
                                //arrayList.add(new VetItem("1", "Funa Vet Clinic", "Veterinary clinics", "1.5Km", "75%", "25%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));

                            }*/
                            //int indext  = data.indexOf(1);
                            //System.out.println("Index 01 : "+arr);
                            //System.out.println(arr);
                            //TextView textView = (TextView)findViewById(R.id.textView);
                            //textView.setText(data);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VeterinariansActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


        };
        RequestQueue queue = Volley.newRequestQueue(VeterinariansActivity.this);
        queue.add(stringRequest);

        //arrayList.add(new VetItem("1", "Funa Vet Clinic", "Veterinary clinics", "1.5Km", "75%", "25%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
        //arrayList.add(new VetItem("2", "Pet Care", "Veterinary clinics", "2Km", "65%", "35%", "https://goo.gl/maps/DERVxZwdDYxAwyUe8", "https://i.pinimg.com/736x/13/44/a9/1344a98c1a7bd9788838922836d813c0.jpg"));

        //arrayList2.add(new VetItem("1", "Funa Vet Clinic", "Veterinary clinics", "1.5Km", "75%", "25%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
        //arrayList2.add(new VetItem("2", "Pet Care", "Veterinary clinics", "2Km", "65%", "35%", "https://goo.gl/maps/DERVxZwdDYxAwyUe8", "https://i.pinimg.com/736x/13/44/a9/1344a98c1a7bd9788838922836d813c0.jpg"));

        vetAdapter.notifyDataSetChanged();

    }

    private void showShops() {
        arrayList.clear();
        arrayList2.clear();
        listView.setAdapter(null);

        VetAdapter vetAdapter = new VetAdapter(VeterinariansActivity.this, R.layout.row_vet_item, arrayList);
        listView.setAdapter(vetAdapter);

        arrayList.add(new VetItem("1", "Funa Vet Clinic Shop", "Veterinary clinics", "1.5Km", "75%", "25%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
        arrayList.add(new VetItem("2", "Pet Care Shop", "Veterinary clinics", "2Km", "65%", "35%", "https://goo.gl/maps/DERVxZwdDYxAwyUe8", "https://i.pinimg.com/736x/13/44/a9/1344a98c1a7bd9788838922836d813c0.jpg"));

        arrayList2.add(new VetItem("1", "Funa Vet Clinic Shop", "Veterinary clinics", "1.5Km", "75%", "25%", "https://goo.gl/maps/KjZxLVHiDX12YoYS9", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
        arrayList2.add(new VetItem("2", "Pet Care Shop", "Veterinary clinics", "2Km", "65%", "35%", "https://goo.gl/maps/DERVxZwdDYxAwyUe8", "https://i.pinimg.com/736x/13/44/a9/1344a98c1a7bd9788838922836d813c0.jpg"));

        vetAdapter.notifyDataSetChanged();
    }

}

class VetItem {

    String id, title, type, distance, good, bad, mapUrl, image;

    public VetItem(String id, String title, String type, String distance, String good, String bad, String mapUrl, String image) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.distance = distance;
        this.good = good;
        this.bad = bad;
        this.mapUrl = mapUrl;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDistance() {
        return distance;
    }

    public String getGood() {
        return good;
    }

    public String getBad() {
        return bad;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public String getImage() {
        return image;
    }
}

class VetAdapter extends ArrayAdapter<VetItem> {

    private Context mContext;
    private int mResource;

    public VetAdapter(@NonNull Context context, int resource, @NonNull ArrayList<VetItem> objects) {
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
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView type = (TextView) convertView.findViewById(R.id.type);
        TextView distance = (TextView) convertView.findViewById(R.id.distance);
        TextView good = (TextView) convertView.findViewById(R.id.good);
        TextView bad = (TextView) convertView.findViewById(R.id.bad);

        title.setText(getItem(position).getTitle());
        type.setText(getItem(position).getType());
        distance.setText(getItem(position).getDistance());
        good.setText(getItem(position).getGood());
        bad.setText(getItem(position).getBad());

        Uri imgUri = Uri.parse(getItem(position).getImage());
        Picasso.get().load(imgUri).into(image);

        return convertView;
    }

}