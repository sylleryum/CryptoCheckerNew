package com.cryptochecker.dao;

import java.util.List;

import com.cryptochecker.entity.Coin;

public interface CoinDao {

	
	public List<Coin> getCoins();

	public int saveCoin(Coin theCoin);

	public Coin getCoinById(int theCoin);
	
	public void deleteCoin(Coin coin);

	public void updateCoin(Coin coin);
	
	public void test();
}
