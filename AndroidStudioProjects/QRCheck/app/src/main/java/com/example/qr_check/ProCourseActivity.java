package com.example.qr_check;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProCourseActivity extends AppCompatActivity {

    List<ProCourse> list;
    RecyclerView recyclerView;
    ProRecycleAdapter proRecycleAdapter;
    private Button CourseManager;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procourse);

        Intent intent = getIntent();
        String pro_name = intent.getStringExtra("pro_name");

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.pro_recyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.25.44/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SeverInterface apiInterface = retrofit.create(SeverInterface.class);
        apiInterface.getProCourse(pro_name).enqueue(new Callback<List<ProCourse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProCourse>> call, @NonNull Response<List<ProCourse>> response) {
                if(response.isSuccessful()) {
                    list = response.body();
                    proRecycleAdapter = new ProRecycleAdapter(list);
                    recyclerView.setAdapter(proRecycleAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<ProCourse>> call, Throwable t) {
                t.printStackTrace();
            }
         });

        MySwipeHelper swipeHelper = new MySwipeHelper(ProCourseActivity.this, recyclerView, 300) {
            @Override
            public void instantiatrMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
                buffer.add(new MyButton(ProCourseActivity.this, "삭제", 30,
                        R.drawable.ic_baseline_delete_24, Color.parseColor("#FF3C30"), new MyButtonClickListener() {
                    @Override
                    public void onClick(int pos) {

                        AlertDialog.Builder dialog = new AlertDialog.Builder(ProCourseActivity.this);
                        dialog.setIcon(R.drawable.ic_baseline_notification_important_24);
                        dialog.setTitle("강의를 삭제하시겠습니까?");

                        dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                apiInterface.deleteProCourse(pro_name, list.get((viewHolder.getAbsoluteAdapterPosition())).course_name).enqueue(new Callback<List<ProCourse>>() {
                                    @Override
                                    public void onResponse(Call<List<ProCourse>> call, Response<List<ProCourse>> response) {
                                        if(response.isSuccessful()) {
                                            System.out.println("성공");
                                            Toast.makeText(ProCourseActivity.this, "강의가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<ProCourse>> call, Throwable t) {
                                        System.out.println("실패");
                                        t.printStackTrace();
                                    }
                                });

                                list.remove(viewHolder.getAbsoluteAdapterPosition());
                                proRecycleAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                                dialog.dismiss();
                            }
                        });

                        dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                }));
            }
        };

        CourseManager = (Button)findViewById(R.id.course_manager);
        CourseManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProCourseActivity.this, CourseRegister.class);
                startActivity(intent);
            }
        });
    }

    public class ProCourse {

        @SerializedName("course_name")
        private String course_name;

        @SerializedName("pro_name")
        private String pro_name;

        @SerializedName("course_room")
        private String course_room;

        public String getCourse_name() { return course_name; }

        public void setCourse_name(String course_name) { this.course_name = course_name; }

        public String getPro_name() { return pro_name; }

        public void setPro_name(String pro_name) { this.pro_name = pro_name; }

        public String getCourse_room() { return course_room; }

        public void setCourse_room(String course_room) { this.course_room = course_room; }
    }
}
