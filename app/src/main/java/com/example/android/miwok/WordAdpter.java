package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Altysh on 8/1/2016.
 */
public class WordAdpter extends ArrayAdapter<Word> {
    int colorthem;
    public WordAdpter(Activity context, ArrayList<Word> word,int color){

        super(context,0,word);

        colorthem=color;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View theview=convertView;
        if(theview == null) {
            theview = LayoutInflater.from(getContext()).inflate(
                    R.layout.my_lay_out, parent, false);
        }
        Word r= getItem(position);

        TextView english=(TextView) theview.findViewById(R.id.eng);
        english.setText(r.getEnglishTranslation());
        TextView mowax=(TextView)theview.findViewById(R.id.mewax);
        mowax.setText(r.getmMewaxTranslation());
        //Log.d("color them=======", "="+colorthem);
        int colorr= ContextCompat.getColor(getContext(),colorthem);
        theview.setBackgroundColor(colorr);
        ImageView image=(ImageView)theview.findViewById(R.id.imge);
        if (r.hasimage()){
        image.setImageResource(r.getmImageid());
            image.setVisibility(View.VISIBLE);}
        else
        image.setVisibility(View.GONE);



        return theview;
    }
}
