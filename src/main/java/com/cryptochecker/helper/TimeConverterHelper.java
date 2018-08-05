package com.cryptochecker.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeConverterHelper{  

	/**	
	@param time in the following format: 12-30-2017 00:00:00	
	@return time in miliseconds[]
	*/
	public static long[] convertInitFinal(String horaInit, String horaFinal){
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	String dateInStringInit = horaInit;
	String dateInStringFinal = horaFinal;
	Date dateInit = new Date();
	Date dateFinal = new Date();
	try {
		dateInit = sdf.parse(horaInit);
		dateFinal = sdf.parse(horaFinal);
	} catch (ParseException e) {
		e.printStackTrace();
	}

	System.out.println("Data inicial: "+dateInStringInit);
	System.out.println("Data Final: "+dateInStringFinal);	
	System.out.println("Data Inicial - milliseconds: " + dateInit.getTime());
	System.out.println("Data Final - milliseconds: " + dateFinal.getTime());
	
	
	
	return new long[] {dateInit.getTime(), dateFinal.getTime()};
	}	
	
	/**	
	@param time in the following format: 12-30-2017 00:00:00	
	@return time in miliseconds
	*/
	public static long convertSingle(String hora){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String dateInString = hora;
		Date date = new Date();
		try {
			date = sdf.parse(hora);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		//System.out.println("Data : "+dateInString);
		//System.out.println("Data - milliseconds: " + date.getTime());
		
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		System.out.println("Calender - Time in milliseconds : " + calendar.getTimeInMillis());
		return date.getTime();

		}

	/**
	 @param time in the following format: 2017-12-30 00:00:00
	 @return time in miliseconds
	 */
	public static long convertSingleSql(String hora){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
		String dateInString = hora;
		Date date = new Date();
		try {
			date = sdf.parse(hora);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		//System.out.println("Data : "+dateInString);
		//System.out.println("Data - milliseconds: " + date.getTime());

//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		System.out.println("Calender - Time in milliseconds : " + calendar.getTimeInMillis());
		return date.getTime();

	}
	
	/**	
	@param time in miliseconds
	@return time in the following CALENDAR format: 12-30-2017 00:00:00
	*/
	public static Calendar convertMiliToDate(long mili) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(mili);
		System.out.println("horario convertido "+calendar.get(Calendar.DAY_OF_MONTH)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+
		calendar.get(Calendar.YEAR)+" "+calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE));
		return calendar;		
	}
	
	/*
	@param time in miliseconds
	@return time in the following STRING format: 12-30-2017 00:00:00
	*/
	public static String convertMiliToString(long mili) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(mili);
		String dataString = calendar.get(Calendar.DAY_OF_MONTH)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+
		calendar.get(Calendar.YEAR)+" "+calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE);
		return dataString;		
	}

	/**
	@param time in miliseconds
	@return time in the following SQL STRING format: yyyy-MM-dd hh:mm:ss
	*/
	public static String convertMiliToSql(long mili) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(mili);
//		String dataString = calendar.get(Calendar.DAY_OF_MONTH)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+
//				calendar.get(Calendar.YEAR)+" "+calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE);
		String dataString = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+" "+
						calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND);
		return dataString;
	}

	/**
	 * Get now date as SQL yyyy-MM-dd hh:mm:ss
	 */
	public static String getNowDate() {

//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
//		//Date date = new Date();
//		Date date = new Date();
//		try {
//			date = sdf.parse();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String data = dateFormat.format(date);

		return data;


	}

	/**
	 * Get now date as milliseconds
	 */
	public static Long getNowDateMil() {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String data = dateFormat.format(date);

		return TimeConverterHelper.convertSingleSql(data);


	}
  
}
