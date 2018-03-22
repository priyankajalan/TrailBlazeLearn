package org.nus.trailblaze.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.nus.trailblaze.R;
import org.nus.trailblaze.listeners.ListItemClickListener;
import org.nus.trailblaze.models.ContributedItem;
import org.nus.trailblaze.viewholders.FeedViewHolder;

/**
 * Created by wengweichen on 14/3/18.
 */

public class FeedFirestoreAdapter extends FirestoreRecyclerAdapter<ContributedItem, FeedViewHolder> {

    private static final String TAG = FeedFirestoreAdapter.class.getSimpleName();
    final private ListItemClickListener mOnClickListener;

    public FeedFirestoreAdapter(FirestoreRecyclerOptions<ContributedItem> response,
                                ListItemClickListener listener) {
        super(response);
        mOnClickListener = listener;
    }

    public FeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trail_blaza_feed_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        FeedViewHolder viewHolder = new FeedViewHolder(view, mOnClickListener);

        return viewHolder;
    }

    public void onBindViewHolder(FeedViewHolder viewHolder, int index, ContributedItem model) {
        viewHolder.bind(model);
    }

    @Override
    public void onError(FirebaseFirestoreException e) {
        Log.e("error", e.getMessage());
    }
}
