package org.shefron.fc.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class JabberClientThread extends Thread {
	private Socket socket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;

	public JabberClientThread(Socket socket) {
		this.socket = socket;
		try {
			in = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));

			out = new PrintWriter(new OutputStreamWriter(socket
					.getOutputStream()), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.start();
	}

	public void run() {
		try {
			out.println("client send : hello");
			while (true) {
				String str = in.readLine();
				System.out.println("client receive:" + str);
				out.println("END");
				if ("END".endsWith(str)) {
					Thread.sleep(100);
					break;
				}
			}

		} catch (Exception e) {

		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

public class MultiJabberClient {

	public static void main(String[] args) {
		int count = 1;
		Socket socket = null;
		while (count < MultiJabberServer.MAXTHREAD) {
			try {
				InetAddress addr = InetAddress.getByName(null);
				socket = new Socket(addr, MultiJabberServer.PORT);
				System.out.println("client connect :" + count);
				new JabberClientThread(socket);

				Thread.sleep(1000);
				count++;
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}