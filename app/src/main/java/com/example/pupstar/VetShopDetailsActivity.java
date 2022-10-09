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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VetShopDetailsActivity extends AppCompatActivity {

    private ImageView btnBack, vetImage, direction;
    private ListView listView;
    private TextView title, desc, reviews, distance, detailTitle, address, openingTime;

    private ArrayList<CommentItem> arrayList = new ArrayList<>();

    private String mapUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_shop_details);

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

        mapUrl = "https://goo.gl/maps/KjZxLVHiDX12YoYS9";

        title.setText("Pet New Care");
        desc.setText("Veterinary Dentist");
        reviews.setText("125 Reviews");
        distance.setText("1.5 km");
        detailTitle.setText("Veterinary clinic 'Alden-Vet'");
        address.setText("141 N Union Ave, Los Angeles, CA");
        openingTime.setText("Wed 9 Sep — 10:30 am");

        Uri imgUri = Uri.parse("https://i.pinimg.com/736x/13/44/a9/1344a98c1a7bd9788838922836d813c0.jpg");
        Picasso.get().load(imgUri).into(vetImage);

        showComments();

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

        CommentAdapter commentAdapter = new CommentAdapter(VetShopDetailsActivity.this, R.layout.row_comment_item, arrayList);
        listView.setAdapter(commentAdapter);

        arrayList.add(new CommentItem("1", "User One", "Good vet... ", "https://www.thesprucepets.com/thmb/vR6i92pOyYmL6FEH3yQXHtR4HAA=/941x0/filters:no_upscale():max_bytes(150000):strip_icc():format(webp)/names-for-german-shepherds-4797840-hero-ed34431ad20c42c6894b4a29765b4d68.jpg"));
        arrayList.add(new CommentItem("2", "Pet Care", "Veterinary clinics", "https://i.pinimg.com/736x/13/44/a9/1344a98c1a7bd9788838922836d813c0.jpg"));

        commentAdapter.notifyDataSetChanged();
    }

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