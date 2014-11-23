package co.becotix.becotix;

import android.content.Context;
import android.content.SharedPreferences;

import co.becotix.becotix.DB.Ticket;

public class TicketManager {

    private final static String CURRENT_TICKET = "CURRENT_TICKET";

    Context mContext;

    public TicketManager(Context context) {
        mContext = context;
    }

    public Ticket currentOrNew() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        long currentTicketId = sharedPreferences.getLong(CURRENT_TICKET, 0);
        if (currentTicketId != 0) {
            Ticket ticket = Ticket.find(currentTicketId);
            if (ticket != null) {
                return ticket;
            }
        }
        Ticket ticket = Ticket.all().get(0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(CURRENT_TICKET, ticket.getId());
        editor.commit();
        return ticket;
    }

    public void closeCurrent() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CURRENT_TICKET);
        editor.commit();
    }
}
