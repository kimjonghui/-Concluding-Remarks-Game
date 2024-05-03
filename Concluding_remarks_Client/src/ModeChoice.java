import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;

public class ModeChoice extends JFrame {
	private ActionListener singleMode;
	private ActionListener multiMode;
	private ActionListener select_Back;
	public static List<String> MultiList = new ArrayList<>();

	public ModeChoice() {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(false);
		getContentPane().setLayout(null);
		setTitle("모드선택");

		JLabel lbl모드선택 = new JLabel("모드선택");
		lbl모드선택.setBounds(59, 58, 94, 30);
		getContentPane().add(lbl모드선택);

		JButton btn싱글 = new JButton("");
		btn싱글.setIcon(new ImageIcon(ModeChoice.class.getResource("/img/싱글버튼.png")));
		btn싱글.setBounds(87, 198, 200, 240);
		getContentPane().add(btn싱글);

		btn싱글.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (singleMode != null) {
					singleMode.actionPerformed(e);
//				SingleMode sm = new SingleMode();
//				sm.setVisible(true);
//				setVisible(false);
				}
			}
		});

		JButton btn멀티 = new JButton("");
		btn멀티.setIcon(new ImageIcon(ModeChoice.class.getResource("/img/멀티버튼.png")));
		btn멀티.setBounds(307, 198, 200, 240);
		getContentPane().add(btn멀티);

		btn멀티.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (multiMode != null) {
					multiMode.actionPerformed(e);
//				MultiMode mm = new MultiMode();
//				mm.setVisible(true);
//				setVisible(false);
//				MultiList.add(Client.list.toString());
//				System.out.println("입장 유저: " + Client.list.toString());

				}
			}
		});

		JButton btn모드선택_뒤로가기 = new JButton();
		btn모드선택_뒤로가기.setIcon(new ImageIcon(ModeChoice.class.getResource("/img/뒤로가기 버튼.png")));
		btn모드선택_뒤로가기.setBorderPainted(false);
		btn모드선택_뒤로가기.setContentAreaFilled(false);
		btn모드선택_뒤로가기.setOpaque(false);
		btn모드선택_뒤로가기.setBounds(475, 528, 100, 30);
		getContentPane().add(btn모드선택_뒤로가기);

		btn모드선택_뒤로가기.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_Back != null) {
					select_Back.actionPerformed(e);
//				MainView mv = new MainView();
//				mv.setVisible(true);
//				setVisible(false);
				}
			}
		});
	}

	public void setSingleMode(ActionListener listener) {
		this.singleMode = listener;
	}

	public void setMultiMode(ActionListener listener) {
		this.multiMode = listener;
	}

	public void setSelect_Back(ActionListener listener) {
		this.select_Back = listener;
	}

}