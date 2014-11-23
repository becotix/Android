package co.becotix.becotix.DB;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name="bus_beacons")
public class BusBeacon extends Model {
    @Column(name="remote_id")
    public long remote_id;
    @Column(name = "major")
    public Integer major;
    @Column(name = "minor")
    public Integer minor;

    public static List<BusBeacon> all() {
        return new Select().from(BusBeacon.class).execute();
    }

    public static void destroyAll() {
        for (BusBeacon stopInfo : all()) {
            stopInfo.delete();
        }
    }

    public static BusBeacon find(long remoteId) {
        return new Select().all().from(BusBeacon.class).where("remote_id = ?", remoteId).executeSingle();
    }
}