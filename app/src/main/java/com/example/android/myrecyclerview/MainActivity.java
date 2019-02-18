package com.example.android.myrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCategory;

    private ArrayList<Hero> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCategory = findViewById(R.id.rv_category);
        // Set item in recyclerview to be the same size
        rvCategory.setHasFixedSize(true);

        // Add data to ArrayList
        list.addAll(HeroesData.getListData());
        // Call method
        showRecyclerList();
    }

    private void showRecyclerList(){
        rvCategory.setLayoutManager(new LinearLayoutManager(this)); // LinearLayout is the type of RecyclerView
        ListHeroAdapter listHeroAdapter = new ListHeroAdapter(this); // Create Adapter object that extends RecyclerViewAdapter
        listHeroAdapter.setListHero(list); // Call setter method dri ArrayList yg menampung data sbg parameter
        rvCategory.setAdapter(listHeroAdapter); // Set adapter ke RecyclerView
    }

    private void showRecyclerGrid(){
        rvCategory.setLayoutManager(new GridLayoutManager(this, 2)); // GridLayout is the type of RecyclerView
        GridHeroAdapter gridHeroAdapter = new GridHeroAdapter(this); // Create Adapter object that extends RecyclerViewAdapter
        gridHeroAdapter.setListHero(list); // Call setter method dri ArrayList yg menampung data sbg parameter
        rvCategory.setAdapter(gridHeroAdapter); // Set adapter ke RecyclerView
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); // Create option menu from menu file in xml
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Action when menu item is selected
        switch (item.getItemId()){
            case R.id.action_list:
                showRecyclerList(); // Call method that show list in recyclerview
                break;
            case R.id.action_grid:
                showRecyclerGrid(); // Call method that show grid in recyclerview
                break;
            case R.id.action_cardview:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
