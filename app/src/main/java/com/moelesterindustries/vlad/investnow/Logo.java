package com.moelesterindustries.vlad.investnow;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Feras on 27/02/2016.
 */
public class Logo {
    private String sURL;
    private String returnedLogo;

    public Logo(String name) {
        String[] words = name.split(" ", 2);
        sURL = "https://autocomplete.clearbit.com/v1/companies/suggest?query=:" + words[0];
        new getData().execute(sURL);
    }

    private class getData extends AsyncTask<String, Void, JSONArray> {

        protected JSONArray doInBackground(String... urls) {
            try {

                URL urlOut = new URL(urls[0]);
                System.out.println(urlOut);
                InputStream inStream = urlOut.openStream();
                DataInputStream dataInStream = new DataInputStream(inStream);

                byte[] buffer = new byte[1024];
                int bufferLength;

                ByteArrayOutputStream output = new ByteArrayOutputStream();
                System.out.println("HERE1");
                while ((bufferLength = dataInStream.read(buffer)) > 0) {
                    output.write(buffer, 0, bufferLength);
                }
                System.out.println(output);
                 System.out.println("HERE1.5");
                return new JSONArray(output.toString("UTF-8"));


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("failed");
                return null;
            }
        }

        protected void onPostExecute(JSONArray json) {
            try {

                System.out.println("HERE2");
                JSONArray dataJsonArray = json;
                System.out.println("HERE3");


                JSONObject dataObj = (JSONObject) dataJsonArray.get(0);
                String logo = dataObj.getString("logo");
                returnedLogo = logo;
                System.out.println(returnedLogo);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public String getLogoURL() {
        return returnedLogo;
    }
}
