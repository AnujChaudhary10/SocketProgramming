package client.server.programe;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Client implements Runnable {
	private final Socket s;
	private static List<String> clientName = new ArrayList<>();
	private String name;

	static {
		clientName.add("Client1");
		clientName.add("Client2");
	}

	public Client(String ip, int port, String name) throws UnknownHostException, IOException {
		s = new Socket(ip, port);
		this.name = name;
	}

	public static void main(String[] args) {
		clientName.forEach((name) -> {
			try {
				Client c = new Client("localhost", 9291, name);
				Thread t = new Thread(c);
				t.start();
				System.out.println(name + " started");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void run() {
		try {
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			int count = 0;
			while (count < 11) {
				dos.writeUTF(name + " count : " + count);
				count++;
			}
			dos.writeUTF("close");
			dos.close();
			this.s.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
