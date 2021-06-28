package it.polito.tdp.food.model;

public class Adiacenza {
	
	Food f1;
	Food f2;
	int conta;
	double somma;
	public Adiacenza(Food f1, Food f2, int conta, double somma) {
		super();
		this.f1 = f1;
		this.f2 = f2;
		this.conta = conta;
		this.somma = somma;
	}
	public Food getF1() {
		return f1;
	}
	public void setF1(Food f1) {
		this.f1 = f1;
	}
	public Food getF2() {
		return f2;
	}
	public void setF2(Food f2) {
		this.f2 = f2;
	}
	public int getConta() {
		return conta;
	}
	public void setConta(int conta) {
		this.conta = conta;
	}
	public double getSomma() {
		return somma;
	}
	public void setSomma(double somma) {
		this.somma = somma;
	}
	

}
