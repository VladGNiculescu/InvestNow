package com.moelesterindustries.vlad.investnow;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by vlad on 2/20/2016.
 */
public class BaseAdapterClass extends BaseAdapter {

    private Activity a;
    private ArrayList companyname;
    private ArrayList riskdata;
    private ArrayList returnAssets;

    private String comp;
    private String ris;
    private  String re;

    public BaseAdapterClass(Activity a,ArrayList companyname, ArrayList riskdata,ArrayList returnAssets)
    {
        System.out.println("a intrat");
        this.a =a;
        this.companyname = companyname;
        this.riskdata = riskdata;
        this.returnAssets = returnAssets;

        System.out.println("APELATA");


    }

    @Override
    public int getCount() {
        if(riskdata==null) return 0;
        return riskdata.size();
    }

    @Override
    public Object getItem(int position) {
        if(riskdata==null) return null;
        return riskdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
System.out.println("ASA ba");

        if(convertView==null)
        {
            LayoutInflater inflater= a.getLayoutInflater();
            v = inflater.inflate(R.layout.row_listitem,parent,false);
            System.out.println("CACAT");
        }


        System.out.println("AFISARE");

        TextView companyName = (TextView) v.findViewById(R.id.company);
        comp = companyname.get(position).toString();
        companyName.setText(comp);



        TextView riskData = (TextView) v.findViewById(R.id.risk);
        ris = riskdata.get(position).toString();
        riskData.setText(ris);


        TextView returna = (TextView) v.findViewById(R.id.ret);
        re = returnAssets.get(position).toString();
        returna.setText(re);








        return v;
    }
}
