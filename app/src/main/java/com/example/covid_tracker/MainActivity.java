package com.example.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView label;
    ImageView refresh;
    ProgressBar pg;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<Model> modelList;
    JavaAdapter jad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        label = findViewById(R.id.textView1);
        refresh = findViewById(R.id.refresh);

        loadData();
        initRecyclerView();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                initRecyclerView();
            }
        });
    }

    private void loadData()
    {
        String url = "https://api.rootnet.in/covid19-in/stats/latest";
        modelList = new ArrayList<>();
        ProgressBar pg = findViewById(R.id.progressBar);
        pg.setVisibility(View.VISIBLE);
            StringRequest str = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pg.setVisibility(View.INVISIBLE);

                try {
                    JSONObject obj = new JSONObject(response);

                    JSONObject obj1 = obj.getJSONObject("data");

                    JSONArray dataArr = obj1.getJSONArray("regional");

                    for (int i = 0; i < dataArr.length(); i++) {
                        JSONObject dataObj = dataArr.getJSONObject(i);

                        Model m = new Model(dataObj.getString("loc"), dataObj.getString("confirmedCasesIndian"), dataObj.getString("confirmedCasesForeign"), dataObj.getString("discharged"), dataObj.getString("deaths"), dataObj.getString("totalConfirmed"));
                        modelList.add(m);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(str);
    }


    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyle);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        jad = new JavaAdapter(modelList);
        recyclerView.setAdapter(jad);
    }
}

