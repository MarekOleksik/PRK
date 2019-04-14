package warcaby.klient;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Klasa Kafelek zawiera podstawowe zmienne oraz metody obs�ugi ruchu pionk�w po
 * szachownicy oraz okre�la r�wnie� bicia. Klasa posiada zmienne oraz metody
 * odpowiedzialne za okre�lenie pojedynczego kafelka wyst�puj�cego na
 * szachownicy, dzi�ki temu mo�na �atwo okre�li� mo�liwe ruchy dla pionka oraz
 * mo�liwo�ci bicia. Ponadto w klasie zdefiniowano wyst�pienie kr�l�wek zgodnie
 * z zasadami gry - warcaby. Zdefiniowano r�wnie� dzia�anie kafelka
 * informacyjnego, kt�ry pokazuje wsp�rz�dne kursora u�ytkownika wzgl�dem
 * szachownicy - kafelek znajduje si� w prawym dolnym rogu g��wnego okna
 * aplikacji. Klasa Kafelek dziedziczy po klasie {@link javax.swing.JComponent}
 * celem stworzenia obiektu kafelka informacyjnego. Ponadto rozszerzona jest ona
 * poprzez interfejs {@link java.awt.event.MouseListener} celem zdefiniowania
 * akcji mysz� - czyli podstawowego kontrolera rozgrywki - poprzez klikanie
 * mysz� na odpowiednie pionki mo�emy je przemieszcza� oraz wykonywa� bicia.
 * 
 * @author Bart�omiej Osak, Tomasz Pasternak
 * @see javax.swing.JComponent
 * @see java.awt.event.MouseListener
 */
public class Kafelek extends JComponent implements MouseListener {

	/** Zmienna okre�laj�ca unikalny numer w celu serializacji. */
	private static final long serialVersionUID = 1L;
	/** Zmienna okre�laj�ca wsp�rz�dn� x pojedynczego kafelka szachownicy. */
	protected Integer x;
	/** Zmienna okre�laj�ca wsp�rz�dn� y pojedynczego kafelka szachownicy. */
	protected Integer y;
	/**
	 * Zmienna okre�laj�ca wymiar - szeroko�� pojedynczego kafelka szachownicy.
	 */
	protected Integer width = 62;
	/**
	 * Zmienna okre�laj�ca wymiar - wysoko�� pojedynczego kafelka szachownicy.
	 */
	protected Integer height = 62;
	/**
	 * Zmienna okre�laj�ca wsp�rz�dn� x klikni�tego przez u�ytkownika
	 * pojedynczego kafelka szachownicy.
	 */
	protected int _i;
	/**
	 * Zmienna okre�laj�ca wsp�rz�dn� y klikni�tego przez u�ytkownika
	 * pojedynczego kafelka szachownicy.
	 */
	protected int _j;
	/**
	 * Obiekt klasy StringBuilder maj�cy na celu wy�wietlanie aktualnych
	 * wsp�rz�dnych kursora myszy na szachownicy na kafelku informacyjnym.
	 * 
	 * @see StringBuilder
	 */
	protected StringBuilder nazwa = new StringBuilder();
	/**
	 * Zmienna typu String inicjalizuj�ca pocz�tkow� warto�� wy�wietlan� przez
	 * kafelek informacyjny.
	 */
	protected static String tmp = "A1";
	/** Zmienna JFrame odwo�uj�ca si� do okna g��wnej aplikacji. */
	protected static JFrame parent;
	/** Wsp�rz�dna x kafelka, na kt�rym mo�e doj�� do bicia pionka. */
	protected static int iBicie;
	/** Wsp�rz�dna y kafelka, na kt�rym mo�e doj�� do bicia pionka. */
	protected static int jBicie;

	/**
	 * Konstruktor klasy Kafelek inicjalizuje zmienne typu JFrame - odwo�anie do
	 * okna g��wnego aplikacji, zmienne okre�laj�ce wsp�rz�dne. Ponadto
	 * inicjalizuje interfejs {@link java.awt.event.MouseListener} poprzez
	 * metod� {@link java.awt.Component#addMouseListener}. Ponadto wywo�ywana
	 * jest metoda ustawNazwe, kt�ra ustawia napis aktualnej pozycji kursora
	 * myszy w kafelku informacyjnym.
	 * 
	 * @param f
	 *            - parametr JFrame - g��wne okno aplikacji
	 * @param i
	 *            - wsp�rz�dna x napisu w kafelku informacyjnym
	 * @param j
	 *            - wsp�rz�dna y napisu w kafelku informacyjnym
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
	 * Metoda ustawNazwe ma za zadanie poprawne wy�wietlanie informacji na
	 * kafelku informacyjnym. Przyjmuje dwa parametry wywo�ania. W zale�no�ci od
	 * wsp�rz�dnych informacja jest wy�wietlana poprzez dodanie do zmiennej
	 * StringBuilder nowych tre�ci poprzez metod� append().
	 * 
	 * @param i
	 *            - wsp�rz�dna x pojedynczego kafelka na szachownicy.
	 * @param j
	 *            - wsp�rz�dna y pojedynczego kafelka na szachownicy.
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
	 * Przeci��ona metoda toString(), kt�ra ma na celu prawid�owe wy�wietlanie
	 * informacji na kafelku informacyjnym.
	 */
	@Override
	public String toString() {
		return nazwa + "";
	}

	/**
	 * Metoda przes�oni�ta pochodz�ca z interfejsu {@link MouseListener}.
	 * Okre�la ona zachowanie programu podczas poruszania si� kursorem myszy po
	 * oknie aplikacji, czyli gdy kursor myszy naje�d�a na dany komponent - w
	 * naszym przypadku komponentem jest okno g��wne aplikacji - w szczeg�lno�ci
	 * szachownica. W naszym przypadku wykorzystywana jest do aktualizacji
	 * wsp�rz�dnych wy�wietlanych na kafelku informacyjnym.
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
	 * Metoda przes�oni�ta pochodz�ca z interfejsu {@link MouseListener}.
	 * Okre�la ona zachowanie programu podczas, gdy u�ytkownik kliknie myszk� na
	 * komponent. Funkcja wykorzystywana do opracowania logiki ruchu pionk�w
	 * oraz bi� pionk�w. Wa�ne oznaczenia w celu zrozumienia algorytmu ruchu
	 * oraz bi� pion�w: 0 - oznaczenie pola pustego 1 - oznaczenie pionka
	 * czarnego 2 - oznaczenie pionka bia�ego 3 - oznaczenie wybranego pionka
	 * czarnego (wybrany, czyli klikni�ty myszk� przez gracza) 4 - oznaczenie
	 * wybranego pionka bia�ego (wybrany, czyli klikni�ty myszk� przez gracza) 5
	 * - oznaczenie pola pustego, na kt�re mo�na wykona� ruch (przesun�� pionka)
	 * 6 - oznaczenie bia�ej kr�l�wki 7 - oznaczenie czarnej kr�l�wki 8 -
	 * oznaczenie wybranej bia�ej kr�l�wki 9 - oznaczenie wybranej czarnej
	 * kr�l�wki Takie rozgraniczenie pozwala na do�� czytelne odczytanie
	 * algorytmu ruchu oraz bi�. Ponadto w metodzie wysy�any jest obiekt tablicy
	 * pionk�w poprzez sie� do drugiego gracza. W metodzie zdefiniowano r�wnie�
	 * warunek zako�czenia gry - zako�czenie gry sygnalizowane jest specjalnym
	 * komunikatem wy�wietlanym w oknie dialogowym.
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
	 * Metoda przes�oni�ta pochodz�ca z interfejsu {@link MouseListener} -
	 * niewykorzystywana. Jej obecno�� jest obowi�zkowa w celu poprawnego
	 * zaimplementowania np. metody {@link Kafelek#mouseClicked(MouseEvent)}.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Metoda przes�oni�ta pochodz�ca z interfejsu {@link MouseListener} -
	 * niewykorzystywana. Jej obecno�� jest obowi�zkowa w celu poprawnego
	 * zaimplementowania np. metody {@link Kafelek#mouseClicked(MouseEvent)}.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * Metoda przes�oni�ta pochodz�ca z interfejsu {@link MouseListener} -
	 * niewykorzystywana. Jej obecno�� jest obowi�zkowa w celu poprawnego
	 * zaimplementowania np. metody {@link Kafelek#mouseClicked(MouseEvent)}.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
