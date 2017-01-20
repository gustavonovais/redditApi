package com.novais.gustavo.redditapi.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.novais.gustavo.redditapi.R;
import com.novais.gustavo.redditapi.adapter.TimeLineAdapter;
import com.novais.gustavo.redditapi.databinding.ActivityTimeLineBinding;
import com.novais.gustavo.redditapi.model.TimeLine;
import com.novais.gustavo.redditapi.retrofit.ManagerRequest;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeLineActivity extends AppCompatActivity {

    private ActivityTimeLineBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_time_line);

        setUp();
    }
    public void setUp(){
        setSupportActionBar(binding.toolbar);
        Fresco.initialize(this);
        listPosts();
    }

    private void listPosts() {
        ManagerRequest<TimeLine> mApi = new ManagerRequest<>();
        mApi.listPosts(new Callback<TimeLine>() {
            @Override
            public void onResponse(Call<TimeLine> call, Response<TimeLine> response) {
                loadTimeLine(response.body());
            }

            @Override
            public void onFailure(Call<TimeLine> call, Throwable t) {
                Toast.makeText(TimeLineActivity.this, R.string.not_conection, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadTimeLine(TimeLine timeLine) {
        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(timeLine.getData().getChildren());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(timeLineAdapter);
        binding.recyclerView.getAdapter().notifyDataSetChanged();
    }
}
