package synergy.rottco.bullet.black.rottcosynergy;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by boghi on 12/17/2017.
 */

public class DatabaseSingleton {

    private  AppDatabase db= null;
    public DatabaseSingleton(Context myContext)
    {
        if(db==null)
        {
            db = Room.databaseBuilder(myContext.getApplicationContext(),
                    AppDatabase.class, "database-name").build();
        }
    }

    public AppDatabase getDbSingleton()
    {
        return db;
    }


}
