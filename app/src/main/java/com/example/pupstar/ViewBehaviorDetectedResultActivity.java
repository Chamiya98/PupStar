package com.example.pupstar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewBehaviorDetectedResultActivity extends AppCompatActivity {

    private ImageView setImage;
    private ListView listView;
    private TextView mood;
    private CardView veterinary;
    private ImageView btnBack;

    private ArrayList<BehaviorItem> arrayList = new ArrayList<>();

    private Bitmap bitmap = null;

    private BottomSheetDialog detailDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_behavior_detected_result);

        Intent intent = getIntent();
        bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");

        detailDialog = new BottomSheetDialog(ViewBehaviorDetectedResultActivity.this, R.style.BottomSheetTheme);
        detailDialog.setContentView(R.layout.dialog_box_behabior_details);

        setImage = (ImageView) this.findViewById(R.id.setImage);
        listView = (ListView) this.findViewById(R.id.listView);
        mood = (TextView) this.findViewById(R.id.mood);
        veterinary = (CardView) this.findViewById(R.id.veterinary);

        btnBack = (ImageView) findViewById(R.id.btnBack);

        Uri imgUri = Uri.parse("https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F47%2F2020%2F08%2F06%2Frottweiler-headshot-678833089-2000.jpg");
        Picasso.get().load(imgUri).into(setImage);

        mood.setText("Mood Happy");

//        bitmap = (Bitmap) MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//        setImage.setImageBitmap(bitmap);

        showBehaviors();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        veterinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewBehaviorDetectedResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                String selectTile = arrayList.get(i).getTitle();
                String selectDesc = arrayList.get(i).getDescription();

                TextView title, desc;
                detailDialog.show();

                title = (TextView) detailDialog.findViewById(R.id.title);
                desc = (TextView) detailDialog.findViewById(R.id.desc);

                title.setText(selectTile);
                desc.setText(selectDesc);
            }
        });

    }

    private void showBehaviors()
    {
        arrayList.clear();
        listView.setAdapter(null);

        BehaviorAdapter myDogAdapter = new BehaviorAdapter(ViewBehaviorDetectedResultActivity.this, R.layout.row_behaviors_item, arrayList);
        listView.setAdapter(myDogAdapter);

        arrayList.add(new BehaviorItem("1", "A Good Diet", "Need good diet", "https://img.freepik.com/premium-vector/bowl-food-dog-cat-pet-flat-style-vector-illustration-animal-bowl-silhouette-print_501826-267.jpg?w=2000"));
        arrayList.add(new BehaviorItem("2", "Exercise", "Need good exercise", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRcIygi3-IUvIW0VeGSI45FU_OSutWHJVRhOW9oKX934Q&s"));

        myDogAdapter.notifyDataSetChanged();
    }

}

class BehaviorItem {

    String id, title, description, image;

    public BehaviorItem(String id, String title, String description, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}

class BehaviorAdapter extends ArrayAdapter<BehaviorItem> {

    private Context mContext;
    private int mResource;

    public BehaviorAdapter(@NonNull Context context, int resource, @NonNull ArrayList<BehaviorItem> objects) {
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

        title.setText(getItem(position).getTitle());

        Uri imgUri = Uri.parse(getItem(position).getImage());
        Picasso.get().load(imgUri).into(image);

        return convertView;
    }

}