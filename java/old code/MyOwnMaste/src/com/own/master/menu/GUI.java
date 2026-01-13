package com.own.master.menu;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.own.master.engine.math.Vector3f;

public class GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	static GUI Window;
	JFrame window;

	JButton option1 = new JButton("Screwy Lewy (WIP)");
	JButton option2 = new JButton("Black Jack");
	JButton option3 = new JButton("Scum *NOT WORKING*");
	JButton option4 = new JButton("RektLand - The Bedroom(WIP)");
	JButton option5 = new JButton("Font test");
	JButton option6 = new JButton("RektLand - The Kitchen(WIP)");
	JButton option7 = new JButton("RektLand - The Slopes(WIP)");
	JButton option8 = new JButton("RektLand - Tutorial map(WIP)");
	JButton option9 = new JButton("RektLand - Green Hills(WIP)");
	JButton option10 = new JButton("RektLand - PlayerTest(WIP)");

	Vector3f nouce;

	public GUI() {
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(4, 2));
		menuPanel.add(new JLabel("Games"));
		menuPanel.add(option1);
		menuPanel.add(option2);
		menuPanel.add(option3);
		menuPanel.add(option4);
		menuPanel.add(option5);
		menuPanel.add(option6);
		menuPanel.add(option7);
		menuPanel.add(option8);
		menuPanel.add(option9);
		menuPanel.add(option10);
		window = new JFrame("3D Games Main Menu");
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setMinimumSize(new Dimension(300,600));
		window.setSize(300, 600);
		option1.addActionListener(this);
		option2.addActionListener(this);
		option3.addActionListener(this);
		option4.addActionListener(this);
		option5.addActionListener(this);
		window.add(menuPanel);
		window.setAlwaysOnTop(false);
		window.pack();
		window.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == option1) {
			Main.Main.runProgram(1);
			window.setVisible(false);
		}
		if (e.getSource() == option2) {
			Main.Main.runProgram(2);
			window.setVisible(false);
		}
		if (e.getSource() == option3) {
			Main.Main.runProgram(3);
			window.setVisible(false);
		}
		if (e.getSource() == option4) {
			Main.Main.runProgram(4);
			window.setVisible(false);
		}
		if (e.getSource() == option5) {
			Main.Main.runProgram(5);
			window.setVisible(false);
		}
	}
}