package com.example.semen.greendaocat;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ListView listView;
    public ArrayList<Cat> arrayList = new ArrayList();
    public DaoSession daoSession;
    public ArrayAdapter<Cat> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);

        daoSession = ((App) getApplication()).getDaoSession();
        setupListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserList();
    }

    private void getUserList() {
        arrayList.clear();
        CatDao catDao = daoSession.getCatDao();

        arrayList.addAll(catDao.loadAll());
        arrayAdapter.notifyDataSetChanged();
    }

    private void setupListView() {
        arrayAdapter = new ArrayAdapter<Cat>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showOptions(position);
                return false;
            }
        });
    }

    private void showOptions(int position) {
        final Cat catItem = arrayList.get(position);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        String[] options = new String[2];

        options[0] = "Edit " + catItem.getName();
        options[1] = "Delete ";


        alertDialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    updateCat(catItem);
                } else if (which == 1) {
                    deleteCat(catItem.getId());
                }
            }
        });

        alertDialogBuilder.show();

    }

    private void updateCat(Cat catItem) {
        Intent intent = new Intent(this, CatActivity.class);
        intent.putExtra("create", false);
        intent.putExtra("Cat", catItem);

        startActivity(intent);
    }

    private void deleteCat(long id) {
        CatDao catDao = daoSession.getCatDao();
        catDao.deleteByKey(id);
        getUserList();
    }

    public void addNewItem(View view) {
        Intent intent = new Intent(this, CatActivity.class);
        intent.putExtra("create",true);
        startActivity(intent);
    }
}
