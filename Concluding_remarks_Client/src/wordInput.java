import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class wordInput extends JFrame {
	private ActionListener loginListener;
	private JTextField insertword;

	public wordInput() {
		JPanel pnl = new JPanel();
		insertword = new JTextField(10);
		JButton sendWord = new JButton("단어 전송");

//		insertword.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//					sendWord.doClick();
//				}
//			}
//		});

		sendWord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (loginListener != null) {
					loginListener.actionPerformed(e);
				}
			}
		});
		pnl.add(insertword);
		pnl.add(sendWord);

		add(pnl);

		showGUI();
	}

	private void showGUI() {
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public String getInsertword() {
		return insertword.getText();
	}

	public void setsendWordListener(ActionListener listener) {
		this.loginListener = listener;
	}
}