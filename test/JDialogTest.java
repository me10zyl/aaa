import javax.swing.JDialog;
import javax.swing.JFrame;


public class JDialogTest extends JFrame{
	
	
	public static void main(String args[])
	{
		JDialog dialog = new JDialog();
		dialog.setModal(false);
		dialog.setTitle("dsds");
		dialog.setVisible(true);
	}
}
