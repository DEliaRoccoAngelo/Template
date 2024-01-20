package it.template.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

public class DateUtility {

	public static Date createAfterYear(Date data,int d){
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.YEAR, d);
		return cal.getTime();
	}
	
	public static Date createAfterMonth(Date data,int d){
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.MONTH, d);
		return cal.getTime();
	}
	
	public static Date createAfterDay(Date data,int d){
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.DAY_OF_YEAR, d);
		return cal.getTime();
	}
	
	public static Date createDataFineMeseFattura(Date  data, int d){
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 50);
		cal.add(Calendar.DAY_OF_YEAR, d);
		return cal.getTime();
	}
	
	public int calcolaDifferenzaDate(Date data1, Date data2){
		double millisecondiGiorno =  86400000.0;
		long millisecondiFraDueDate = data1.getTime() - data2.getTime();
		double giorniFraDueDate = Math.round(millisecondiFraDueDate/millisecondiGiorno);
		return (int) giorniFraDueDate;
	}
	
	public static String createDateString(Date data){
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		return format.format(cal.getTime());
	}
	
	public static Date convertDateFomString(String data, String pattern){
		if(StringUtils.isNotEmpty(data)) {
			DateFormat format = new SimpleDateFormat(pattern);
			try {
				return format.parse(data);
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public static String convertDateToString(Date data, String pattern){
		if (data != null) {
			DateFormat format = new SimpleDateFormat(pattern);
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			return format.format(cal.getTime());
		} else 
		{
			return null;
		}
	}
	
	public static Date sottraiGiorni(Date today, int dd){
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_YEAR, -dd);
		return cal.getTime();
	}
	
	public static String sottraiGiorniString(Date today, int dd){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_YEAR, -dd);
		return format.format(cal.getTime());
	}
	
	public Date createFakeDate(){
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String dataFake = "01/01/2000";
		try {
			return format.parse(dataFake);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	public static Date createDateFirstMonth(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	public static Date createDateFirstMonthProx(int prox){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, prox);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	public static Date createDateFirstMonth(String anno, String mese){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, new Integer(anno));
		cal.set(Calendar.MONTH, new Integer(mese));
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	public static Date createDateEndMonth(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	public static Date createDateEndMonthProx(int prox){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, prox);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	public static Date createDateEndMonth(String anno, String mese){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, new Integer(anno));
		cal.set(Calendar.MONTH, new Integer(mese));
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	public static long getDifferenceDays(Date d1, Date d2) {
	    long diff = d2.getTime() - d1.getTime();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	public static String getDayOfWeek(int number) {
		Calendar cal =GregorianCalendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK,number);
		return cal.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG_FORMAT, new Locale("it"));
	}
	
	public static String createStringDateStartMonth(){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return format.format(cal.getTime());
	}
	
	public static String createStringDateEndMonth(){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(cal.getTime());
	}
	
	public static String createDateStartMonth(int m){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, m);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return format.format(cal.getTime());
	}
	
	public static String createDateEndMonth(int m){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, m);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(cal.getTime());
	}
	
	public static String createDateFirstDayCurrentYear(){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR,1);
		return format.format(cal.getTime());
	}
	
	public static Date getDateFirstDayCurrentYear(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR,1);
		return cal.getTime();
	}
	
	public static Date getDateFirstDayLastYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
		cal.set(Calendar.DAY_OF_YEAR,1);
		return cal.getTime();
	}
	
	public static Date getDateToDayLastYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
		return cal.getTime();
	}
}
