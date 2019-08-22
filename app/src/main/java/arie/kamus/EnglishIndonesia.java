package arie.kamus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import arie.kamus.adapters.IndonesiaAdapter;
import arie.kamus.db.IndonesiaHelper;
import arie.kamus.db.IndonesiaModel;

public class EnglishIndonesia extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private EditText edtCari;
    private Button btnClear;
    private RecyclerView recyclerView;
    private IndonesiaAdapter indonesiaAdapter;
    private IndonesiaHelper indonesiaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_to_indonesia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        edtCari = findViewById(R.id.edtCari);
        btnClear = findViewById(R.id.btnClear);
        recyclerView = findViewById(R.id.rvKamus);
        indonesiaHelper = new IndonesiaHelper(this);
        indonesiaAdapter = new IndonesiaAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(indonesiaAdapter);

        indonesiaHelper.open();
        ArrayList<IndonesiaModel> indonesiaModels = indonesiaHelper.getAllData();
        indonesiaHelper.close();
        indonesiaAdapter.addItem(indonesiaModels);

        edtCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                indonesiaHelper.open();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ArrayList<IndonesiaModel> indonesiaModels = indonesiaHelper.getDataByName(editable.toString());
                indonesiaHelper.close();
                indonesiaAdapter.addItem(indonesiaModels);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtCari.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_english_indonesia) {
            Toast.makeText(this, "You already opened this dictionary", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_indonesia_english) {
            startActivity(new Intent(this, IndonesiaEnglish.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
