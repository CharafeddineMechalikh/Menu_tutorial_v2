package com.mechalikh.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private Button btnContextual, btnPopup;
    private ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnContextual = findViewById(R.id.btnContextual);
        btnPopup = findViewById(R.id.btnPopup);

        btnContextual.setOnClickListener(this);
        btnPopup.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContextual:
                Intent intent= new Intent(this, ContextMenuActivity.class);
                startActivity(intent);
                break;
            case R.id.btnPopup:
                showPopupMenu(v);
                break;
            default: break;
        }
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    private void showOptionsMenu() {
        openOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Toast.makeText(this, "Share selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cut:
                Toast.makeText(this, "Cut selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_copy:
                Toast.makeText(this, "Copy selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_paste:
                Toast.makeText(this, "Paste selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

}