package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;

    public WriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            this.writer = new PrintWriter(output, true);
        } catch(IOException e) {
            System.out.println("Error getting output stream: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void run(){
        System.out.println("Initializing WriteThread: " + super.getName());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String userName = "";

        userName = readLine(br, userName);
        client.setUserName(userName);
        writer.println(userName);

        String text = "";

        do {
            text = readLine(br, text);
            writer.println(text);
        } while(!text.equals("bye"));

        try {
            socket.close();
        } catch(IOException e) {
            System.out.println("Error writing to server: " + e.getMessage());
        }

    }

    private String readLine(BufferedReader br, String text) {
        try {
            text = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
