package com.mechalikh.myapplication;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> mData;
    private SparseBooleanArray mSelectedItems;
    private ActionMode.Callback mActionModeCallback;
    androidx.appcompat.view.ActionMode actionMode;

    public MyAdapter(List<String> data) {
        mData = data;
        mSelectedItems = new SparseBooleanArray();
    }

    public void toggleSelection(int position) {
        if (mSelectedItems.get(position, false)) {
            mSelectedItems.delete(position);
        } else {
            mSelectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void clearSelections() {
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    public List<Integer> getSelectedItemPositions() {
        List<Integer> selectedItems = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            selectedItems.add(mSelectedItems.keyAt(i));
        }
        return selectedItems;
    }

    public List<String> getSelectedItems() {
        List<String> selectedItems = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            selectedItems.add(mData.get(mSelectedItems.keyAt(i)));
        }
        return selectedItems;
    }

    public void removeSelectedItems() {
        List<Integer> selectedItems = getSelectedItemPositions();
        for (int i = selectedItems.size() - 1; i >= 0; i--) {
            int position = selectedItems.get(i);
            mData.remove(position);
            notifyItemRemoved(position);
        }
        mSelectedItems.clear();
    }


    public void setActionModeCallback(ActionMode.Callback callback) {
        mActionModeCallback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(mData.get(position));
        holder.itemView.setActivated(mSelectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (mSelectedItems.size() > 0) {
                toggleSelection(position);
                if (actionMode != null)
                    actionMode.setTitle(String.valueOf(getSelectedItemCount()));
            } else {
                // Handle item click event here
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            if (mSelectedItems.size() == 0) {
                toggleSelection(position);
                // Start the action mode
                actionMode = ((AppCompatActivity) v.getContext()).startSupportActionMode(mActionModeCallback);
                if (actionMode != null) {
                    // Set the title of the action mode to the number of selected items
                    actionMode.setTitle(String.valueOf(getSelectedItemCount()));
                }
                return true;
            }
            return false;
        }
    }
}
