package net.xicp.zyl_me.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.prefs.Preferences;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;

public class Saver {
	private static Preferences preferences = Preferences.userRoot().node("net.xicp.zyl_me");

	public static void saveUserID(String userID) {
		preferences.put("userID", userID);
	}

	public static String getUserID() {
		return preferences.get("userID", "");
	}

	public static void saveUserPW(String userPW) {
		String encyptPassword = null;
		encyptPassword = EncyptUtil.encyptOrDecyptPassword(userPW);
		preferences.put("userPW", encyptPassword);
	}

	public static String getUserPW() {
		String decyptPassword = null;
		decyptPassword = EncyptUtil.encyptOrDecyptPassword(preferences.get("userPW", ""));
		return decyptPassword;
	}

	public static void saveCheckboxStatus(String checkboxName, boolean checked) {
		preferences.putBoolean(checkboxName, checked);
	}

	public static boolean getCheckboxStatus(String checkboxName) {
		return preferences.getBoolean(checkboxName, false);
	}
}
