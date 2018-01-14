package synergy.rottco.bullet.black.rottcosynergy;

import android.arch.persistence.room.Room;
import android.content.Context;



public class DatabaseSingleton {

    private  AppDatabase db= null;
    public DatabaseSingleton(Context myContext)
    {
        if(db==null)
        {
            db = Room.databaseBuilder(myContext.getApplicationContext(),
                    AppDatabase.class, "database-name").fallbackToDestructiveMigration().build();
        }
    }

    public AppDatabase getDbSingleton()
    {
        return db;
    }


}
