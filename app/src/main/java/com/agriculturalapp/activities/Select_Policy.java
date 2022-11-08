package com.agriculturalapp.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import com.agriculturalapp.R;
import com.agriculturalapp.adapters.PolicyAdapter;

public class Select_Policy extends AppCompatActivity {

    private PolicyAdapter adapter;
    private  ArrayList list;
    private RecyclerView recyclerView;

    private String[] links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_recycler);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        links=getResources().getStringArray(R.array.policies_link);

        list = new ArrayList<>();

        list=new ArrayList();

        String[] array=getResources().getStringArray(R.array.policies);

        for(int i=0;i<array.length;i++){
            list.add(array[i]);
        }

        adapter = new PolicyAdapter(this,list,links);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.progress).setVisibility(View.GONE);
    }
}
