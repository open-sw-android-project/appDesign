package com.example.qr_check;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.ncorti.slidetoact.SlideToActView;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CourseSearch extends AppCompatActivity {

    List list;
    RecyclerView recyclerView;
    RecycleAdapter recycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_search);

        Intent intent = getIntent();
        String str = intent.getStringExtra("std_id");

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.184/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SeverInterface apiInterface = retrofit.create(SeverInterface.class);
        apiInterface.getCourseInfo(str).enqueue(new Callback<List<Course>>() {

            @Override
            public void onResponse(Call<List<Course>> call, retrofit2.Response<List<Course>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    recycleAdapter = new RecycleAdapter(list, str);
                    recyclerView.setAdapter(recycleAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public class Course {

        @SerializedName("course_id")
        private String course_id;

        @SerializedName("course_name")
        private String course_name;

        @SerializedName("std_id")
        private String std_id;

        @SerializedName("pro_name")
        private String pro_name;

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getStd_id() {
            return std_id;
        }

        public void setStd_id(String std_id) {
            this.std_id = std_id;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public String getPro_name() {
            return pro_name;
        }

        public void setPro_name(String pro_name) {
            this.pro_name = pro_name;
        }

    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

        private List<CourseSearch.Course> mdata;
        String id;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.151/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SeverInterface apiInterface = retrofit.create(SeverInterface.class);

        public RecycleAdapter(List<CourseSearch.Course> list, String str) { mdata = list; id = str;}

        @Override
        public RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
            RecycleAdapter.ViewHolder vh = new RecycleAdapter.ViewHolder(view);

            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.lecture_name.setText(mdata.get(position).getCourse_name() + "--" + mdata.get(position).getPro_name());
        }

        @Override
        public int getItemCount() {
            return mdata.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            SlideToActView lecture_name;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                lecture_name = (SlideToActView) itemView.findViewById(R.id.course_name);
                lecture_name.setAnimateCompletion(true);

                lecture_name.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
                    @Override
                    public void onSlideComplete(SlideToActView slideToActView) {

                        String[] str = lecture_name.getText().toString().split("-");

                        apiInterface.putCourseInfo(id, str[0]).enqueue(new Callback<List<Course>>() {
                            @Override
                            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                                System.out.println("성공");
                            }

                            @Override
                            public void onFailure(Call<List<Course>> call, Throwable t) {
                                System.out.println("실패");
                                t.printStackTrace();
                            }
                        });

                        ViewHolder viewHolder = new ViewHolder(itemView);
                        mdata.remove(viewHolder.getAbsoluteAdapterPosition() + 1);
                        recycleAdapter.notifyItemRemoved(getAdapterPosition());
                    }
                });
            }
        }
    }
}
