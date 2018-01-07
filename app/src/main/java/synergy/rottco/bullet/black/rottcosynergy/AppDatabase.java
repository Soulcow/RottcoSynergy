package synergy.rottco.bullet.black.rottcosynergy;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by boghi on 12/17/2017.
 */

@Database(entities = {Hashes.class, GasStation.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MyDao myDao();
}
