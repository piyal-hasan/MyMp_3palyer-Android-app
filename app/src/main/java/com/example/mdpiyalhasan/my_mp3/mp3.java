package com.example.mdpiyalhasan.my_mp3;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class mp3 extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    ListView mp3lv;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3);
        //listview initialization
        mp3lv=(ListView) findViewById(R.id.mp3lv);  //play list assignment
        final ArrayList<File> mysong=findsong(Environment.getExternalStorageDirectory());  //file read and store in arry list
        items=new String[mysong.size()];       //song name store in string arry and declare

        for(int i=0;i<mysong.size();i++)
        {
            //toast(mysong.get(i).getName().toString());
            items[i]= String.valueOf(mysong.get(i).getName()).replace(".mp3","").replace(".wav",""); //replace the song last part .mp3
        }
        ArrayAdapter<String>adb=new ArrayAdapter<String>(getApplicationContext(),R.layout.mp3layout,R.id.textView,items);  //make array adaptar to read the list view
        mp3lv.setAdapter(adb); //i can use arry adb to store rthe song in arrylist
        mp3lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {    //song click listener
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),PlayerActivity.class).putExtra("pos",position).putExtra("songlist",mysong)); //sent the another activity

            }
        });
    }
    public ArrayList<File> findsong(File root)  //arrylist function to create arry only use mp3 file

    {
        ArrayList<File> arraylist=new ArrayList<File>();
        File[] files=root.listFiles();
        for(File singlesong:files)
        {
            if(singlesong.isDirectory()&& !singlesong.isHidden())
            {
               arraylist.addAll(findsong(singlesong)); //if directory within directory,the recursive method that call and store arry list
            }
            else {
                if(singlesong.getName().endsWith(".mp3")||singlesong.getName().endsWith(".wav"))
                {
                    arraylist.add(singlesong); //if mp3 so i add the mp3 song from the to arry list
                }
            }
        }

     return arraylist;
    }
    public void toast(String file)
    {
        Toast.makeText(getApplicationContext(),file,Toast.LENGTH_SHORT).show();
    }

}
