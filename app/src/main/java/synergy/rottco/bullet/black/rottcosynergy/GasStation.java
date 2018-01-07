package synergy.rottco.bullet.black.rottcosynergy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by boghi on 12/18/2017.
 */

@Entity
public class GasStation {
    @PrimaryKey
    @NonNull
    public String hash;

    @ColumnInfo(name = "gas_station")
    public String gas_station;

    public GasStation(String hash,String gas_station) {
        this.hash = hash;
        this.gas_station=  gas_station;
    }

    @NonNull
    public String getHash() {
        return hash;
    }

    public String getName() {
        return gas_station;
    }
}
