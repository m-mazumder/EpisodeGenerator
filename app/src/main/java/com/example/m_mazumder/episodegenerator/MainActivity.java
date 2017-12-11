package com.example.m_mazumder.episodegenerator;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.MalformedJsonException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button generator;
    EditText text;

    //data dependent variables
    static TextView name;
    static TextView title;
    static TextView season;
    static TextView episode;
    static TextView airdate;
    static TextView runtime;
    static TextView summary;
    static ImageView image;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generator = (Button)findViewById(R.id.generatorButton);
        text = (EditText)findViewById(R.id.textInput);
        name = (TextView)findViewById(R.id.name);
        title = (TextView)findViewById(R.id.title);
        season = (TextView)findViewById(R.id.season);
        episode = (TextView)findViewById(R.id.episode);
        airdate = (TextView)findViewById(R.id.airdate);
        runtime = (TextView)findViewById(R.id.runtime);
        summary = (TextView)findViewById(R.id.summary);
        image = (ImageView)findViewById(R.id.episodeImage);
        text.setHintTextColor(Color.argb(255, 169, 169, 169));

        generator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowEpisodeFinder s = new ShowEpisodeFinder(text.getText().toString());
                s.execute();
            }
        });

    }
}


