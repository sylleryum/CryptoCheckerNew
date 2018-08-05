package com.cryptochecker;

import java.sql.Time;
import java.text.ParseException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.cryptochecker.entity.Coin;
import com.cryptochecker.helper.GetData;
import com.cryptochecker.helper.TimeConverterHelper;

public class TestGetTimeInMilSec {

	@Test
	public void test() {
		//TimeMilisecond convertTime= new TimeMilisecond();
		// 1522675256000	02-04-2018 10:20:56
		// 1523366456000	10-04-2018 10:20:56
		// 1523415600000	11-04-2018 00:00:00
		// 1523502000000	12-04-2018 00:00:00
		// 1523539256000	12-04-2018 10:20:56
		
		//get time in miliseconds
//		long[] datas = TimeConverterHelper.convertInitFinal("10-04-2018 00:00:00", "12-04-2018 00:00:00");
//		for(long tempData:datas) {
//			System.out.println("-------"+tempData);
//		}
		//TimeConverterHelper.convertMiliToDate(1523640600000l);

//		long[] datas2 = TimeConverterHelper.convertInitFinal("24-07-2018 02:20:00", "24-07-2018 03:30:00");
//		for(long tempData:datas2) {
//			System.out.println("-------"+tempData);
//		}

		//System.out.println("horario " +TimeConverterHelper.getNowDateMil());

		//get time in miliseconds from sql format
		//System.out.println("sql "+TimeConverterHelper.convertSingleSql("2018-07-24 11:28:45"));
		System.out.println("thetest "+TimeConverterHelper.convertMiliToSql(1533247894113l));


	}

}
