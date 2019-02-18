package com.example.android.myrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCategory;

    private ArrayList<Hero> list = new ArrayList<>();

    final String STATE_TITLE = "state_string";
    final String STATE_LIST = "state_list";
    final String STATE_MODE = "state_mode";
    int mode;

    String title = "Mode List";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCategory = findViewById(R.id.rv_category);
        // Set item in recyclerview to be the same size
        rvCategory.setHasFixedSize(true);

        if (savedInstanceState == null) {
            setActionBarTitle(title);
            // Add data to ArrayList
            list.addAll(HeroesData.getListData());
            showRecyclerList();
            mode = R.id.action_list;

        } else {
            title = savedInstanceState.getString(STATE_TITLE); // Get value in key STATE_TITLE
            ArrayList<Hero> stateList = savedInstanceState.getParcelableArrayList(STATE_LIST);
            // Get value in key STATE_LIST
            int stateMode = savedInstanceState.getInt(STATE_MODE); // Get value in key STATE_MODE

            setActionBarTitle(title); // Set title
            // Add data to ArrayList
            list.addAll(stateList); // Set data content
            setMode(stateMode); // Set mode
        }
    }

    private void showSelectedHero(Hero hero){
        // Toast message untuk menunjukkan kamu memilih nama apa
        Toast.makeText(this, "Kamu memilih " + hero.getName(), Toast.LENGTH_SHORT).show();
    }

    private void showRecyclerList(){
        rvCategory.setLayoutManager(new LinearLayoutManager(this)); // Vertical LinearLayout is the type of RecyclerView
        ListHeroAdapter listHeroAdapter = new ListHeroAdapter(this); // Create Adapter object that extends RecyclerViewAdapter
        listHeroAdapter.setListHero(list); // Call setter method dri ArrayList yg menampung data sbg parameter
        rvCategory.setAdapter(listHeroAdapter); // Set adapter ke RecyclerView
        // Call itemclicksupport class and static method addto,
        // sehingga attach event click ke item yang ada di RecyclerView
        ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            // Apply method from interface
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // Call method to show toast message
                showSelectedHero(list.get(position));
            }
        });
    }

    private void showRecyclerGrid(){
        rvCategory.setLayoutManager(new GridLayoutManager(this, 2)); // GridLayout is the type of RecyclerView
        GridHeroAdapter gridHeroAdapter = new GridHeroAdapter(this); // Create Adapter object that extends RecyclerViewAdapter
        gridHeroAdapter.setListHero(list); // Call setter method dri ArrayList yg menampung data sbg parameter
        rvCategory.setAdapter(gridHeroAdapter); // Set adapter ke RecyclerView

    }

    private void showRecyclerCardView(){
        rvCategory.setLayoutManager(new LinearLayoutManager(this)); // Vertical LinearLayout is the type of RecyclerView
        CardViewHeroAdapter cardViewHeroAdapter = new CardViewHeroAdapter(this); // Create Adapter object that extends RecyclerViewAdapter
        cardViewHeroAdapter.setListHero(list); // Call setter method dri ArrayList yg menampung data sbg parameter
        rvCategory.setAdapter(cardViewHeroAdapter); // Set adapter ke RecyclerView
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); // Create option menu from menu file in xml
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Action when menu item is selected
        setMode(item.getItemId()); // Call to change mode
        return super.onOptionsItemSelected(item);
    }

    private void setActionBarTitle(String title){
        // Check jika action bar itu ada
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    public void setMode(int selectedMode) {
        switch (selectedMode) {
            case R.id.action_list:
                title = "Mode List";
                showRecyclerList(); // Call method that show list in recyclerview
                break;

            case R.id.action_grid:
                title = "Mode Grid";
                showRecyclerGrid(); // Call method that show grid in recyclerview
                break;

            case R.id.action_cardview:
                title = "Mode CardView";
                showRecyclerCardView(); // Call method that show card view in recyclerview
                break;
        }

        mode = selectedMode; // Set mode value based on menu item selection
        setActionBarTitle(title); // Call method to change action bar title
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_TITLE, title); // Set value in key STATE_TITLE
        outState.putParcelableArrayList(STATE_LIST, list); // Set value in key STATE_LIST
        outState.putInt(STATE_MODE, mode); // Set value in key STATE_MODE
    }
}
