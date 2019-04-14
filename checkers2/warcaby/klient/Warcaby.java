package warcaby.klient;

import warcaby.klient.Plansza;
import warcaby.klient.Warcaby;

/** G��wna klasa aplikacji, kt�ra wywo�uje konstruktory pozosta�ych klas, odpowiedzialna za uruchomienie rozgrywki po stronie Klienta. 
 *  Klasa ta zawiera w swoim konstruktorze wywo�anie konstruktora klasy Plansza, kt�ra zawiera ca�� logik� aplikacji - ruchy, bicia itd.
 *  @see warcaby.klient.Plansza
 *  @author Bart�omiej Osak, Tomasz Pasternak
 */

public class Warcaby {

	/** Konstruktor klasy Warcaby bezargumentowy, w kt�rym wywo�ywany jest konstruktor klasy {@link warcaby.klient.Plansza#Plansza()}.
	 * Dzi�ki temu mo�liwa jest rozgrywka, poniewa� w klasie Plansza zawarta jest ca�a logika aplikacji - ruchy, bicia itd,
	 * po stronie Klienta.
	 */
	
	public Warcaby() {
		new Plansza();
	}

	/** Metoda odpowiedzialna za wywo�anie konstruktora klasy Warcaby ({@link warcaby.klient.Warcaby#Warcaby()}). 
	 *  Dzi�ki wywo�aniu konstruktora w tej metodzie mo�liwe jest prawid�owe uruchomienie gry poprzez jej kompilacj� 
	 *  lub bezpo�rednio z poziomu archiwum .jar dla u�ytkownika - Klienta.
	 *  @param args - domy�lna tablica String�w w celu poprawnego wywo�ania metody statycznej main(). 
	 */
	
	public static void main(String args[]) {
		new Warcaby();
	}
}