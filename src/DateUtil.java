import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    private static final String DATE_TIME_BR = "dd/MM/yyyy HH:mm:ss.SSS";

    private static final String DATE_TIME_USA = "yyyy-MM-dd'T'HH:mm:ss.SSSz";

    public static String getDateTimeBRfromUSA(String date) {
        if(date == null || date.isEmpty()) {
            return "";
        }

        if(date.length() == 28){
            date = date.substring(0, date.length()-5);
        }

        var formatterBr = DateTimeFormatter.ofPattern(DATE_TIME_BR);
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_USA);
            sdf.setLenient(false);
            ZonedDateTime parseDate = ZonedDateTime.parse(date, DateTimeFormatter.ofPattern(DATE_TIME_USA));
            return parseDate.
                    format(formatterBr);
        } catch (Exception e) {
            if(date.length() == 10) {
                var day = date.substring(8, 10);
                var month = date.substring(5,7);
                var year = date.substring(0,4);
                return String.format("%s/%s/%s",day, month, year);
            }
            return date;
        }
    }

    public static String getDateTime() {
        var now = ZonedDateTime.now();
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'-0300'");
        return now.withZoneSameInstant(ZoneId.of("America/Sao_Paulo")).format(formatter);
    }

    public static String getStringDateTime(String date, String inputFormat) {
        try {
            var myDate = new SimpleDateFormat(inputFormat).parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(myDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            sdf.setTimeZone(TimeZone.getTimeZone(ZoneId.of("America/Sao_Paulo")));
            return sdf.format(myDate);

        } catch (ParseException e) {

        }
        return null;
    }

    public static final String NUMBER_REGEX = "[^0-9]";

    public static void main(String[] args) throws ParseException {


        String data01 = "07/09/2022".replaceAll(NUMBER_REGEX, "");

        var localTime = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        //pegando data de agora
        System.out.println("Pegando data local: " + LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));


        //convertendo para instant uma data isto que sera salvo
        Instant instant2 = localTime.toInstant(ZoneOffset.of("-03:00"));

        System.out.println("convertendo para instant uma data: " + instant2);


        // covertendo string to local data
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
        final LocalDate dt = LocalDate.parse(data01, dtf);

        System.out.println(dt);
    }
}
