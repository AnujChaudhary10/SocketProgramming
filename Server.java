package client.server.programe;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	final private ServerSocket ss;

	public Server(int port) throws IOException {
		ss = new ServerSocket(port);
	}

	public static void main(String[] args) throws IOException {
		Server s = new Server(9291);
		s.communicateToClient();
	}

	public void communicateToClient() throws IOException {
		while(true){
		Socket so = ss.accept();
			ReadData r =  new ReadData(so);
			Thread t =  new Thread(r);
			t.start();
		}
	}
 }
class ReadData implements Runnable {
	final Socket so;

	public ReadData(Socket so) {
		this.so = so;
	}

	@Override
	public void run() {
		DataInputStream di = null;
		try {
			di = new DataInputStream(so.getInputStream());
			String str = "";
			while (!str.equals("close")) {
				str = di.readUTF();
				System.out.println("server reads :" + str);

			}
			di.close();
			so.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
