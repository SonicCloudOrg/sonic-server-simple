package com.sonic.simple.tools;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author JayWenStar
 * @date 2021/12/18 4:54 下午
 */
public class DateTool {

    public static LocalDateTime Date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
