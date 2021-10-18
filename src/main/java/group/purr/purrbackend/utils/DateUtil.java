package group.purr.purrbackend.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
public class DateUtil {

    public static String formatE8Date(Date date){
        SimpleDateFormat dateE8 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateE8.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        if (date == null) return "";
        return dateE8.format(date);
    }
}
