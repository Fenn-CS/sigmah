package org.sigmah.server.util;

import java.util.Calendar;
import java.util.Date;
import org.sigmah.shared.dto.pivot.model.DateUnit;
import org.sigmah.shared.util.DateRange;
import org.sigmah.shared.util.Dates;
import org.sigmah.shared.util.Month;

/**
 * Calendar implementation of {@link Dates}.
 * 
 * @author Alex Bertram (akbertram@gmail.com)
 * @author Raphaël Calabro (rcalabro@ideia.fr) v2.0
 */
public class CalendarDates extends Dates {

	@Override
    public Month getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return new Month(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1);
    }

    @Override
    public DateRange yearRange(int year) {
        DateRange range = new DateRange();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        range.setMinDate(calendar.getTime());

        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DATE, 31);
        range.setMaxDate(calendar.getTime());

        return range;
    }

    @Override
    public DateRange monthRange(int year, int month) {
        DateRange range = new DateRange();
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DATE, 1);
        range.setMinDate(calendar.getTime());

        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        range.setMaxDate(calendar.getTime());

        return range;
    }

    @Override
    public int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DATE);
    }

    @Override
    public int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);
    }

    @Override
    public int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.MONTH) + 1;
    }

	@Override
    public Date floor(Date date, DateUnit dateUnit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if(dateUnit == DateUnit.YEAR) {
            calendar.set( Calendar.MONTH, calendar.getActualMinimum( Calendar.MONTH ) );
            calendar.set( Calendar.DATE, calendar.getActualMinimum( Calendar.MONTH ));

        } else if(dateUnit == DateUnit.QUARTER ) {

            // TODO
            throw new Error("not implemented");

        } else if(dateUnit == DateUnit.MONTH ) {

            calendar.set( Calendar.DATE, calendar.getActualMinimum( Calendar.MONTH ) );

        } else if(dateUnit == DateUnit.WEEK ) {

            // TODO
            throw new Error("not implemented");

        }
        return calendar.getTime();
    }

	@Override
    public Date ceil(Date date, DateUnit dateUnit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if(dateUnit == DateUnit.YEAR) {
            calendar.set( Calendar.MONTH, calendar.getActualMaximum( Calendar.MONTH ) );
            calendar.set( Calendar.DATE, calendar.getActualMinimum( Calendar.DAY_OF_MONTH ) );

        } else if(dateUnit == DateUnit.QUARTER ) {

            // TODO
            throw new Error("not implemented");

        } else if(dateUnit == DateUnit.MONTH ) {

            calendar.set( Calendar.DATE,
                    calendar.getActualMaximum( Calendar.DATE ));

        } else if(dateUnit == DateUnit.WEEK ) {

            // TODO
            throw new Error("not implemented");

        }

        return calendar.getTime();
    }

	@Override
    public Date add(Date date, DateUnit dateUnit, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if(dateUnit == DateUnit.YEAR) {

            calendar.add( Calendar.YEAR, count );

        } else if(dateUnit == DateUnit.QUARTER ) {

            calendar.add( Calendar.MONTH, count * 3);

        } else if(dateUnit == DateUnit.MONTH ) {

            calendar.add( Calendar.MONTH, count);

        } else if(dateUnit == DateUnit.WEEK ) {

            calendar.add( Calendar.DATE, count * 7);

        }

        return calendar.getTime();
    }

    @Override
    public boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        return calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DATE);
    }
}
