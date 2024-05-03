import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;


public class JoinMembership extends JFrame {
	private JTextField joinMembership_ID;
	private JTextField joinMembership_PassWord;
	private JTextField joinMembership_PassWord_DoubleCheck;
	private Container pnl;
	private ActionListener btnJoinMembershipINBack;
	private ActionListener btnJoinMembership;
	private JTextField joinMembership_Email;
	

	public JoinMembership() {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(false);
		getContentPane().setLayout(null);
		setTitle("회원가입");

		JLabel lbl회원가입 = new JLabel("회원가입");
		lbl회원가입.setBounds(12, 10, 57, 15);
		getContentPane().add(lbl회원가입);

		JLabel lbl아이디입력 = new JLabel("아아디 입력");
		lbl아이디입력.setBounds(113, 98, 99, 24);
		getContentPane().add(lbl아이디입력);

		JLabel lbl비밀번호_입력 = new JLabel("비밀번호 입력");
		lbl비밀번호_입력.setBounds(113, 188, 99, 24);
		getContentPane().add(lbl비밀번호_입력);

		JLabel lbl비밀번호_확인 = new JLabel("비밀번호 확인");
		lbl비밀번호_확인.setBounds(113, 236, 99, 24);
		getContentPane().add(lbl비밀번호_확인);

		JButton btn뒤로가기 = new JButton();
		btn뒤로가기.setBorderPainted(false);
		btn뒤로가기.setContentAreaFilled(false);
		btn뒤로가기.setOpaque(false);
		btn뒤로가기.setIcon(new ImageIcon(JoinMembership.class.getResource("/img/뒤로가기 버튼.png")));
		btn뒤로가기.setBounds(480, 433, 100, 30);
		getContentPane().add(btn뒤로가기);

		btn뒤로가기.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnJoinMembershipINBack != null) {
					btnJoinMembershipINBack.actionPerformed(e);
				}
			}
		});

		joinMembership_ID = new JTextField();
		joinMembership_ID.setBounds(221, 100, 273, 21);
		getContentPane().add(joinMembership_ID);
		joinMembership_ID.setColumns(10);

		joinMembership_PassWord = new JTextField();
		joinMembership_PassWord.setColumns(10);
		joinMembership_PassWord.setBounds(221, 190, 273, 21);
		getContentPane().add(joinMembership_PassWord);

		joinMembership_PassWord_DoubleCheck = new JTextField();
		joinMembership_PassWord_DoubleCheck.setColumns(10);
		joinMembership_PassWord_DoubleCheck.setBounds(221, 238, 273, 21);
		getContentPane().add(joinMembership_PassWord_DoubleCheck);

		JButton btn회원가입 = new JButton();
		btn회원가입.setIcon(new ImageIcon(JoinMembership.class.getResource("/img/회원가입 버튼.png")));
		btn회원가입.setBorderPainted(false);
		btn회원가입.setContentAreaFilled(false);
		btn회원가입.setOpaque(false);
		btn회원가입.setBounds(464, 274, 97, 23);
		getContentPane().add(btn회원가입);

		JLabel lbl아이디입력_1 = new JLabel("이메일 입력");
		lbl아이디입력_1.setBounds(113, 140, 99, 24);
		getContentPane().add(lbl아이디입력_1);

		joinMembership_Email = new JTextField();
		joinMembership_Email.setColumns(10);
		joinMembership_Email.setBounds(221, 142, 273, 21);
		getContentPane().add(joinMembership_Email);

		joinMembership_PassWord_DoubleCheck.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btn회원가입.doClick();
				}
			}
		});

		btn회원가입.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnJoinMembership != null) {
					btnJoinMembership.actionPerformed(e);
				}
			}
		});

	}

	// 회원가입 창_뒤로가기 버튼 액션 리스너 설정 메소드
	public void setJoinMembership_back_LoginListener(ActionListener listener) {
		this.btnJoinMembershipINBack = listener;
	}

	// 회원가입 창_회원가입 버튼 액션 리스너 설정 메소드
	public void setJoinMembershipListener(ActionListener listener) {
		this.btnJoinMembership = listener;
	}

	public String getJoinMembership_ID() {
		return joinMembership_ID.getText();
	}

	public String getJoinMembership_PassWord() {
		return joinMembership_PassWord.getText();
	}

	public String getJoinMembership_PassWord_DoubleCheck() {
		return joinMembership_PassWord_DoubleCheck.getText();
	}

	public String getJoinMembership_Email() {
		return joinMembership_Email.getText();
	}

}