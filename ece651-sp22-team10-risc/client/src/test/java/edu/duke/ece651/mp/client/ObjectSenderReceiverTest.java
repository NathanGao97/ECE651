package edu.duke.ece651.mp.client;

import edu.duke.ece651.mp.common.Color;
import edu.duke.ece651.mp.common.Message;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ObjectSenderReceiverTest {

    static ServerSocket server;
    static int buff_size = 32;

    static Thread th;
    static ServerSocket serverSocket;


    private static void creat_echo_server() throws IOException {

        int portNumber = 4444;

        serverSocket = new ServerSocket(portNumber);

        // use to hold how many bytes received
        int status = -1;

        byte[] buffer = new byte[buff_size];

        // we will use server.close to close this thread
        while (true) {

            Socket clientSocket = serverSocket.accept();

            OutputStream out = clientSocket.getOutputStream();
            InputStream in = clientSocket.getInputStream();

            // recv and resp unitl the remote close connection

            while (true) {

                status = in.read(buffer);
                if (status == -1) {
                    break;
                }

                out.write(buffer, 0, status);
            }

            clientSocket.close();
        }
    }


    @BeforeAll
    public static void start_echo_server_thread() {
        th = new Thread() {
            @Override()
            public void run() {
                try {
                    creat_echo_server();
                } catch (Exception e) {
                    System.out.println("catch an exception");
                }
            }

        };

        // create the thread
        th.start();
    }



    @Test
    void sendReceiveObject() throws IOException, ClassNotFoundException {
        Socket sock = new Socket("localhost", 4444);
        ObjectSenderReceiver objectSenderReceiver = new ObjectSenderReceiver(sock.getInputStream(), sock.getOutputStream());
        objectSenderReceiver.sendObject(new Message("test"));
        Message mess = objectSenderReceiver.receiveObject();
        assertEquals(new Message("test"), mess);
    }

    @Test
    public void testSendRecv() throws IOException{
        try{
            FileOutputStream fos = new FileOutputStream("t.tmp");
            FileInputStream fis = new FileInputStream("t.tmp");
            ObjectOutputStream ois = new ObjectOutputStream(fos);
            ObjectInputStream iis = new ObjectInputStream(fis);
            ObjectSenderReceiver operator = new ObjectSenderReceiver(iis, ois);
            operator.sendObject("Text");
            operator.receiveObject();
        }
        catch (Exception e) {}
    }

}

