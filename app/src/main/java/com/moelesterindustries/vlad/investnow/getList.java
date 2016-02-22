package com.moelesterindustries.vlad.investnow;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


    ArrayList<String> companyname = new ArrayList<String>();
    ArrayList<String> riskdata = new ArrayList<String>();
    ArrayList<String> returnAssets = new ArrayList<String>();



    ListView liste;
    BaseAdapterClass adapter;
    private String urlString;
    private String positions ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        positions = "BLK~9|AAPL~11|IXN~10|HPE~10|GOOG~10|TSLA~10|MSFT~10|JPM~10|IBM~10|F~10";
      //  positions = "AAPL~100";


        liste = (ListView) findViewById(R.id.listView1);
//        init();
//        outputLine("Request started...");

         urlString = "https://test3.blackrock.com/tools/hackathon/portfolio-analysis?calculateRisk=true&startDate=20120221&";
        urlString += "positions=" + positions;


        System.out.println(urlString);
        System.out.println("PULA MEA");


       new getData().execute(urlString);

      //  new getData().execute("http://www.vladniculescu.com/black.json");
        System.out.println("INTRA PE LINK");




    }



    private class getData extends AsyncTask<String,Double,JSONObject> {

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected  JSONObject doInBackground(String... params) {

            if(params.length!=1) return null;

            try{
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

            System.out.println("A copiat");
            System.out.println("LENGTH is "+ result.length());

        //    System.out.println(result.length());
            if (result == null )

                return;

            try {
               // JSONArray list = new JSONArray(result);
                System.out.println(result);

             //   System.out.println(list.get("resultMap").toString());

           //   System.out.println((result.getJSONObject("resultMap")).getJSONArray("PORTFOLIOS").getJSONObject(0).getJSONArray("portfolios").getJSONObject(0).getJSONArray("holdings").toString());


          JSONArray list = (result.getJSONObject("resultMap")).getJSONArray("PORTFOLIOS").getJSONObject(0).getJSONArray("portfolios").getJSONObject(0).getJSONArray("holdings");






                    System.out.println(list);








              /*  JSONObject obj5 = new JSONObject("holdings");
                JSONArray list5 = obj5.getJSONArray("riskData");

*/


               System.out.println(list.length());

                JSONObject l;
                JSONObject l2;

                System.out.println(companyname);

                for(int i= 0;i<list.length();i++)
                {
                   l = list.getJSONObject(i);
                  //  System.out.println(l.get("description").toString());

                    companyname.add(l.getString("description"));
                    returnAssets.add("Return: "+l.getString("returnOnAssets"));

                    l2 = l.getJSONObject("riskData");
                    riskdata.add("Risk: "+l2.getString("totalRisk"));
                    System.out.println(companyname.get(i));

                }


           //     System.out.println(list.getJSONObject(1).getJSONArray("riskData").toString());
              /*  for(int i =0;i<list2.length();i++)
                {
                    l2 = list.getJSONObject(i);

                    riskdata.add(" Total risk: "+ l2.getString("totalRisk"));
                    System.out.println(riskdata.get(i));
                }
*/
                adapter = new BaseAdapterClass(getList.this,companyname,riskdata,returnAssets);
                liste.setAdapter(adapter);



            }catch (Exception e)
            {
                System.out.println("SA-mi BAG PULA ");
            }
        }


    }


}
