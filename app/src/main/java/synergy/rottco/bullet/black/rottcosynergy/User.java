package synergy.rottco.bullet.black.rottcosynergy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String authKey;

    @ColumnInfo(name = "user_data")
    public String user_data;

    public User(String authKey, String user_data) {
        this.authKey = authKey;
        this.user_data=  user_data;
    }

    @NonNull
    public String getAuthKey() {
        return authKey;
    }

    public String getUser_data() {
        return user_data;
    }
}
