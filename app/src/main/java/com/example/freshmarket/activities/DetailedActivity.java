package com.example.freshmarket.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.freshmarket.R;
import com.example.freshmarket.models.ViewAllModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailedActivity extends AppCompatActivity {


    int totalQuantity = 1;
    int totalPrice = 0;
    TextView quantity;
    ImageView detailedImg;
    TextView price, rating,description;
    Button addToCart;
    ImageView addItem, removeItem;
    Toolbar toolbar;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    ViewAllModel viewAllModel = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if(object instanceof ViewAllModel){
            viewAllModel = (ViewAllModel) object;
        }

        quantity = findViewById(R.id.quantity);
        detailedImg = findViewById(R.id.detailed_img);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);

        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_dec);


        if(viewAllModel != null) {
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            price.setText("Price :$" + viewAllModel.getPrice() + "/kg");

            totalPrice = viewAllModel.getPrice() * totalQuantity;

            if(viewAllModel.getType().equals("egg")){
                price.setText("Price :$"+viewAllModel.getPrice()+"/dozen");
                totalPrice = viewAllModel.getPrice() * totalQuantity;
            }

            if(viewAllModel.getType().equals("milk")){
                price.setText("Price :$"+viewAllModel.getPrice()+"/litre");
                totalPrice = viewAllModel.getPrice() * totalQuantity;
            }
        }

        addToCart = findViewById(R.id.add_to_cart);
    }
}