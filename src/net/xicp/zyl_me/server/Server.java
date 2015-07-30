package net.xicp.zyl_me.server;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import net.xicp.zyl_me.MainUI;
import net.xicp.zyl_me.util.ExternalTools;

public class Server {
	private Socket s;
	private ServerSocket ss;
	private OnReconnectListener onReconnectListener;
	private PrintWriter pw_socket;

	public OnReconnectListener getOnReconnectListener() {
		return onReconnectListener;
	}

	public void setOnReconnectListener(OnReconnectListener onReconnectListener) {
		this.onReconnectListener = onReconnectListener;
	}

	public interface OnReconnectListener {
		public void onReconnect();
	}

	public void create() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					ss = new ServerSocket(57168);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				while (true) {
					try {
						s = ss.accept();
						InputStream is = s.getInputStream();
						OutputStream os = s.getOutputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						pw_socket = new PrintWriter(os, true);
						String str;
						while ((str = br.readLine()) != null) {
							System.out.println(str);
							if (str != null) {
								if ("connect".equals(str)) {
									pw_socket.println("connect ok");
									pw_socket.flush();
									pw_socket.println("loginStatus="+MainUI.client.getLoginStatus());
									pw_socket.flush();
								} else if ("shutdown now".equals(str)) {
									ExternalTools.shutdown();
									pw_socket.println("shutdown now ok");
									pw_socket.flush();
								} else if (str.startsWith("shutdown time=")) {
									String[] split = str.split("=");
									try {
										ExternalTools.shutdownAtTime(split[1]);
										pw_socket.println("shutdown time ok=" + split[1]);
										pw_socket.flush();
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else if (str.equals("shutdown cancel")) {
									ExternalTools.shutdownCancel();
									printMessage("shutdown cancel ok");
								} else if ("reconnect".equals(str)) {
									for(JDialog dialog :MainUI.dialogs)
									{
										dialog.dispose();
									}
									MainUI.dialogs.clear();
									onReconnectListener.onReconnect();
									pw_socket.println("reconnect ok");
									pw_socket.flush();
								}
							}
						}
						is.close();
						os.close();
						br.close();
						pw_socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	public void printMessage(String printMessage) {
		if (pw_socket != null) {
			pw_socket.println(printMessage);
			pw_socket.flush();
		}
	}
}
