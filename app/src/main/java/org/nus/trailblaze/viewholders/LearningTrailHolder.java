package org.nus.trailblaze.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.nus.trailblaze.R;
import org.nus.trailblaze.listeners.ListItemClickListener;
import org.nus.trailblaze.models.LearningTrail;

/**
 * Created by kooc on 3/20/2018.
 */

public class LearningTrailHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private EditText learningTrailName;
    private TextView tvLearningTrailName;
    private RecyclerView learningTrailView;
    private ListItemClickListener listener;

    public LearningTrailHolder(View itemView, ListItemClickListener listener) {
        super(itemView);
        this.listener = listener;

        itemView.setOnClickListener(this);

        learningTrailView = (RecyclerView) itemView.findViewById(R.id.rvLearningTrail);
        learningTrailName = (EditText) itemView.findViewById(R.id.learningTrailName);
        tvLearningTrailName = (TextView) itemView.findViewById(R.id.tvLearningTrailName);
    }

    public void bind(LearningTrail item) {
        learningTrailName.setText(item.getName());
        tvLearningTrailName.setText(item.getName());
    }

    public void onClick(View view) {
        int clickedPosition = getAdapterPosition();
        listener.onListItemClick(clickedPosition);
    }
}
