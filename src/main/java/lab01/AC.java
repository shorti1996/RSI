package lab01;

import org.apache.xmlrpc.AsyncCallback;

import java.net.URL;

public class AC implements AsyncCallback {

    private RpcClient rpcClient;

    public AC(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    @Override
    public void handleResult(Object o, URL url, String s) {
        System.out.println("o = " + o);
        System.out.println("url = " + url);
        System.out.println("s = " + s);
        rpcClient.setReady(true);
    }

    @Override
    public void handleError(Exception e, URL url, String s) {
        System.out.println("e = " + e);
        System.out.println("url = " + url);
        System.out.println("s = " + s);
        rpcClient.setReady(true);
    }
}
