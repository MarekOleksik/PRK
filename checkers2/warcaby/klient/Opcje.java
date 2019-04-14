package warcaby.klient;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/** Klasa Opcje zawiera zmienne oraz metody odpowiedzialne za prawid�owe wy�wietlanie okienka opcji gry oraz ich dzia�anie. 
 * W klasie istniej� zmienne b�d�ce obiektami klas z pakietu Swing: JFrame, JLabel, JButton oraz JComboBox. Ponadto
 * wyst�puje konstruktor oraz przes�oni�ta metoda actionPerformed odpowiedzialna za prawid�owe akcje po wci�ni�ciu 
 * danego przycisku lub innego komponentu. 
 * Klasa implementuje interfejs ActionListener odpowiadaj�cy za okre�lenie akcji po wci�ni�ciu np. danego przycisku.
 * Ponadto dziedziczy ona po klasie JFrame - odpowiedzialnej za tworzenie okna opcji gry.
 * @author Bart�omiej Osak, Tomasz Pasternak
 * @see ActionListener
 * @see JFrame
 */
public class Opcje extends JFrame implements ActionListener {

	/** Zmienna okre�laj�ca unikalny numer w celu serializacji. */
	private static final long serialVersionUID = 1L;
	/** Zmienna JFrame odwo�uj�ca si� do okna g��wnej aplikacji. Wykorzystywana podczas zmiany koloru planszy/pionk�w w opcjach. */
	protected static JFrame parent; 
	 /** Zmienna JFrame okreslaj�ca okno z opcjami gry. */
	protected JFrame okno; 
	/** Zmienna JLabel u�ywana w celu tworzenia napisu 'Opcje Gry'. */
	protected JLabel napisGlowny;
	/** Zmienna JLabel u�ywana w celu tworzenia napisu 'Styl aplikacji'. */
	protected JLabel napisStyl; 
	/** Zmienna JLabel u�ywana w celu tworzenia napisu 'Wygl�d planszy'. */
	protected JLabel napisZmianaWygladu; 
	/** Zmienna JLabel u�ywana w celu tworzenia napisu 'Wygl�d pionk�w'. */
	protected JLabel napisZmianaPionkow; 
	/** Zmienna JButton odpowiedzialna za przycisk w sekcji 'Wygl�d planszy' z nazw� 'Domy�lny' - ustawia styl planszy na domy�lny. */
	protected JButton stylDomyslny; 
	/** Zmienna JButton odpowiedzialna za przycisk w sekcji 'Wygl�d planszy' z nazw� 'Be�owo - bia�a' - ustawia styl planszy na be�owo-bia��. */
	protected JButton stylPlanszy1; 
	/** Zmienna JButton odpowiedzialna za przycisk w sekcji 'Wygl�d planszy' z nazw� 'Niebiesko - ��ta' - ustawia styl planszy na niebiesko-��t�. */
	protected JButton stylPlanszy2; 
	/** Zmienna JButton odpowiedzialna za przycisk w sekcji 'Wygl�d pionk�w' z nazw� 'Czerwono - ��te' - ustawia styl pionk�w na czerwono-��te. */
	protected JButton stylPionkow1; 
	/** Zmienna JButton odpowiedzialna za przycisk w sekcji 'Wygl�d pionk�w' z nazw� 'Zielono - szare' - ustawia styl pionk�w na zielono - szare. */
	protected JButton stylPionkow2; 
	/** Zmienna JButton odpowiedzialna za przycisk w sekcji 'Wygl�d pionk�w' z nazw� 'Domy�lny' - ustawia styl pionk�w na domy�lny. */
	protected JButton stylDomyslnyPionkow; 
	/** Zmienna JComboBox odpowiedzialna za list� z mo�liwo�ci� wyboru og�lnego wygl�du aplikacji - zmiana koloru okienka. */
	protected JComboBox<Object> style; 

	/** Konstruktor klasy Opcje przyjmuj�cy jeden argument.
	 * W konstruktorze definiowane s�: rozmiar okna z opcjami gry, jego pozosta�e w�a�ciwo�ci. Ponadto 
	 * zdefiniowano w�a�ciwo�ci napis�w poszczeg�lnych sekcji na komponencie JLabel tj. font, rozmiar, wymiary, kolor. 
	 * Zdefiniowano list� wysuwan� umo�lwiaj�c� wyb�r stylu aplikacji. Okre�lono rozmiar, styl.
	 * Ponadto zdefiniowano w�a�ciwo�ci poszczeg�lnych przycisk�w na komponencie JButton - ustalono font, napis oraz wymiar.
	 * Po zdefiniowaniu ka�dego z u�ytych komponent�w dodano go do okna z opcjami g��wnymi metod� {@link JFrame#add(java.awt.Component)}.
	 * @param oknoF - argument typu JFrame, czyli okno - g��wne okno aplikacji.
	 * @see JFrame
	 * @see JLabel
	 * @see JComboBox
	 * @see JButton
	 * @see JFrame#add(java.awt.Component)
	 */
	public Opcje(JFrame oknoF) {
		parent = oknoF;
		okno = new JFrame();
		okno.setTitle("Opcje");
		okno.setSize(650, 300);
		okno.setLocationRelativeTo(okno);
		okno.setLayout(null);
		okno.setResizable(false);

		napisGlowny = new JLabel("OPCJE GRY");
		napisGlowny.setBounds(265, 10, 120, 25);
		napisGlowny.setFont(new Font("Arial", Font.BOLD, 18));
		napisGlowny.setForeground(Color.BLUE);
		okno.add(napisGlowny);

		napisStyl = new JLabel("Styl aplikacji");
		napisStyl.setBounds(10, 70, 100, 20);
		napisStyl.setFont(new Font("Calibri", Font.BOLD, 14));
		napisStyl.setForeground(Color.RED);
		okno.add(napisStyl);

		style = new JComboBox<Object>(new String[] { "Domy�lny", "Metal", "Motif", "Nimbus" });
		style.setBounds(225, 90, 200, 20);
		okno.add(style);
		style.addActionListener(this);

		napisZmianaWygladu = new JLabel("Wygl�d planszy");
		napisZmianaWygladu.setBounds(10, 130, 120, 20);
		napisZmianaWygladu.setFont(new Font("Calibri", Font.BOLD, 14));
		napisZmianaWygladu.setForeground(Color.RED);
		okno.add(napisZmianaWygladu);

		stylDomyslny = new JButton("Domy�lna");
		stylDomyslny.setBounds(100, 160, 150, 20);
		okno.add(stylDomyslny);
		stylDomyslny.addActionListener(this);

		stylPlanszy1 = new JButton("Be�owo - bia�a");
		stylPlanszy1.setBounds(260, 160, 150, 20);
		okno.add(stylPlanszy1);
		stylPlanszy1.addActionListener(this);

		stylPlanszy2 = new JButton("Niebiesko - ��ta");
		stylPlanszy2.setBounds(420, 160, 150, 20);
		okno.add(stylPlanszy2);
		stylPlanszy2.addActionListener(this);

		napisZmianaPionkow = new JLabel("Wygl�d pionk�w");
		napisZmianaPionkow.setBounds(10, 200, 120, 20);
		napisZmianaPionkow.setFont(new Font("Calibri", Font.BOLD, 14));
		napisZmianaPionkow.setForeground(Color.RED);
		okno.add(napisZmianaPionkow);

		stylDomyslnyPionkow = new JButton("Domy�lny");
		stylDomyslnyPionkow.setBounds(100, 230, 150, 20);
		okno.add(stylDomyslnyPionkow);
		stylDomyslnyPionkow.addActionListener(this);

		stylPionkow1 = new JButton("Czerwono - ��te");
		stylPionkow1.setBounds(260, 230, 150, 20);
		okno.add(stylPionkow1);
		stylPionkow1.addActionListener(this);

		stylPionkow2 = new JButton("Zielono - szare");
		stylPionkow2.setBounds(420, 230, 150, 20);
		okno.add(stylPionkow2);
		stylPionkow2.addActionListener(this);

		okno.setVisible(true);
	}

	/** Przes�oni�ta metoda s�u��ca do okre�lania zachowania aplikacji po klikni�ciu na dany komponent przez u�ytkownika.
	 * W metodzie okre�lone s� dzia�ania dla przycisk�w oraz listy wysuwanej. Ponadto po wci�ni�ciu niekt�rych z przycisk�w
	 * wy�wietlane s� okna dialogowe za pomoc� JOptionPane. W przypadku zmiany wygl�du aplikacji lub pionk�w lub planszy
	 * wywo�ywana jest metoda klasy {@link JFrame#repaint()}, w celu na�o�enia zmian na wygl�d poszczeg�lnych komponent�w.
	 * Metoda jest typu void - nie zwraca �adnej warto�ci. 
	 * @see JOptionPane
	 * @see JOptionPane#showMessageDialog(java.awt.Component, Object)
	 * @see JFrame#repaint()
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == stylDomyslny) {
			JOptionPane.showMessageDialog(null, "Zmieniono!");
			Plansza.kolorPlanszy1 = new Color(255, 120, 0);
			Plansza.kolorPlanszy2 = new Color(61, 43, 31);
			parent.repaint();
		}
		if (source == stylPlanszy1) {
			JOptionPane.showMessageDialog(null, "Zmieniono!");
			Plansza.kolorPlanszy1 = new Color(194, 178, 128);
			Plansza.kolorPlanszy2 = new Color(255, 255, 255);
			parent.repaint();
		}
		if (source == stylPlanszy2) {
			JOptionPane.showMessageDialog(null, "Zmieniono!");
			Plansza.kolorPlanszy1 = new Color(98, 98, 251);
			Plansza.kolorPlanszy2 = new Color(254, 254, 142);
			parent.repaint();
		}
		if (source == stylDomyslnyPionkow) {
			JOptionPane.showMessageDialog(null, "Zmieniono!");
			Plansza.kolorPionkow1 = Color.BLACK;
			Plansza.kolorPionkow2 = Color.WHITE;
			parent.repaint();
		}
		if (source == stylPionkow1) {
			JOptionPane.showMessageDialog(null, "Zmieniono!");
			Plansza.kolorPionkow1 = Color.RED;
			Plansza.kolorPionkow2 = Color.YELLOW;
			parent.repaint();
		}
		if (source == stylPionkow2) {
			JOptionPane.showMessageDialog(null, "Zmieniono!");
			Plansza.kolorPionkow1 = Color.GREEN;
			Plansza.kolorPionkow2 = Color.DARK_GRAY;
			parent.repaint();
		}

		if (source == style) {
			String s = (String) style.getSelectedItem();
			if (s == "Domy�lny") {
				parent.getContentPane().setBackground(null);
				parent.repaint();
			}
			if (s == "Metal") {
				parent.getContentPane().setBackground(new Color(230, 230, 254));
				parent.repaint();
			}
			if (s == "Motif") {
				parent.getContentPane().setBackground(new Color(219, 247, 151));
				parent.repaint();
			}
			if (s == "Nimbus") {
				parent.getContentPane().setBackground(new Color(254, 254, 142));
				parent.repaint();
			}
		}
	}

}