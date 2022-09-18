package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

public class Date {

    private static final String DD_MM_YYYY = "dd/MM/yyyy";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String DATE_TIME_USA = "yyyy-MM-dd'T'HH:mm:ss.SSSz";
    private static final String DATE_TIME_BR = "dd/MM/yyyy HH:mm:ss.SSS";

    public static final String NUMBER_REGEX = "[^0-9]";


    public static String getDateTimeBRfromUSA(final String date) {
        if(date == null || date.isEmpty()) {
            return "";
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


    public static String getDateTimeUSAfromBR(final String date) {
        if(date == null || date.isEmpty()) {
            return "";
        }
        var formatterUS = DateTimeFormatter.ofPattern(DATE_TIME_USA);
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_BR);
            sdf.setLenient(false);
            java.util.Date parseDate = sdf.parse(date);
            return parseDate.toInstant()
                    .atZone(ZoneId.of("America/Sao_Paulo"))
                    .toLocalDate().format(formatterUS);
        } catch (Exception e) {
            if(date.length() == 10) {
                var day = date.substring(0, 2);
                var month = date.substring(3,5);
                var year = date.substring(6,10);
                return String.format("%s-%s-%s", year, month, day );
            }
            return date;
        }
    }


    // testar
    public static String getDateTime() {
        var now = ZonedDateTime.now();
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'-0300'");
        return now.withZoneSameInstant(ZoneId.of("America/Sao_Paulo")).format(formatter);
    }


    // tesstar
    public boolean isBefore(java.util.Date dateBase, String dateTest, String inputFormat) {
        try {
            var myDate = new SimpleDateFormat(inputFormat).parse(dateTest);
            myDate   = adjustInitialDate(myDate);
            dateBase = adjustInitialDate(dateBase);
            return myDate.before(dateBase);
        } catch (ParseException e) {
//            log.error(e.getMessage());
        }
        return false;
    }

    public boolean isAfter(java.util.Date dateBase, String dateTest, String inputFormat) {
        try {
            var myDate = new SimpleDateFormat(inputFormat).parse(dateTest);
            myDate   = adjustInitialDate(myDate);
            dateBase = adjustInitialDate(dateBase);
            return myDate.after(dateBase);
        } catch (ParseException e) {
//            log.error(e.getMessage());
        }
        return false;
    }


    public static String convertFormat(String date, String inputFormat, String outputFormat) {
        try {
            var formatInput  = new SimpleDateFormat(inputFormat);
            var formatOutput = new SimpleDateFormat(outputFormat);
            java.util.Date myDate      = formatInput.parse(date);
            return formatOutput.format(myDate);
        } catch (ParseException e) {
//            log.error(e.getMessage());
        }
        return null;
    }


    public java.util.Date adjustInitialDate(java.util.Date date) {
//        log.info("adjustInitialDate({})", date);

        var result = adjustDate(date, "yyyy-MM-dd'T'00:00:00.000Z");

        return result;
    }


    public java.util.Date adjustDate(java.util.Date date, String format) {
//        log.info("adjustDate({}, {})", date, format);

        var formatOutput = new SimpleDateFormat(format);
        var dateString = formatOutput.format(date);
        var formatInput = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        var result = date;
        try {
            result = formatInput.parse(dateString);
        } catch (ParseException e) {
//            log.error(e.getMessage());
        }

//        log.info("adjustDate({}, {}) = {}", date, format, result);
        return result;
    }


    public static LocalDate checkIfTheDateIsValidAndConvert(String strDate) {
        String dateFormat = "dd/MM/uuuu";

        if(!strDate.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) return null;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(dateFormat)
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            return LocalDate.parse(strDate, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }



    public static void main(String[] args) throws ParseException {
        String lvarStrDateOfTransaction = "29/072013";

        var nha = lvarStrDateOfTransaction.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})");

        System.out.println(nha);


        //checking if you received the correct date
        System.out.println("29/02/2016 eh uma data valida? " + checkIfTheDateIsValidAndConvert("29/02/2016"));
        System.out.println("29/02/2017 eh uma data valida? " + checkIfTheDateIsValidAndConvert("29/02/2017"));
        System.out.println("31/06/2017 eh uma data valida? " + checkIfTheDateIsValidAndConvert("30/01/2017"));
        System.out.println("31/04/2017 eh uma data valida? " + checkIfTheDateIsValidAndConvert("31/04/2017"));



        // Example of dates received. We remove the special characters
        String data01 = "07/09/2022".replaceAll(NUMBER_REGEX, "");
        String data02 = "07-09-2020".replaceAll(NUMBER_REGEX, "");
        String data03 = "07/09/2090".replaceAll(NUMBER_REGEX, "");


        // Converting to local date time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDate dt = LocalDate.parse(data01, formatter);

        //DataTime Now Format
        var dateNowFormat = LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Date time Local
        var dateNow = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

        LocalDate lt
                = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

        //printing comparisons
//        System.out.println(dateNow);
//        System.out.println(dt.isEqual(ChronoLocalDate.from(dateNow)));
//        System.out.println(dt.isBefore(ChronoLocalDate.from(dateNow)));
//        System.out.println(dt.isAfter(ChronoLocalDate.from(dateNow)));
//        var date = getDateTime();
//        System.out.println(date);


        LocalDate date = LocalDate.now();
        Instant instant = date.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toInstant();

        System.out.println("2025-01-10T03:00:00Z".length());




        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        java.util.Date dateParsed = sdf.parse("2025-01-10T03:00:00Z");

        System.out.println(output.format(dateParsed));

//        return output.format(dateParsed);


//        var dateResult = simpleDateFormat.parse(date.replaceAll("Z$", "+0000"));
//        return (new SimpleDateFormat(DATE_TIME_BR))
//                .format(dateResult).replace("T", " ").substring(0, dateResult.toString().length()-5);
//
//        SimpleDateFormat formatter2 = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
//        String formattedDate = formatter2.format(myDate );
//
//        System.out.println(formattedDate);
    }
}
