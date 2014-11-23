package co.becotix.becotix.DB;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name="tickets")
public class Ticket extends Model {
    @Column(name = "word")
    public String word;
    @Column(name = "color")
    public String color;
    @Column(name = "date")
    public String date;

    public static List<Ticket> all() {
        return new Select().from(Ticket.class).execute();
    }

    public static void destroyAll() {
        for (Ticket stopInfo : all()) {
            stopInfo.delete();
        }
    }
}