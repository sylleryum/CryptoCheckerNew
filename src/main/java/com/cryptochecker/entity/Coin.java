package com.cryptochecker.entity;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="savedcoins")
public class Coin implements Comparable<Coin>{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="coin")
	private String name;
	
	@Column(name="dataAdd")
	private String dataAdd; 
	
	@Column(name="min24h")
	private double p24l;
	
	@Column(name="atual")
	private double current;
	
	@Column(name="mais5p")
	private double mais5p;
	
	@Column(name="menos5p")
	private double menos5p;
	
	@Column(name="max24h")
	private double p24h;
	
	@Column(name="aPmin")
	private double peMin;
	
	@Column(name="aPmax")
	private double peMax;
	
	@Column(name="mPmax")
	private double mPmax;
	
	@Column(name="lCheckDate")
	private String lCheckDate;
	
	@Column(name="bateu")
	private boolean bateu;
	
	@Column(name="quando")
	private String quando;
	
	public Coin(String name, double p24l, double p24h, double current) {
		this.name = name;
		this.p24l = p24l;
		this.p24h = p24h;
		this.current = current;
		this.peMin = ((current * 100)/p24l)-100;
		this.peMax = ((p24h * 100)/current)-100;
		this.bateu=false;
	}
	
	public Coin(String name, String dataAdd, double p24l, double current, double mais5p, double menos5p, double p24h,
			double peMin, double peMax, String lCheckDate, boolean bateu, String quando) {
		this.name = name;
		this.dataAdd = dataAdd;
		this.p24l = p24l;
		this.current = current;
		this.mais5p = mais5p;
		this.menos5p = menos5p;
		this.p24h = p24h;
		this.peMin = peMin;
		this.peMax = peMax;
		this.lCheckDate = lCheckDate;
		this.bateu = bateu;
		this.quando = quando;
	}

	public Coin(){		
	}

	public int getId() {
		return id;
	}

	public String getDataAdd() {
		return dataAdd;
	}

	public double getMais5p() {
		return mais5p;
	}
	
	public String getMais5pS() {
		return doubleToStringFormatted(mais5p);
	}

	public double getMenos5p() {
		return menos5p;
	}
	
	public String getMenos5pS() {
		return doubleToStringFormatted(menos5p);
	}

	public String getlCheckDate() {
		return lCheckDate;
	}

	public boolean isBateu() {
		return bateu;
	}

	public String getQuando() {
		return quando;
	}
	
	public String getName() {
		return name;
	}
	public double getP24l() {		
		return p24l;
	}
	
	public String getP24lS() {		
		return doubleToStringFormatted(p24l);
	}
	
	public double getP24h() {		
		return p24h;
	}
	
	public String getP24hS() {		
		return doubleToStringFormatted(p24h);
	}
	
	public double getCurrent() {
		return current;
	}
	
	public String getCurrentS() {
		return doubleToStringFormatted(current);
	}
	
	public double getPeMin() {
		return peMin;
	}
	
	public String getPeMinS() {
		return doubleToStringFormatted(peMin);
	}
	
	public double getPeMax() {
		return peMax;
	}
	
	public String getPeMaxS() {
		return doubleToStringFormatted(peMax);
	}
	
	public double getmPmax() {
		return mPmax;
	}
	
	public String getmPmaxS() {
		return doubleToStringFormatted(mPmax);
	}

	public void setmPmax(double mPmax) {
		this.mPmax = mPmax;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDataAdd(String dataAdd) {
		this.dataAdd = dataAdd;
	}

	public void setMais5p(double mais5p) {
		this.mais5p = mais5p;
	}

	public void setMenos5p(double menos5p) {
		this.menos5p = menos5p;
	}

	public void setlCheckDate(String lCheckDate) {
		this.lCheckDate = lCheckDate;
	}

	public void setBateu(boolean bateu) {
		this.bateu = bateu;
	}

	public void setQuando(String quando) {
		this.quando = quando;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setP24l(double p24l) {
		this.p24l = p24l;
	}
	public void setP24h(double p24h) {
		this.p24h = p24h;
	}
	public void setCurrent(double current) {
		this.current = current;
	}
	public void setPeMin(double peMin) {
		this.peMin = peMin;
	}
	public void setPeMax(double peMax) {
		this.peMax = peMax;
	}
	@Override
	public String toString() {
		return "Coin [id=" + id + ", name=" + name + ", dataAdd=" + dataAdd + ", p24l=" + p24l + ", current=" + current
				+ ", mais5p=" + mais5p + ", menos5p=" + menos5p + ", p24h=" + p24h + ", peMin=" + peMin + ", peMax="
				+ peMax + ", mPmax=" + mPmax + ", lCheckDate=" + lCheckDate + ", bateu=" + bateu + ", quando=" + quando
				+ "]";
	}
	
	public String toStringFormatted() {
		DecimalFormat df = new DecimalFormat("0.00000000");
		//System.out.println("----"+df.format(p24l)+" "+df.format(p24h));
//		return("Coin [name=" + name + ", p24l=" + df.format(p24l) + ", p24h=" + df.format(p24h) +
//				", current=" + df.format(current) + ", peMin=" + peMin
//				+ ", peMax=" + peMax + "]");
		return "Coin [id=" + id + ", name=" + name + ", dataAdd=" + dataAdd + ", p24l=" + df.format(p24l) + ", current=" + df.format(current)
				+ ", mais5p=" + df.format(mais5p) + ", menos5p=" + df.format(menos5p) + ", p24h=" + df.format(p24h) + ", peMin=" 
				+ df.format(peMin) + ", peMax="+ df.format(peMax) + ", mPmax=" + df.format(mPmax) + ", lCheckDate=" 
				+ lCheckDate + ", bateu=" + bateu + ", quando=" + quando + "]";
	}
	
	public String doubleToStringFormatted(Double value) {
		DecimalFormat df = new DecimalFormat("0.00000000");
		return df.format(value);
	}
	
	public String convertDoubletoString(Double valor) {
		DecimalFormat df = new DecimalFormat("0.00000000");		
		return df.format(valor);
	}

	@Override
	public int compareTo(Coin o) {
		if (this.peMin < o.peMin) return -1;
	    if (this.peMin > o.peMin) return 1;
		return 0;
	}
}
