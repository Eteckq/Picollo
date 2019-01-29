package me.yohan.fc.questions;

import java.util.Random;

public class CustomRandom extends Random { //Genere des items aléatoire pour dynamiser les questions

	private static final long serialVersionUID = -5834576599949387141L;

	public CustomRandom (long seed) {
		super(seed);
	}
	
	public String card() {
		String carte = "";
		int chiffre = this.nextInt(13)+1;
		switch (chiffre) {
			case 1:
				carte = "un As";break;
			case 2:
				carte = "un 2";break;
			case 3:
				carte = "un 3";break;
			case 4:
				carte = "un 4";break;
			case 5:
				carte = "un 5";break;
			case 6:
				carte = "un 6";break;
			case 7:
				carte = "un 7";break;
			case 8:
				carte = "un 8";break;
			case 9:
				carte = "un 9";break;
			case 10:
				carte = "un 10";break;
			case 11:
				carte = "un Valet";break;
			case 12:
				carte = "une Dame";break;
			case 13:
				carte = "un Roi";break;
		}
		return carte;
	}
	
	public String cardColor() {
		String color = "";
		int chiffre = this.nextInt(4)+1;
		switch (chiffre) {
			case 1:
				color = "Carreau";break;
			case 2:
				color = "Tréfle";break;
			case 3:
				color = "Coeur";break;
			case 4:
				color = "Pique";break;
		}
		return color;
	}
	
	public String cardRB() {
		int chiffre;
		String color = null;
		chiffre = this.nextInt(2)+1;
		switch (chiffre) {
			case 1:
				color = "Rouge";break;
			case 2:
				color = "Noire";break;
		}
		return color;
	}
	
	public String de() {
		Integer valeur = this.nextInt(6)+1;
		return valeur.toString();
	}
	
	public String drink() {
		int chiffre;
		String drink = null;
		chiffre = this.nextInt(2)+1;
		switch (chiffre) {
			case 1:
				drink = "une gorgée";break;
			case 2:
				drink = "deux gorgées";break;
			case 3:
				drink = "trois gorgées";break;
		}
		return drink;
	}
	
	public String hugeDrink() {
		int chiffre;
		String drink = null;
		chiffre = this.nextInt(2)+1;
		switch (chiffre) {
			case 1:
				drink = "quatre gorgées";break;
			case 2:
				drink = "cinq gorgées";break;
			case 3:
				drink = "six gorgées";break;
		}
		return drink;
	}
	
	public String PoF() {
		int chiffre;
		String piece = null;
		chiffre = this.nextInt(2)+1;
		switch (chiffre) {
			case 1:
				piece = "Pile";break;
			case 2:
				piece = "Face";break;
		}
		return piece;
	}
	
}
