import java.io.*;
import java.net.Socket;

/**
 * Created by user_name on 21/05/2017.
 */
public class Main {
    private static  String IPAdress;
    private static int port;
    public static void main(String[] args){
        Program.run(createSocket());
    }

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
