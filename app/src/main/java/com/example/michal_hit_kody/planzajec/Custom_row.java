package com.example.michal_hit_kody.planzajec;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Custom_row extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] tab;
    private final String[] tab1;
    private final String[] tab2;
    private final String[] tab3;
    private final String[] tab4;
    private final String[] tab5;

    public Custom_row(Activity context,
                         String[] zma,String[] zma1, String[] zma2,String[] zma3,
    String[] zma4,String[] zma5) {
        super(context, R.layout.activity_custom_row, zma1);
        this.context = context;
        this.tab = zma;
        this.tab1 = zma1;
        this.tab2 = zma2;
        this.tab3 = zma3;
        this.tab4 = zma4;
        this.tab5 = zma5;

    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_custom_row, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView);
        TextView txtTitle1 = (TextView) rowView.findViewById(R.id.textView2);
        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.textView3);
        TextView txtTitle3 = (TextView) rowView.findViewById(R.id.textView4);
        TextView txtTitle4 = (TextView) rowView.findViewById(R.id.textView5);
        TextView txtTitle5 = (TextView) rowView.findViewById(R.id.textView6);


        txtTitle.setText(tab[position]);
        txtTitle1.setText(tab1[position]);
        txtTitle2.setText(tab2[position]);
        txtTitle3.setText(tab3[position]);
        txtTitle4.setText(tab4[position]);
        txtTitle5.setText(tab5[position]);

        return rowView;
    }
}