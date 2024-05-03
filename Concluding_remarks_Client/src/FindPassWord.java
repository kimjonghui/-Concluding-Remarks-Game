import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;

public class FindPassWord extends JFrame {
	private JTextField FindPassWord_ID;
	private JTextField FindPassWord_Email;
	private ActionListener btnINfindPW;
	private ActionListener btnINfindPWback;

	public FindPassWord() {
		getContentPane().setBackground(Color.LIGHT_GRAY);

		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(false);
		getContentPane().setLayout(null);

		JLabel lbl비밀번호_찾기 = new JLabel("비밀번호 찾기");
		lbl비밀번호_찾기.setBounds(12, 10, 95, 15);
		getContentPane().add(lbl비밀번호_찾기);

		JButton btn비밀번호찾기_뒤로가기 = new JButton();
		btn비밀번호찾기_뒤로가기.setBorderPainted(false);
		btn비밀번호찾기_뒤로가기.setContentAreaFilled(false);
		btn비밀번호찾기_뒤로가기.setOpaque(false);
		btn비밀번호찾기_뒤로가기.setIcon(new ImageIcon(FindPassWord.class.getResource("/img/뒤로가기 버튼.png")));
		btn비밀번호찾기_뒤로가기.setBounds(475, 528, 100, 30);
		getContentPane().add(btn비밀번호찾기_뒤로가기);

		btn비밀번호찾기_뒤로가기.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnINfindPWback != null) {
					btnINfindPWback.actionPerformed(e);
				}
			}
		});

		JLabel lblNewLabel = new JLabel("아이디 입력");
		lblNewLabel.setBounds(100, 132, 83, 15);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("이메일 입력");
		lblNewLabel_1.setBounds(100, 173, 83, 15);
		getContentPane().add(lblNewLabel_1);

		FindPassWord_ID = new JTextField();
		FindPassWord_ID.setBounds(195, 129, 267, 21);
		getContentPane().add(FindPassWord_ID);
		FindPassWord_ID.setColumns(10);

		FindPassWord_Email = new JTextField();
		FindPassWord_Email.setColumns(10);
		FindPassWord_Email.setBounds(195, 170, 267, 21);
		getContentPane().add(FindPassWord_Email);

		JButton btn비밀번호찾기_비밀번호찾기 = new JButton();
		btn비밀번호찾기_비밀번호찾기.setIcon(new ImageIcon(FindPassWord.class.getResource("/img/비밀번호 찾기 1.png")));
		btn비밀번호찾기_비밀번호찾기.setBorderPainted(false);
		btn비밀번호찾기_비밀번호찾기.setContentAreaFilled(false);
		btn비밀번호찾기_비밀번호찾기.setOpaque(false);
		btn비밀번호찾기_비밀번호찾기.setBounds(406, 225, 100, 30);
		getContentPane().add(btn비밀번호찾기_비밀번호찾기);
		btn비밀번호찾기_비밀번호찾기.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnINfindPW != null) {
					btnINfindPW.actionPerformed(e);
				}
			}
		});
	}

	// 비밀번호를 찾기위해 입력한 아이디를 반환하는 메소드
	public String getFindPassWord_ID() {
		return FindPassWord_ID.getText();
	}

	// 비밀번호를 찾기위해 입력한 이메일를 반환하는 메소드
	public String getFindPassWord_Email() {
		return FindPassWord_Email.getText();
	}

	// 비밀번호 찾기 찾기 버튼 액션 리스너 설정 메소드
	public void setFindPassWordListener(ActionListener listener) {
		this.btnINfindPW = listener;
	}

	// 비밀번호 찾기_뒤로가기 액션 리스너 설정 메소드
	public void setFindPassWord_BackListener(ActionListener listener) {
		this.btnINfindPWback = listener;
	}

}