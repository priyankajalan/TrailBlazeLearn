package org.nus.trailblaze.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wengweichen on 17/3/18.
 */

public class AsyncTaskReadTextFile extends AsyncTask<String, Integer, String> {

    private TextView textViewToSet;
    private ProgressDialog dialog;

    public AsyncTaskReadTextFile(TextView textView, ProgressDialog dialog) {
        this.textViewToSet = textView;
        this.dialog = dialog;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        try {
            URL url = new URL(params[0]);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = null;

            while ((line = in.readLine()) != null) {
                //get lines
                result+=line;
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        this.textViewToSet.setText(result);
        this.dialog.dismiss();
    }
}