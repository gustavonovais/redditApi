package com.novais.gustavo.redditapi.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.novais.gustavo.redditapi.R;
import com.novais.gustavo.redditapi.adapter.TimeLineAdapter;
import com.novais.gustavo.redditapi.databinding.ActivityTimeLineBinding;
import com.novais.gustavo.redditapi.model.Children;
import com.novais.gustavo.redditapi.model.TimeLine;
import com.novais.gustavo.redditapi.retrofit.ManagerRequest;

import java.sql.Time;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.RecyclerView.*;

public class TimeLineActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ActivityTimeLineBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_time_line);

        setUp();
    }

    public void setUp() {
        setSupportActionBar(binding.toolbar);
        Fresco.initialize(this);
        configRecycleView();
        listPosts();
        configSwipe();
    }

    private TimeLine timeLine;

    private void loadTimeLine(TimeLine timeLine) {
        this.timeLine = timeLine;
        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(this.timeLine.getData().getChildren());

        binding.recyclerView.setAdapter(timeLineAdapter);
        binding.recyclerView.getAdapter().notifyDataSetChanged();
    }

    LinearLayoutManager layoutManager;

    private void configRecycleView() {
        layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setNestedScrollingEnabled(false);
        binding.recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
    }

    public void configSwipe() {
        binding.swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        listPosts();
        binding.swipeRefreshLayout.setRefreshing(false);
    }


    private void listPosts() {
        ManagerRequest<TimeLine> mApi = new ManagerRequest<>();
        mApi.listPosts(new Callback<TimeLine>() {
            @Override
            public void onResponse(Call<TimeLine> call, Response<TimeLine> response) {
                if (response.isSuccessful()) {
                    loadTimeLine(response.body());
                }
            }

            @Override
            public void onFailure(Call<TimeLine> call, Throwable t) {
                Toast.makeText(TimeLineActivity.this, R.string.not_conection, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void listPostsPagination(String after){
        ManagerRequest<TimeLine> mApi = new ManagerRequest<>();
        mApi.listPostsPagination(new Callback<TimeLine>() {
            @Override
            public void onResponse(Call<TimeLine> call, Response<TimeLine> response) {
                if (response.isSuccessful()) {
                    binding.progressBar.setVisibility(View.GONE);
                    loadTimeLine(response.body());
                }
            }

            @Override
            public void onFailure(Call<TimeLine> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(TimeLineActivity.this, R.string.not_conection, Toast.LENGTH_LONG).show();
            }
        }, after);
    }

    private OnScrollListener recyclerViewOnScrollListener = new OnScrollListener() {
        private boolean loading = true;
        int pastVisiblesItems, visibleItemCount, totalItemCount;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) {
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        binding.progressBar.setVisibility(View.VISIBLE);
                        listPostsPagination(timeLine.getData().getAfter());
                        loading = true;
                    }
                }
            }
        }
    };

}
