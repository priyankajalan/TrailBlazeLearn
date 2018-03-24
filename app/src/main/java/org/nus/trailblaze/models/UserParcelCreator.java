package org.nus.trailblaze.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by plasmashadow on 3/22/18.
 */

public class UserParcelCreator implements Parcelable.Creator<User> {
    @Override
    public User createFromParcel(Parcel parcel) {
        return new User(parcel);
    }

    @Override
    public User[] newArray(int i) {
        return new User[i];
    }
}
