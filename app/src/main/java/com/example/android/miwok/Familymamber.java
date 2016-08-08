package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Familymamber extends AppCompatActivity {
    MediaPlayer media;
    int seek=0;
    //AudioManager service=(AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
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
        setContentView(R.layout.activity_familymamber);
        final ArrayList<Word> family = new ArrayList<Word>();
        family.add(new Word("father","әpә",R.drawable.family_father,R.raw.family_father));
        family.add(new Word("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        family.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        family.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        family.add(new Word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        family.add(new Word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        family.add(new Word("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        family.add(new Word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        family.add(new Word("grandmother ","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        family.add(new Word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));


        //LinearLayout rootview=(LinearLayout)findViewById(R.id.rootView);
        WordAdpter familyAdapter = new WordAdpter(this, family,R.color.category_family);

        ListView listView3 = (ListView) findViewById(R.id.list3);

        if (listView3 != null) {
            listView3.setAdapter(familyAdapter);
            listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(media != null){
                        if(!media.isPlaying())  {
                            media = MediaPlayer.create(getApplicationContext(),family.get(position).getAudioId());
                            media.start();
                            media.setOnCompletionListener(m);

                            int dur= Toast.LENGTH_SHORT;
                            Toast t = Toast.makeText(getApplicationContext(),family.get(position).getEnglishTranslation(),dur);
                            t.show();}
                    /*else {media.stop();

                         media = MediaPlayer.create(getApplicationContext(),color.get(position).getAudioId());

                         media.start();}*/}
                    else {
                        reles();
                        media = MediaPlayer.create(getApplicationContext(),family.get(position).getAudioId());
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
