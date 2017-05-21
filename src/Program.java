import javax.swing.*;
import java.net.Socket;

/**
 * Created by user_name on 21/05/2017.
 */
public class Program {
    private static MenuWindow menuWindow;

    public static void run(Socket socket){
        if(socket!=null){
            getSettings(socket);
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

    }

    public static void createMenu(Socket socket){
        SokobanExample.main(new String[0]);
    }
}
