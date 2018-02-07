package synergy.rottco.bullet.black.rottcosynergy;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Hashes.class, GasStation.class, User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MyDao myDao();
}
