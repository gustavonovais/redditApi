package com.novais.gustavo.redditapi.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.novais.gustavo.redditapi.R;
import com.novais.gustavo.redditapi.databinding.ActivityDetailPostBinding;
import com.novais.gustavo.redditapi.model.Children;
import com.novais.gustavo.redditapi.model.ParamKey;

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

    private void configTitle() {
        if (children != null && children.getData() != null) {
            binding.txtTitle.setText(children.getData().getTitle());
        }
    }

    private void configLink() {
        if (children != null && children.getData() != null) {
            binding.txtLink.setText(children.getData().getUrl());
        }
        binding.txtLink.setOnClickListener(this);
    }

    private void configText() {
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

    private Children getChildren() {
        return (Children) getIntent().getSerializableExtra(ParamKey.CHILDREN);
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

    private void linkClick() {
        if (children != null && children.getData() != null) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(children.getData().getUrl()));
            startActivity(i);
        }
    }
}
