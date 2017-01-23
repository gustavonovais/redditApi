package com.novais.gustavo.redditapi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.novais.gustavo.redditapi.R;
import com.novais.gustavo.redditapi.model.Children;

public class DetailPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        getChildren();
    }

    public Children getChildren(){
        return  (Children) getIntent().getSerializableExtra("Children");
    }
}
