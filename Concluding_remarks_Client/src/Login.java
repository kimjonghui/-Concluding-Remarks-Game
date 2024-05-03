import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame {
	private ActionListener loginListener;
	private ActionListener btnJoinMembership;
	private ActionListener btnfindID;
	private ActionListener btnfindPW;
	private JTextField Login_ID;
	private JPasswordField Login_PassWord_DoubleCheck;

	public Login() {
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.LIGHT_GRAY);
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(109, 149, 14, 16);
		JLabel lblpassowrd = new JLabel("PassWord");
		lblpassowrd.setBounds(109, 206, 60, 16);
		JButton btnlogin = new JButton();
		btnlogin.setIcon(new ImageIcon(Login.class.getResource("/img/로그인 버튼.png")));
		btnlogin.setBorderPainted(false);
		btnlogin.setContentAreaFilled(false);
		btnlogin.setOpaque(false);
		btnlogin.setBounds(341, 144, 100, 86);

		// 로그인 버튼에 액션 리스너 추가
		btnlogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (loginListener != null) {
					loginListener.actionPerformed(e);
				}
			}
		});
		pnl.setLayout(null);

		pnl.add(lblId);
		pnl.add(lblpassowrd);
		pnl.add(btnlogin);

		getContentPane().add(pnl);

		JButton btn로그인화면_회원가입 = new JButton();
		btn로그인화면_회원가입.setIcon(new ImageIcon(Login.class.getResource("/img/회원가입 버튼.png")));
		btn로그인화면_회원가입.setBorderPainted(false);
		btn로그인화면_회원가입.setContentAreaFilled(false);
		btn로그인화면_회원가입.setOpaque(false);
		btn로그인화면_회원가입.setBounds(89, 392, 100, 30);
		pnl.add(btn로그인화면_회원가입);

		btn로그인화면_회원가입.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnJoinMembership != null) {
					btnJoinMembership.actionPerformed(e);
				}
			}
		});

		JButton btn아이디찾기 = new JButton();
		btn아이디찾기.setIcon(new ImageIcon(Login.class.getResource("/img/아이디 찾기.png")));
		btn아이디찾기.setBorderPainted(false);
		btn아이디찾기.setContentAreaFilled(false);
		btn아이디찾기.setOpaque(false);
		btn아이디찾기.setBounds(203, 392, 100, 30);
		pnl.add(btn아이디찾기);

		btn아이디찾기.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnfindID != null) {
					btnfindID.actionPerformed(e);
				}
			}
		});

		JButton btn비밀번호찾기 = new JButton();
		btn비밀번호찾기.setIcon(new ImageIcon(Login.class.getResource("/img/비밀번호 찾기 1.png")));
		btn비밀번호찾기.setBorderPainted(false);
		btn비밀번호찾기.setContentAreaFilled(false);
		btn비밀번호찾기.setOpaque(false);
		btn비밀번호찾기.setBounds(305, 387, 136, 40);
		pnl.add(btn비밀번호찾기);

		Login_ID = new JTextField();
		pnl.add(Login_ID);
		Login_ID.setBounds(184, 149, 130, 21);

		Login_PassWord_DoubleCheck = new JPasswordField();
		pnl.add(Login_PassWord_DoubleCheck);
		Login_PassWord_DoubleCheck.setBounds(184, 204, 130, 21);

		btn비밀번호찾기.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnfindPW != null) {
					btnfindPW.actionPerformed(e);
				}
			}
		});

		showGUI();
	}

	private void showGUI() {
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	// ID 반환 메소드
	public String getID() {
		return Login_ID.getText();
	}

	// 비밀번호 반환 메소드
	public String getPassword() {
		return new String(Login_PassWord_DoubleCheck.getPassword());
	}

	// 로그인 버튼 액션 리스너 설정 메소드
	public void setLoginListener(ActionListener listener) {
		this.loginListener = listener;
	}

	// 회원가입 버튼 액션 리스너 설정 메소드
	public void setJoinMembershipListener(ActionListener listener) {
		this.btnJoinMembership = listener;
	}

	// 아이디 찾기 버튼 액션 리스너 설정 메소드
	public void setFindIDListener(ActionListener listener) {
		this.btnfindID = listener;
	}

	// 비밀번호 찾기 액션 리스너 설정 메소드
	public void setFindPassWordListener(ActionListener listener) {
		this.btnfindPW = listener;
	}

	// 로그인 시 Join Membership 비밀번호 반환 메소드
	public String getLogin_PassWord() {
		return new String(Login_PassWord_DoubleCheck.getPassword());
	}

	// 로그인 시 Join Membership 비밀번호 확인 반환 메소드
	public String getLogin_PassWord_DoubleCheck() {
		return new String(Login_PassWord_DoubleCheck.getPassword());
	}
}
