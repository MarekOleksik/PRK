package warcaby.klient;

import warcaby.klient.Plansza;
import warcaby.klient.Warcaby;

/** G³ówna klasa aplikacji, która wywo³uje konstruktory pozosta³ych klas, odpowiedzialna za uruchomienie rozgrywki po stronie Klienta. 
 *  Klasa ta zawiera w swoim konstruktorze wywo³anie konstruktora klasy Plansza, która zawiera ca³¹ logikê aplikacji - ruchy, bicia itd.
 *  @see warcaby.klient.Plansza
 *  @author Bart³omiej Osak, Tomasz Pasternak
 */

public class Warcaby {

	/** Konstruktor klasy Warcaby bezargumentowy, w którym wywo³ywany jest konstruktor klasy {@link warcaby.klient.Plansza#Plansza()}.
	 * Dziêki temu mo¿liwa jest rozgrywka, poniewa¿ w klasie Plansza zawarta jest ca³a logika aplikacji - ruchy, bicia itd,
	 * po stronie Klienta.
	 */
	
	public Warcaby() {
		new Plansza();
	}

	/** Metoda odpowiedzialna za wywo³anie konstruktora klasy Warcaby ({@link warcaby.klient.Warcaby#Warcaby()}). 
	 *  Dziêki wywo³aniu konstruktora w tej metodzie mo¿liwe jest prawid³owe uruchomienie gry poprzez jej kompilacjê 
	 *  lub bezpoœrednio z poziomu archiwum .jar dla u¿ytkownika - Klienta.
	 *  @param args - domyœlna tablica Stringów w celu poprawnego wywo³ania metody statycznej main(). 
	 */
	
	public static void main(String args[]) {
		new Warcaby();
	}
}