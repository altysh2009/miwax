package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class number extends AppCompatActivity {
    MediaPlayer media;
    int seek=0;
   // AudioManager service=(AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
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
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);


        final ArrayList<Word> res = new ArrayList<Word>();
        res.add(new Word("one","lutti",R.drawable.number_one,R.raw.number_one));
        res.add(new Word("two","otiiko",R.drawable.number_two,R.raw.number_two));
        res.add(new Word("three","tolookosu",R.drawable.number_three,R.raw.number_three));
        res.add(new Word("four","oyyisa",R.drawable.number_four,R.raw.number_four));
        res.add(new Word("five","massokka",R.drawable.number_five,R.raw.number_five));
        res.add(new Word("six","temmokka",R.drawable.number_six,R.raw.number_six));
        res.add(new Word("seven","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        res.add(new Word("eight","kawinta",R.drawable.number_eight,R.raw.number_eight));
        res.add(new Word("nine","wo’e",R.drawable.number_nine,R.raw.number_nine));
        res.add(new Word("ten","na’aacha",R.drawable.number_ten,R.raw.number_ten));


        //LinearLayout rootview=(LinearLayout)findViewById(R.id.rootView);
        //Log.d("coor======", "==="+R.color.category_numbers);
        WordAdpter itemsAdapter = new WordAdpter(this, res,R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        if (listView != null) {
            listView.setAdapter(itemsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(media != null){
                        if(!media.isPlaying())  {
                            media = MediaPlayer.create(getApplicationContext(),res.get(position).getAudioId());
                            media.start();
                            media.setOnCompletionListener(m);

                            int dur= Toast.LENGTH_SHORT;
                            Toast t = Toast.makeText(getApplicationContext(),res.get(position).getEnglishTranslation(),dur);
                            t.show();}
                    /*else {media.stop();

                         media = MediaPlayer.create(getApplicationContext(),color.get(position).getAudioId());

                         media.start();}*/}
                    else {
                        reles();
                        media = MediaPlayer.create(getApplicationContext(),res.get(position).getAudioId());
                        media.start();

                    }


                }
            });
        }

        }

    @Override
    protected void onStart() {
        super.onStart();
        Toast t =Toast.makeText(getApplicationContext(),"hallo",Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast t = Toast.makeText(getApplicationContext(),"where r u goning",Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast t = Toast.makeText(getApplicationContext(),"last call",Toast.LENGTH_SHORT);
        t.show();
        reles();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast t = Toast.makeText(getApplicationContext(),"Good by",Toast.LENGTH_SHORT);
        t.show();
    }

    private void reles(){
        if (media != null){

            media.release();
            media = null;

            //int result = service.abandonAudioFocus(afChangeListener);

        }
    }
    }

