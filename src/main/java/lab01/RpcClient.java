package lab01;

import org.apache.xmlrpc.XmlRpcClient;
import org.apache.xmlrpc.XmlRpcException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * Klient RPC
 */
public class RpcClient {

    private static String SERVER_HANDLER_NAME = "mojserwer";
    private volatile boolean mReady;

    public static void main(String[] args) {
        try {
//            Zamiast xxx podaj port = 10000 + numer komputera w laboratorium
            XmlRpcClient srv = new XmlRpcClient("http://localhost:10001");
            Vector<Integer> params = new Vector<>();
            params.addElement(13);
            params.addElement(21);
            Object result = srv.execute("mojserwer.echo", params);
            int wynik = (Integer) result;
            System.out.println("wynik = " + wynik);

            lab01.AC cb = new lab01.AC();
            Vector<Integer> params2 = new Vector<>();
            params2.addElement(1000);
            srv.executeAsync("mojserwer.asy", params2, cb);
            System.out.println("Wywolano asynchronicznie");

        } catch (Exception exception) {
            System.err.println("Klient XML-RPC: " + exception);
        }
//        RpcClient rpcClient = new RpcClient();
//        rpcClient.zad2_loop();
    }

    private void zad2_loop() {
        System.out.println("Podaj adres serwera: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        "http://localhost:10001"
        XmlRpcClient srv = null;
        try {
            String serverAddress = br.readLine();
            srv = new XmlRpcClient(serverAddress);
            System.out.println(srv.execute(SERVER_HANDLER_NAME + ".show", new Vector()));
            mReady = true;
            while (mReady) {
                System.out.println("Podaj metode: ");
                String method = br.readLine();
                switch (method) {
                    case "show":
                        break;
                    case "differentTypes":
                        AC callback = new AC(this);
                        Vector<Object> params = new Vector<>();
                        System.out.println("Podaj napis: ");
                        params.add(br.readLine());
                        params.add(33);
                        System.out.println("Poczekaj chwile (async)...");
                        srv.executeAsync(SERVER_HANDLER_NAME + "." + method, params, callback);
                        mReady = false;
                        while (!mReady);
                        break;
                    case "substraction":
                    case "addition":
                    case "multiplication":
                    case "division":
                        execNumericBinaryMethod(br, srv, method);
                        break;
                    default:
                        System.out.println("Nieprawidlowa metoda");
                }
            }
        } catch (IOException | XmlRpcException e) {
            e.printStackTrace();
        }
    }

    private static void execNumericBinaryMethod(BufferedReader br, XmlRpcClient srv, String method)
            throws IOException, XmlRpcException {
        System.out.println("Podaj x:");
        Float x = Float.parseFloat(br.readLine());
        System.out.println("Podaj y:");
        Float y = Float.parseFloat(br.readLine());
        Vector<Float> v = new Vector<>();
        v.add(x);
        v.add(y);
        Object res = srv.execute(SERVER_HANDLER_NAME + "." + method, v);
        System.out.println("result = " + res);
    }

    public void setReady(boolean newValue) {
        mReady = newValue;
    }
}
