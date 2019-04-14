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

/** Klasa Plansza zawiera deklaracje oraz definicje wszystkich komponentów tworz¹cych okno g³ówne aplikacji.
 * W klasie zdefiniowano wygl¹d ogólny okna gry, wygl¹d planszy, której wygl¹d mo¿na zmieniæ poprzez opcje gry - {@link warcaby.klient.Opcje}.
 * Ponadto w klasie zadeklarowano zmienne potrzebne do nawi¹zania po³¹czenia sieciowego z drugim graczem - tu z serwerem. 
 * W klasie ustawiane s¹ równie¿ pocz¹tkowe wspó³rzêdne tablicy pionków na szachownicy. 
 * Ponadto okreœlono dzia³anie przycisków - w szczególnoœci przycisk 'Multiplayer' (zdefiniowano w¹tek, który odczytuje 
 * obiekt ze strumienia umo¿liwiaj¹cego zapis danych do gniazda (w tym przypadku tablica pionków - {@link Plansza#tablicaPionkow}.
 * Klasa implementuje interfejs ActionListener odpowiadaj¹cy za okreœlenie akcji po wciœniêciu np. danego przycisku.
 * Ponadto implementowany jest interfejs Serializable w celu serializacji obiektu wysy³anego poprzez po³¹czenie sieciowe TCP.
 * Klasa dziedziczy po JFrame - klasie tworz¹cej g³ówne okno aplikacji.
 * @author Bart³omiej Osak, Tomasz Pasternak
 * @see Serializable
 * @see ActionListener
 * @see JFrame
 */
public class Plansza extends JFrame implements ActionListener, Serializable {
	
	/** Zmienna okreœlaj¹ca unikalny numer w celu serializacji. */
	private static final long serialVersionUID = 1L;
	/** Tablica dwuwymiarowa typu int przechowuj¹ca wspó³rzêdne pionków na szachownicy. */
	protected static int tablicaPionkow[][];
	/** Zmienna typu Integer okreœlaj¹ca szerokoœæ okna g³ównego aplikacji. */
	private Integer szerokosc = 650;
	/** Zmienna typu Integer okreœlaj¹ca wysokoœæ okna g³ównego aplikacji. */
	private Integer wysokosc = 600;
	/** Zmienna JButton odpowiedzialna za przycisk 'Start' odpowiedzialny za uruchomienie rozgrywki lokalnej (bez po³¹czenia sieciowego). 
	 * Aktywacja powoduje rozpoczêcie rozgrywki bez po³¹czenia sieciowego. Gra odbywa siê na jednym komputerze oraz na jednym oknie aplikacji 
	 * (bez znaczenia czy jest to okno gry Klienta czy Serwera). Gra mo¿e byæ kontynuuowana przez dwóch graczy lub przez jednego w celu treningu, 
	 * wszystko zgodnie z preferencjami u¿ytkownika. 
	 * */
	private JButton start;
	/** Zmienna JButton odpowiedzialna za przycisk 'Wyjœcie' odpowiedzialny za wyjœcie z aplikacji.
	 * W przypadku gry lokalnej (bez po³¹czenia sieciowego) wyst¹pi okno dialogowe z komunikatem, czy u¿ytkownik na pewno chce wyjœæ
	 * z aplikacji w celu zabezpieczenia przed losowym zamkniêciem aplikacji na wskutek b³êdnej decyzji u¿ytkownika.
	 * W przypadku po³¹czenia sieciowego zamykane s¹ strumienie oraz gniazda serwerowe oraz niepo³¹czone. 
	 * */
	private JButton wyjscie; 
	/** Zmienna JButton odpowiedzialna za przycisk 'Opcje' odpowiedzialny za uruchomienie opcji gry.
	 * @see warcaby.klient.Opcje 
	 */
	private JButton opcje; 
	/** Zmienna JButton odpowiedzialna za przycisk 'Multiplayer' odpowiedzialny za uruchomienie rozgrywki sieciowej.
	 *  Aktywacja powoduje wyszukiwanie dostêpnego serwera - w przypadku powodzenia operacji odczytywany jest
	 *  odbierany obiekt, w tym przypadku tablica pionków. 
	 *  W przypadku niepowodzenia wypisywany jest komunikat o niepowodzeniu w po³¹czeniu z serwerem.
	 * @see ServerSocket
	 * @see Socket
	 */
	private JButton multiplayer;
	/** Zmienna JLabel u¿ywana w celu tworzenia napisu 'WARCABY'. */
	private JLabel napisGlowny;
	/** Zmienna JLabel u¿ywana w celu tworzenia napisu 'KLIENT'. */
	private JLabel napisWersja;
	/** Obiekt klasy Plansza. */
	private Plansza plansza;
	/** Tablica obiektów klasy Kafelek. Ka¿dy element tablicy okreœla wspó³rzêdne ka¿dego pojedynczego kafelka szachownicy. 
	 *  @see Kafelek
	 */
	private Kafelek[][] kafelki;
	/** Zmienna statyczna typu Color okreœlaj¹ca kolor planszy, który jest ustalany poprzez opcje {@link warcaby.klient.Opcje}.
	 *  @see Color
	 */
	protected static Color kolorPlanszy1;
	/** Zmienna statyczna typu Color okreœlaj¹ca kolor planszy, który jest ustalany poprzez opcje {@link warcaby.klient.Opcje}.
	 *  @see Color
	 */
	protected static Color kolorPlanszy2;
	/** Zmienna statyczna typu Color okreœlaj¹ca kolor pionków, który jest ustalany poprzez opcje {@link warcaby.klient.Opcje}.
	 *  @see Color
	 */
	protected static Color kolorPionkow1;
	/** Zmienna statyczna typu Color okreœlaj¹ca kolor pionków, który jest ustalany poprzez opcje {@link warcaby.klient.Opcje}.
	 *  @see Color
	 */
	protected static Color kolorPionkow2;
	/** Zmienna tworz¹ca gniazdo serwerowe powi¹zane z podanym portem - deklaracja obiektu klasy ServerSocket.
	 *  @see ServerSocket
	 */
	protected ServerSocket serverSocket;
	/** Zmienna tworz¹ca gniazdo powi¹zane z podanym portem - deklaracja obiektu klasy Socket.
	 *  @see Socket
	 */
	protected Socket socket;
	/** Zmienna tworz¹ca strumieñ umo¿liwiaj¹cy odczyt danych z gniazda sieciowego. 
	 *  @see ObjectInputStream
	 */
	protected static ObjectInputStream ois;
	/** Zmienna tworz¹ca strumieñ umo¿liwiaj¹cy zapis danych do gniazda sieciowego. 
	 *  @see ObjectOutputStream
	 */
	protected static ObjectOutputStream oos;
	/** Zmienna logiczna okreœlaj¹ca, czy u¿ytkownik wybra³ tryb gry sieciowej poprzez klikniêcie na przycisk 'Multiplayer'.*/
	protected static boolean multi;
	/** Zmienna logiczna okreœlaj¹ca ustawiaj¹ca siê na true w przypadku, gdy pionki zostan¹ ustawione - sygna³ do gry..*/
	protected static boolean gra;

	/** Konstruktor bezparametrowy klasy Plansza odpowiedzialny za inicjalizacjê komponentów z biblioteki Swing, zmiennych, tablic.
	 * Komponenty definiowane: JButton, JLabel, JFrame - dla tych komponentów ustawiane s¹ wymiary, fonty, kolory.
	 * Po zdefiniowaniu ka¿dego z u¿ytych komponentów dodano go do okna z opcjami g³ównymi metod¹ {@link JFrame#add(java.awt.Component)}.
	 * Ponadto definiowane s¹ tablice obiektów tj. tablica obiektów klasy Kafelek, tablica wspó³rzêdnych pionków na szachownicy.
	 * Zdefiniowano równie¿ zmienne typu Color, które okreœlaj¹ kolor planszy lub kolor pionków
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

		wyjscie = new JButton("Wyjœcie");
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

	/** Metoda bezparametrowa odpowiedzialna za ustawianie pionków na szachownicy.
	 * Operacja przeprowadzana jest poprzez u¿ycie pêtli for oraz rozgraniczenie
	 * dwóch kolorów pionów od siebie - jeden kolor oznaczany jest poprzez 1, 
	 * a drugi poprzez 2. Pole puste poprzez 0. Po poprawnym ustawieniu
	 * zmienna logiczna 'gra' jest ustawiana na wartoœæ TRUE.
	 * Metoda jest typu void - nie zwraca ¿adnej wartoœci. 
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
	
	/** Metoda przes³oniêta o nazwie paint. Przyjmuje jeden sta³y parametr typu Graphics.
	 * Metoda wykorzystywana jest do rysowania szachownicy oraz pionków. 
	 * Ponadto w klasie zaadaptowano równie¿ rysowanie podœwietleñ konturów aktywnych kafelków, 
	 * na które mo¿emy przemieœciæ gracz swój pionek - s¹ to tak zwane pola aktywne do ruchu dla pionka. 
	 * W metodzie rysowany jest równie¿ kafelek informacyjny umieszczony w prawym dolnym rogu okna g³ównego
	 * aplikacji, który informuje u¿ytkownika na jakim polu szachownicy obecnie znajduje siê jego kursor myszy.
	 * Rysowanie odbywa siê za pomoc¹ komponentu {@link Graphics2D}. Wykorzystywane s¹ funkcje takie jak:
	 * {@link Graphics2D#drawRect(int, int, int, int)},{@link Graphics2D#setColor(Color)}, {@link Graphics2D#fillRect(int, int, int, int)},
	 * {@link Graphics2D#fillOval(int, int, int, int)} itp. 
	 * Metoda jest typu void - nie zwraca ¿adnej wartoœci. 
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

	/** Przes³oniêta metoda s³u¿¹ca do okreœlania zachowania aplikacji po klikniêciu na dany komponent przez u¿ytkownika.
	 *  W metodzie tej okreœlono dzia³anie dla poszczególnych przycisków znajduj¹cych siê po prawej stronie g³ównego interfejsu gry.
	 *  W przypadku klikniêcia na przycisk 'Start' wywo³ywana jest metoda {@link Plansza#ustaw()}, która ustawia 
	 *  pionki na pocz¹tkowe pozycje. Nastêpnie wywo³ywana jest zdefiniowana metoda {@link java.awt.Component#repaint()}, celem wyœwietlenia
	 *  zmian na interfejsie gry.
	 *  W przypadku klikniêcia na przycisk 'Opcje' tworzona jest nowa instancja klasy {@link Opcje}, poprzez wywo³anie konstruktora tej klasy.
	 *  Otworzy siê wtedy drugie okno z opcjami gry do wyboru.
	 *  W przypadku klikniêcia na przycsik 'Multiplayer' tworzone jest gniazdo, które jest automatycznie ³¹czone z podanym portem i adresem. 
	 *  Nastêpnie jeœli ³¹czenie przebieg³o poprawnie wyœwietlany jest komunikat za pomoc¹ okna dialogowego klasy {@link JOptionPane} ze stosown¹
	 *  informacj¹. Nastêpnie inicjalizowane s¹ strumienie zapisu do gniazda lub odczytu z gniazda. Dalej zmienna logiczna o nazwie 'multi' jest
	 *  ustawiana na wartoœæ 'true'. Nastêpnie wywo³ywana jest metoda {@link Plansza#ustaw()}, która ustawia pionki na pocz¹tkowe pozycje oraz 
	 *  zdefiniowana metoda {@link java.awt.Component#repaint()}, celem wyœwietlenia zmian na interfejsie gry.
	 *  Po funkcji {@link java.awt.Component#repaint()} tworzona jest nowa instancja klasy {@link Thread}, która tworzy nowy w¹tek. 
	 *  W¹tek ma za zadanie odczytywaæ dane ze strumienia odbieraj¹cego dane z gniazda - jest to tablica pionków. W przypadku niepowodzenia w ³¹czeniu siê
	 *  z serwerem wypisywany jest stosowny komunikat za pomoc¹ komponentu okna dialogowego klasy {@link JOptionPane}.
	 *  W przypadku klikniêcia na przycisk 'Wyjscie' pojawia siê komunikat w postaci okna dialogowego z proœb¹ potwierdzenia operacji. 
	 *  Gdy u¿ytkownik potwierdzi wolê zamkniêcia aplikacji to zamykane s¹ strumienie zapisu oraz odczytu z gniazda sieciowego, zamykane jest gniazdo po³¹czone
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
				JOptionPane.showMessageDialog(null, "Po³¹czono z serwerem");
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
				JOptionPane.showMessageDialog(null, "Nie nawi¹zano po³¹czenia");
			}
		}

		if (source == opcje) {
			new Opcje(this);
		}

		if (source == wyjscie) {
			int decyzja = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz wyjœæ?", "Confirm Dialog",
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