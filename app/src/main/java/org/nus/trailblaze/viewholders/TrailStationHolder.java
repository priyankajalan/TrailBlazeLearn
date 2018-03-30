package org.nus.trailblaze.viewholders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.nus.trailblaze.R;
import org.nus.trailblaze.adapters.TrailStationFirestoreAdapter;
import org.nus.trailblaze.listeners.ListItemClickListener;
import org.nus.trailblaze.models.TrailStation;
import org.nus.trailblaze.views.SetTrailStationActivity;


/**
 * Created by AswathyLeelakumari on 24/3/2018.
 */

public class TrailStationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//    private TextView stationId;
    private TextView stationName;
    private Button btnUpdate;
    private Button btnDelete;
    private RecyclerView trailStationView;
    private ListItemClickListener listener;
    private Context context;
    private TrailStationFirestoreAdapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public TrailStationHolder(final Context context, View itemView, ListItemClickListener listener, final DocumentSnapshot docSnapshot) {
        super(itemView);
        this.listener = listener;
        this.context = context;

//        itemView.setOnClickListener(this);

        trailStationView = (RecyclerView) itemView.findViewById(R.id.rv_trail_station_list);
//        stationId = (TextView) itemView.findViewById(R.id.station_id);
        stationName = (TextView) itemView.findViewById(R.id.station_name);
        btnUpdate = (Button) itemView.findViewById(R.id.btnUpdateOptions);
        //btnDelete = (Button) itemView.findViewById(R.id.btnDeleteOptions);

//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateStation(view);
//            }
//        });

        btnUpdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View v) {
                PopupMenu menu = new PopupMenu(TrailStationHolder.this.context, v);
                menu.inflate(R.menu.options_menu);

//                DocumentSnapshot ds = adaptor.getSnapshots().getSnapshot(LearningTrailHolder.super.getAdapterPosition());
//                final String documentID = ds.getId();

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //Not always working...
                        //Delete the first row will crash
                        final String documentID = docSnapshot.getReference().getId();
                        final int itemID = item.getItemId();
                        Log.d("[DocID Trial delete]", String.valueOf(R.id.itemDelete));
                        Log.d("[DocID Trial edit]", String.valueOf(R.id.itemEdit));
                        Log.d("[DocID Trial itemid]", String.valueOf(itemID));
                        String threadValue = null;
                        Object str = docSnapshot.getData().get(SetTrailStationActivity.THREAD);
                        if(str != null) {
                            threadValue = str.toString();
                        }
                        final String nameValue = docSnapshot.getData().get(SetTrailStationActivity.NAME).toString();
                        final String instrValue = docSnapshot.getData().get(SetTrailStationActivity.INSTRUCTION).toString();

                        switch (item.getItemId()) {
                            case R.id.itemDelete:
                                Log.d("stations delete", v.toString());
                                db.collection("stations").document(documentID)
                                        .delete();

                                break;
                            case R.id.itemEdit:

                                Log.d("stations update", v.toString());
                                Intent intent = new Intent(context.getApplicationContext(), SetTrailStationActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(SetTrailStationActivity.DOCUMENTID, documentID);
                                Log.d("[ID-id]", String.valueOf(documentID));
                                bundle.putString(SetTrailStationActivity.THREADVALUE, threadValue);
                                Log.d("[ID-thread]", String.valueOf(threadValue));
                                bundle.putString(SetTrailStationActivity.NAMEVALUE, nameValue);
                                Log.d("[ID-name]", String.valueOf(nameValue));
                                bundle.putString(SetTrailStationActivity.INSTRVALUE, instrValue);
                                Log.d("[ID-instr]", String.valueOf(instrValue));
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });

//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deleteStation(view);
//            }
//        });
    }

    private void updateStation(View v) {
        Log.d("stations update", v.toString());
    }

    private void deleteStation(View v) {
        Log.d("stations delete", v.toString());
    }

    public void bind(TrailStation item) {
//        stationId.setText(item.getId());
        stationName.setText(item.getName());
    }

    public void onClick(View view) {
        int clickedPosition = getAdapterPosition();
        listener.onListItemClick(clickedPosition);
        Log.d("add activity", "next");
    }

}
