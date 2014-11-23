package co.becotix.becotix.DB;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name="beacons")
public class Beacon extends Model {
    @Column(name = "remote_id")
    public long remote_id;
    @Column(name = "major")
    public long major;
    @Column(name = "minor")
    public long minor;

    public static List<Beacon> all() {
        return new Select().from(Beacon.class).execute();
    }

    public static void destroyAll() {
        for (Beacon stopInfo : all()) {
            stopInfo.delete();
        }
    }

    public static Beacon find(long id) {
        return new Select().from(Beacon.class).where("id = ?", id).executeSingle();
    }

    public static Beacon findByMajor(long major) {
        return new Select().from(Beacon.class).where("major = ?", major).executeSingle();
    }
}