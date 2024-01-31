import java.net.*;
import  java.io.*;


class Server{

    ServerSocket  server;    //make a different server
    Socket  socket;
    BufferedReader br;  //for reading
    PrintWriter out;  //for printing or writing
    //constructor...
    public Server(){
        try{
            server = new ServerSocket(7777);   //7777  is a port number  i.e any number
            System.out.println("Server is ready to accept connection");
            System.out.println("waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        }
        catch(Exception e){
            e.getStackTrace();
        }
        
    }

    public void startReading(){
        //thread = read the data
        Runnable r1 = ()->{   //thread using lambada expretion
            System.out.println("Reader started...");

            try{
                while(true){
            
                    String msg = br.readLine();  //msg from client
                    System.out.println("Client : "+msg);
                    if(msg.equals("exit")){
                        System.out.println("Client terminated the chat");
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
        System.out.println("this is server...");
        new Server();
    }
}