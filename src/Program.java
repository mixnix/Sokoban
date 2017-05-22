import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by user_name on 21/05/2017.
 */
public class Program {
    private static MenuWindow menuWindow;

    public static void run(Socket socket){
        if(socket!=null){
            getSettings(socket);
            getLevel(socket);
            Constants.loadConstants();
        } else{
            System.out.println("Nie udalo sie polaczyc z serwerem");
            Object[] options={"Tak","Nie"};
            switch (JOptionPane.showOptionDialog(null,"Nie udalo się połączyć z serwerem, kontynuuować offline?",
                    "Brak połączenia z serwerem",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[1])){
                case JOptionPane.YES_OPTION:
                    break;
                case JOptionPane.NO_OPTION:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
        menuWindow = new MenuWindow(socket);
        menuWindow.setVisible(true);
    }

    public static void getSettings(Socket socket){
        try{
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("GET_SETTINGS");
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String settings = br.readLine();
            PrintWriter out = new PrintWriter("Config\\config.xml");
            out.println(settings);
            out.close();
        }
        catch (IOException e){
            System.out.println("Błąd metody get Settings "+e);
        }

    }

    public static void getLevel(Socket socket){
        try{

            //zniszczenie czegokolwiek co zostalo w buforze
            socket.getInputStream().skip(socket.getInputStream().available());
            OutputStream os = socket.getOutputStream();
            os.flush();
            PrintWriter pw = new PrintWriter(os, true);
            pw.flush();
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            pw.println("GET_LEVEL:1");
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String firstLevelXml = br.readLine();
            PrintWriter out = new PrintWriter("Config\\level1.xml");
            out.println(firstLevelXml);
            out.close();
        }
        catch (IOException e){
            System.out.println("Błąd metody get level"+e);
        }
    }

    public static void createMenu(Socket socket){
        SokobanExample.main(new String[0]);
    }
}
