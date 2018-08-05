package com.cryptochecker;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.binance.api.client.domain.market.Candlestick;
import com.cryptochecker.dao.CoinDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.binance.api.client.domain.market.CandlestickInterval;
import com.cryptochecker.dao.CoinDaoImpl;
import com.cryptochecker.entity.Coin;
import com.cryptochecker.helper.GetData;
import com.cryptochecker.helper.TimeConverterHelper;

public class TestUnit {

	
	@Test
	public void test() {

		//run main application
		GetData data = new GetData();

		//valores anteriores 1524020400000l  1524366000000l
		//valores de teste NEO
		// init= 1532409600000l		open= 0.00394800
		// final= 1532413800000l	high= 0.00422300
		//List<Candlestick> resultado= new ArrayList<>();
		//List<Candlestick> resultado = data.getDataFromInterval("NEOBTC", CandlestickInterval.FIVE_MINUTES, 1532409600000l, 1532413800000l);


		//System.out.println("agora "+TimeConverterHelper.getNowDate());


//		for (Candlestick canRun : resultado){
//			System.out.println("test " + canRun.getOpenTime() + " open "
//					+ canRun.getOpen() + " high " + canRun.getHigh() + " low " + canRun.getLow());
//		}


		//get now date
		//TimeConverterHelper.getNowDate();
		
//		CoinDaoImpl cdi = new CoinDaoImpl();
//		cdi.test();
		
//		int i = -1;
//		for (Coin coin:data.getCoinData()) {
//			i++;
//			System.out.println(i+" - p24l " +coin.getP24l()+" p24h "+coin.getP24h()+" current "+coin.getCurrent()+
//					" peMin "+coin.getPeMin()+ " peMax "+coin.getPeMax());
//			System.out.println(i+"=="+ coin.toStringFormatted());
//		} 
		//data.getDataFromInterval("hicoin", CandlestickInterval.ONE_MINUTE, 345345345l, "02-04-2018 10:20:56");
				
		//repeat action
//		Runnable helloRunnable = new Runnable() {
//		    public void run() {
//		        System.out.println("Hello world");
//		    }
//		};
//		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//		executor.scheduleAtFixedRate(helloRunnable, 0, 3, TimeUnit.SECONDS);

		List<String> coins = data.loadCoinsWithin();

		for (String strCoin: coins) {
			System.out.println("coin: "+strCoin);

		}
	}

}
