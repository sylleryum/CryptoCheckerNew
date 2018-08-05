package com.cryptochecker.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cryptochecker.entity.Coin;

@Component
public class CoinDaoImpl implements CoinDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void test() {
//		Session currentSession = sessionFactory.getCurrentSession();
//		currentSession.save("2, 'xvg','2018-04-01 13:10:01',0.0000104, 0.00001169, 0.000012275, 0.0000111055, \r\n" +
//				"0.000065, 0.279818, 11.78461538, 8.623569188,'2018-04-01 13:10:01' , true, '2018-04-01 13:10:01'");
	}
	
	@Override
	@Transactional
	public List<Coin> getCoins() {
		Session currentSession = sessionFactory.getCurrentSession();

		List<Coin> coins = currentSession.createQuery("from Coin order by id desc").getResultList();

		return coins;
	}

	@Override
	@Transactional
	public int saveCoin(Coin theCoin) {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.save(theCoin);		
	}

	@Override
	@Transactional
	public Coin getCoinById(int theCoin) {
		//maneira manual
//		Session currentSession = sessionFactory.getCurrentSession();
//		Query query = currentSession.createQuery("from Coin c where c.id= :id");
//		query.setParameter("Id", theCoin);
//		if (query.uniqueResult()==null) {
//			System.out.println("nao existe - ????????");
//			return null;
//		} else {
//			Coin coin = (Coin) query.uniqueResult();
//			return coin;
//		}
		Session currentSession = sessionFactory.getCurrentSession();

		Coin coin = currentSession.get(Coin.class, theCoin);

		return coin;
	}

	@Override
	@Transactional
	public void deleteCoin(Coin coin) {

		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.delete(coin);
		
	}

	@Override
	@Transactional
	public void updateCoin(Coin coin) {

		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.update(coin);

	}


}
