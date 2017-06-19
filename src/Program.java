import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by user_name on 21/05/2017.
 */
public class Program {
    /**
     * okno menu
     */
    private static MenuWindow menuWindow;

    /**
     * metoda inicjalizująca zmienne i tworzaca okno
     * @param socket
     */
    public static void run(Socket socket){
        if(socket!=null){
            getSettings(socket);
            getLevels(socket);
            getHelp(socket);
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
        Constants.loadConstants();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                menuWindow = new MenuWindow(socket);
                menuWindow.setVisible(true);
            }
        });
    }

    /**
     * metoda pobierajaca zmienne okreslajace wyglad menu
     * @param socket
     */
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

    /**
     * metoda pobierajaca zmienne okreslajace wyglad poziomu
     * @param socket
     */
    public static void getLevels(Socket socket){
        try{

            //zniszczenie czegokolwiek co zostalo w buforze
            socket.getInputStream().skip(socket.getInputStream().available());
            OutputStream os = socket.getOutputStream();
            os.flush();
            PrintWriter pw = new PrintWriter(os, true);
            pw.flush();
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));



            pw.println("GET_NUMBER_OF_LVLS");
            int numberOfLvls = Integer.parseInt(br.readLine());

            for(int n = 1; n <= numberOfLvls; n++){
                pw.println("GET_LEVEL:"+n);
                String levelXml = br.readLine();
                PrintWriter out = new PrintWriter("Config\\level"+n+".xml");
                out.println(levelXml);
                out.close();
            }

        }
        catch (IOException e){
            System.out.println("Błąd metody get level w klasie program"+e);
        }
    }

    public static void getHelp(Socket socket){
        try{
            //zniszczenie czegokolwiek co zostalo w buforze
            socket.getInputStream().skip(socket.getInputStream().available());
            OutputStream os = socket.getOutputStream();
            os.flush();
            PrintWriter pw = new PrintWriter(os, true);
            pw.flush();
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            pw.println("GET_HELP");
            String levelXml = br.readLine();
            PrintWriter out = new PrintWriter("Config\\help.xml");
            out.println(levelXml);
            out.close();
        }catch (IOException e){
            System.out.println("Błąd metody getHelp w klasie Program"+e);
        }
    }
}
