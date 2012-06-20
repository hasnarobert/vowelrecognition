package vowelrecognition.gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -8399324825428483880L;
	private final JPanel contentPane;
	public JButton recunoastere;
	public JButton antrenare;
	public JLabel litera;
	public JPanel panel;

	public MainWindow() {
		setTitle("VowelRcognition");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 811, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(6, 6, 799, 362);
		contentPane.add(panel);
		panel.setLayout(null);

		litera = new JLabel("");
		litera.setFont(litera.getFont().deriveFont(
				litera.getFont().getSize() + 400f));
		litera.setBounds(235, 6, 350, 350);
		litera.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		panel.add(litera);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 380, 799, 70);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		recunoastere = new JButton("Modul de recunoastere");
		recunoastere.setBounds(390, 0, 409, 70);
		panel_1.add(recunoastere);

		antrenare = new JButton("Modul de antrenare");
		antrenare.setBounds(0, 0, 372, 70);
		panel_1.add(antrenare);
	}
}
