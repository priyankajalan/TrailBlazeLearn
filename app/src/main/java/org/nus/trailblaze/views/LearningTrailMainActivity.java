package org.nus.trailblaze.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.nus.trailblaze.R;
import org.nus.trailblaze.adapters.LearningTrailFirestoreAdaptor;
import org.nus.trailblaze.listeners.ListItemClickListener;
import org.nus.trailblaze.models.LearningTrail;

public class LearningTrailMainActivity extends Activity implements ListItemClickListener {

    private static final String TAG = "LearningTrailActivity";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirestoreRecyclerAdapter adapter;
    private FirebaseFirestore firestoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_trail_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvLearningTrail);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        firestoreDB = FirebaseFirestore.getInstance();

        loadItemsList();
    }

    private void loadItemsList() {
        Query query = firestoreDB.collection("trails");

        FirestoreRecyclerOptions<LearningTrail> response = new FirestoreRecyclerOptions.Builder<LearningTrail>()
                .setQuery(query, LearningTrail.class)
                .build();

        adapter = new LearningTrailFirestoreAdaptor(response, this);

        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onListItemClick(int position) {
        LearningTrail item = (LearningTrail) adapter.getItem(position);
        Log.d("ACTIVITY", item.getName());
        Log.d("[TextView]", String.valueOf(item.getId()));

        //temporary comment
        /*Intent myIntent;

        if(item.getFile().getMimeType().indexOf("audio") > -1){
            myIntent = new Intent(TrailBlazaFeedActivity.this,
                    AudioPlayerActivity.class);

        }
        else{
            myIntent = new Intent(TrailBlazaFeedActivity.this,
                    TrailBlazeItemViewerActivity.class);

        }
        myIntent.putExtra("Item", item);
        startActivity(myIntent);
        // Following the documentation, right after starting the activity
        // we override the transition
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);*/
    }

//    private class OptionsButtonViewHolder extends RecyclerView.ViewHolder {
//
//        public TextView LearningTrailName;
//        public Button buttonOptions;
//
//        public OptionsButtonViewHolder(View itemView) {
//            super(itemView);
//
//            LearningTrailName = (TextView) itemView.findViewById(R.id.tvLearningTrailName);
//            buttonOptions = (Button) itemView.findViewById(R.id.buttonOptions);
//        }
//    }
}
