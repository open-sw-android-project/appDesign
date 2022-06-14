package com.example.qr_check;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProRecycleAdapter extends RecyclerView.Adapter<ProRecycleAdapter.ViewHolder> {

    private List<ProCourseActivity.ProCourse> mdata;

    public ProRecycleAdapter(List<ProCourseActivity.ProCourse> list) { mdata = list; }

    @Override
    public ProRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.pro_recyclerview, parent, false);
        ProRecycleAdapter.ViewHolder vh = new ProRecycleAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ProRecycleAdapter.ViewHolder holder, int position) {
        holder.course_name.setText(mdata.get(position).getCourse_name());
        holder.course_room.setText(mdata.get(position).getCourse_room());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView course_name, course_room;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            course_name = (TextView) itemView.findViewById(R.id.p_name);
            course_room = (TextView) itemView.findViewById(R.id.p_room);
        }
    }
}
