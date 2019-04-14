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

/** Klasa Opcje zawiera zmienne oraz metody odpowiedzialne za prawid³owe wyœwietlanie okienka opcji gry oraz ich dzia³anie. 
 * W klasie istniej¹ zmienne bêd¹ce obiektami klas z pakietu Swing: JFrame, JLabel, JButton oraz JComboBox. Ponadto
 * wystêpuje konstruktor oraz przes³oniêta metoda actionPerformed odpowiedzialna za prawid³owe akcje po wciœniêciu 
 * danego przycisku lub innego komponentu. 
 * Klasa implementuje interfejs ActionListener odpowiadaj¹cy za okreœlenie akcji po wciœniêciu np. danego przycisku.
 * Ponadto dziedziczy ona po klasie JFrame - odpowiedzialnej za tworzenie okna opcji gry.
 * @author Bart³omiej Osak, Tomasz Pasternak
 * @see ActionListener
 * @see JFrame
 */
public class Opcje extends JFrame implements ActionListener {

	/** Zmienna okreœlaj¹ca unikalny numer w celu serializacji. */
	private static final long serialVersionUID = 1L;
	/** Zmienna JFrame odwo³uj¹ca siê do okna g³ównej aplikacji. Wykorzystywana podczas zmiany koloru planszy/pionków w opcjach. */
	protected static JFrame parent; 
	 /** Zmienna JFrame okreslaj¹ca okno z opcjami gry. */
	protected JFrame okno; 
	/** Zmienna JLabel u¿ywana w celu tworzenia napisu 'Opcje Gry'. */
	protected JLabel napisGlowny;
	/** Zmienna JLabel u¿ywana w celu tworzenia napisu 'Styl aplikacji'. */
	protected JLabel napisStyl; 
	/** Zmienna JLabel u¿ywana w celu tworzenia napisu 'Wygl¹d planszy'. */
	protected JLabel napisZmianaWygladu; 
	/** Zmienna JLabel u¿ywana w celu tworzenia napisu 'Wygl¹d pionków'. */
	protected JLabel napisZmianaPionkow; 
	/** Zmienna JButton odpowiedzialna za przycisk w sekcji 'Wygl¹d planszy' z nazw¹ 'Domyœlny' - ustawia styl planszy na domyœlny. */
	protected JButton stylDomyslny; 
	/** Zmienna JButton odpowiedzialna za przycisk w sekcji 'Wygl¹d planszy' z nazw¹ 'Be¿owo - bia³a' - ustawia styl planszy na be¿owo-bia³¹. */
	protected JButton stylPlanszy1; 
	/** Zmienna JButton odpowiedzialna za przycisk w sekcji 'Wygl¹d planszy' z nazw¹ 'Niebiesko - ¿ó³ta' - ustawia styl planszy na niebiesko-¿ó³t¹. */
	protected JButton stylPlanszy2; 
	/** Zmienna JButton odpowiedzialna za przycisk w sekcji 'Wygl¹d pionków' z nazw¹ 'Czerwono - ¿ó³te' - ustawia styl pionków na czerwono-¿ó³te. */
	protected JButton stylPionkow1; 
	/** Zmienna JButton odpowiedzialna za przycisk w sekcji 'Wygl¹d pionków' z nazw¹ 'Zielono - szare' - ustawia styl pionków na zielono - szare. */
	protected JButton stylPionkow2; 
	/** Zmienna JButton odpowiedzialna za przycisk w sekcji 'Wygl¹d pionków' z nazw¹ 'Domyœlny' - ustawia styl pionków na domyœlny. */
	protected JButton stylDomyslnyPionkow; 
	/** Zmienna JComboBox odpowiedzialna za listê z mo¿liwoœci¹ wyboru ogólnego wygl¹du aplikacji - zmiana koloru okienka. */
	protected JComboBox<Object> style; 

	/** Konstruktor klasy Opcje przyjmuj¹cy jeden argument.
	 * W konstruktorze definiowane s¹: rozmiar okna z opcjami gry, jego pozosta³e w³aœciwoœci. Ponadto 
	 * zdefiniowano w³aœciwoœci napisów poszczególnych sekcji na komponencie JLabel tj. font, rozmiar, wymiary, kolor. 
	 * Zdefiniowano listê wysuwan¹ umo¿lwiaj¹c¹ wybór stylu aplikacji. Okreœlono rozmiar, styl.
	 * Ponadto zdefiniowano w³aœciwoœci poszczególnych przycisków na komponencie JButton - ustalono font, napis oraz wymiar.
	 * Po zdefiniowaniu ka¿dego z u¿ytych komponentów dodano go do okna z opcjami g³ównymi metod¹ {@link JFrame#add(java.awt.Component)}.
	 * @param oknoF - argument typu JFrame, czyli okno - g³ówne okno aplikacji.
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

		style = new JComboBox<Object>(new String[] { "Domyœlny", "Metal", "Motif", "Nimbus" });
		style.setBounds(225, 90, 200, 20);
		okno.add(style);
		style.addActionListener(this);

		napisZmianaWygladu = new JLabel("Wygl¹d planszy");
		napisZmianaWygladu.setBounds(10, 130, 120, 20);
		napisZmianaWygladu.setFont(new Font("Calibri", Font.BOLD, 14));
		napisZmianaWygladu.setForeground(Color.RED);
		okno.add(napisZmianaWygladu);

		stylDomyslny = new JButton("Domyœlna");
		stylDomyslny.setBounds(100, 160, 150, 20);
		okno.add(stylDomyslny);
		stylDomyslny.addActionListener(this);

		stylPlanszy1 = new JButton("Be¿owo - bia³a");
		stylPlanszy1.setBounds(260, 160, 150, 20);
		okno.add(stylPlanszy1);
		stylPlanszy1.addActionListener(this);

		stylPlanszy2 = new JButton("Niebiesko - ¿ó³ta");
		stylPlanszy2.setBounds(420, 160, 150, 20);
		okno.add(stylPlanszy2);
		stylPlanszy2.addActionListener(this);

		napisZmianaPionkow = new JLabel("Wygl¹d pionków");
		napisZmianaPionkow.setBounds(10, 200, 120, 20);
		napisZmianaPionkow.setFont(new Font("Calibri", Font.BOLD, 14));
		napisZmianaPionkow.setForeground(Color.RED);
		okno.add(napisZmianaPionkow);

		stylDomyslnyPionkow = new JButton("Domyœlny");
		stylDomyslnyPionkow.setBounds(100, 230, 150, 20);
		okno.add(stylDomyslnyPionkow);
		stylDomyslnyPionkow.addActionListener(this);

		stylPionkow1 = new JButton("Czerwono - ¿ó³te");
		stylPionkow1.setBounds(260, 230, 150, 20);
		okno.add(stylPionkow1);
		stylPionkow1.addActionListener(this);

		stylPionkow2 = new JButton("Zielono - szare");
		stylPionkow2.setBounds(420, 230, 150, 20);
		okno.add(stylPionkow2);
		stylPionkow2.addActionListener(this);

		okno.setVisible(true);
	}

	/** Przes³oniêta metoda s³u¿¹ca do okreœlania zachowania aplikacji po klikniêciu na dany komponent przez u¿ytkownika.
	 * W metodzie okreœlone s¹ dzia³ania dla przycisków oraz listy wysuwanej. Ponadto po wciœniêciu niektórych z przycisków
	 * wyœwietlane s¹ okna dialogowe za pomoc¹ JOptionPane. W przypadku zmiany wygl¹du aplikacji lub pionków lub planszy
	 * wywo³ywana jest metoda klasy {@link JFrame#repaint()}, w celu na³o¿enia zmian na wygl¹d poszczególnych komponentów.
	 * Metoda jest typu void - nie zwraca ¿adnej wartoœci. 
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
			if (s == "Domyœlny") {
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