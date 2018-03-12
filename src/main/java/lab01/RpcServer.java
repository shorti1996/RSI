package lab01;

import org.apache.xmlrpc.WebServer;

/**
 * Serwer RPC
 */
public class RpcServer {

    public static void main(String[] args) {
        try {
//            picalc(100, 1000);
            System.out.println("Startuje serwer XML-RPC...");
            // 10000+nr komputera w laboratorium
            int port = 10001;
            WebServer server = new WebServer(port);
            // ponizej tworzy się obiekt swojej klasy serwera
            // i uruchomia się go:
            server.addHandler("mojserwer", new RpcServer());
            server.start();
            System.out.println("Serwer wystartowal pomyslnie.");
            System.out.println("Nasluchuje na porcie: " + port);
            System.out.println("Aby zatrzymać serwer nacisnij crl+c");
        } catch (Exception exception) {
            System.err.println("Serwer XML-RPC: " + exception);
        }
    }

    /**
     * Sprawdza, jaki trojkat mozna zbudowac z odcinkow o podanych dlugosciach
     * @param x bok trojkata
     * @param y bok trojkata
     * @param z bok trojkata
     * @return Typ trojkata, jaki mozna zbudowac z podanych bokow
     */
    public String trojkat(int x, int y, int z) {
        return Trojkatownik.jakiTrojkat(x, y, z);
    }

    /**
     * Obliczanie liczby PI metoda monte carlo
     * @param liczbaProb ilosc losowan
     * @param czas czas opoznienia ogloszenia wyniku
     * @return przyblizenie liczby PI oraz jej roznica ze stala Math.PI
     */
    public String picalc(int liczbaProb, int czas) {
        try {
            Thread.sleep(czas);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            Thread.currentThread().interrupt();
        }
        double res = PiGuess.pi_calc(liczbaProb);
        double[] result = new double[2];
        result[0] = res;
        result[1] = Math.PI - res;
        return "Wynik: " + res + ", Roznica" + (Math.PI - res);
    }

    /**
     * Dzielenie dwoch liczb rzeczywistych
     * @param x dzielna
     * @param y dzielnik
     * @return iloraz
     */
    public double division(double x, double y) {
        return x / y;
    }

    /**
     * Pokazuje dostepne na serwerze metody
     * @return wiadomosc z dostepnymi metodami
     */
    public String show() {
        return "Lista metod:" +
                "\ntrojkat(int x, int y, int z, int czas): jaki trojkat mozna zbudowac z bokow o podanych dlugosciach, czas- opoznienie" +
                "\npiguess(int lprob, int opoznienie): obliczanie liczby pi metoda monte carlo, opoznienie w milisekundach" +
                "\ndifferentTypes(String x, int y): pokazuje hashCode stringu";
    }
}
