package lab01;

import org.apache.xmlrpc.WebServer;

public class RpcServer {

    public static void main(String[] args) {
        try {
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

    public Integer echo(int x, int y) {
        return x + y;
    }

    public int asy(int x) {
        System.out.println("... wywołano asy - odliczam");
        try {
            Thread.sleep(x);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            Thread.currentThread().interrupt();
        }
        System.out.println("... asy - koniec odliczania");
        return (123);
    }

    public double addition(double x, double y) {
        return x + y;
    }

    public double substraction(double x, double y) {
        return x - y;
    }

    public double multiplication(double x, double y) {
        return x * y;
    }

    public double division(double x, double y) {
        return x / y;
    }

    public String differentTypes(String x, int y) {
        String format = "First argument is of type: %s, hashCode: %d, value: %s\n" +
                "Second argument is an int, value: %d";
        int timeLeft = 2000;
        int timeStep = 500;
        System.out.println("Long calculations");
        try {
            while (timeLeft > 0) {
                Thread.sleep(timeStep);
                timeLeft -= timeStep;
                System.out.println("timeLeft = " + timeLeft);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            Thread.currentThread().interrupt();
        }
        String message = String.format(format,
                x.getClass(), x.hashCode(), x,
                y);
        System.out.println("differentTypes finished");
        return message;
    }

    public String show() {
        return "Stub!";
    }
}
