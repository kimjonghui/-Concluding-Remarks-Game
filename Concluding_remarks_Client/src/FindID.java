import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;

public class FindID extends JFrame {
	private JTextField findid_email;
	private ActionListener btnINfindID;
	private ActionListener btnINfindIDBack;

	public FindID() {
		getContentPane().setBackground(Color.LIGHT_GRAY);

		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(false);
		getContentPane().setLayout(null);

		JLabel lbl아이디_찾기 = new JLabel("아이디 찾기");
		lbl아이디_찾기.setBounds(12, 10, 69, 15);
		getContentPane().add(lbl아이디_찾기);

		findid_email = new JTextField();
		findid_email.setBounds(201, 189, 200, 30);
		findid_email.setBorder(null);
		getContentPane().add(findid_email);
		findid_email.setColumns(10);

		JLabel lbl이메일_입력 = new JLabel("이메일 입력");
		lbl이메일_입력.setBounds(111, 192, 78, 15);
		getContentPane().add(lbl이메일_입력);

		JButton btn아이디찾기_뒤로가기 = new JButton();
		btn아이디찾기_뒤로가기.setBounds(442, 525, 100, 30);
		btn아이디찾기_뒤로가기.setBorderPainted(false);
		btn아이디찾기_뒤로가기.setContentAreaFilled(false);
		btn아이디찾기_뒤로가기.setOpaque(false);
		btn아이디찾기_뒤로가기.setIcon(new ImageIcon(FindID.class.getResource("/img/뒤로가기 버튼.png")));
		getContentPane().add(btn아이디찾기_뒤로가기);

		btn아이디찾기_뒤로가기.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnINfindIDBack != null) {
					btnINfindIDBack.actionPerformed(e);
				}
			}
		});

		JButton btn아이디찾기_찾기 = new JButton();
		btn아이디찾기_찾기.setBounds(384, 242, 100, 30);
		btn아이디찾기_찾기.setBorderPainted(false);
		btn아이디찾기_찾기.setContentAreaFilled(false);
		btn아이디찾기_찾기.setOpaque(false);
		btn아이디찾기_찾기.setIcon(new ImageIcon(FindID.class.getResource("/img/아이디 찾기.png")));
		getContentPane().add(btn아이디찾기_찾기);
		btn아이디찾기_찾기.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnINfindID != null) {
					btnINfindID.actionPerformed(e);
				}
			}
		});
	}

	// 아이디를 찾기 위한 이메일 반환 메소드
	public String getFindID_emil() {
		return findid_email.getText();
	}

	// 아이디 찾기 액션 리스너 설정 메소드
	public void setFindIDListener(ActionListener listener) {
		this.btnINfindID = listener;
	}

	// 아이디찾기_뒤로가기 액션 리스너 설정 메소드
	public void setFindID_BackListener(ActionListener listener) {
		this.btnINfindIDBack = listener;
	}

}