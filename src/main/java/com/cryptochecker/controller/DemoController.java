package com.cryptochecker.controller;

import java.util.List;
import java.util.Optional;

import com.binance.api.client.domain.market.Candlestick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cryptochecker.dao.CoinDao;
import com.cryptochecker.entity.Coin;
import com.cryptochecker.helper.GetData;
import com.cryptochecker.helper.TimeConverterHelper;

@Controller
public class DemoController {

	boolean firstRun = true;
	GetData data;
	
	@Autowired
	CoinDao cDao;

	@GetMapping("/")
	public String showHomePage(Model model, @RequestParam(value="coinAdded", required=false) Optional<String> coinAdded) {

		if (firstRun == true) {
			data = new GetData();
			firstRun = false;
		}

		if (coinAdded.isPresent()){		
			model.addAttribute("coin", coinAdded.get());
			/*if(coinAdded.get().equalsIgnoreCase("ADABTC")) {
				
				System.out.println("coinAdded: "+coinAdded.get());
			}*/
			
		}
		
		model.addAttribute("data", data);
		// model.addAttribute("coin", new Coin());
//		int i = -1;
//		for (Coin coin : data.getCoinData()) {
//			i++;
//			System.out.println(i + " - " + coin.toStringFormatted());
//
//		}
		System.out.println("==loading done==");
		
		//////RUN TASK EACH
		// Runnable helloRunnable = new Runnable() {
		// public void run() {
		// System.out.println("Hello world");
		// }
		// };
		//
		// ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		// executor.scheduleAtFixedRate(helloRunnable, 0, 3, TimeUnit.SECONDS);
		return "home";
	}
	
	@GetMapping("/savecoin")
	public String saveCoin(Model model, @RequestParam(value="coinname", required=false) String coinName) {
		
		
		
		Coin markedCoin;
		try {
			markedCoin = data.searchCoinByName(coinName);
			
			markedCoin.setDataAdd(TimeConverterHelper.getNowDate());
			markedCoin.setMais5p(markedCoin.getCurrent()*1.05);
			markedCoin.setMenos5p(markedCoin.getCurrent()*0.95);
			markedCoin.setmPmax((markedCoin.getP24h() * 100)/markedCoin.getMais5p()-100);
			markedCoin.setlCheckDate(TimeConverterHelper.getNowDate());

			//System.out.println("horario salvo " +markedCoin.getlCheckDate());
			int id = cDao.saveCoin(markedCoin);
			
			markedCoin.setId(id);
			System.out.println("Coin saved "+markedCoin.toStringFormatted());
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			return "/";
			
		}
		
		
		//////busca do nome do coin usando stream e filter
		//List<Coin> retorno = data.getCoinData().stream().filter(p-> p.getName().equals((coinName))).collect(Collectors.toList());	
		
		System.out.println("go "+ markedCoin.getName());
		
		return "redirect:/?coinAdded="+markedCoin.getName();
	}
	
	@GetMapping("/watchlist")
	public String showWatchlist(Model model) {

		List<Coin> listCoin = cDao.getCoins();

		//teste se tava batendo
//		Candlestick candleReached = data.checkIfReachedNow("GVTBTC", 0.00000911, 1532821890773l);
//		if (candleReached!=null){
//			System.out.println("hora que bateu " +TimeConverterHelper.convertMiliToString(candleReached.getCloseTime()));
//		} else{
//			System.out.println("não bateu");
//		}

		model.addAttribute("coinList", listCoin);
		return "watchlist";
	}

	@GetMapping("/checker")
	public String checkIfReached (Model model, @RequestParam(value="id") int id){

		Coin coin = cDao.getCoinById(id);

		Optional<Candlestick> candle = Optional.ofNullable(data.checkIfReached(coin, coin.getCurrent(), TimeConverterHelper.convertSingleSql(coin.getlCheckDate())));
		if (candle.isPresent()){
			System.out.println("Reached! close time "+candle.get().getCloseTime() +" - "+ TimeConverterHelper.convertMiliToString(candle.get().getCloseTime()));
			coin.setBateu(true);
			coin.setQuando(TimeConverterHelper.convertMiliToSql(candle.get().getCloseTime()));
			cDao.updateCoin(coin);
		} else {
			coin.setlCheckDate(TimeConverterHelper.getNowDate());
			cDao.updateCoin(coin);
		}
		return "redirect:/watchlist";
	}

	@GetMapping("/checkall")
	public String checkAll (){
		List<Coin> listCoin = cDao.getCoins();

		data.checkIfAllCoinsReached(listCoin);
		return "redirect:/watchlist";
	}
}
