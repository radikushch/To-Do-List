package com.sqlite.radik.to_do_list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.sqlite.radik.to_do_list.data.ItemParent;
import com.sqlite.radik.to_do_list.database.DBHelper;
import com.sqlite.radik.to_do_list.model.Model;
import com.sqlite.radik.to_do_list.presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_todo)
    RecyclerView recyclerViewList;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ListAdapter listAdapter;

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startTaskActivity();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listAdapter = new ListAdapter(this, new ArrayList<ParentObject>(), 0);
        initAdapter();
        recyclerViewList.setLayoutManager(layoutManager);
        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setAdapter(listAdapter);
        recyclerViewList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) fab.hide();
                else fab.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        DBHelper dbHelper = new DBHelper(this);
        Model model = new Model(dbHelper);
        presenter = new Presenter(model);
        presenter.attachMainView(this);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                ItemParent itemParent = (ItemParent) listAdapter.getParentObjects()
                        .get(viewHolder.getAdapterPosition());
                presenter.deleteItem(itemParent);
            }
        }).attachToRecyclerView(recyclerViewList);
    }

    private void initAdapter(){
        listAdapter.setCustomParentAnimationViewId(R.id.iv_list_item);
        listAdapter.setParentClickableViewAnimationDefaultDuration();
        listAdapter.setParentAndIconExpandOnClick(true);
    }

    public void showItems(List<ParentObject> items) {
        listAdapter = new ListAdapter(this, items, items.size());
        initAdapter();
        recyclerViewList.setAdapter(listAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.viewIsReady();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachMainView();
        Log.d(">>>", "OnDestroy");
    }
}
