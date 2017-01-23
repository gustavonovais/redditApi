package com.novais.gustavo.redditapi.activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import com.novais.gustavo.redditapi.BuildConfig;
import com.novais.gustavo.redditapi.R;
import com.novais.gustavo.redditapi.databinding.ActivityDetailPostBinding;
import com.novais.gustavo.redditapi.databinding.ActivityTimeLineBinding;
import com.novais.gustavo.redditapi.model.Children;

public class DetailPostActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityDetailPostBinding binding;
    private Children children;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_post);

        children = getChildren();
        configTitle();
        configLink();
        configText();
    }

    public void configTitle() {
        if (children != null && children.getData() != null) {
            binding.txtTitle.setText(children.getData().getTitle());
        }
    }

    public void configLink() {
        if (children != null && children.getData() != null) {
            binding.txtLink.setText(children.getData().getUrl());
        }
        binding.txtLink.setOnClickListener(this);
    }

    public void configText() {
        if (children != null && children.getData() != null) {
            if (!children.getData().getSelftext().isEmpty()) {
                binding.txtSelfText.setVisibility(View.VISIBLE);
                binding.txtSelfText.setText(children.getData().getSelftext());
            } else {
                binding.txtSelfText.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Children getChildren() {
        return (Children) getIntent().getSerializableExtra("Children");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtLink:
                linkClick();
                break;
            default:
                break;
        }
    }

    public void linkClick() {
        if (children != null && children.getData() != null) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(children.getData().getUrl()));
            startActivity(i);
        }
    }
}
