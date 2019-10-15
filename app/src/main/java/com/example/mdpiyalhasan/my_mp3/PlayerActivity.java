package com.example.mdpiyalhasan.my_mp3;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

class PlayerActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList mysong;
    static MediaPlayer mp;
    SeekBar seekBar;
    Button nxt,pev,pevfw,nxtfw,playbt;
    int position;
    Uri song_uri;
    Thread th;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        nxt=(Button)findViewById(R.id.nxt);

        nxtfw=(Button)findViewById(R.id.nxtfw);

        pev=(Button)findViewById(R.id.pev);

        pevfw=(Button)findViewById(R.id.pevfw);

        playbt=(Button)findViewById(R.id.playbt);

        playbt.setOnClickListener(this);

        nxt.setOnClickListener(this);

        nxtfw.setOnClickListener(this);

        pev.setOnClickListener(this);

        pevfw.setOnClickListener(this);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        th=new Thread(){
            @Override
            public void run() {
                int totalduration =mp.getDuration();
                int currentposition=0;
                while(currentposition<totalduration)
                {
                    try {
                        sleep(500);
                        currentposition=mp.getCurrentPosition();
                        seekBar.setProgress(currentposition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
               // super.run();

            }
        };
        if(mp!=null)
        {
            mp.stop();
            mp.release();
        }
        Intent player_intent=getIntent();
        Bundle playlist_bundle=player_intent.getExtras();
        mysong=(ArrayList)playlist_bundle.getParcelableArrayList("songlist");
        position=playlist_bundle.getInt("pos",0);
        song_uri=Uri.parse(mysong.get(position).toString());
        mp=MediaPlayer.create(getApplicationContext(),song_uri);
        mp.start();
        seekBar.setMax(mp.getDuration());
        th.start(); //seekbar start option
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            mp.seekTo(seekBar.getProgress());
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.playbt:
            {
                if(mp.isPlaying())
                {
                    mp.pause();
                    playbt.setText(">");
                }
                else {
                    mp.start();
                    seekBar.setMax(mp.getDuration());
                    playbt.setText("||");
                    break;
                }
            }
            case R.id.nxtfw:
            {
                mp.seekTo(mp.getCurrentPosition()+5000);
                break;
            }
            case R.id.pevfw:
            {
                mp.seekTo(mp.getCurrentPosition()-5000);
                break;
            }
            case R.id.nxt:
            {
                mp.stop();
                mp.release();
                position=(position+1)%mysong.size();
                song_uri=Uri.parse(mysong.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),song_uri);
                mp.start();
                seekBar.setMax(mp.getDuration());
                break;
            }
            case R.id.pev:
            {

                mp.stop();
                mp.release();
                position=(position-1<0)?mysong.size()-1:position-1;
                song_uri=Uri.parse(mysong.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),song_uri);
                mp.start();
                seekBar.setMax(mp.getDuration());
                break;
            }
        }

    }
}
