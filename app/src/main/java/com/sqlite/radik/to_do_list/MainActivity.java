package com.sqlite.radik.to_do_list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        ListAdapter.FabHideCallback {

    @BindView(R.id.rv_todo)
    RecyclerView listOfTODOs;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ButterKnife.bind(this);
        initializeRecycleView();
        }

    private void initializeRecycleView() {
        ArrayList<ParentObject> list = generateItems();
        listAdapter = new ListAdapter(this, list, list.size());
        listAdapter.registerCallback(this);
        listAdapter.setCustomParentAnimationViewId(R.id.iv_list_item);
        listAdapter.setParentClickableViewAnimationDefaultDuration();
        listAdapter.setParentAndIconExpandOnClick(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listOfTODOs.setLayoutManager(layoutManager);
        listOfTODOs.setHasFixedSize(true);
        listOfTODOs.setAdapter(listAdapter);
    }

    private ArrayList<ParentObject> generateItems() {
        ArrayList<ParentObject> parentList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            ArrayList<Object> childList = new ArrayList<>();
            childList.add(new ItemChild("Definition" + i));
            parentList.add(new ItemParent("Item" + i, (int) (Math.random() * 3 + 1), childList));
        }
        return parentList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void hideFAB(boolean needToHide) {
        if(needToHide) fab.show();
        else           fab.hide();
    }
}
