package com.example.pbb.jsonparserbmkg;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pbb.R;
import com.example.pbb.jsonparser.JSONParserActivity;
import com.example.pbb.utils.HTTPHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JSONParserBMKGActivity extends AppCompatActivity {
    private ListView lvGempa;
    ArrayList<HashMap<String, String>> gempaList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonparser_bmkgactivity);

        gempaList = new ArrayList<>();
        lvGempa = findViewById(R.id.lvGempa);
        new GetCuaca().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetCuaca extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pDialog;
        @Override
        protected Void doInBackground(Void... arg0) {
            final String urlReq = "https://data.bmkg.go.id/DataMKG/TEWS/gempadirasakan.json";
            HTTPHandler sh = new HTTPHandler();
            String jsonStr = sh.makeServiceCall(urlReq);
            Log.e("JSONParserBMKG", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // getting JSON array node
                    JSONObject infoGempa = jsonObj.getJSONObject("Infogempa");
                    JSONArray gempas = infoGempa.getJSONArray("gempa");
                    // looping through all contacts
                    for (int i = 0; i < gempas.length(); i++) {
                        JSONObject c = gempas.getJSONObject(i);

                        String tanggal = c.getString("Tanggal");
                        String jam = c.getString("Jam");
                        String magnitude = c.getString("Magnitude");
                        String kedalaman = c.getString("Kedalaman");
                        String wilayah = c.getString("Wilayah");
                        String lintang = c.getString("Lintang");
                        String bujur = c.getString("Bujur");

                        HashMap<String, String> gempaData = new HashMap<>();

                        gempaData.put("TanggalJam", tanggal + " " + jam);
                        gempaData.put("MagnitudeKedalamanLintangBujur", "Kekuatan: " + magnitude + " / Kedalaman: " + kedalaman + " / Lintang: " + lintang + " / Bujur: " + bujur);
                        gempaData.put("Wilayah", wilayah);

                        gempaList.add(gempaData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(JSONParserBMKGActivity.this);
            pDialog.setMessage("Data sedang diunduh...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(JSONParserBMKGActivity.this, gempaList, R.layout.json_parser_bmkg_list_item,
                    new String[] {"TanggalJam", "MagnitudeKedalamanLintangBujur", "Wilayah"},
                    new int[] {R.id.tvTanggalJam, R.id.tvMagnitudeKedalamanLintangBujur, R.id.tvWilayah});
            lvGempa.setAdapter(adapter);
        }
    }
}