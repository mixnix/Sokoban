import java.net.Socket;

/**
 * Created by user_name on 21/05/2017.
 */
public class Program {
    public static void run(Socket socket){
        if(socket!=null){
            getSettings(socket);

        }
        SokobanExample.main(new String[0]);
    }
}
