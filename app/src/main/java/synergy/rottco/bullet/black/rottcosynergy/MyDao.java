package synergy.rottco.bullet.black.rottcosynergy;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by boghi on 12/17/2017.
 */

@Dao
public interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertHash(Hashes hash);

    @Insert
    public void insertHashList(List<Hashes> hashesList);

    @Query("SELECT * FROM hashes")
    public Hashes[] loadAllHashes();

    @Query("DELETE FROM hashes")
    public void deleteAllHashes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertGasStation(GasStation gasStation);

    @Insert
    public void insertGasStationList(List<GasStation> gasStationList);

    @Query("SELECT * FROM gasstation")
    public GasStation[] loadAllGasStations();

    @Query("DELETE FROM gasstation")
    public void deleteAllGasStations();


    //User
    @Query("SELECT * FROM User")
    public User[] loadAllUsers();

    @Query("DELETE FROM User")
    public void deleteAllUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUser(User user);

}
