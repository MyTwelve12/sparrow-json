package com.sparrowjson.util;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liukai
 */
public class DateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static  final String DATEFORMAT2 = "yyyy-MM-dd HH:mm:ss sss";
    public static  final String DATEFORMAT3 = "YYYYMMDD'T'HHMMSS'Z'";

    public static final String EXPANDED_DATE_FORMAT = "yyyy-MM-dd";

    public static final String YEAR_DATE_FORMAT = "yyyy-MM";
   
    public static Date parseDateFromString(String input) {
        if (StringUtils.isEmpty(input)) {
            return null;
        }
        try {
            Date date = dateFormat.parse(input);
            return date;
        } catch (Exception e) {
            LOGGER.error("input time format error , input str : " + input, e);
            throw new RuntimeException();
        }
    }
    /**
     * parse a String date to Date type date .
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date parse(String dateStr) throws ParseException {
        return parse(dateStr,DATEFORMAT2);
    }
    /**
	 * parse a String date to Date type date .
	 *
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String dateStr, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(dateStr);
	}
	
    public static Date parseDateFromString(String input, String simpleformat) {
        if (StringUtils.isEmpty(simpleformat)) {
            simpleformat = "yyyy-MM-dd";
        }
        if (StringUtils.isEmpty(input)) {
            return null;
        }
        try {
            DateFormat format = new SimpleDateFormat(simpleformat);
            Date date = format.parse(input);
            return date;
        } catch (Exception e) {
            LOGGER.error("input time format error , input: " + input + ", format:"
                    + simpleformat, e);
            throw new RuntimeException();
        }
    }

    public static String formatTime(long input, String simpleformat) {
        if (StringUtils.isEmpty(simpleformat)) {
            simpleformat = "yyyy-MM-dd";
        }
        SimpleDateFormat formate = new SimpleDateFormat(simpleformat);
        return formate.format(input);
    }

    @SuppressWarnings("deprecation")
    public static Date getTimeByDateFormat(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        String dateString = dateFormat.format(date);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            LOGGER.error(
                    "time format error , input date : " + date.toLocaleString()
                            + ", input format : " + format, e);
            throw new RuntimeException();
        }
    }

    /**
     * 获取当前日期的前后几天的日期
     *
     * @param date
     * @param day
     * @return
     */
    public static Date getDayBydateAndKey(Date date, int day) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    public static String dateToString(Date date) {
        return dateToString(date,DATEFORMAT2);
    }

    public static String dateToString2(Date date) {
        return dateToString(date,DATEFORMAT3);
    }

    public static String dateToString(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String dateToStringToIos(Date date) {
        return dateToString(new Date(), EXPANDED_DATE_FORMAT);
    }
    
    /**
     * 获取当年的第一天
     * @param year
     * @return
     */
    public static Date getCurrYearFirst(){
        Calendar currCal=Calendar.getInstance();  
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }
    
    /**
     * 获取某年某月的最后一天
     * @param year
     * @return
     */
    public static Date getLastDayOfMonth(int year, int month){
        Calendar currCal=Calendar.getInstance();  
        currCal.set(Calendar.YEAR, year);
        currCal.set(Calendar.MONTH, month);
        currCal.set(Calendar.DAY_OF_MONTH, 1);
        currCal.add(Calendar.DAY_OF_MONTH, -1);
//        
//        int lastDay = currCal.getActualMaximum(Calendar.DAY_OF_MONTH);
//        currCal.set(Calendar.DAY_OF_MONTH, lastDay);
        return currCal.getTime();
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }
    
    /**
     * 按类型获取日、周、月的开始结束时间
     * @param timeType  时间类型 1、天   2、周   3、月
     * @param isNow true为今天，false取前天、上周、上月的开始结束时间
     * @return
     */
    public static Map<String, Date> getQueryDate(Integer timeType,boolean isNow) {
		Calendar cal = Calendar.getInstance();
		int field = 0,field2 = 0,valueStar = 0,valueEnd = 0,amount = -1;
		Map<String, Date> map = new HashMap<String, Date>();
		if (timeType == 1) {
			field2 = Calendar.DATE;
			cal.setTime(new Date());
		} else if (timeType == 2) {
			field = Calendar.DAY_OF_WEEK;
			field2 = Calendar.WEEK_OF_YEAR;
			valueStar = Calendar.MONDAY;
			valueEnd = Calendar.SUNDAY;
		} else if (timeType == 3) {
			field = Calendar.DATE;
			field2 = Calendar.MONTH;
			valueStar = cal.getActualMinimum(Calendar.DATE);
			valueEnd = cal.getActualMaximum(Calendar.DATE);
		}
		if(timeType == 2 || timeType == 3){
			cal.set(field,valueStar);
		}
		
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if(!isNow){
    		cal.add(field2, amount);
		}
		map.put("starDate",cal.getTime());
		if(timeType == 2 || timeType == 3){
			cal.set(field,valueEnd);
		}
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		if(timeType == 2 ){
			cal.add(Calendar.WEEK_OF_YEAR, 1);
		}
		map.put("endDate", cal.getTime());
		return map;
	}
    
    public static String StringFormat(String str , String format){
    	SimpleDateFormat f = new SimpleDateFormat(format);
    	Date date = null;
    	if(null ==str || "".equals(str) ){
            return null;
        }
		try {
			date = f.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
    	return f.format(date);
    }
    
    
    public static Date getDateByMonth(Date date, int month) {
    	Calendar calendar = Calendar.getInstance(); 
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH, month);
    	return calendar.getTime();
    }
    
    public static String getYYMMDDHHNNSSMI(Date date){
    	String temp="";
    	int i=(int)(Math.random()*1000);
    	if(i>=10 && i<100){
    		temp ="0"+i;
    	}else if(i<10){
    		temp = "00"+i;
    	}else{
    		temp=""+i;
    	}
    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
    	String str =(sdf.format(date)+temp).toString();
		return str;
    }
/*    public static void main(String[] args) {
    	Set<String> a = new HashSet<String>();
    	String temp="";
    	int j = 0;
    	while(j < 80){
    		int i=(int)(Math.random()*1000);
        	if(i>=10 && i<100){
        		temp ="0"+i;
        	}else if(i<10){
        		temp = "00"+i;
        	}else{
        		temp=""+i;
        	}
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssS");
	    	System.err.println(temp);
//	    		a.add(sdf.format(new Date())+temp);
	    		j++;
		}
//    	System.err.println(a.size());
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssS");
//    	int i=(int)(Math.random()*900)+100; 
//    	System.err.println(i);
	}*/
    
    
    public static Date getTimeOfStart() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_MONTH, 0);
        return  cal.getTime();
    }

	public static Date getTimeOfEnd() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		cal.add(Calendar.DAY_OF_MONTH, 0);
		return  cal.getTime();
	}
	
	public static Date str2Date(String str, String hour){
		if(str == null || "".equals(str)){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		str = str.split(" ")[0];
		str = str + " " + hour;
		Date date = new Date();
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		return date;
	}
	
	public static List<String> getMonthListByStartAndEndDate(String startDate, String endDate) {
		List<String> list = Lists.newArrayList();
		try {
			Date startMonth = new SimpleDateFormat("yyyy-MM").parse(startDate);
			Date endMonth = new SimpleDateFormat("yyyy-MM").parse(endDate);
			Calendar end = Calendar.getInstance();
			end.setTime(endMonth);
			end.add(Calendar.MONTH, 1);
			Calendar dd = Calendar.getInstance();
			dd.setTime(startMonth);
			while(dd.getTime().before(end.getTime())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				list.add(sdf.format(dd.getTime()));
				System.out.println(sdf.format(dd.getTime()));
				dd.add(Calendar.MONTH, 1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 获得本周一0点时间  
    public static Date getTimesWeekmorning() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
        return cal.getTime();  
    }  
    
 // 获得本月第一天0点时间  
    public static Date getTimesMonthmorning() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));  
        return cal.getTime();  
    }  
    
   public static int getDateSpace(String date1, String date2)
           throws ParseException {

       Calendar cal = Calendar.getInstance();;

       cal.setTime(parseDateFromString(date1, "yyyy-MM-dd"));
       long time1 = cal.getTimeInMillis();    
       cal.setTime(parseDateFromString(date2, "yyyy-MM-dd"));
       long time2 = cal.getTimeInMillis(); 
       //得到两个日期相差的天数   
       long between_days = (time2 - time1)/(1000*3600*24);
       return Integer.parseInt(String.valueOf(between_days)) + 1;
   }
   
   /**
	 * 日期相加
	 * @param date   Date
	 * @param day int
	 * @return Date
	 */
	public static Date addDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}
	
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

    /**
     * 获取某年某月的最后一天最后一秒
     * @param year
     * @param month
     * @return Date
     */
    public static Date getLastDaySecondOfMonth(int year, int month){
        Calendar currCal=Calendar.getInstance();
        currCal.set(Calendar.YEAR, year);
        currCal.set(Calendar.MONTH, month);
        currCal.set(Calendar.DAY_OF_MONTH, 1);
        currCal.set(Calendar.HOUR_OF_DAY,0);
        currCal.set(Calendar.MINUTE,0);
        currCal.set(Calendar.SECOND,0);
        currCal.add(Calendar.SECOND, -1);
        return currCal.getTime();
    }

    /**
     * 获取两个日期间的天数差
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static int getDateSpace(Date date1, Date date2)
            throws ParseException {
        Calendar cal = Calendar.getInstance();;
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        //得到两个日期相差的天数
        long between_days = (time2 - time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days)) + 1;
    }

    /**
     * 日期相加
     * @param date   Date
     * @param millis 毫秒
     * @return Date
     */
    public static Date addMillis(Date date, long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + millis);
        return c.getTime();
    }

    public static Date getCurrentDate() {
        return new Date();
    }
}
