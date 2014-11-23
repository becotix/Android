package co.becotix.becotix.DB;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name="stop_infos")
public class StopInfo extends Model {
    @Column(name="remote_id")
    public long remoteId;
    @Column(name = "name")
    public String name;
    @Column(name = "major")
    public Integer major;
    @Column(name = "minor")
    public Integer minor;

    public static List<StopInfo> all() {
        return new Select().from(StopInfo.class).execute();
    }

    public static void destroyAll() {
        for (StopInfo stopInfo : all()) {
            stopInfo.delete();
        }
    }

    public static StopInfo find(long id) {
        return new Select().from(StopInfo.class).where("id = ?", id).executeSingle();
    }

    public static StopInfo findByMajor(long id) {
        return new Select().from(StopInfo.class).where("major = ?", id).executeSingle();
    }

    public static StopInfo findByRemoteId(long id) {
        return new Select().from(StopInfo.class).where("remote_id = ?", id).executeSingle();
    }
}