package com.cryptochecker.helper;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.cryptochecker.dao.CoinDao;
import com.cryptochecker.entity.Coin;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;

public class GetData {
	private static String location = "C:\\temp\\coins.txt";
	private static Path path = Paths.get(location);
	private List<Coin> coinData;
	private List<String> coinsReached = new ArrayList<String>();
	@Autowired
	CoinDao cDao;
	BinanceApiClientFactory factory = BinanceApiClientFactory
			.newInstance("Wc5VIcc3aStTnFDiaXxyVvZLFRwDXmYmhxBZlZuFwCoJzryieq9lNaaNfIWaUkgm", "319419");
	private BinanceApiRestClient client = factory.newRestClient();

	public GetData() {
		List<String> temp = new ArrayList<String>();
		temp = loadCoins();
		coinData = loadCoinData(temp);
		sortCoins();
	}

	public List<Coin> getCoinData() {
		return coinData;
	}

	public void setCoinData(List<Coin> coinData) {
		this.coinData = coinData;
	}

	// carregar moedas
	/**
	 * lista com todas as moedas da direção da variavel location
	 *
	 * @return lista com o nome das moedas
	 */
	public List<String> loadCoins() {
		try (Scanner scan = new Scanner(new BufferedReader(new FileReader(location)))) {
			List<String> tempList = new ArrayList<String>();
			while (scan.hasNext()) {
				String tempCoin = scan.nextLine();
				tempList.add(tempCoin);
			}
			return tempList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * lista com todas as moedas dentro de resoureces
	 *
	 * @return lista com o nome das moedas
	 */
	public List<String> loadCoinsWithin() {
		ClassLoader classLoader = getClass().getClassLoader();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/coins.txt")));
		String line;
		List<String> tempList = new ArrayList<String>();
		try {
			while ((line = bReader.readLine()) != null) {
				tempList.add(line);
			}
			return tempList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

//		try (Scanner scan = new Scanner(new File(classLoader.getResource("coins.txt").toURI()))) {
//			scan.useDelimiter(",");
//			List<String> tempList = new ArrayList<String>();
//			while (scan.hasNext()) {
//				//String tempCoin = scan.nextLine();
//				//scan.skip(scan.delimiter());
//				tempList.add(scan.next());
//			}
//			return tempList;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}

	}

	// carregar dados das moedas
	/**
	 * carrega dados das moedas passadas
	 *
	 * @param listCoins
	 *            lista de moedas
	 * @return lista de Coin, com dados das moedas que foram passados
	 */
	public List<Coin> loadCoinData(List<String> listCoins) {

		List<Coin> listCoinsLoaded = new ArrayList<>();

		// aumentar o timeout
		OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
		builder.connectTimeout(5, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES);
		@SuppressWarnings("unused")
		OkHttpClient cliente = builder.build();

		 for(String tempCoin: listCoins){
		 List<Candlestick> candles = client.getCandlestickBars(tempCoin,
		 CandlestickInterval.DAILY);
		 listCoinsLoaded.add(new Coin(tempCoin,
		 Double.parseDouble(candles.get(candles.size()-1).getLow()),
		 Double.parseDouble(candles.get(candles.size()-1).getHigh()),
		 Double.parseDouble(candles.get(candles.size()-1).getClose())));
		 //client.getCandlestickBars(tempCoin, CandlestickInterval.DAILY, 20, startTime, endTime);

		 //System.out.println("time=====" +candles.get(candles.size()-1).getCloseTime());
		 }
		int i = 0;

		// HERE teste
		// getDataFromInterval("hicoin", CandlestickInterval.ONE_MINUTE, 1522675256000l,
		// "omio");
		// get from interval

		// 1 dia hourly = 25 candles
		// 1 dia 5 min = 289 candles
		// 1 dia = 86400000 milli

		// for (Candlestick can : client.getCandlestickBars("ETHBTC",
		// CandlestickInterval.FIVE_MINUTES, 500,
		// 1524193200000l, 1524279600000l)) {
		// i++;
		// System.out.println(i + " - " +
		// TimeConverterHelper.convertMiliToString(can.getOpenTime()) + " open "
		// + can.getOpen() + " high " + can.getHigh() + " low " + can.getLow());
		// }

		// System.out.println("il teste "+client.getCandlestickBars("ETHBTC",
		// CandlestickInterval.HOURLY, 80, 1523502000000l, 1523640600000l));
		if (!listCoinsLoaded.isEmpty()) {
			return listCoinsLoaded;
		} else {
			return null;
		}

	}



	/**
	 * Get the data from the passed date intervals
	 *
	 * @param initTime
	 *            miliseconds or the following format: 12-30-2017 00:00:00, both
	 *            init and final in the same format
	 * @param finalTime
	 *            miliseconds or the following format: 12-30-2017 00:00:00, both
	 *            init and final in the same format
	 * @param intervalo
	 *            CandlestickInterval.intervaloRequerido
	 * @return the list of candlesticks for the given interval, null in case of
	 *         error
	 */
	// HERE metodo
	public List<Candlestick> getDataFromInterval(String moeda, CandlestickInterval intervalo, Object initTime,
												 Object finalTime) {

		List<Candlestick> dataFromInterval = new ArrayList<>();
		// converter calendar para milli
		if ((initTime.getClass().equals(String.class)) && (finalTime.getClass().equals(String.class))) {
			// System.out.println("mili "+initTime.getClass());
			// System.out.println("calendar");
			initTime = TimeConverterHelper.convertSingle((String) initTime);
			finalTime = TimeConverterHelper.convertSingle((String) finalTime);
			//////System.out.println("calendar convertido para mili, I- " + initTime + " F- " + finalTime);
		}

		try {
			// se tá em formato de milli (long)
			if ((initTime.getClass().equals(Long.class)) && (finalTime.getClass().equals(Long.class))) {
				long initTimeL = (long) initTime;
				long finalTimeL = (long) finalTime;
				// System.out.println("long");
				//////System.out.println(
				//////"mili convertidos p calendar " + TimeConverterHelper.convertMiliToString((long) initTime) + " "
				//////			+ TimeConverterHelper.convertMiliToString((long) initTime) + " - "
				//////			+ TimeConverterHelper.convertMiliToString((long) finalTime));
				long timeCount = initTimeL;

				// se o o tempo inicial é realmente menor que o final (verificacao basica)
				if ((initTimeL) < finalTimeL) {
					int i = 0;
					int j = 0;

					// rodar até o tempo inicial passar do final
					while (timeCount < finalTimeL) {
						// if ((timeCount + 86400000) < finalTimeL) {
						if ((timeCount + 86400000) < finalTimeL) {
							j++;
							//////System.out.println("timecount "+timeCount+" - "+(timeCount + 86400000));
							for (Candlestick can : client.getCandlestickBars(moeda, intervalo, 500, timeCount,
									(timeCount + 86400000))) {
								i++;

								dataFromInterval.add(can);
								//////System.out.println("run: " + j + " - " + i + " - "
								//////	+ TimeConverterHelper.convertMiliToString(can.getOpenTime()) + " open "
								//////	+ can.getOpen() + " high " + can.getHigh() + " low " + can.getLow());

							}
							timeCount = timeCount + 86400000;
						} else {
							//////System.out.println("last run");
							//////System.out.println("timecount "+timeCount+" - "+finalTimeL);
							for (Candlestick can : client.getCandlestickBars(moeda, intervalo, 500, timeCount,
									finalTimeL)) {
								i++;

								dataFromInterval.add(can);
								//////System.out.println(i + " - "
								//////	+ TimeConverterHelper.convertMiliToString(can.getOpenTime()) + " open "
								//////	+ can.getOpen() + " high " + can.getHigh() + " low " + can.getLow());

							}
							timeCount = timeCount + 86400000;
						}

						// }
					}

				} else {
					System.out.println("tempo inicial maior que final");
				}

				// for (Candlestick can : client.getCandlestickBars(moeda, intervalo, 500,
				// initTimeL, finalTimeL)) {
				// i++;
				// System.out.println(i + " - " +
				// TimeConverterHelper.convertMiliToString(can.getOpenTime()) + " open "
				// + can.getOpen() + " high " + can.getHigh() + " low " + can.getLow());
				// }

			} else {
				System.out.println("Erro, datas não estão em milli");

			}
			return dataFromInterval;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Checks if the 5% goal was reached since last check
	 *
	 * @param lCheckDate
	 *            date IN MILLISECONDS which was last checked
	 * @param current valor atual
	 * @param coin
	 * 			which coin to check
	 * @return if the goal was reached, returns the respective Candlestick, if not, null
	 */
	@SuppressWarnings("Duplicates")
	public Candlestick checkIfReached(Coin coin, Double current, Long lCheckDate){
		Double target = current*1.05;
		String closeValue = null;
		try{

			while (lCheckDate<TimeConverterHelper.getNowDateMil()){
				for(Candlestick candles: getDataFromInterval(coin.getName(), CandlestickInterval.FIVE_MINUTES, lCheckDate, lCheckDate+86400000)){
					//System.out.println("close time "+candles.getCloseTime());
					closeValue = candles.getClose();
					if (target<=(Double.parseDouble(candles.getHigh()))){
						//System.out.println("//data reached "+TimeConverterHelper.convertMiliToString(candles.getCloseTime()));
						//System.out.println("coin to check "+coin);
//						coin.setBateu(true);
//						coin.setQuando(TimeConverterHelper.convertMiliToSql(candles.getCloseTime()));
						//System.out.println("******* "+coinsReached);
						return candles;
					}

				}
				lCheckDate += 86400000;
			}

		} catch (NumberFormatException e){
			System.out.println("Number received from candle cannot be parsed to double");
			e.printStackTrace();
			return null;
		}
		System.out.println("current value (didn't reach) "+closeValue);
		return null;

	}

	/**
	 * Checks if 5% goal was reached since last check for all coins
	 *
	 * @param coinList
	 *            list of coins to check
	 * @return if the goal was reached, returns the respective Candlestick, if not, null
	 */
	@SuppressWarnings("Duplicates")
	public void checkIfAllCoinsReached(List<Coin> coinList){
		System.out.println("entrou");
		for(Coin coin: coinList){
			System.out.println("-coin "+coin.getId()+" "+coin.getName());
		}

		for (Coin coin: coinList) {
			System.out.println("start");
			System.out.println("|Coin: "+coin.getId() + " " +coin.getName());
			Double target = coin.getCurrent()*1.05;
			//String closeValue = null;
			Long lCheckDate = TimeConverterHelper.convertSingleSql(coin.getlCheckDate());
			try{
				//if (coin.isBateu()!=true){
					while (lCheckDate<TimeConverterHelper.getNowDateMil()){
						for(Candlestick candles: getDataFromInterval(coin.getName(), CandlestickInterval.FIVE_MINUTES,
								lCheckDate, lCheckDate+86400000)){
							if (target<=(Double.parseDouble(candles.getHigh()))){
//						coin.setBateu(true);
//						coin.setQuando(TimeConverterHelper.convertMiliToSql(candles.getCloseTime()));
								//System.out.println("******* "+coinsReached);
								////return candles;
								System.out.println("/Coin reached: "+coin.getId() + " " +coin.getName()+" "+
										TimeConverterHelper.convertMiliToString(candles.getCloseTime()));
								break;
							}

						}
						lCheckDate += 86400000;
					}
				//}
			} catch (NumberFormatException e){
				System.out.println("Number received from candle cannot be parsed to double");
				e.printStackTrace();
				////return null;
			}
		}

		//System.out.println("current value (didn't reach) "+closeValue);
		////return null;

	}








	/**
	 * **Deprecated. Checks if the 5% goal was reached since last check until current day
	 *
	 * @param lCheckDate
	 *            date IN MILLISECONDS which was last checked
     * @param current valor atual
	 * @param coin
	 * 			which coin to check
	 * @return if the goal was reached, returns the respective Candlestick, if not, null
	 */
	@SuppressWarnings("Duplicates")
	public Candlestick checkIfReachedNow(Coin coin, Double current, Long lCheckDate){
		Double target = current*1.05;
		String closeValue = null;
		try{

			for(Candlestick candles: getDataFromInterval(coin.getName(), CandlestickInterval.FIVE_MINUTES, lCheckDate, TimeConverterHelper.getNowDateMil())){
				//System.out.println("close time "+candles.getCloseTime());
				closeValue = candles.getClose();
				if (target<=(Double.parseDouble(candles.getHigh()))){
					System.out.println("//data reached "+TimeConverterHelper.convertMiliToString(candles.getCloseTime()));
					System.out.println("coin to check "+coin);
					coinsReached.add(coin.getName());

                    return candles;
				}

			}
		} catch (NumberFormatException e){
            System.out.println("Number received from candle cannot be parsed to double");
            e.printStackTrace();
            return null;
		}
		System.out.println("current value (didn't reach) "+closeValue);
        return null;

	}

	private void sortCoins() {
		Collections.sort(coinData, new Comparator<Coin>() {
			@Override
			public int compare(Coin o1, Coin o2) {
				return o1.compareTo(o2);
			}
		});

	}

	public Coin searchCoinByName(String coinName) {

		for (Coin coin : coinData) {
			if (coin.getName().equalsIgnoreCase(coinName)) {
				return coin;
			}
		}
		return null;
	}

	public List<String> getCoinsReached() {
		return coinsReached;
	}

	public void setCoinsReached(List<String> coinsReached) {
		this.coinsReached = coinsReached;
	}
}
