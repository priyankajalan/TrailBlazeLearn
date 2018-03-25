package org.nus.trailblaze.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import org.nus.trailblaze.R;

/**
 * Created by priyanka on 15/3/2018.
 */

public class NewTrailActivity extends AppCompatActivity {
    private EditText trailTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_learning_trail);
        trailTitle = (EditText) findViewById(R.id.trailTitle);
    }
}
