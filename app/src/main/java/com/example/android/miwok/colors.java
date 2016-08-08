package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class colors extends AppCompatActivity {
MediaPlayer media;
    int seek=0;
  //  AudioManager service=(AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        // Pause playback because your Audio Focus was
                        // temporarily stolen, but will be back soon.
                        // i.e. for a phone call
                        media.pause();
                        seek = media.getCurrentPosition();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Stop playback, because you lost the Audio Focus.
                        // i.e. the user started some other playback app
                        // Remember to unregister your controls/buttons here.
                        // And release the kra — Audio Focus!
                        // You’re done.
                        reles();

                    } else if (focusChange ==
                            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Lower the volume, because something else is also
                        // playing audio over you.
                        // i.e. for notifications or navigation directions
                        // Depending on your audio playback, you may prefer to
                        // pause playback here instead. You do you.
                        media.pause();
                        seek = media.getCurrentPosition();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Resume playback, because you hold the Audio Focus
                        // again!
                        // i.e. the phone call ended or the nav directions
                        // are finished
                        // If you implement ducking and lower the volume, be
                        // sure to return it to normal here, as well.
                        media.seekTo(seek);
                        media.start();
                    }
                }
            };
    private MediaPlayer.OnCompletionListener m= new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            reles();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        final ArrayList<Word> color = new ArrayList<Word>();
        color.add(new Word("red","weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
        color.add(new Word("green","chokokki",R.drawable.color_green,R.raw.color_green));
        color.add(new Word("brown","ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
        color.add(new Word("gray","ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
        color.add(new Word("black","kululli",R.drawable.color_black,R.raw.color_black));
        color.add(new Word("white","kelelli",R.drawable.color_white,R.raw.color_white));
        color.add(new Word("dusty yellow","ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        color.add(new Word("mustard yellow","chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        final int maxVolume  = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,maxVolume, 0);
        ListView listView2 = (ListView) findViewById(R.id.list2);
        LinearLayout l1=(LinearLayout)findViewById(R.id.leniear);
        //LinearLayout rootview=(LinearLayout)findViewById(R.id.rootView);
        WordAdpter colorAdapter = new WordAdpter(this, color,R.color.category_colors);

        ListView colorView = (ListView) findViewById(R.id.list4);
        if (colorView != null) {
            colorView.setAdapter(colorAdapter);
            colorView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(media != null){
                     if(!media.isPlaying())  {
                    media = MediaPlayer.create(getApplicationContext(),color.get(position).getAudioId());
                    media.start();
                         media.setOnCompletionListener(m);

                    int dur=Toast.LENGTH_SHORT;
                    Toast t = Toast.makeText(getApplicationContext(),color.get(position).getEnglishTranslation(),dur);
                    t.show();}
                    /*else {media.stop();

                         media = MediaPlayer.create(getApplicationContext(),color.get(position).getAudioId());

                         media.start();}*/}
                    else {
                        reles();
                        media = MediaPlayer.create(getApplicationContext(),color.get(position).getAudioId());
                        media.start();

                    }


                }
            });


        }





    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast t = Toast.makeText(getApplicationContext(),"last call",Toast.LENGTH_SHORT);
        t.show();
        reles();
    }
    private void reles(){
        if (media != null){

                media.release();
                media = null;

        }
    }
}
