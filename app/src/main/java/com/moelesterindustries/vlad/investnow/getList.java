package com.moelesterindustries.vlad.investnow;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by vlad on 2/20/2016.
 */
public class getList extends Activity {


    private     ArrayList<String> companyname = new ArrayList<String>();
    private  ArrayList<String> riskdata = new ArrayList<String>();
    private    ArrayList<String> returnEquity = new ArrayList<String>();
    private ProgressDialog progressBar;
    private Button adds;

    private Loader ld;


    ListView liste;
    BaseAdapterClass adapter;
    private String baseUrlString;
    private String finalUrlString;
    private String positions ;
    private getData getInfo = new getData();
    private Boolean adapterExists = false;
    private Handler progressBarbHandler = new Handler();
    private int progressBarStatus=0;
    private int status;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adds = (Button) findViewById(R.id.add);

        positions = "BLK~10|AAPL~10|IXN~10|HPE~10|GOOG~10|TSLA~10|MSFT~10|JPM~10|IBM~10|F~10";
        //  positions = "AAPL~100";


        liste = (ListView) findViewById(R.id.listView1);
//        init();
//        outputLine("Request started...");

        baseUrlString = "https://test3.blackrock.com/tools/hackathon/portfolio-analysis?calculateRisk=true&startDate=20120221&";
        finalUrlString = baseUrlString+ "positions=" + positions;


        System.out.println(finalUrlString);
        System.out.println("PULA MEA");
        //StockSymbol sym = new StockSymbol("");


        getInfo.execute(finalUrlString);

        //  new getData().execute("http://www.vladniculescu.com/black.json");
        System.out.println("INTRA PE LINK");

        adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToList(baseUrlString + "positions=" + "AAPL~100");
                circle(v);
            }
        });

    }



    private class getData extends AsyncTask<String,Double,JSONObject> {

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected  JSONObject doInBackground(String... params) {

            if(params.length!=1) return null;

            try{



                status = 0;

                URL url = new URL(params[0]);
                InputStream inStream = url.openStream();
                DataInputStream dataInStream = new DataInputStream(inStream);

                byte[] buffer = new byte[1024];
                int bufferLength;

                ByteArrayOutputStream output  = new ByteArrayOutputStream();

                while((bufferLength = dataInStream.read(buffer))>0)
                {
                    output.write(buffer,0,bufferLength);
                }
                //   outputLine("Success1!");
               /* JSONArray js = new JSONArray();
                js.put(output);*/
                status = 20;


                // System.out.println(js.get(1));
                return  new JSONObject(output.toString("UTF-8"));

            }catch (Exception e){
                System.out.println("Imi bag pula ");
                //   outputLine("Something went wrong1!");
                return null;
            }


        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        protected void onPostExecute(JSONObject result)
        {
            status = 40;
            System.out.println("A copiat");
            System.out.println("LENGTH is " + result.length());

            //    System.out.println(result.length());
            if (result == null )

                return;

            try {
                // JSONArray list = new JSONArray(result);
                System.out.println(result);

                //   System.out.println(list.get("resultMap").toString());

                //   System.out.println((result.getJSONObject("resultMap")).getJSONArray("PORTFOLIOS").getJSONObject(0).getJSONArray("portfolios").getJSONObject(0).getJSONArray("holdings").toString());


                JSONArray list = (result.getJSONObject("resultMap")).getJSONArray("PORTFOLIOS").getJSONObject(0).getJSONArray("portfolios").getJSONObject(0).getJSONArray("holdings");

                status = 60;




                System.out.println(list);

                System.out.println(list.length());

                JSONObject l;
                JSONObject l2;

                System.out.println(companyname);

                for(int i= 0;i<list.length();i++)
                {
                    l = list.getJSONObject(i);
                    //  System.out.println(l.get("description").toString());

                    companyname.add(l.getString("description"));
                    returnEquity.add("Assets return: "+l.getString("returnOnAssets")+"%");


                    l2 = l.getJSONObject("riskData");
                    riskdata.add("Risk: " + l2.getString("totalRisk") + "%");
                    System.out.println(companyname.get(i));
//progressBar.dismiss();
                }
                status =80;



                if(adapterExists)
                {
                    adapter.notifyDataSetChanged();
                }
                else{
                    System.out.println("E gol");
                    adapter = new BaseAdapterClass(getList.this,companyname,riskdata,returnEquity);
                    liste.setAdapter(adapter);
                    adapterExists = true;
                }


                status =100;








            }catch (Exception e)
            {
                System.out.println("SA-mi BAG PULA ");
            }
        }





    }
    public void circle(View v)
    {
        progressBar = new ProgressDialog(v.getContext());
        progressBar.setCancelable(true);
        progressBar.setMessage("Updating...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();

        new Thread(new Runnable() {
            public void run() {
                while (progressBarStatus < 100) {
                    progressBarStatus = status;

                    try {
                        Thread.sleep(1000);
                    }

                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progressBarbHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }

                if (progressBarStatus >= 100) {
                    try {
                        Thread.sleep(2000);
                    }

                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.dismiss();

                    liste.smoothScrollToPosition(companyname.size());

                }
            }
        }).start();



    }

    public void addToList(String link)
    {
        getInfo = new getData();
        getInfo.execute(link);
        adapter.notifyDataSetChanged();

    }

}
