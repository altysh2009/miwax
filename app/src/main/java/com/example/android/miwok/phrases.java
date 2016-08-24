package com.example.android.miwok;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
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

public class phrases extends AppCompatActivity {
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
        setContentView(R.layout.activity_phrases);
        final ArrayList<Word> phrases = new ArrayList<Word>();
        phrases.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        phrases.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        phrases.add(new Word("My name is..", "oyaaset...", R.raw.phrase_my_name_is));
        phrases.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        phrases.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        phrases.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        phrases.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        phrases.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        phrases.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        phrases.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));
        WordAdpter phraseadapter = new WordAdpter(this, phrases, R.color.category_phrases);
        AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        final int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);


        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);

        ListView listView2 = (ListView) findViewById(R.id.list2);
        LinearLayout l1 = (LinearLayout) findViewById(R.id.leniear);


        //listView2.setAdapter(phraseadapter);
        if (listView2 != null) {
            listView2.setAdapter(phraseadapter);
            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(media != null){
                        if(!media.isPlaying())  {
                            media = MediaPlayer.create(getApplicationContext(),phrases.get(position).getAudioId());
                            media.start();
                            media.setOnCompletionListener(m);

                            int dur=Toast.LENGTH_SHORT;
                            Toast t = Toast.makeText(getApplicationContext(),phrases.get(position).getEnglishTranslation(),dur);
                            t.show();}
                    /*else {media.stop();

                         media = MediaPlayer.create(getApplicationContext(),color.get(position).getAudioId());

                         media.start();}*/}
                    else {
                        reles();
                        media = MediaPlayer.create(getApplicationContext(),phrases.get(position).getAudioId());
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
