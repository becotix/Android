package co.becotix.becotix.DB;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name="journey")
public class Journey extends Model {
    @Column(name = "start_id")
    public long start_id = 0;
    @Column(name = "start_time")
    public String start_time;
    @Column(name = "end_id")
    public long end_id = 0;
    @Column(name = "end_time")
    public String end_time;

    public static List<Journey> all() {
        return new Select().from(Journey.class).execute();
    }

    public static void destroyAll() {
        for (Journey stopInfo : all()) {
            stopInfo.delete();
        }
    }

    public static Journey find(long id) {
        return new Select().from(Journey.class).where("id = ?", id).executeSingle();
    }

    public StopInfo start_stop() {
        if (start_id != 0) {
            StopInfo stopInfo = StopInfo.findByRemoteId(start_id);
            if (stopInfo != null) {
                return stopInfo;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    public StopInfo end_stop() {
        if (start_id != 0) {
            StopInfo stopInfo = StopInfo.findByRemoteId(end_id);
            if (stopInfo != null) {
                return stopInfo;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }
}