package com.example.qr_check;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StdRecycleAdapter extends RecyclerView.Adapter<StdRecycleAdapter.ViewHolder> {

    private List<StdCourseActivity.StdCourse> mdata;

    public StdRecycleAdapter(List<StdCourseActivity.StdCourse> list) { mdata = list; }

    @Override
    public StdRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.std_recyclerview, parent, false);
        StdRecycleAdapter.ViewHolder vh = new StdRecycleAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(StdRecycleAdapter.ViewHolder holder, int position) {
        holder.name.setText(mdata.get(position).getCourse_name());
        holder.room.setText(mdata.get(position).getCourse_room());
    }

    @Override
    public int getItemCount() { return mdata.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name, room;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            room = itemView.findViewById(R.id.room);
        }
    }
}
