package com.novais.gustavo.redditapi.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.novais.gustavo.redditapi.R;
import com.novais.gustavo.redditapi.activities.DetailPostActivity;
import com.novais.gustavo.redditapi.model.Children;
import com.novais.gustavo.redditapi.model.ParamKey;

import java.util.List;

/**
 * Created by gustavo on 21/09/2016.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.MyViewHolder>  {

    public final List<Children> childrenList;

    public TimeLineAdapter(List<Children> childrenList) {
        this.childrenList = childrenList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Children children = childrenList.get(position);

        holder.txtTitle.setText(children.getData().getTitle());
        holder.txtDecription.setText(children.getData().getSelftext());

        Uri uri = Uri.parse(children.getData() .getThumbnail());
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

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView txtTitle;
        public final SimpleDraweeView img;
        public final TextView txtDecription;

        public MyViewHolder(View view ) {
            super(view);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtDecription = (TextView) view.findViewById(R.id.txtDescription);
            img = (SimpleDraweeView) view.findViewById(R.id.img);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int postition = getAdapterPosition();

            Intent intent = new Intent(v.getContext(), DetailPostActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ParamKey.CHILDREN, childrenList.get(postition));
            intent.putExtras(bundle);

            v.getContext().startActivity(intent);
        }
    }

}