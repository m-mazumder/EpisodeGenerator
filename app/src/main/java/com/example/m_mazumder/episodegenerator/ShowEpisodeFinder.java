package com.example.m_mazumder.episodegenerator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.MalformedJsonException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Created by m-mazumder on 11/5/2017.
 */

class ShowEpisodeFinder extends AsyncTask<Void, Void, Void> {
    private String showName;
    String name;
    String title;
    String season;
    String number;
    String airdate;
    String runtime;
    Bitmap image;
    String summary;

    ShowEpisodeFinder(String showName){
        this.showName = showName.replaceAll(" ", "-").toLowerCase();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //establish a connection
            URL url = new URL("http://api.tvmaze.com/singlesearch/shows?q=" + showName + "&embed=episodes" );
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            //read data from the site
            InputStream is = http.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String data = "";
            String nextLine = "";
            while(nextLine != null){
                nextLine = br.readLine();
                data += nextLine;
            }

            //parse the JSON data
            JSONObject mainObject = new JSONObject(data);
            name = mainObject.getString("name");

            //get number of episodes within the show
            JSONObject embedded = mainObject.getJSONObject("_embedded");
            JSONArray episodes = embedded.getJSONArray("episodes");

            //get the random episode
            Random random = new Random();
            int randomEpisodeNumber = random.nextInt(episodes.length()) + 1;
            JSONObject episode = episodes.getJSONObject(randomEpisodeNumber);

            title = episode.getString("name");
            season = episode.getString("season");
            number = episode.getString("number");
            airdate = episode.getString("airdate");
            runtime = episode.getString("runtime");
            String imageURL = episode.getString("image");
            summary = episode.getString("summary").replace("<p>", "");
            summary = summary.replace("</p>", "");
            imageURL = episode.getJSONObject("image").getString("medium");

            //load image
            image = BitmapFactory.decodeStream((InputStream)new URL(imageURL).getContent());



        }
        catch(MalformedJsonException e){
            e.printStackTrace();
        }
        catch(IOException i){
            i.printStackTrace();
        }
        catch (JSONException j){
            j.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.name.setText(this.name);
        MainActivity.title.setText(this.title);
        MainActivity.season.setText(this.season);
        MainActivity.episode.setText(this.number);
        MainActivity.airdate.setText(this.airdate);
        MainActivity.runtime.setText(this.runtime);
        MainActivity.summary.setText(this.summary);
        MainActivity.image.setImageBitmap(image);
    }

}