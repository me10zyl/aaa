package net.xicp.zyl_me;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import net.xicp.zyl_me.exception.DisableException;
import net.xicp.zyl_me.exception.ExpireException;
import net.xicp.zyl_me.exception.HTTPNotOKException;
import net.xicp.zyl_me.exception.IDPWWrongException;
import net.xicp.zyl_me.exception.LogoutFailedException;
import net.xicp.zyl_me.soap.Client;
import net.xicp.zyl_me.soap.Client.OnErrorListener;
import net.xicp.zyl_me.soap.Client.OnLogoutSuccessListener;
import net.xicp.zyl_me.soap.Client.OnNewPublicMessageReceivedListener;
import net.xicp.zyl_me.soap.Client.OnNewUserMessageReceivedListener;
import net.xicp.zyl_me.util.ExternalTools;
import net.xicp.zyl_me.util.Saver;
import net.xicp.zyl_me.util.SystemUtil;
import net.xicp.zyl_me.util.VersionAdministrator;
import net.xicp.zyl_me.util.VersionInfomation;

import org.dom4j.DocumentException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MainUI {
	private boolean isTheFirstTimeToOpen = true;
	private TrayIcon trayicon;
	private JFrame frame;
	private JTextField userIDTextField;
	private JPasswordField userPWField;
	private VersionAdministrator versionAdministrator;
	private String userID = "";
	private String userPW = "";
	private String userIP = "169.254.92.24,10.97.34.26,192.168.160.1,192.168.242.1";
	private String errInfo = "ED3B41FAD0157C3D8EBCB395426C52E5";
	private String computerName = "ACCELERATEDWORL";
	private String mac = "6002B4E55A37,C45444AB85DE,005056C00001,005056C00008";
	private String isAutoLogin = "false";
	private String clientVersion = "1.14.10.16";
	private String osVersion = "Microsoft Windows NT 6.1.7601 Service Pack 1";
	private JButton loginBtn;
	private JEditorPane noticeEditorPane;
	private Client client;
	private JCheckBox savePasswordCheckBox;
	private JMenu menu_1;
	private JMenuItem menuItem_1;
	private JMenuItem menuItem_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI window = new MainUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainUI() {
		initialize();
	}

	private void detectVersion() {
		versionAdministrator = new VersionAdministrator();
		VersionInfomation versionInfomation = versionAdministrator.detectVersion();
		int version = versionInfomation.getVersion();
		String message = versionInfomation.getMsg();
		if (version > VersionAdministrator.version) {
			int result = JOptionPane.showConfirmDialog(frame, "有新版本 version" + version + " 可下载,是否下载?\n" + message, "新版本detected!", JOptionPane.YES_NO_OPTION);
			if (result == 0)// 是
			{
				String downloadSite = versionInfomation.getDownload();
				try {
					Desktop.getDesktop().browse(new URI(downloadSite));
				} catch (IOException | URISyntaxException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frame, e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	private void clientWorking() {
		try {
			computerName = SystemUtil.getComputerName();
			userIP = SystemUtil.getIPAddress();
			mac = SystemUtil.getMACAddress();
			userID = userIDTextField.getText();
			userPW = new String(userPWField.getPassword());
			client.setClientVersion(clientVersion);
			client.setOsVersion(osVersion);
			client.setComputerName(computerName);
			client.setErrInfo(errInfo);
			client.setIsAutoLogin(isAutoLogin);
			client.setMac(mac);
			client.setUserID(userID);
			client.setUserIP(userIP);
			client.setUserPW(userPW);
			client.setOnNewPublicMessageReceivedListener(new OnNewPublicMessageReceivedListener() {
				@Override
				public void onMessageReceived(String message) {
					// TODO Auto-generated method stub
					JOptionPane.showMessageDialog(frame, message);
					System.out.println(message);
				}
			});
			client.setOnNewUserMessageReceivedListener(new OnNewUserMessageReceivedListener() {
				@Override
				public void onMessageReceived(String message) {
					// TODO Auto-generated method stub
					JOptionPane.showMessageDialog(frame, message);
					System.out.println(message);
				}
			});
			client.setOnErrorListener(new OnErrorListener() {
				@Override
				public void onError(String message) {
					// TODO Auto-generated method stub
					JOptionPane.showMessageDialog(frame, message);
					detectLoginStatus();
				}
			});
			client.setOnLogoutSuccessListener(new OnLogoutSuccessListener() {
				@Override
				public void onLogout(String message) {
					// TODO Auto-generated method stub
					detectLoginStatus();
				}
			});
			String msg = client.work();
			noticeEditorPane.setText(msg);
			if (client.getLoginStatus().equals("login"))// TODO 登录成功
			{
				Saver.saveUserID(userID);
				String userPW2 = new String(userPWField.getPassword());
				if (savePasswordCheckBox.isSelected() && !"".equals(userPW2)) {
					Saver.saveUserPW(userPW2);
				}
			}
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					detectVersion();
				}
			}, 30000);
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | DocumentException | IOException | IDPWWrongException | ExpireException | HTTPNotOKException | DisableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, e.getMessage());
			client.cancelKeepSession("error");
			detectLoginStatus();
		}
	}

	private void detectLoginStatus() {
		if ("login".equals(client.getLoginStatus())) {
			loginBtn.setText("注销");
		} else {
			loginBtn.setText("登录");
			if (!"logout".equals(client.getLoginStatus())) {
				noticeEditorPane.setText("");
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainUI.class.getResource("/images/icon3.png")));
		frame.setBounds(100, 100, 557, 287);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		client = new Client();
		String lnfName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			javax.swing.UIManager.setLookAndFeel(lnfName);
			SwingUtilities.updateComponentTreeUI(frame);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u767B\u5165", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(8, 17, 255, 186);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		userIDTextField = new JTextField();
		userIDTextField.setBounds(72, 40, 177, 24);
		panel.add(userIDTextField);
		userIDTextField.setColumns(10);
		userPWField = new JPasswordField();
		userPWField.setBounds(72, 97, 177, 24);
		panel.add(userPWField);
		JLabel label = new JLabel("\u7528\u6237\u540D\uFF1A");
		label.setBounds(6, 43, 72, 18);
		panel.add(label);
		JLabel label_1 = new JLabel("\u5BC6  \u7801\uFF1A");
		label_1.setBounds(6, 100, 72, 18);
		panel.add(label_1);
		loginBtn = new JButton("\u767B\u5F55");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ("login".equals(client.getLoginStatus()))// 注销
				{
					try {
						noticeEditorPane.setText(client.logout());
					} catch (NoSuchAlgorithmException | DocumentException | IOException | HTTPNotOKException | LogoutFailedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(frame, e1.getMessage());
						client.cancelKeepSession("loginout-failed");
					}
				} else {
					clientWorking();
				}
				detectLoginStatus();
			}
		});
		loginBtn.setBounds(136, 146, 113, 27);
		panel.add(loginBtn);
		savePasswordCheckBox = new JCheckBox("\u4FDD\u5B58\u5BC6\u7801");
		savePasswordCheckBox.setBounds(6, 146, 100, 27);
		panel.add(savePasswordCheckBox);
		noticeEditorPane = new JEditorPane();
		noticeEditorPane.setBounds(271, 13, 254, 175);
		frame.getContentPane().add(noticeEditorPane);
		frame.setLocationRelativeTo(null);
		savePasswordCheckBox.setSelected(Saver.getCheckboxStatus("savePasswordCheckBox"));
		userIDTextField.setText(Saver.getUserID());
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("\u529F\u80FD");
		menuBar.add(menu);
		
		menu_1 = new JMenu("\u5B9A\u65F6\u5173\u673A");
		menu.add(menu_1);
		
		menuItem_1 = new JMenuItem("\u5F00\u542F");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String res = (String) JOptionPane.showInputDialog(frame, "请输入关机时间(默认11点关机 格式HH:mm):", "定时关机器", JOptionPane.OK_CANCEL_OPTION,null,null,"23:00");
				if(res != null)
				{
					try {
						ExternalTools.shutdownAtTime(res);
					} catch (IOException | ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(frame, e1.getMessage());
					}
				}
			}
		});
		menu_1.add(menuItem_1);
		
		menuItem_2 = new JMenuItem("\u5173\u95ED");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ExternalTools.shutdownCancel();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
			}
		});
		menu_1.add(menuItem_2);
		if (!"".equals(Saver.getUserID()) && Saver.getUserID() != null && savePasswordCheckBox.isSelected()) {
			userPWField.setText(Saver.getUserPW());
		}
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				Saver.saveCheckboxStatus("savePasswordCheckBox", savePasswordCheckBox.isSelected());
				super.windowClosing(e);
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowIconified(e);
				if("login".equals(client.getLoginStatus()))
				{
					frame.setVisible(false);
					if(isTheFirstTimeToOpen)
					{
						trayicon.displayMessage("NSUAAAC", "me10zyl@qq.com", MessageType.INFO);
						isTheFirstTimeToOpen = false;
					}
				}
			}
			
		});
		if (!SystemTray.isSupported()) {
			return;
		} else {
			SystemTray systemTray = SystemTray.getSystemTray();
			String title = "NSUAAAC - me10zyl@qq.com";
			Image image = Toolkit.getDefaultToolkit().getImage(MainUI.class.getResource("/images/trayIcon.png"));
			trayicon = new TrayIcon(image, title, createMenu());
			trayicon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					frame.setVisible(true);
				}
			});
			try {
				systemTray.add(trayicon);
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private PopupMenu createMenu() {
		PopupMenu menu = new PopupMenu();
		MenuItem exit = new MenuItem("关闭");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ex) {
				System.exit(0);
			}
		});
		MenuItem open = new MenuItem("打开");
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ex) {
				if (!frame.isVisible()) {
					frame.setVisible(true);
					frame.toFront();
				} else {
					frame.toFront();
				}
			}
		});
		menu.add(open);
		menu.addSeparator();
		menu.add(exit);
		return menu;
	}
}
