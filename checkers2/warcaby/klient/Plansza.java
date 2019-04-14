package warcaby.klient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/** Klasa Plansza zawiera deklaracje oraz definicje wszystkich komponent�w tworz�cych okno g��wne aplikacji.
 * W klasie zdefiniowano wygl�d og�lny okna gry, wygl�d planszy, kt�rej wygl�d mo�na zmieni� poprzez opcje gry - {@link warcaby.klient.Opcje}.
 * Ponadto w klasie zadeklarowano zmienne potrzebne do nawi�zania po��czenia sieciowego z drugim graczem - tu z serwerem. 
 * W klasie ustawiane s� r�wnie� pocz�tkowe wsp�rz�dne tablicy pionk�w na szachownicy. 
 * Ponadto okre�lono dzia�anie przycisk�w - w szczeg�lno�ci przycisk 'Multiplayer' (zdefiniowano w�tek, kt�ry odczytuje 
 * obiekt ze strumienia umo�liwiaj�cego zapis danych do gniazda (w tym przypadku tablica pionk�w - {@link Plansza#tablicaPionkow}.
 * Klasa implementuje interfejs ActionListener odpowiadaj�cy za okre�lenie akcji po wci�ni�ciu np. danego przycisku.
 * Ponadto implementowany jest interfejs Serializable w celu serializacji obiektu wysy�anego poprzez po��czenie sieciowe TCP.
 * Klasa dziedziczy po JFrame - klasie tworz�cej g��wne okno aplikacji.
 * @author Bart�omiej Osak, Tomasz Pasternak
 * @see Serializable
 * @see ActionListener
 * @see JFrame
 */
public class Plansza extends JFrame implements ActionListener, Serializable {
	
	/** Zmienna okre�laj�ca unikalny numer w celu serializacji. */
	private static final long serialVersionUID = 1L;
	/** Tablica dwuwymiarowa typu int przechowuj�ca wsp�rz�dne pionk�w na szachownicy. */
	protected static int tablicaPionkow[][];
	/** Zmienna typu Integer okre�laj�ca szeroko�� okna g��wnego aplikacji. */
	private Integer szerokosc = 650;
	/** Zmienna typu Integer okre�laj�ca wysoko�� okna g��wnego aplikacji. */
	private Integer wysokosc = 600;
	/** Zmienna JButton odpowiedzialna za przycisk 'Start' odpowiedzialny za uruchomienie rozgrywki lokalnej (bez po��czenia sieciowego). 
	 * Aktywacja powoduje rozpocz�cie rozgrywki bez po��czenia sieciowego. Gra odbywa si� na jednym komputerze oraz na jednym oknie aplikacji 
	 * (bez znaczenia czy jest to okno gry Klienta czy Serwera). Gra mo�e by� kontynuuowana przez dw�ch graczy lub przez jednego w celu treningu, 
	 * wszystko zgodnie z preferencjami u�ytkownika. 
	 * */
	private JButton start;
	/** Zmienna JButton odpowiedzialna za przycisk 'Wyj�cie' odpowiedzialny za wyj�cie z aplikacji.
	 * W przypadku gry lokalnej (bez po��czenia sieciowego) wyst�pi okno dialogowe z komunikatem, czy u�ytkownik na pewno chce wyj��
	 * z aplikacji w celu zabezpieczenia przed losowym zamkni�ciem aplikacji na wskutek b��dnej decyzji u�ytkownika.
	 * W przypadku po��czenia sieciowego zamykane s� strumienie oraz gniazda serwerowe oraz niepo��czone. 
	 * */
	private JButton wyjscie; 
	/** Zmienna JButton odpowiedzialna za przycisk 'Opcje' odpowiedzialny za uruchomienie opcji gry.
	 * @see warcaby.klient.Opcje 
	 */
	private JButton opcje; 
	/** Zmienna JButton odpowiedzialna za przycisk 'Multiplayer' odpowiedzialny za uruchomienie rozgrywki sieciowej.
	 *  Aktywacja powoduje wyszukiwanie dost�pnego serwera - w przypadku powodzenia operacji odczytywany jest
	 *  odbierany obiekt, w tym przypadku tablica pionk�w. 
	 *  W przypadku niepowodzenia wypisywany jest komunikat o niepowodzeniu w po��czeniu z serwerem.
	 * @see ServerSocket
	 * @see Socket
	 */
	private JButton multiplayer;
	/** Zmienna JLabel u�ywana w celu tworzenia napisu 'WARCABY'. */
	private JLabel napisGlowny;
	/** Zmienna JLabel u�ywana w celu tworzenia napisu 'KLIENT'. */
	private JLabel napisWersja;
	/** Obiekt klasy Plansza. */
	private Plansza plansza;
	/** Tablica obiekt�w klasy Kafelek. Ka�dy element tablicy okre�la wsp�rz�dne ka�dego pojedynczego kafelka szachownicy. 
	 *  @see Kafelek
	 */
	private Kafelek[][] kafelki;
	/** Zmienna statyczna typu Color okre�laj�ca kolor planszy, kt�ry jest ustalany poprzez opcje {@link warcaby.klient.Opcje}.
	 *  @see Color
	 */
	protected static Color kolorPlanszy1;
	/** Zmienna statyczna typu Color okre�laj�ca kolor planszy, kt�ry jest ustalany poprzez opcje {@link warcaby.klient.Opcje}.
	 *  @see Color
	 */
	protected static Color kolorPlanszy2;
	/** Zmienna statyczna typu Color okre�laj�ca kolor pionk�w, kt�ry jest ustalany poprzez opcje {@link warcaby.klient.Opcje}.
	 *  @see Color
	 */
	protected static Color kolorPionkow1;
	/** Zmienna statyczna typu Color okre�laj�ca kolor pionk�w, kt�ry jest ustalany poprzez opcje {@link warcaby.klient.Opcje}.
	 *  @see Color
	 */
	protected static Color kolorPionkow2;
	/** Zmienna tworz�ca gniazdo serwerowe powi�zane z podanym portem - deklaracja obiektu klasy ServerSocket.
	 *  @see ServerSocket
	 */
	protected ServerSocket serverSocket;
	/** Zmienna tworz�ca gniazdo powi�zane z podanym portem - deklaracja obiektu klasy Socket.
	 *  @see Socket
	 */
	protected Socket socket;
	/** Zmienna tworz�ca strumie� umo�liwiaj�cy odczyt danych z gniazda sieciowego. 
	 *  @see ObjectInputStream
	 */
	protected static ObjectInputStream ois;
	/** Zmienna tworz�ca strumie� umo�liwiaj�cy zapis danych do gniazda sieciowego. 
	 *  @see ObjectOutputStream
	 */
	protected static ObjectOutputStream oos;
	/** Zmienna logiczna okre�laj�ca, czy u�ytkownik wybra� tryb gry sieciowej poprzez klikni�cie na przycisk 'Multiplayer'.*/
	protected static boolean multi;
	/** Zmienna logiczna okre�laj�ca ustawiaj�ca si� na true w przypadku, gdy pionki zostan� ustawione - sygna� do gry..*/
	protected static boolean gra;

	/** Konstruktor bezparametrowy klasy Plansza odpowiedzialny za inicjalizacj� komponent�w z biblioteki Swing, zmiennych, tablic.
	 * Komponenty definiowane: JButton, JLabel, JFrame - dla tych komponent�w ustawiane s� wymiary, fonty, kolory.
	 * Po zdefiniowaniu ka�dego z u�ytych komponent�w dodano go do okna z opcjami g��wnymi metod� {@link JFrame#add(java.awt.Component)}.
	 * Ponadto definiowane s� tablice obiekt�w tj. tablica obiekt�w klasy Kafelek, tablica wsp�rz�dnych pionk�w na szachownicy.
	 * Zdefiniowano r�wnie� zmienne typu Color, kt�re okre�laj� kolor planszy lub kolor pionk�w
	 * @see JButton
	 * @see JFrame
	 * @see JLabel
	 * @see Kafelek
	 * @see Opcje
	 */
	public Plansza() {
		super("Warcaby");
		tablicaPionkow = new int[8][8];
		kafelki = new Kafelek[8][8];
		multi = false;
		gra = false;

		setSize(szerokosc, wysokosc);
		setLocationRelativeTo(plansza);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		napisGlowny = new JLabel("WARCABY");
		napisGlowny.setBounds(535, 15, 140, 30);
		napisGlowny.setFont(new Font("Arial", Font.BOLD, 17));
		napisGlowny.setForeground(Color.BLUE);
		add(napisGlowny);

		napisWersja = new JLabel("KLIENT");
		napisWersja.setBounds(555, 55, 140, 20);
		napisWersja.setFont(new Font("Calibri", Font.BOLD, 14));
		napisWersja.setForeground(Color.RED);
		add(napisWersja);

		start = new JButton("Start");
		start.setBounds(530, 100, 100, 20);
		start.setFont(new Font("Calibri", Font.BOLD, 13));
		add(start);
		start.addActionListener(this);

		multiplayer = new JButton("Multiplayer");
		multiplayer.setBounds(530, 130, 100, 20);
		multiplayer.setFont(new Font("Calibri", Font.BOLD, 13));
		add(multiplayer);
		multiplayer.addActionListener(this);

		opcje = new JButton("Opcje");
		opcje.setBounds(530, 160, 100, 20);
		opcje.setFont(new Font("Calibri", Font.BOLD, 13));
		add(opcje);
		opcje.addActionListener(this);

		wyjscie = new JButton("Wyj�cie");
		wyjscie.setBounds(530, 190, 100, 20);
		wyjscie.setFont(new Font("Calibri", Font.BOLD, 13));
		add(wyjscie);
		wyjscie.addActionListener(this);

		kolorPlanszy1 = new Color(255, 120, 0);
		kolorPlanszy2 = new Color(61, 43, 31);
		kolorPionkow1 = Color.BLACK;
		kolorPionkow2 = Color.WHITE;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				kafelki[i][j] = new Kafelek(this, i, j);
				add(kafelki[i][j]);
			}
		}
		setVisible(true);

	}

	/** Metoda bezparametrowa odpowiedzialna za ustawianie pionk�w na szachownicy.
	 * Operacja przeprowadzana jest poprzez u�ycie p�tli for oraz rozgraniczenie
	 * dw�ch kolor�w pion�w od siebie - jeden kolor oznaczany jest poprzez 1, 
	 * a drugi poprzez 2. Pole puste poprzez 0. Po poprawnym ustawieniu
	 * zmienna logiczna 'gra' jest ustawiana na warto�� TRUE.
	 * Metoda jest typu void - nie zwraca �adnej warto�ci. 
	 */
	protected void ustaw() {
		for (int j = 0; j < 8; j++)
			for (int i = 0; i < 8; i++) {
				tablicaPionkow[i][j] = 0;
			}
		for (int j = 0; j < 3; j++)
			for (int i = 0; i < 8; i++) {
				if ((i + j) % 2 == 0)
					tablicaPionkow[i][j] = 1;
			}

		for (int j = 5; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				if ((i + j) % 2 == 0)
					tablicaPionkow[i][j] = 2;
			}
		}

		gra = true;
	}
	
	/** Metoda przes�oni�ta o nazwie paint. Przyjmuje jeden sta�y parametr typu Graphics.
	 * Metoda wykorzystywana jest do rysowania szachownicy oraz pionk�w. 
	 * Ponadto w klasie zaadaptowano r�wnie� rysowanie pod�wietle� kontur�w aktywnych kafelk�w, 
	 * na kt�re mo�emy przemie�ci� gracz sw�j pionek - s� to tak zwane pola aktywne do ruchu dla pionka. 
	 * W metodzie rysowany jest r�wnie� kafelek informacyjny umieszczony w prawym dolnym rogu okna g��wnego
	 * aplikacji, kt�ry informuje u�ytkownika na jakim polu szachownicy obecnie znajduje si� jego kursor myszy.
	 * Rysowanie odbywa si� za pomoc� komponentu {@link Graphics2D}. Wykorzystywane s� funkcje takie jak:
	 * {@link Graphics2D#drawRect(int, int, int, int)},{@link Graphics2D#setColor(Color)}, {@link Graphics2D#fillRect(int, int, int, int)},
	 * {@link Graphics2D#fillOval(int, int, int, int)} itp. 
	 * Metoda jest typu void - nie zwraca �adnej warto�ci. 
	 * @see Graphics
	 * @see Graphics2D
	 */
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(10, 40, 505, 505);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0)
					g2d.setColor(kolorPlanszy1);
				else
					g2d.setColor(kolorPlanszy2);
				g2d.fillRect(11 + 63 * i, 41 + 63 * j, 62, 62);
			}
		}
		for (int j = 0; j < 8; j++)
			for (int i = 0; i < 8; i++) {
				if (tablicaPionkow[i][j] == 3 || tablicaPionkow[i][j] == 4 || tablicaPionkow[i][j] == 5
						|| tablicaPionkow[i][j] == 8 || tablicaPionkow[i][j] == 9) {
					g2d.setColor(Color.YELLOW);
					g2d.drawRect(kafelki[i][j].x + 3, kafelki[i][j].y + 25, kafelki[i][j].width, kafelki[i][j].height);
				}
			}
		for (int j = 0; j < 8; j++)
			for (int i = 0; i < 8; i++) {
				if (tablicaPionkow[i][j] == 1 || tablicaPionkow[i][j] == 3 || tablicaPionkow[i][j] == 7
						|| tablicaPionkow[i][j] == 9)
					g2d.setColor(kolorPionkow1);
				else if (tablicaPionkow[i][j] == 2 || tablicaPionkow[i][j] == 4 || tablicaPionkow[i][j] == 6
						|| tablicaPionkow[i][j] == 8)
					g2d.setColor(kolorPionkow2);

				if (tablicaPionkow[i][j] == 1 || tablicaPionkow[i][j] == 2 || tablicaPionkow[i][j] == 3
						|| tablicaPionkow[i][j] == 4)
					g2d.fillOval(17 + 63 * i, 47 + 63 * j, 50, 50);
				if (tablicaPionkow[i][j] == 6 || tablicaPionkow[i][j] == 7 || tablicaPionkow[i][j] == 8
						|| tablicaPionkow[i][j] == 9)
					g2d.fillRect(17 + 63 * i, 47 + 63 * j, 50, 50);
			}

		g2d.setColor(Color.BLACK);
		g2d.drawRect(550, 493, 50, 50);
		g2d.setFont(new Font("Arial", Font.BOLD, 12));
		g2d.drawString("POLE", 560, 515);
		g2d.drawString(Kafelek.tmp, 570, 535);

	}

	/** Przes�oni�ta metoda s�u��ca do okre�lania zachowania aplikacji po klikni�ciu na dany komponent przez u�ytkownika.
	 *  W metodzie tej okre�lono dzia�anie dla poszczeg�lnych przycisk�w znajduj�cych si� po prawej stronie g��wnego interfejsu gry.
	 *  W przypadku klikni�cia na przycisk 'Start' wywo�ywana jest metoda {@link Plansza#ustaw()}, kt�ra ustawia 
	 *  pionki na pocz�tkowe pozycje. Nast�pnie wywo�ywana jest zdefiniowana metoda {@link java.awt.Component#repaint()}, celem wy�wietlenia
	 *  zmian na interfejsie gry.
	 *  W przypadku klikni�cia na przycisk 'Opcje' tworzona jest nowa instancja klasy {@link Opcje}, poprzez wywo�anie konstruktora tej klasy.
	 *  Otworzy si� wtedy drugie okno z opcjami gry do wyboru.
	 *  W przypadku klikni�cia na przycsik 'Multiplayer' tworzone jest gniazdo, kt�re jest automatycznie ��czone z podanym portem i adresem. 
	 *  Nast�pnie je�li ��czenie przebieg�o poprawnie wy�wietlany jest komunikat za pomoc� okna dialogowego klasy {@link JOptionPane} ze stosown�
	 *  informacj�. Nast�pnie inicjalizowane s� strumienie zapisu do gniazda lub odczytu z gniazda. Dalej zmienna logiczna o nazwie 'multi' jest
	 *  ustawiana na warto�� 'true'. Nast�pnie wywo�ywana jest metoda {@link Plansza#ustaw()}, kt�ra ustawia pionki na pocz�tkowe pozycje oraz 
	 *  zdefiniowana metoda {@link java.awt.Component#repaint()}, celem wy�wietlenia zmian na interfejsie gry.
	 *  Po funkcji {@link java.awt.Component#repaint()} tworzona jest nowa instancja klasy {@link Thread}, kt�ra tworzy nowy w�tek. 
	 *  W�tek ma za zadanie odczytywa� dane ze strumienia odbieraj�cego dane z gniazda - jest to tablica pionk�w. W przypadku niepowodzenia w ��czeniu si�
	 *  z serwerem wypisywany jest stosowny komunikat za pomoc� komponentu okna dialogowego klasy {@link JOptionPane}.
	 *  W przypadku klikni�cia na przycisk 'Wyjscie' pojawia si� komunikat w postaci okna dialogowego z pro�b� potwierdzenia operacji. 
	 *  Gdy u�ytkownik potwierdzi wol� zamkni�cia aplikacji to zamykane s� strumienie zapisu oraz odczytu z gniazda sieciowego, zamykane jest gniazdo po��czone
	 *  oraz gniazdo serwerowe. 
	 *  @see Plansza#ustaw()
	 *  @see java.awt.Component#repaint()
	 *  @see JOptionPane
	 *  @see Socket
	 *  @see ServerSocket
	 *  @see Thread
	 *  @see Thread#run()
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == start) {
			ustaw();
			repaint();
		}

		if (source == multiplayer) {
			try {
				socket = new Socket("localhost", 5555);
				JOptionPane.showMessageDialog(null, "Po��czono z serwerem");
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
				multi = true;
				ustaw();
				repaint();
				new Thread() {
					public void run() {
						while (true) {
							try {
								int tmp[][] = (int[][]) ois.readObject();
								if (!tmp.equals(tablicaPionkow)) {
									System.out.println("Zamieniono");
									tablicaPionkow = tmp;
									repaint();
								}
							} catch (ClassNotFoundException | IOException e) {
							}
						}
					}
				}.start();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Nie nawi�zano po��czenia");
			}
		}

		if (source == opcje) {
			new Opcje(this);
		}

		if (source == wyjscie) {
			int decyzja = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz wyj��?", "Confirm Dialog",
					JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
			if (decyzja == JOptionPane.YES_OPTION) {
				dispose();
				try {
					ois.close();
					oos.close();
					socket.close();
					serverSocket.close();
				} catch (IOException e1) {

				}

			}
		}
	}
}