package nl.hsleiden.service;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DateService {

    public Date addWeeks(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.WEEK_OF_YEAR, amount);
        return calendar.getTime();
    }

}
