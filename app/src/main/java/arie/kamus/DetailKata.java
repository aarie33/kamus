package arie.kamus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailKata extends AppCompatActivity {
    private TextView txtKata, txtKeterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kata);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setTitle("");

        txtKata = findViewById(R.id.txtKata);
        txtKeterangan = findViewById(R.id.txtKeterangan);

        txtKata.setText(getIntent().getStringExtra("kata"));
        txtKeterangan.setText(getIntent().getStringExtra("keterangan"));
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//            return false;
//        }else {
//            return super.onOptionsItemSelected(item);
//        }
//    }
}
