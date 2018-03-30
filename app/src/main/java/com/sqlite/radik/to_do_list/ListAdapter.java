package com.sqlite.radik.to_do_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.sqlite.radik.to_do_list.data.ItemChild;
import com.sqlite.radik.to_do_list.data.ItemParent;

import java.util.List;

/**
 * Created by Radik on 27.03.2018.
 */

public class ListAdapter extends
        ExpandableRecyclerAdapter<ListAdapter.ListParentViewHolder, ListAdapter.ListChildViewHolder> {
    int numOfItems;

    public ListAdapter(Context context, List<ParentObject> parentItemList, int numOfItems) {
        super(context, parentItemList);
        this.numOfItems = numOfItems;
    }

    @Override
    public ListParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.parent_list_item, viewGroup, false);
        return new ListParentViewHolder(view);
    }

    @Override
    public ListChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.child_list_item, viewGroup, false);
        return new ListChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(ListParentViewHolder listParentViewHolder, int i, Object o) {
        listParentViewHolder.bind(i, o);
    }

    @Override
    public void onBindChildViewHolder(ListChildViewHolder listChildViewHolder, int i, Object o) {
        listChildViewHolder.bind(i, o);
    }

    public void swapData(List<ParentObject> items) {
        super.mParentItemList = items;
        notifyDataSetChanged();
    }

    class ListParentViewHolder extends ParentViewHolder {

        public TextView listCaption;
        public ImageView arrowDirection;
        public View priority;

        public ListParentViewHolder(View itemView) {
            super(itemView);
            listCaption = itemView.findViewById(R.id.tv_list_item);
            arrowDirection = itemView.findViewById(R.id.iv_list_item);
            priority = itemView.findViewById(R.id.iv_priority_list_item);
        }

        public void bind(int i, Object item) {
            ItemParent parentItem = (ItemParent) item;
            listCaption.setText(parentItem.getCaption());
            switch(parentItem.getPriority()){
                case 1:
                    priority.setBackgroundResource(R.drawable.round_view_red); break;
                case 2:
                    priority.setBackgroundResource(R.drawable.round_view_yellow); break;
                case 3:
                    priority.setBackgroundResource(R.drawable.round_view_green); break;
                default:
                    priority.setBackgroundResource(R.drawable.round_view_green); break;
            }
        }
    }

    class ListChildViewHolder extends ChildViewHolder {

        public TextView listDefinition;

        public ListChildViewHolder(View itemView) {
            super(itemView);
            listDefinition = itemView.findViewById(R.id.tv_child_list_item_definition);
        }

        public void bind(int i, Object o) {
            ItemChild itemChild = (ItemChild) o;
            listDefinition.setText(itemChild.getmDefinition());
        }
    }
}
