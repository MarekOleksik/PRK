package warcaby.klient;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Klasa Kafelek zawiera podstawowe zmienne oraz metody obs³ugi ruchu pionków po
 * szachownicy oraz okreœla równie¿ bicia. Klasa posiada zmienne oraz metody
 * odpowiedzialne za okreœlenie pojedynczego kafelka wystêpuj¹cego na
 * szachownicy, dziêki temu mo¿na ³atwo okreœliæ mo¿liwe ruchy dla pionka oraz
 * mo¿liwoœci bicia. Ponadto w klasie zdefiniowano wyst¹pienie królówek zgodnie
 * z zasadami gry - warcaby. Zdefiniowano równie¿ dzia³anie kafelka
 * informacyjnego, który pokazuje wspó³rzêdne kursora u¿ytkownika wzglêdem
 * szachownicy - kafelek znajduje siê w prawym dolnym rogu g³ównego okna
 * aplikacji. Klasa Kafelek dziedziczy po klasie {@link javax.swing.JComponent}
 * celem stworzenia obiektu kafelka informacyjnego. Ponadto rozszerzona jest ona
 * poprzez interfejs {@link java.awt.event.MouseListener} celem zdefiniowania
 * akcji mysz¹ - czyli podstawowego kontrolera rozgrywki - poprzez klikanie
 * mysz¹ na odpowiednie pionki mo¿emy je przemieszczaæ oraz wykonywaæ bicia.
 * 
 * @author Bart³omiej Osak, Tomasz Pasternak
 * @see javax.swing.JComponent
 * @see java.awt.event.MouseListener
 */
public class Kafelek extends JComponent implements MouseListener {

	/** Zmienna okreœlaj¹ca unikalny numer w celu serializacji. */
	private static final long serialVersionUID = 1L;
	/** Zmienna okreœlaj¹ca wspó³rzêdn¹ x pojedynczego kafelka szachownicy. */
	protected Integer x;
	/** Zmienna okreœlaj¹ca wspó³rzêdn¹ y pojedynczego kafelka szachownicy. */
	protected Integer y;
	/**
	 * Zmienna okreœlaj¹ca wymiar - szerokoœæ pojedynczego kafelka szachownicy.
	 */
	protected Integer width = 62;
	/**
	 * Zmienna okreœlaj¹ca wymiar - wysokoœæ pojedynczego kafelka szachownicy.
	 */
	protected Integer height = 62;
	/**
	 * Zmienna okreœlaj¹ca wspó³rzêdn¹ x klikniêtego przez u¿ytkownika
	 * pojedynczego kafelka szachownicy.
	 */
	protected int _i;
	/**
	 * Zmienna okreœlaj¹ca wspó³rzêdn¹ y klikniêtego przez u¿ytkownika
	 * pojedynczego kafelka szachownicy.
	 */
	protected int _j;
	/**
	 * Obiekt klasy StringBuilder maj¹cy na celu wyœwietlanie aktualnych
	 * wspó³rzêdnych kursora myszy na szachownicy na kafelku informacyjnym.
	 * 
	 * @see StringBuilder
	 */
	protected StringBuilder nazwa = new StringBuilder();
	/**
	 * Zmienna typu String inicjalizuj¹ca pocz¹tkow¹ wartoœæ wyœwietlan¹ przez
	 * kafelek informacyjny.
	 */
	protected static String tmp = "A1";
	/** Zmienna JFrame odwo³uj¹ca siê do okna g³ównej aplikacji. */
	protected static JFrame parent;
	/** Wspó³rzêdna x kafelka, na którym mo¿e dojœæ do bicia pionka. */
	protected static int iBicie;
	/** Wspó³rzêdna y kafelka, na którym mo¿e dojœæ do bicia pionka. */
	protected static int jBicie;

	/**
	 * Konstruktor klasy Kafelek inicjalizuje zmienne typu JFrame - odwo³anie do
	 * okna g³ównego aplikacji, zmienne okreœlaj¹ce wspó³rzêdne. Ponadto
	 * inicjalizuje interfejs {@link java.awt.event.MouseListener} poprzez
	 * metodê {@link java.awt.Component#addMouseListener}. Ponadto wywo³ywana
	 * jest metoda ustawNazwe, która ustawia napis aktualnej pozycji kursora
	 * myszy w kafelku informacyjnym.
	 * 
	 * @param f
	 *            - parametr JFrame - g³ówne okno aplikacji
	 * @param i
	 *            - wspó³rzêdna x napisu w kafelku informacyjnym
	 * @param j
	 *            - wspó³rzêdna y napisu w kafelku informacyjnym
	 * @see java.awt.Component
	 * @see java.awt.event.MouseListener
	 * @see javax.swing.JComponent
	 */
	public Kafelek(JFrame f, int i, int j) {
		parent = f;
		x = 8 + 63 * i;
		y = 15 + 63 * j;
		_i = i;
		_j = j;
		setBounds(x, y, width, height);
		ustawNazwe(i, j);
		addMouseListener(this);
		setLayout(null);
		setDoubleBuffered(false);
		setVisible(true);
	}

	/**
	 * Metoda ustawNazwe ma za zadanie poprawne wyœwietlanie informacji na
	 * kafelku informacyjnym. Przyjmuje dwa parametry wywo³ania. W zale¿noœci od
	 * wspó³rzêdnych informacja jest wyœwietlana poprzez dodanie do zmiennej
	 * StringBuilder nowych treœci poprzez metodê append().
	 * 
	 * @param i
	 *            - wspó³rzêdna x pojedynczego kafelka na szachownicy.
	 * @param j
	 *            - wspó³rzêdna y pojedynczego kafelka na szachownicy.
	 * @see StringBuilder
	 */
	void ustawNazwe(int i, int j) {
		String tmp = null;
		if (i == 0) {
			tmp = "A";
		}
		if (i == 1) {
			tmp = "B";
		}
		if (i == 2) {
			tmp = "C";
		}
		if (i == 3) {
			tmp = "D";
		}
		if (i == 4) {
			tmp = "E";
		}
		if (i == 5) {
			tmp = "F";
		}
		if (i == 6) {
			tmp = "G";
		}
		if (i == 7) {
			tmp = "H";
		}
		nazwa.append(tmp);
		nazwa.append(j + 1);
	}

	/**
	 * Przeci¹¿ona metoda toString(), która ma na celu prawid³owe wyœwietlanie
	 * informacji na kafelku informacyjnym.
	 */
	@Override
	public String toString() {
		return nazwa + "";
	}

	/**
	 * Metoda przes³oniêta pochodz¹ca z interfejsu {@link MouseListener}.
	 * Okreœla ona zachowanie programu podczas poruszania siê kursorem myszy po
	 * oknie aplikacji, czyli gdy kursor myszy naje¿d¿a na dany komponent - w
	 * naszym przypadku komponentem jest okno g³ówne aplikacji - w szczególnoœci
	 * szachownica. W naszym przypadku wykorzystywana jest do aktualizacji
	 * wspó³rzêdnych wyœwietlanych na kafelku informacyjnym.
	 * 
	 * @see MouseListener
	 * @see MouseListener#mouseEntered(MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		Object source = e.getSource();
		tmp = source.toString();
		parent.repaint(560, 515, 25, 25);
	}

	/**
	 * Metoda przes³oniêta pochodz¹ca z interfejsu {@link MouseListener}.
	 * Okreœla ona zachowanie programu podczas, gdy u¿ytkownik kliknie myszk¹ na
	 * komponent. Funkcja wykorzystywana do opracowania logiki ruchu pionków
	 * oraz biæ pionków. Wa¿ne oznaczenia w celu zrozumienia algorytmu ruchu
	 * oraz biæ pionów: 0 - oznaczenie pola pustego 1 - oznaczenie pionka
	 * czarnego 2 - oznaczenie pionka bia³ego 3 - oznaczenie wybranego pionka
	 * czarnego (wybrany, czyli klikniêty myszk¹ przez gracza) 4 - oznaczenie
	 * wybranego pionka bia³ego (wybrany, czyli klikniêty myszk¹ przez gracza) 5
	 * - oznaczenie pola pustego, na które mo¿na wykonaæ ruch (przesun¹æ pionka)
	 * 6 - oznaczenie bia³ej królówki 7 - oznaczenie czarnej królówki 8 -
	 * oznaczenie wybranej bia³ej królówki 9 - oznaczenie wybranej czarnej
	 * królówki Takie rozgraniczenie pozwala na doœæ czytelne odczytanie
	 * algorytmu ruchu oraz biæ. Ponadto w metodzie wysy³any jest obiekt tablicy
	 * pionków poprzez sieæ do drugiego gracza. W metodzie zdefiniowano równie¿
	 * warunek zakoñczenia gry - zakoñczenie gry sygnalizowane jest specjalnym
	 * komunikatem wyœwietlanym w oknie dialogowym.
	 * 
	 * @see MouseListener
	 * @see Plansza
	 * @see Plansza#tablicaPionkow
	 * @see JOptionPane
	 * @see JFrame
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (Plansza.tablicaPionkow[_i][_j] != 5) {
			for (int j = 0; j < 8; j++)
				for (int i = 0; i < 8; i++) {
					if (Plansza.tablicaPionkow[i][j] == 3 || Plansza.tablicaPionkow[i][j] == 4 || Plansza.tablicaPionkow[i][j] == 8 || Plansza.tablicaPionkow[i][j] == 9) {
						Plansza.tablicaPionkow[i][j] -= 2;
					}
					if (Plansza.tablicaPionkow[i][j] == 5) {
						Plansza.tablicaPionkow[i][j] = 0;
					}
				}

			// *****************************************************
			if (Plansza.tablicaPionkow[_i][_j] == 1 || Plansza.tablicaPionkow[_i][_j] == 2 || Plansza.tablicaPionkow[_i][_j] == 6 || Plansza.tablicaPionkow[_i][_j] == 7) {
				iBicie = -2;
				jBicie = -2;
				Plansza.tablicaPionkow[_i][_j] += 2;
				// ******************************************************
				if (Plansza.tablicaPionkow[_i][_j] == 8 || Plansza.tablicaPionkow[_i][_j] == 9) {
					for (int i = 0; i < 4; i++) {
						int tmpX = _i;
						int tmpY = _j;
						while (tmpX != -1 && tmpX != 8 && tmpY != -1 && tmpY != 8) {
							if (i == 0) {
								tmpX--;
								tmpY--;
							} else if (i == 1) {
								tmpX--;
								tmpY++;
							} else if (i == 2) {
								tmpX++;
								tmpY++;
							} else if (i == 3) {
								tmpX++;
								tmpY--;
							}
							if (tmpX != -1 && tmpX != 8 && tmpY != -1 && tmpY != 8)
								if (Plansza.tablicaPionkow[tmpX][tmpY] == 0) {
									Plansza.tablicaPionkow[tmpX][tmpY] = 5;
								} else {
									if (((Plansza.tablicaPionkow[tmpX][tmpY] == 1 || Plansza.tablicaPionkow[tmpX][tmpY] == 7) && Plansza.tablicaPionkow[_i][_j] == 8)
											|| (Plansza.tablicaPionkow[tmpX][tmpY] == 2 || Plansza.tablicaPionkow[tmpX][tmpY] == 6) && Plansza.tablicaPionkow[_i][_j] == 9) {
										if (i == 0) {
											tmpX--;
											tmpY--;
										} else if (i == 1) {
											tmpX--;
											tmpY++;
										} else if (i == 2) {
											tmpX++;
											tmpY++;
										} else if (i == 3) {
											tmpX++;
											tmpY--;
										}
										if (tmpX != -1 && tmpX != 8 && tmpY != -1 && tmpY != 8)
											if (Plansza.tablicaPionkow[tmpX][tmpY] == 0) {
												Plansza.tablicaPionkow[tmpX][tmpY] = 5;
												if (i == 0) {
													tmpX++;
													tmpY++;
												} else if (i == 1) {
													tmpX++;
													tmpY--;
												} else if (i == 2) {
													tmpX--;
													tmpY--;
												} else if (i == 3) {
													tmpX--;
													tmpY++;
												}
												iBicie = tmpX;
												jBicie = tmpY;
											}
									}
									break;
								}
						}
					}
				}
				// ******************************************************
				if (Plansza.tablicaPionkow[_i][_j] == 4) // KLIKNIETY PIONEK
															// BIALY
				{
					if (_i != 0 && _i != 7) {
						if (Plansza.tablicaPionkow[_i - 1][_j - 1] == 0) {
							Plansza.tablicaPionkow[_i - 1][_j - 1] = 5;
						}
						if (Plansza.tablicaPionkow[_i + 1][_j - 1] == 0) {
							Plansza.tablicaPionkow[_i + 1][_j - 1] = 5;
						}

					} else if (_i == 0) {
						if (Plansza.tablicaPionkow[_i + 1][_j - 1] == 0)
							Plansza.tablicaPionkow[_i + 1][_j - 1] = 5;
					} else if (_i == 7) {
						if (Plansza.tablicaPionkow[_i - 1][_j - 1] == 0)
							Plansza.tablicaPionkow[_i - 1][_j - 1] = 5;
					}
					// *****************************************************
					if (_j != 1)
						if (_i != 0 && _i != 1 && _i != 6 && _i != 7) {
							if (Plansza.tablicaPionkow[_i - 2][_j - 2] == 0 && (Plansza.tablicaPionkow[_i - 1][_j - 1] == 1 || Plansza.tablicaPionkow[_i - 1][_j - 1] == 7)) {
								Plansza.tablicaPionkow[_i - 2][_j - 2] = 5;
								iBicie = _i - 1;
								jBicie = _j - 1;
							}
							if (Plansza.tablicaPionkow[_i + 2][_j - 2] == 0 && (Plansza.tablicaPionkow[_i + 1][_j - 1] == 1 || Plansza.tablicaPionkow[_i - 1][_j - 1] == 7)) {
								Plansza.tablicaPionkow[_i + 2][_j - 2] = 5;
								iBicie = _i + 1;
								jBicie = _j - 1;
							}
						} else if (_i == 1 || _i == 0) {
							if (Plansza.tablicaPionkow[_i + 2][_j - 2] == 0 && (Plansza.tablicaPionkow[_i + 1][_j - 1] == 1 || Plansza.tablicaPionkow[_i + 1][_j - 1] == 7)) {
								Plansza.tablicaPionkow[_i + 2][_j - 2] = 5;
								iBicie = _i + 1;
								jBicie = _j - 1;
							}
						} else if (_i == 6 || _i == 7) {
							if (Plansza.tablicaPionkow[_i - 2][_j - 2] == 0 && (Plansza.tablicaPionkow[_i - 1][_j - 1] == 1 || Plansza.tablicaPionkow[_i - 1][_j - 1] == 7)) {
								Plansza.tablicaPionkow[_i - 2][_j - 2] = 5;
								iBicie = _i - 1;
								jBicie = _j - 1;
							}
						}
				}
				// *****************************************************
				if (Plansza.tablicaPionkow[_i][_j] == 3) {
					iBicie = -2;
					jBicie = -2;
					if (_i != 0 && _i != 7) {
						if (Plansza.tablicaPionkow[_i - 1][_j + 1] == 0) {
							Plansza.tablicaPionkow[_i - 1][_j + 1] = 5;
						}
						if (Plansza.tablicaPionkow[_i + 1][_j + 1] == 0) {
							Plansza.tablicaPionkow[_i + 1][_j + 1] = 5;
						}
					} else if (_i == 0) {
						if (Plansza.tablicaPionkow[_i + 1][_j + 1] == 0)
							Plansza.tablicaPionkow[_i + 1][_j + 1] = 5;
					} else if (_i == 7) {
						if (Plansza.tablicaPionkow[_i - 1][_j + 1] == 0)
							Plansza.tablicaPionkow[_i - 1][_j + 1] = 5;
					}
					// *****************************************************
					if (_j != 6) {
						if (_i != 0 && _i != 1 && _i != 6 && _i != 7) {

							if (Plansza.tablicaPionkow[_i - 2][_j + 2] == 0 && (Plansza.tablicaPionkow[_i - 1][_j + 1] == 2 || Plansza.tablicaPionkow[_i - 1][_j + 1] == 6)) {
								Plansza.tablicaPionkow[_i - 2][_j + 2] = 5;
								iBicie = _i - 1;
								jBicie = _j + 1;
							}
							if (Plansza.tablicaPionkow[_i + 2][_j + 2] == 0 && (Plansza.tablicaPionkow[_i + 1][_j + 1] == 2 || Plansza.tablicaPionkow[_i + 1][_j + 1] == 6)) {
								Plansza.tablicaPionkow[_i + 2][_j + 2] = 5;
								iBicie = _i + 1;
								jBicie = _j + 1;
							}
						} else if (_i == 1 || _i == 0) {
							if (Plansza.tablicaPionkow[_i + 2][_j + 2] == 0 && (Plansza.tablicaPionkow[_i + 1][_j + 1] == 2 || Plansza.tablicaPionkow[_i + 1][_j + 1] == 6)) {
								Plansza.tablicaPionkow[_i + 2][_j + 2] = 5;
								iBicie = _i + 1;
								jBicie = _j + 1;
							}
						} else if (_i == 6 || _i == 7) {
							if (Plansza.tablicaPionkow[_i - 2][_j + 2] == 0 && (Plansza.tablicaPionkow[_i - 1][_j + 1] == 2 || Plansza.tablicaPionkow[_i - 1][_j + 1] == 6)) {
								Plansza.tablicaPionkow[_i - 2][_j + 2] = 5;
								iBicie = _i - 1;
								jBicie = _j + 1;
							}
						}
					}
				}
			}
		}
		// *****************************************************
		else {
			for (int j = 0; j < 8; j++)
				for (int i = 0; i < 8; i++) {
					if (Plansza.tablicaPionkow[i][j] == 4) {
						Plansza.tablicaPionkow[i][j] = 0;
						Plansza.tablicaPionkow[_i][_j] = 2;
					}
					if (Plansza.tablicaPionkow[i][j] == 3) {
						Plansza.tablicaPionkow[i][j] = 0;
						Plansza.tablicaPionkow[_i][_j] = 1;
					}
					if (Plansza.tablicaPionkow[i][j] == 8) {
						Plansza.tablicaPionkow[i][j] = 0;
						Plansza.tablicaPionkow[_i][_j] = 6;
					}
					if (Plansza.tablicaPionkow[i][j] == 9) {
						Plansza.tablicaPionkow[i][j] = 0;
						Plansza.tablicaPionkow[_i][_j] = 7;
					}
					if (Plansza.tablicaPionkow[i][j] == 5)
						Plansza.tablicaPionkow[i][j] = 0;
				}
			if ((_i == iBicie + 1 || _i == iBicie - 1) && (_j == jBicie - 1 || _j == jBicie + 1)) {
				Plansza.tablicaPionkow[iBicie][jBicie] = 0;
				iBicie = -2;
				jBicie = -2;
			}
			// *********** KROLOWKA B /*
			if (_j == 0 && Plansza.tablicaPionkow[_i][_j] == 2) {
				Plansza.tablicaPionkow[_i][_j] = 6;
			}
			if (_j == 7 && Plansza.tablicaPionkow[_i][_j] == 1) {
				Plansza.tablicaPionkow[_i][_j] = 7;
			}
			// ********** KROLOWKA B*/
			if (Plansza.multi) {
				try {
					Plansza.oos.writeObject(Plansza.tablicaPionkow);
					Plansza.oos.flush();
					Plansza.oos.reset();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (Plansza.gra) {
			int b = 0;
			int c = 0;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (Plansza.tablicaPionkow[i][j] == 1 || Plansza.tablicaPionkow[i][j] == 3 || Plansza.tablicaPionkow[i][j] == 7 || Plansza.tablicaPionkow[i][j] == 9)
						c++;
					if (Plansza.tablicaPionkow[i][j] == 2 || Plansza.tablicaPionkow[i][j] == 4 || Plansza.tablicaPionkow[i][j] == 6 || Plansza.tablicaPionkow[i][j] == 8)
						b++;
				}
			}
			if (b == 0 || c == 0)
				JOptionPane.showMessageDialog(null, "Koniec gry!");
		}
		parent.repaint();
	}

	/**
	 * Metoda przes³oniêta pochodz¹ca z interfejsu {@link MouseListener} -
	 * niewykorzystywana. Jej obecnoœæ jest obowi¹zkowa w celu poprawnego
	 * zaimplementowania np. metody {@link Kafelek#mouseClicked(MouseEvent)}.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Metoda przes³oniêta pochodz¹ca z interfejsu {@link MouseListener} -
	 * niewykorzystywana. Jej obecnoœæ jest obowi¹zkowa w celu poprawnego
	 * zaimplementowania np. metody {@link Kafelek#mouseClicked(MouseEvent)}.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * Metoda przes³oniêta pochodz¹ca z interfejsu {@link MouseListener} -
	 * niewykorzystywana. Jej obecnoœæ jest obowi¹zkowa w celu poprawnego
	 * zaimplementowania np. metody {@link Kafelek#mouseClicked(MouseEvent)}.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
