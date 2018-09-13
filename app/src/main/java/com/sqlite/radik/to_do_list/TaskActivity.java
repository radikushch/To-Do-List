package com.sqlite.radik.to_do_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.sqlite.radik.to_do_list.data.TaskDescription;
import com.sqlite.radik.to_do_list.data.TaskCaption;
import com.sqlite.radik.to_do_list.database.DBHelper;
import com.sqlite.radik.to_do_list.model.Model;
import com.sqlite.radik.to_do_list.presenter.Presenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Radik on 30.03.2018.
 */

public class TaskActivity extends AppCompatActivity {

    private Presenter presenter;
    private Model model;
    @BindView(R.id.btn_add_task)
    Button mAddButton;
    @BindView(R.id.et_caption)
    EditText mCaptionEditText;
    @BindView(R.id.et_definition)
    EditText mDefinittionEditText;
    @BindView(R.id.rg_priority)
    RadioGroup mPriorityRadioGroup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addTask();
            }
        });
        DBHelper dbHelper = new DBHelper(this);
        Model model = new Model(dbHelper);
        presenter = new Presenter(model);
        presenter.attachTaskView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachTaskView();
    }

    public ParentObject getItemData() {
        TaskCaption taskCaption = new TaskCaption();
        TaskDescription taskDescription = new TaskDescription();
        taskCaption.setCaption(mCaptionEditText.getText().toString());
        switch(mPriorityRadioGroup.getCheckedRadioButtonId()){
            case R.id.rb_priority_high:
                taskCaption.setPriority(1);
                break;
            case R.id.rb_priority_medium:
                taskCaption.setPriority(2);
                break;
            case R.id.rb_priority_low:
                taskCaption.setPriority(3);
                break;
            default:
                taskCaption.setPriority(3);
                break;
        }
        taskDescription.setDefinition(mDefinittionEditText.getText().toString());
        ArrayList<Object> list = new ArrayList<>();
        list.add(taskDescription);
        taskCaption.setChildObjectList(list);
        return taskCaption;
    }

    public void close(){
        finish();
    }
}
