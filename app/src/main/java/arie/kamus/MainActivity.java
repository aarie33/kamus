package arie.kamus;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import arie.kamus.db.EnglishHelper;
import arie.kamus.db.EnglishModel;
import arie.kamus.db.IndonesiaHelper;
import arie.kamus.db.IndonesiaModel;
import arie.kamus.sharedpref.Pref;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        IndonesiaHelper indonesiaHelper;
        EnglishHelper englishHelper;
        Pref pref;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {

            indonesiaHelper = new IndonesiaHelper(MainActivity.this);
            englishHelper = new EnglishHelper(MainActivity.this);
            pref = new Pref(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Boolean firstRun = pref.getFirstRun();

            if (firstRun) {
                ArrayList<IndonesiaModel> indonesiaModel = preLoadRaw_i();
                ArrayList<EnglishModel> englishModel = preLoadRaw_e();
                indonesiaHelper.open();

                progress = 20;
                publishProgress((int) progress);
                Double progressMaxInsert = 90.0;
                Double progressDiff = (progressMaxInsert - progress) /  (indonesiaModel.size()+englishModel.size());

                for (IndonesiaModel model : indonesiaModel) {
                    indonesiaHelper.insert(model);
                    progress += progressDiff;
                    publishProgress((int)progress);
                }

                indonesiaHelper.close();

                englishHelper.open();

                for (EnglishModel model : englishModel) {
                    englishHelper.insert(model);
                    progress += progressDiff;
                    publishProgress((int)progress);
                }

                englishHelper.close();

                pref.setFirstRun(false);
                publishProgress((int) maxprogress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(1000);

                        publishProgress(50);

                        this.wait(1000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(MainActivity.this, EnglishIndonesia.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<IndonesiaModel> preLoadRaw_i() {
        ArrayList<IndonesiaModel> englishModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.english_indonesia);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                IndonesiaModel indonesiaModel;

                indonesiaModel = new IndonesiaModel(splitstr[0], splitstr[1]);
                englishModels.add(indonesiaModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return englishModels;
    }
    public ArrayList<EnglishModel> preLoadRaw_e() {
        ArrayList<EnglishModel> englishModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.indonesia_english);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                EnglishModel englishModel;

                englishModel = new EnglishModel(splitstr[0], splitstr[1]);
                englishModels.add(englishModel);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return englishModels;
    }

}
