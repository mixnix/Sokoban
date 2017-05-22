import java.io.*;
import java.net.Socket;

/**
 * Created by user_name on 21/05/2017.
 */
public class Main {
    /**
     * Adres ip na którym się łączymy z serwerem
     */
    private static  String IPAdress;
    /**
     * numer portu na któym się łączymy  z serwerem
     */
    private static int port;

    /**
     * metoda main, ktora odpala klase odpowiedzialna za dzialanie calego programu
     * @param args
     */
    public static void main(String[] args){
        Program.run(createSocket());
    }

    /**
     * metoda inicjalizujaca polaczenie z serwerem
     * @return
     */
    private static Socket createSocket(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("Config\\server.txt"));
            IPAdress = br.readLine();
            port = Integer.parseInt(br.readLine());
            Socket serverSocket = new Socket(IPAdress, port);
            OutputStream output = serverSocket.getOutputStream();
            PrintWriter outputWriter = new PrintWriter(output, true);
            outputWriter.println("CONNECT");
            InputStream input = serverSocket.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));
            if(br.readLine().contains("CONNECTED")){
                return serverSocket;
            } else{
                return null;
            }
        } catch (Exception e){
            System.out.println("Wystapil blad");
            System.out.println("error: "+e);
        }
        return null;
    }
}
