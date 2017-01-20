package com.novais.gustavo.redditapi.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.novais.gustavo.redditapi.R;
import com.novais.gustavo.redditapi.databinding.RowBinding;
import com.novais.gustavo.redditapi.model.Children;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gustavo on 21/09/2016.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.MyViewHolder> {

    private List<Children> childrenList;
    private Activity activity;
    private RowBinding rowBinding;

    public TimeLineAdapter(Activity activity, List<Children> cadastroList) {
        this.childrenList = cadastroList;
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Children children = childrenList.get(position);

        holder.txtTitle.setText(children.data.title);
        holder.txtDecription.setText(children.data.selftext);

        Uri uri = Uri.parse(children.data.thumbnail);
        holder.img.setImageURI(uri);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return childrenList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public SimpleDraweeView img;
        public TextView txtDecription;

        public MyViewHolder(View view) {
            super(view);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtDecription = (TextView) view.findViewById(R.id.txtDescription);
            img = (SimpleDraweeView) view.findViewById(R.id.img);
        }
    }


}