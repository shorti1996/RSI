package lab01;

import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

/**
 * Klient RPC
 */
public class RPCklient {
	private static Scanner skan;

	public static void main(String[] args) {
		skan = new Scanner(System.in);
		try {
			//XmlRpcClient srv = new XmlRpcClient ("http://localhost:888");
			XmlRpcClient srv = new XmlRpcClient ("http://192.168.43.51:10001");
			
			do {
				System.out.println("Podaj metode");
				int x = skan.nextInt();
				switch(x) {
					case 1:
						zad1(srv);
						break;
					case 2:
						zad2(srv);
						break;
					case 3:
						zad3(srv);
						break;
					default:
						break;
				}
			} while (true);
			
			
			
		} catch (Exception ex) {
			System.err.println ("Klient XML-RPC: " + ex);
		}
	}

    /**
     * Metoda wywyolujaca asynchronicznie znajdowanie liczby Pi na serwerze
     * @param srv zadany serwer
     */
	public static void zad1(XmlRpcClient srv) {
		// zad 1 liczba PI
		AC cb = new AC();
		Vector <Integer> params = new Vector <Integer> ();
		System.out.print("Podaj l. powtrzen:");
		params.addElement (new Integer (skan.nextInt()));
		System.out.print("Podaj opoznienie");
		params.addElement (new Integer (skan.nextInt()));
		
		srv.executeAsync("mojserwer.picalc", params, cb); 
	}

    /**
     * Metoda wywyolujaca trojkat na serwerze
     * @param srv zadany serwer
     * @throws XmlRpcException
     * @throws IOException
     */
	public static void zad2(XmlRpcClient srv) throws XmlRpcException, IOException {
		// zad 2 trojkaty
					Vector <Integer> params2 = new Vector <Integer> ();
					System.out.println("Podaj trzy boki:");
					params2.addElement (new Integer (skan.nextInt()));
					params2.addElement (new Integer (skan.nextInt()));
					params2.addElement (new Integer (skan.nextInt()));
					Object result2 = srv.execute ("mojserwer.trojkat", params2);
					System.out.println ("Zadanie 2 - " + (String)result2);
	}

    /**
     * Metoda wywyolujaca dzielenie na serwerze
     * @param srv zadany serwer
     * @throws XmlRpcException
     * @throws IOException
     */
	public static void zad3(XmlRpcClient srv) throws XmlRpcException, IOException {
		// zad 3 
		Vector <Double> params3 = new Vector <Double> ();
		System.out.println ("Podaj dwie liczby rzeczywiste: ");
		params3.addElement (skan.nextDouble());
		params3.addElement (skan.nextDouble());
		Object result3 = srv.execute ("mojserwer.division", params3);
		System.out.println ("Zadanie 3 - " + (Double)result3);
	}
}
