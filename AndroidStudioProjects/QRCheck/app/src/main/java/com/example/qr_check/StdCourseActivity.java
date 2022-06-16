package com.example.qr_check;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StdCourseActivity extends AppCompatActivity {

    List<StdCourse> list;
    RecyclerView recyclerView;
    StdRecycleAdapter stdRecycleAdapter;
    ImageView setting;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stdcourse);

        Intent intent = getIntent();
        String std_id = intent.getStringExtra("std_id");

        setting = (ImageView) findViewById(R.id.setting);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.std_recyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.151/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        SeverInterface apiInterface = retrofit.create(SeverInterface.class);
        apiInterface.getStdCourse(std_id).enqueue(new Callback<List<StdCourse>>() {
            @Override
            public void onResponse(Call<List<StdCourse>> call, Response<List<StdCourse>> response) {
                if(response.isSuccessful()) {
                    list = response.body();
                    stdRecycleAdapter = new StdRecycleAdapter(list, std_id);
                    recyclerView.setAdapter(stdRecycleAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<StdCourse>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        MySwipeHelper swipeHelper = new MySwipeHelper(StdCourseActivity.this, recyclerView, 300) {
            @Override
            public void instantiatrMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
                buffer.add(new MyButton(StdCourseActivity.this, "삭제", 30,
                        R.drawable.ic_baseline_delete_24, Color.parseColor("#FF3C30"), new MyButtonClickListener() {
                    @Override
                    public void onClick(int pos) {

                        AlertDialog.Builder dialog = new AlertDialog.Builder(StdCourseActivity.this);
                        dialog.setIcon(R.drawable.ic_baseline_notification_important_24);
                        dialog.setTitle("강의를 삭제하시겠습니까?");

                        dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                apiInterface.deleteStdCourse(std_id, list.get((viewHolder.getAbsoluteAdapterPosition())).course_name).enqueue(new Callback<List<StdCourse>>() {
                                    @Override
                                    public void onResponse(Call<List<StdCourse>> call, Response<List<StdCourse>> response) {
                                        if(response.isSuccessful()) {
                                            Toast.makeText(StdCourseActivity.this, "강의가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<StdCourse>> call, Throwable t) {
                                        System.out.println("실패");
                                        t.printStackTrace();
                                    }
                                });

                                list.remove(viewHolder.getAbsoluteAdapterPosition());
                                stdRecycleAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

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

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StdCourseActivity.this, CourseSearch.class);
                intent.putExtra("std_id", std_id);
                startActivity(intent);
            }
        });
    }

    public class StdCourse {

        @SerializedName("course_name")
        private String course_name;

        @SerializedName("pro_name")
        private String pro_name;

        @SerializedName("std_id")
        private String std_id;

        @SerializedName("course_room")
        private String course_room;

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

        public String getStd_id() {
            return std_id;
        }

        public void setStd_id(String std_id) {
            this.std_id = std_id;
        }

        public String getCourse_room() { return course_room; }

        public void setCourse_room(String course_room) { this.course_room = course_room; }
    }

    public class StdRecycleAdapter extends RecyclerView.Adapter<StdRecycleAdapter.ViewHolder> {

        private List<StdCourseActivity.StdCourse> mdata;
        String s_id;

        public StdRecycleAdapter(List<StdCourseActivity.StdCourse> list, String id) { mdata = list; s_id = id; }

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

                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(StdCourseActivity.this, ScanQR.class);
                        intent.putExtra("std_id", s_id);
                        intent.putExtra("course_name", name.getText().toString());
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
