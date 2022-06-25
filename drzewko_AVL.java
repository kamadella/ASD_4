
package com.company;
import java.util.Scanner;
import java.io.*;



//-------------------------------WEZEL----------------------------------
// Tworzenie Wezla
class Wezel {
    double liczba;
    int wysokosc;
    Wezel lewa;
    Wezel prawa;

    Wezel(double liczba) {
        this.liczba = liczba;
        this.wysokosc = 1;
    }
}



//----------------------------------------------------------------------------------------- =)
//--------------------------------DRZEWKO-------------------------------------------------- =)
// Tree class
class drzewko_AVL {
    Wezel wezel;


    //--------------------------------WYSOKOSC -----------------------------------------------
    //funkcja do zwracania wysokosci drzewa
    int wysokosc(Wezel wezel) {
        if (wezel == null) {
            return 0;
        } else return wezel.wysokosc;
    }

    //--------------------------------WYSOKOSC AKTUALIZACJA-----------------------------------------------
    void aktualizacja_wysokosci(Wezel wezel) {
        wezel.wysokosc = Math.max(wysokosc(wezel.lewa), wysokosc(wezel.prawa)) + 1;
    }


    //---------------------------------WSPOLCZYNNIK ROWNOWAGI-----------------------------------
    // wyliczam wspolczynnik rownowagi aka 0,1,-1
    int znajdowanie_wspolcznynnika(Wezel wezel) {
        if (wezel == null) {
            return 0;
        }
        return wysokosc(wezel.lewa) - wysokosc(wezel.prawa); //wysokosc lewego dziecka-prawego dziecka
    }



    //-------------------------------ZNAJDOWANIE MIN Z PRAWEJ------------------------------------------
    Wezel Minimum(Wezel wezel) {

        //idziemy tak dlugo az zejde do ostatniego po lewej
        while (wezel.lewa != null) {
            wezel = wezel.lewa;
        }

        return wezel;
    }


    //   X
    //    \
    //     Y
    //----------------------------------OBROT W LEWO----------------------------------------------
    Wezel obrot_w_lewo(Wezel stary) {

        Wezel nowy = stary.prawa;
        Wezel lewa_nowego = nowy.lewa;

        //stary korzen zostaje lewa stroną nowego korzenia
        nowy.lewa = stary;

        //a na prawo od starego korzenia bedzie teraz lewe dziecko nowego korzenia
        stary.prawa = lewa_nowego;


        aktualizacja_wysokosci(stary);
        aktualizacja_wysokosci(nowy);


        return nowy;
    }



    //      X
    //     /
    //    Y
    //-------------------------------OBROT W PRAWO---------------------------------------------
    Wezel obrot_w_prawo(Wezel stary) {
        Wezel nowy = stary.lewa;
        Wezel prawa_nowego = nowy.prawa;

        //stary korzen zostaje prawą stroną nowego korzenia
        nowy.prawa = stary;

        //a na lewo od starego korzenia bedzie teraz prawe dziecko nowego korzenia
        stary.lewa = prawa_nowego;

        aktualizacja_wysokosci(stary);
        aktualizacja_wysokosci(nowy);

        return nowy;

    }



    //---------------------------------BALANS -------------------------------------
    Wezel sprawdzanie_balansu(Wezel wezel){
        //Jeżeli brak równowagi
        if (znajdowanie_wspolcznynnika(wezel) == -2) {
            if (znajdowanie_wspolcznynnika(wezel.prawa) == -1) {    //RR
                wezel = obrot_w_lewo(wezel);                        //obrót w lewo
            }
            else if (znajdowanie_wspolcznynnika(wezel.prawa) == 1) {       //RL
                wezel.prawa = obrot_w_prawo(wezel.prawa);                 //obrót w prawo
                wezel = obrot_w_lewo(wezel);                               //obrót w lewo
            }
        }
        if (znajdowanie_wspolcznynnika(wezel) == 2) {
            if (znajdowanie_wspolcznynnika(wezel.lewa) == 1) {       //LL
                wezel = obrot_w_prawo(wezel);                         //obrót w prawo
            }
            else if (znajdowanie_wspolcznynnika(wezel.lewa) == -1) {    //LR
                wezel.lewa = obrot_w_lewo(wezel.lewa);                 //obrót w lewo
                wezel = obrot_w_prawo(wezel);                           //obrót w prawo
            }
        }

        //jezeli z balansem wszystko ok
        return wezel;
    }



    //--------------------------------------------DODAWANIE-----------------------------------------------
    //nowy wezel dodajemy zawsze jako lisc o wadze 0
    //zaczynamy od korzenia
    Wezel dodaj_Nowy_Wezel(Wezel wezel, double liczba) {

        //Szukam miejsca
        //jeżeli znalezlismy w koncu miejsce to dodajemu nowy wezel
        if (wezel == null) {
            return (new Wezel(liczba));
        }
        else if (liczba < wezel.liczba) { // idziemy do lewego wezla i powtarzamy procedure
            wezel.lewa = dodaj_Nowy_Wezel(wezel.lewa, liczba);
        }
        else if (liczba > wezel.liczba) { // idziemy do prawego wezla i powtarzamy procedure
            wezel.prawa = dodaj_Nowy_Wezel(wezel.prawa, liczba);
        }

        aktualizacja_wysokosci(wezel);
        wezel = sprawdzanie_balansu(wezel);

        //jezeli z balansem wszystko ok
        return wezel;
    }






    //------------------------------------USUWANIE--------------------------------------------
    // usuwanie wezla
    Wezel Usun_Wezel(Wezel wezel, double liczba) {

        //Szukanie liczby do usuniecia
        if (wezel == null) {
            return null;
        }
        //liczba do usuniecia jest mniejsza
        else if (liczba < wezel.liczba) {
            wezel.lewa = Usun_Wezel(wezel.lewa, liczba);
        }
        //liczba do usuniecia jest wieksza
        else if (liczba > wezel.liczba) {
            wezel.prawa = Usun_Wezel(wezel.prawa, liczba);
        }


        //znalezlismy liczbe
        else {
            // 0 dzieci
            if ((wezel.lewa == null) && (wezel.prawa == null)) {
                wezel = null;
            }
            // 1 dziecko
            else if ((wezel.lewa == null) || (wezel.prawa == null)) {
                Wezel pomocniczy;

                //brak lewego dziecka
                if (wezel.lewa == null) {
                    pomocniczy = wezel.prawa; //to pomocniczy staje sie prawym
                }
                //brak prawego dziecka
                else {
                    pomocniczy = wezel.lewa;
                }

                // wezel przjmuje wartosc dziecka
                wezel = pomocniczy;
            }
            // 2 dzieci
            else {
                //znajduje najmniejszy element w jego prawym poddrzewie
                Wezel pomocniczy = Minimum(wezel.prawa);

                //liczba w wezle staje sie tym znalezionym min
                wezel.liczba = pomocniczy.liczba;

                //nowym prawa strona staje sie czesc bez tego zdublowanego min
                wezel.prawa = Usun_Wezel(wezel.prawa, pomocniczy.liczba);

            }
        }

        //jezeli usunelismy ostatni to wracamy
        if (wezel == null) {
            return null;
        }

        aktualizacja_wysokosci(wezel);
        wezel = sprawdzanie_balansu(wezel);

        //jezeli z balansem wszystko ok
        return wezel;
    }




//-------------------------------------NOWE SZUKANIE--------------------------------------
    private String Szukanie_liczby_nowe(Wezel wezel, double liczba) {

        if(wezel == null ){
            return "NIE";
        }

        else if (liczba > wezel.liczba){ //kiedy wiekszy to na prawo
            return Szukanie_liczby_nowe(wezel.prawa, liczba);
        }

        else if (liczba < wezel.liczba){ //kiedy mniejszy to idziemy na lewo
            return Szukanie_liczby_nowe(wezel.lewa, liczba);
        }

        //jezeli znalezlismy
        return "TAK";
    }




//-------------------------------------WYSTAPIENIA ULEPSZONE-------------------------------------
    private int Liczenie_wystapien_lepsiejsze(Wezel wezel, double liczba) {
        int suma_wystapien = 0;

        if (wezel == null) {
            return 0; //zwracam 0 jezeli zadnej takiej liczby nie bylo
        }

        else if(liczba < Math.floor(wezel.liczba)){
             return Liczenie_wystapien_lepsiejsze(wezel.lewa, liczba);
        }
        else if(liczba > Math.floor(wezel.liczba)){
             return Liczenie_wystapien_lepsiejsze(wezel.prawa, liczba);
        }

        else{ //kiedy jest równe
            suma_wystapien++;
            suma_wystapien += ( Liczenie_wystapien_lepsiejsze(wezel.lewa, liczba) + Liczenie_wystapien_lepsiejsze(wezel.prawa, liczba));
        }

        return suma_wystapien;
    }






// -----------------------------------POKAZ DRZEWKO-------------------------------------------
    private void wyswietl_drzewo(Wezel wezel, String tabulacja, int ktora) {

        if (wezel != null) {
            int wspolczynnik = znajdowanie_wspolcznynnika(wezel);
            //najpierw nie ma żadnej tabulacji
            System.out.print(tabulacja);


            if (ktora == 1) {
                System.out.print("L= ");
                tabulacja += "     ";
            }

            if (ktora == 2) {
                System.out.print("P= ");
                tabulacja += "     ";
            }


            System.out.println(wezel.liczba +" ("+ wspolczynnik + ")"); //na koncu tabulacji dopisujemy wartosc i wspolczynnik

            wyswietl_drzewo(wezel.prawa, tabulacja, 2); //najpierw robie cale prawa strone
            wyswietl_drzewo(wezel.lewa, tabulacja, 1); //potem przechodze do lewych dzieci

        }

    }



// ---------------------------------------- MAIN ------------------------------------------
    public static void main(String[] args) {
        drzewko_AVL drzewko = new drzewko_AVL();

        Scanner odczyt_z_klawiatury = new Scanner(System.in);

        int wybor;
        double liczba_uzytkowanika;
        while(true) {
            System.out.println("\nWybierz co chcesz zrobic:");
            System.out.println("1. Wczytaj plik");
            System.out.println("2. Dodaj liczbe");
            System.out.println("3. Usun liczbe");
            System.out.println("4. Znajdz liczbe");
            System.out.println("5. Znajdz ilosc wystapien");
            System.out.println("6. Wyświetl drzewo");
            System.out.println("0. EXIT");

            System.out.println("Wprowadz liczbe: ");
            wybor = odczyt_z_klawiatury.nextInt();

            switch (wybor) {
                case 1:
                    try {
                        File pliczek_wejsciowy = new File("in1.txt");
                        PrintWriter zapis_do_pliku = new PrintWriter("out.txt");
                        Scanner odczyt_z_pliku = new Scanner(pliczek_wejsciowy);
                        int ilosc_polecen = odczyt_z_pliku.nextInt();


                        char co_zrobic;
                        double liczba_todo;

                        for (int i = 0; i < ilosc_polecen; i++) {
                            co_zrobic = odczyt_z_pliku.next().charAt(0);
                            liczba_todo = odczyt_z_pliku.nextDouble();

                            //skoro juz pobralismy dane to sprawdzamy co trzeba z nimi zrobic

                            if (co_zrobic == 'W') {
                                drzewko.wezel = drzewko.dodaj_Nowy_Wezel(drzewko.wezel, liczba_todo);
                            } else if (co_zrobic == 'U') {
                                drzewko.wezel = drzewko.Usun_Wezel(drzewko.wezel, liczba_todo);
                            } else if (co_zrobic == 'S') {
                                zapis_do_pliku.println(drzewko.Szukanie_liczby_nowe(drzewko.wezel, liczba_todo));
                            } else if (co_zrobic == 'L') {
                                zapis_do_pliku.println(drzewko.Liczenie_wystapien_lepsiejsze(drzewko.wezel, liczba_todo));
                            }


                        }
                        odczyt_z_pliku.close();
                        zapis_do_pliku.close();
                    } catch (FileNotFoundException x) {
                        System.out.println("Podano zły pliczek - nie widzę go :(");
                        System.exit(-1);
                    }
                    break;

                case 2:
                    System.out.println("Wprowadz liczbe: ");
                    liczba_uzytkowanika = odczyt_z_klawiatury.nextDouble();
                    drzewko.wezel = drzewko.dodaj_Nowy_Wezel(drzewko.wezel, liczba_uzytkowanika);
                    break;
                case 3:
                    System.out.println("Wprowadz liczbe: ");
                    liczba_uzytkowanika = odczyt_z_klawiatury.nextDouble();
                    drzewko.wezel = drzewko.Usun_Wezel(drzewko.wezel, liczba_uzytkowanika);
                    break;
                case 4:
                    System.out.println("Wprowadz liczbe: ");
                    liczba_uzytkowanika = odczyt_z_klawiatury.nextDouble();
                    drzewko.Szukanie_liczby_nowe(drzewko.wezel, liczba_uzytkowanika);
                    break;
                case 5:
                    System.out.println("Wprowadz liczbe: ");
                    liczba_uzytkowanika = odczyt_z_klawiatury.nextDouble();
                    System.out.println(drzewko.Liczenie_wystapien_lepsiejsze(drzewko.wezel, liczba_uzytkowanika));
                    break;
                case 6:
                    drzewko.wyswietl_drzewo(drzewko.wezel, "", 1);
                    break;
                case 0: System.exit(0);
                default: System.out.println("Nie ze mna te numery!");
            }

        }
    }
}
