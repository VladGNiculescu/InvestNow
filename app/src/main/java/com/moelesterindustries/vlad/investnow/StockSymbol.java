package com.moelesterindustries.vlad.investnow;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Feras on 27/02/2016.
 */
public class StockSymbol {

    private String searchQuery;
    private String sURL;
    private Map<String, String> symbols;

    public StockSymbol(String searchQuery) {
        symbols = new HashMap<String, String>();
        this.searchQuery = searchQuery;
        sURL = "https://s.yimg.com/aq/autoc?query=" + searchQuery + "&region=US&lang=en-US";
        new getData().execute(sURL);
        /*for(String s :symbols.keySet()) {
            System.out.println("(" + s +", " + symbols.get(s) + ")");
        } */

    }

    private class getData extends AsyncTask<String, Void, JSONObject> {

        private Map<String, String> results;

        protected JSONObject doInBackground(String... urls) {
            try {

                URL urlOut = new URL(urls[0]);
                System.out.println(urlOut);
                InputStream inStream = urlOut.openStream();
                DataInputStream dataInStream = new DataInputStream(inStream);

                byte[] buffer = new byte[1024];
                int bufferLength;

                ByteArrayOutputStream output = new ByteArrayOutputStream();
                //System.out.println("HERE1");
                while ((bufferLength = dataInStream.read(buffer)) > 0) {
                    output.write(buffer, 0, bufferLength);
                }
               //System.out.println(output);
               // System.out.println("HERE1.5");
                return new JSONObject(output.toString("UTF-8"));


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("failed");
                return null;
            }
        }

        protected void onPostExecute(JSONObject json) {
            try {
                results = new HashMap<String, String>();

                //System.out.println("HERE2");
                JSONArray dataJsonArray = json.getJSONObject("ResultSet").getJSONArray("Result");
                //System.out.println("HERE3");
                for (int i = 0; i < dataJsonArray.length(); i++) {
                    JSONObject dataObj = (JSONObject) dataJsonArray.get(i);
                    String symbol = dataObj.getString("symbol");
                    String name = dataObj.getString("name");
                    results.put(name, symbol);
                    System.out.println("(" + name +", " + symbol + ")");
                }
                symbols.putAll(results);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
