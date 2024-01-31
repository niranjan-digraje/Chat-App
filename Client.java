import java.net.*;
import  java.io.*;

public class Client {
    Socket socket;
    BufferedReader br;  //for reading
    PrintWriter out;  //for printing or writing

    public Client(){
        try{
            System.out.println("sendding request to server ...");
            socket = new Socket("127.0.0.1",7777);  //ip address and port number of server
            System.out.println("connection done.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void startReading(){
        //thread = read the data
        Runnable r1 = ()->{   //thread using lambada expretion
            System.out.println("Reader started...");

            try{
                 while(true){
                    String msg = br.readLine();  //msg from client
                    System.out.println("Server : "+msg);
                    if(msg.equals("exit")){
                        System.out.println("Server terminated the chat");
                        socket.close();
                        break;
                    }
                }
            }catch(Exception e){
                //e.printStackTrace();
                System.out.println("Connection is closed");
            }
           
        };
        new Thread(r1).start();
    }

    public void startWriting(){
        //thread = data input from user and send to client
        Runnable r2 = ()->{
            System.out.println("Writer Started.....");
            try{
                while(!socket.isClosed()){
                    BufferedReader br1 =new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                }
            }catch(Exception e){
                    //e.printStackTrace();
                    System.out.println("Connection is closed");
            }
        };
          new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("this is client...");
        new Client();
    }
}
