package com.mechalikh.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContextMenuActivity extends AppCompatActivity {
        private RecyclerView mRecyclerView;
        private MyAdapter mAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_context_menu);

            mRecyclerView = findViewById(R.id.recycler_view);
            mAdapter = new MyAdapter(new ArrayList<>(Arrays.asList("Item 1", "Item 2", "Item 3")));
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    getMenuInflater().inflate(R.menu.contextual_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    if (item.getItemId() == R.id.action_delete) {
                        List<String> selectedItems = mAdapter.getSelectedItems();
                        mAdapter.removeSelectedItems();
                        Toast.makeText(ContextMenuActivity.this, selectedItems.size() + " items deleted", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        return true;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    mAdapter.clearSelections();
                }
            });
        }

    }
