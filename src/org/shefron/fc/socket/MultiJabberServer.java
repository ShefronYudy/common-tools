package org.shefron.fc.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class JabberOneServer extends Thread {

	private BufferedReader in = null;
	private PrintWriter out = null;
	private Socket socket = null;

	public JabberOneServer(Socket socket) {
		this.socket = socket;
		try {
			in = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));

			out = new PrintWriter(new OutputStreamWriter(socket
					.getOutputStream()), true);

			this.start();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		String readStr = null;
		try {
			while (true) {
				try {
					readStr = in.readLine();
					System.out.println("server receive :" + readStr);
					out.println("server response :" + readStr);
					if ("END".equals(readStr.toUpperCase())) {
						out.println("END");
						break;
					}
					Thread.sleep(100);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

public class MultiJabberServer {
	static final int MAXTHREAD = 30;
	static final int PORT = 8888;

	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(PORT);
			System.out.println("server start :" + server.getInetAddress());
			Socket socket = null;
			int maxThreads = 0;
			while (true) {
				try {
					socket = server.accept();
					new JabberOneServer(socket);
					System.out.println("start the server " + (maxThreads++)
							+ " :" + server.getInetAddress());
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("超过服务器最大服务数！！！");
					socket.close();
				}
				if (maxThreads > MAXTHREAD) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}