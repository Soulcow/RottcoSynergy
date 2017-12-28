package synergy.rottco.bullet.black.rottcosynergy;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by boghi on 12/17/2017.
 */

@Entity
public class Hashes {
    @PrimaryKey
    @NonNull
    public String hash;

    public Hashes(String hash)
    {
        this.hash=hash;
    }

    @NonNull
    public String getHash() {
        return hash;
    }
}
