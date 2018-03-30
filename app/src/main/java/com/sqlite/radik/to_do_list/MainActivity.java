package com.sqlite.radik.to_do_list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.sqlite.radik.to_do_list.data.ItemChild;
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
        List<ParentObject> list = generateItems();
        //listAdapter = new ListAdapter(this, list, list.size());
        listAdapter = new ListAdapter(this, new ArrayList<ParentObject>(), 0);
        listAdapter.setCustomParentAnimationViewId(R.id.iv_list_item);
        listAdapter.setParentClickableViewAnimationDefaultDuration();
        listAdapter.setParentAndIconExpandOnClick(true);

        recyclerViewList.setLayoutManager(layoutManager);
        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setAdapter(listAdapter);
        recyclerViewList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) fab.hide();
                else       fab.show();
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        DBHelper dbHelper = new DBHelper(this);
        Model model = new Model(dbHelper);
        presenter = new Presenter(model);
        presenter.attachMainView(this);
        presenter.viewIsReady();
        //Log.e(">>>", String.valueOf(model.getItemsCount()));

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

    public void showItems(List<ParentObject> items) {
        listAdapter.swapData(items);
        ItemParent itemParent;
        for (int i = 0; i < items.size(); i++) {
            itemParent = (ItemParent) items.get(i);
            Log.e(">>>", itemParent.get_id() + "|" + itemParent.getCaption() + "|" + itemParent.getPriority() + "|" +
                    ((ItemChild)itemParent.getChildObjectList().get(0)).getmDefinition());        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachMainView();
    }
}
