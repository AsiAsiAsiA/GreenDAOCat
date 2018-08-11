package com.example.semen.greendaocat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CatActivity extends AppCompatActivity {
    public Cat cat;
    public DaoSession daoSession;
    public EditText name;
    public Button ok;
    public TextView textView;
    public Boolean createNew = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        name = findViewById(R.id.name);
        ok = findViewById(R.id.ok);
        textView = findViewById(R.id.textView);

        daoSession = ((App) getApplication()).getDaoSession();
        setClickEventListener();

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        createNew = intent.getBooleanExtra("create", false);

        if (!createNew) {
            textView.setText("Update");
            cat = (Cat) intent.getSerializableExtra("Cat");
            name.setText(cat.getName());
        }
    }

    private void setClickEventListener() {

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (createNew) {
                    insertItem();
                } else {
                    updateItem(cat.getId());
                }
            }
        });
    }

    private void updateItem(Long id) {
        CatDao catDao = daoSession.getCatDao();

        cat = new Cat();
        cat.setName(name.getText().toString());
        cat.setId(id);
        catDao.save(cat);

        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void insertItem() {
        CatDao catDao = daoSession.getCatDao();

        cat = new Cat();
        cat.setName(name.getText().toString());

        catDao.insert(cat);
        Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
        finish();
    }


}
