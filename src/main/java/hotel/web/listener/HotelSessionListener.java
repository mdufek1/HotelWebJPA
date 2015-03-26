package hotel.web.listener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class HotelSessionListener implements HttpSessionListener {
    

    public void sessionCreated(HttpSessionEvent se) {
        String[] dateSuffixes =
            //    0     1     2     3     4     5     6     7     8     9
               { "  ", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
            //    10    11    12    13    14    15    16    17    18    19
                 "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
            //    20    21    22    23    24    25    26    27    28    29
                 "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
            //    30    31
                 "th", "st" };
        Date date = new Date();
        int day = Calendar.DAY_OF_MONTH;
        String s = "Welcome! You first logged in on: "+new SimpleDateFormat("EEEE MMMM dd").format(date)+dateSuffixes[day]+" at "+new SimpleDateFormat("hh:mm a").format(date);
        se.getSession().setAttribute("time",s);
//        System.out.println(s);
        
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        
    }

}
