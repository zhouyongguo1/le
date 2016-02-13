package le.oa.core.test;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeTest {
    @Test
    public void test_date() {
        String date = "2015-10-10";
        LocalDate dateTime = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(dateTime);
    }
}
