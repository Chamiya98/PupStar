package com.example.pupstar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class VetShopDetailsActivity extends AppCompatActivity {

    //Intent intent = getIntent(); // gets the previously created intent
    //String firstKeyName = intent.getStringExtra("id"); // will return "FirstKeyValue"

    //String secondKeyName= intent.getStringExtra("secondKeyName"); // will return "SecondKeyValue"

    private ImageView btnBack, vetImage, direction;
    private ListView listView;
    private TextView title, desc, reviews, distance, detailTitle, address, openingTime;

    private ArrayList<CommentItem> arrayList = new ArrayList<>();

    private String mapUrl = "";
    String finalTitle = "";
    String finaldesc = "";
    String finalreviews = "";
    String finaldistance = "";
    String finaldetailTitle = "";
    String finaldaddress = "";
    String finaldopeningTime = "";

    String id;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_shop_details);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        //getSupportActionBar().setTitle();
        int castedid = Integer.parseInt(id);
        System.out.println("This is from New Intent:" + id);

        btnBack = (ImageView) this.findViewById(R.id.btnBack);
        vetImage = (ImageView) this.findViewById(R.id.vetImage);
        direction = (ImageView) this.findViewById(R.id.direction);

        listView = (ListView) this.findViewById(R.id.listView);

        title = (TextView) this.findViewById(R.id.title);
        desc = (TextView) this.findViewById(R.id.desc);
        reviews = (TextView) this.findViewById(R.id.reviews);
        distance = (TextView) this.findViewById(R.id.distance);
        detailTitle = (TextView) this.findViewById(R.id.detailTitle);
        address = (TextView) this.findViewById(R.id.address);
        openingTime = (TextView) this.findViewById(R.id.openingTime);

        title.setText("This is Text");
        desc.setText(finaldesc);
        reviews.setText(finalreviews);
        distance.setText(finaldistance);
        detailTitle.setText(finaldetailTitle);
        address.setText(finaldaddress);
        openingTime.setText(finaldopeningTime);


        //System.out.println(firstKeyName);
        mapUrl = "https://goo.gl/maps/KjZxLVHiDX12YoYS9";

        String URL = API.BASE_URL + "/getspecificclinicdata";

        CommentAdapter commentAdapter = new CommentAdapter(VetShopDetailsActivity.this, R.layout.row_comment_item, arrayList);
        listView.setAdapter(commentAdapter);
        //String image = getStringImage(bitmap);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        //params.put("behavior", DetectedBehavior);
        //params.put("user_id", Preferences.LOGGED_USER_ID);
        JSONObject parameter = new JSONObject(params);
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, parameter, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    JSONArray arr = jsonObject.getJSONArray("message");
                    System.out.println(arr);

                    int ClinicId = arr.getJSONArray(0).getInt(0);
                    String ClinicName = arr.getJSONArray(0).getString(1);
                    String adress = arr.getJSONArray(0).getString(2);
                    String imageref = arr.getJSONArray(0).getString(3);
                    //int goodComment = arr.getJSONArray(0).getInt(4);

                    String castedId = String.valueOf(ClinicId);

                    finalTitle = ClinicName;
                    //finalTitle = "";
                    finaldesc = "";
                    finalreviews = "";
                    finaldistance = "";
                    finaldetailTitle = "";
                    finaldaddress = adress;
                    finaldopeningTime = "";

                    Uri imgUri = Uri.parse(API.BASE_URL + "/static/images/images/team/" + String.valueOf(imageref));
                    Picasso.get().load(imgUri).into(vetImage);

                    System.out.println("Inside Request:" +finalTitle +finaldesc +finalreviews +finaldistance +finaldetailTitle +finaldaddress +finaldopeningTime);

                    title.setText(finalTitle);
                    desc.setText(finaldesc);
                    reviews.setText(finalreviews);
                    distance.setText(finaldistance);
                    detailTitle.setText(finaldetailTitle);
                    address.setText(finaldaddress);
                    openingTime.setText(finaldopeningTime);
                    //arrayList.add(new CommentItem(castedId, "User One", "Good vet... ", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
                    //commentAdapter.notifyDataSetChanged();




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VetShopDetailsActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });




        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObject);


        System.out.println("Outside Request:" +finalTitle +finaldesc +finalreviews +finaldistance +finaldetailTitle +finaldaddress +finaldopeningTime);

        showComments();
        try {
            getCommentList(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mapUrl.equals("")) {
                    Intent defaultBrowser = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER);
                    defaultBrowser.setData(Uri.parse(mapUrl));
                    startActivity(defaultBrowser);
                }

            }
        });

    }

    private void showComments()
    {

        arrayList.clear();
        listView.setAdapter(null);

        String URL = API.BASE_URL + "/getcommentsforclinic";

        CommentAdapter commentAdapter = new CommentAdapter(VetShopDetailsActivity.this, R.layout.row_comment_item, arrayList);
        listView.setAdapter(commentAdapter);
        //String image = getStringImage(bitmap);
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        //params.put("behavior", DetectedBehavior);
        //params.put("user_id", Preferences.LOGGED_USER_ID);
        JSONObject parameter = new JSONObject(params);
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, parameter, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    JSONArray arr = jsonObject.getJSONArray("message");
                    System.out.println(arr);
                    for (int i = 0; i < arr.length(); i++) {
                        int commentid = arr.getJSONArray(i).getInt(0);
                        String content = arr.getJSONArray(i).getString(1);
                        String name = arr.getJSONArray(i).getString(3);

                        String castedcommentId = String.valueOf(commentid);

                        //arrayList.add(new CommentItem(castedId, "User One", "Good vet... ", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
                        //commentAdapter.notifyDataSetChanged();
                        CommentAdapter commentAdapter2 = new CommentAdapter(VetShopDetailsActivity.this, R.layout.row_comment_item, arrayList);
                        listView.setAdapter(commentAdapter2);

                        arrayList.add(new CommentItem(castedcommentId, name, content, ""));
                        //arrayList.add(new CommentItem("2", "Pet Care", "Veterinary clinics", "https://i.pinimg.com/736x/13/44/a9/1344a98c1a7bd9788838922836d813c0.jpg"));

                        commentAdapter2.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VetShopDetailsActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });




        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObject);


    }

    private void getCommentList(String id) throws JSONException {

        //System.out.println(SelectedDogId);
        //System.out.println(DetectedBehavior);


    }

    class CommentItem {

        String id, title, comment, image;

        public CommentItem(String id, String title, String comment, String image) {
            this.id = id;
            this.title = title;
            this.comment = comment;
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getComment() {
            return comment;
        }

        public String getImage() {
            return image;
        }
    }

    class CommentAdapter extends ArrayAdapter<CommentItem> {

        private Context mContext;
        private int mResource;

        public CommentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CommentItem> objects) {
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
            TextView comment = (TextView) convertView.findViewById(R.id.comment);

            title.setText(getItem(position).getTitle());
            comment.setText(getItem(position).getComment());

            Uri imgUri = Uri.parse(getItem(position).getImage());
            Picasso.get().load(imgUri).into(image);

            return convertView;
        }


    }
}