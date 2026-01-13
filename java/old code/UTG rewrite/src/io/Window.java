package io;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Main;
import main.thread;

@SuppressWarnings("all")
public class Window extends JFrame implements ActionListener, ChangeListener, WindowListener {

	static Window Window;
	JFrame window;
	JFrame advanced_panel;
	JFrame credits;
	JFrame file_explorer;
	JMenuItem Credits = new JMenuItem("Credits");
	JMenuItem exit = new JMenuItem("Exit");
	JMenuItem stop_thread = new JMenuItem("Stop run");
	JMenuItem run_all = new JMenuItem("Run");
	JMenuItem run_main = new JMenuItem("Run (preset showcase)");
	JMenuItem run_usersubm = new JMenuItem("Run (user submitted showcase)");
	JMenuItem run_file = new JMenuItem("Run TXT file..");
	JMenuItem option_cleardata = new JMenuItem("Clear data");
	JMenuItem option_repeat = new JMenuItem("Set Repeat...");
	JMenuItem option_voice = new JMenuItem("Set TTS voice as...");
	JMenuItem option_advanced = new JMenuItem("Advanced options");
	JButton advanced_setting_general_music = new JButton("Disable Music");
	JButton advanced_setting_general_TTS = new JButton("Disable TTS");
	JButton advanced_setting_general_userSubm = new JButton("Disable User created data");
	JButton advanced_setting_generation_chaos = new JButton("Enable True database chaos");
	JButton advanced_setting_generation_unlimitedTime = new JButton("(NOTWORKING) Toggle compile time limit");
	JButton advanced_setting_data_massDatabase = new JButton("advanced_setting_data_massDatabase");

	JButton button_play;// = new JButton();
	JButton button_pause;// = new JButton();
	JButton button_stopped;// = new JButton();
	JPanel buttons = new JPanel();
	JButton button_multithread;

	JCheckBox box1;
	JSlider sl = new JSlider(JSlider.HORIZONTAL, 1, 4, 4);
	// int, JSlider.orentation, unknown?

	JPanel fileList;
	JPanel fileListContents;
	JPanel fileDirectoryListContents;
	JPanel fileDirectoryList;
	JScrollPane __file_List;
	JScrollPane file_List_Dir;

	public static JTextArea Text = new JTextArea();
	JLabel Info = new JLabel();
	JLabel Status = new JLabel();
	public JLabel Status_Info = new JLabel();
	JPanel Status_Bar;
	static JScrollPane scroll;
	static Thread core = new Thread(new thread(0));

	public Window() {
		// define text areas:
		Status_Info.setText("Current file path: N/A Data file: N/A");
		Status_Info.setFont(new Font("Verdana", Font.PLAIN, 10));
		Text.setText("");
		Text.setFont(new Font("Verdana", Font.PLAIN, 15));
		Info.setText("Hehe. Nice.");
		Info.setFont(new Font("Verdana", Font.PLAIN, 20));
		Status.setText("Stopped...");
		Status.setFont(new Font("Verdana", Font.PLAIN, 10));
		scroll = new JScrollPane(Text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// main window menu bar items:
		JMenuBar menuBar = new JMenuBar();
		// File
		JMenu menu_file = new JMenu("File");
		JMenu menu_program_run = new JMenu("Run...");
		menu_program_run.add(run_all);
		menu_program_run.add(run_main);
		menu_program_run.add(run_usersubm);
		menu_program_run.add(run_file);
		menu_program_run.add(stop_thread);
		menu_file.add(menu_program_run);
		menu_file.add(Credits);
		menu_file.add(exit);
		JMenu menu_options = new JMenu("Options"); // {
		menu_options.add(option_cleardata);
		menu_options.add(option_voice);
		menu_options.add(option_advanced);
		menuBar.add(menu_file);
		menuBar.add(menu_options);
		// tool bar:
		ImageIcon image_play;
		ImageIcon image_pause;
		ImageIcon image_stopped;
		image_play = new ImageIcon("images/play.png");
		image_pause = new ImageIcon("images/pause.png");
		image_stopped = new ImageIcon("images/stopped.png");
		// 32x32 is the image size, change these if you are modding to the new image
		// sizes
		button_play = new JButton(image_play);
		button_play.setPreferredSize(new Dimension(32, 32));
		button_pause = new JButton(image_pause);
		button_pause.setPreferredSize(new Dimension(32, 32));
		button_stopped = new JButton(image_stopped);
		button_stopped.setPreferredSize(new Dimension(32, 32));
		buttons.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		buttons.add(button_play);
		buttons.add(button_pause);
		buttons.add(button_stopped);
		button_multithread = new JButton("Experemental: Enable multithreading");
		sl.setMajorTickSpacing(1);
		sl.setMinorTickSpacing(1);
		sl.setPaintTicks(true);
		sl.setPaintLabels(true);
		buttons.add(sl);
		buttons.add(button_multithread);
		// buttons.add(Text);
		// top panel:
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(2, 1));
		top.add(Info);
		top.add(buttons);
		// side panel with tabs:
		JTabbedPane files = new JTabbedPane();
		// files.setLayout(new BoxLayout(files, BoxLayout.PAGE_AXIS));
		// tab user files:
		fileList = new JPanel();
		fileListContents = new JPanel();
		File dir_path = new File("input/userSubmitted");
		String[] contents = dir_path.list();
		fileListContents.setLayout(new GridLayout(30, 1));
		for (int i = 0; i < contents.length; i++) {
			fileListContents.add(new JLabel(contents[i]));
		}
		if (30 - contents.length > 0) {
			for (int i = 0; i < (30 - contents.length); i++) {
				fileListContents.add(new JLabel());
			}
		}
		fileList.add(fileListContents);
		__file_List = new JScrollPane(fileList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		__file_List.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// list of generated files (DIRECTORIES ONLY)

		fileDirectoryList = new JPanel();
		fileDirectoryListContents = new JPanel();
		File dir_path_file = new File("data");
		String[] contentsList = dir_path_file.list();
		fileDirectoryListContents.setLayout(new GridLayout(30, 1));
		for (int i = 0; i < contentsList.length; i++) {
			fileDirectoryListContents.add(new JLabel(contentsList[i]));
		}
		fileDirectoryList.add(fileDirectoryListContents);
		file_List_Dir = new JScrollPane(fileDirectoryList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		file_List_Dir.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// add the tabs:
		files.addTab("User Files", __file_List);
		files.addTab("Data Files", file_List_Dir);

		// advanced options window:
		advanced_panel = new JFrame("Advanced Options");
		advanced_panel.setResizable(true);
		advanced_panel.setMinimumSize(new Dimension(640, 480));
		JPanel adv = new JPanel();

		adv.setLayout(new GridLayout(3, 2));
		adv.add(advanced_setting_general_music);
		adv.add(advanced_setting_general_TTS);
		adv.add(advanced_setting_general_userSubm);
		adv.add(advanced_setting_generation_chaos);
		adv.add(advanced_setting_generation_unlimitedTime);
		adv.add(advanced_setting_data_massDatabase);
		advanced_panel.add(adv);
		advanced_panel.setVisible(false);
		// status bar:
		Status_Bar = new JPanel();
		Status_Bar.setLayout(new GridLayout(2, 1));
		Status_Bar.add(Status_Info);
		Status_Bar.add(Status);
		// main window:
		window = new JFrame("UselessTextGenerator GUI");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.setSize(1000, 700);
		window.setMinimumSize(new Dimension(1000, 700));
		window.setDefaultLookAndFeelDecorated(true);
		window.setJMenuBar(menuBar);
		window.add(top, BorderLayout.NORTH);
		window.add(scroll);// AAAAAAA
		window.add(Status_Bar, BorderLayout.SOUTH);
		window.add(files, BorderLayout.WEST);
		window.setAlwaysOnTop(false);
		// advanced options window0:
		// advanced.setResizable(true);
		// advanced.setMinimumSize(new Dimension(640, 480));

		// credits window
		credits = new JFrame("Credits");
		credits.setResizable(false);
		credits.setMinimumSize(new Dimension(640, 480));
		credits.setLayout(new GridLayout(5, 5));
		JLabel text_area_0 = new JLabel("Thanks to:");
		JLabel text_area_1 = new JLabel("FreeTTS \n" + "https://freetts.sourceforge.io/docs/index.php");
		JLabel text_area_2 = new JLabel("Music and sounds: \n" + "Microsoft Corporation (Windows 95 to Windows XP) \n"
				+ "Microsoft Sam: tetyys.com/SAPI4/");
		credits.add(text_area_0);
		credits.add(text_area_1);
		credits.add(text_area_2);

		// add listeners to all buttons
		run_all.addActionListener(this);
		run_usersubm.addActionListener(this);
		exit.addActionListener(this);
		stop_thread.addActionListener(this);
		option_advanced.addActionListener(this);
		advanced_setting_general_music.addActionListener(this);
		advanced_setting_general_TTS.addActionListener(this);
		advanced_setting_general_userSubm.addActionListener(this);
		advanced_setting_data_massDatabase.addActionListener(this);
		advanced_setting_generation_chaos.addActionListener(this);
		Credits.addActionListener(this);
		option_cleardata.addActionListener(this);
		button_play.addActionListener(this);
		button_pause.addActionListener(this);
		button_stopped.addActionListener(this);
		button_multithread.addActionListener(this);
		sl.addChangeListener(this);

		window.pack();
		credits.pack();
		advanced_panel.pack();
		credits.setVisible(false);
		window.setVisible(true);

	}

	public void updateLists() {
		fileList = new JPanel();
		fileListContents = new JPanel();
		File dir_path = new File("input/userSubmitted");
		String[] contents = dir_path.list();
		fileListContents.setLayout(new GridLayout(30, 1));
		for (int i = 0; i < contents.length; i++) {
			fileListContents.add(new JLabel(contents[i]));
		}
		if (30 - contents.length > 0) {
			for (int i = 0; i < (30 - contents.length); i++) {
				fileListContents.add(new JLabel());
			}
		}
		fileDirectoryList = new JPanel();
		fileDirectoryListContents = new JPanel();
		File dir_path_file = new File("data");
		String[] contentsList = dir_path_file.list();
		fileDirectoryListContents.setLayout(new GridLayout(30, 1));
		for (int i = 0; i < contentsList.length; i++) {
			fileDirectoryListContents.add(new JLabel(contentsList[i]));
		}
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (e.getSource() == sl) {
			Main.compile_setting_amount = (int) source.getValue();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == run_all || e.getSource() == button_play) {
			if (!core.isAlive()) {
				updateStatus("Starting...");
				core = new Thread(new thread(0));
				core.start();
			}
		}
		if (e.getSource() == button_multithread) {
			if (!core.isAlive()) {
				if (!Main.advanced_setting_generation_multithread) {
					Main.advanced_setting_generation_multithread = true;
					button_multithread.setText("Experemental: Disable multithreading");
				} else {
					Main.advanced_setting_generation_multithread = false;
					button_multithread.setText("Experemental: Enable multithreading");
				}
			}
		}
		if (e.getSource() == exit) {
			System.exit(0);
		}
		if (e.getSource() == option_advanced) {
			advanced_panel.setVisible(true);
		}
		if (e.getSource() == stop_thread || e.getSource() == button_stopped) {
			if (core.isAlive()) {
				System.out.println("Stopping thread");
				core.stop();
				updateStatus("Stopped...");
			}
		}
		if (e.getSource() == advanced_setting_generation_chaos) {
			if (Main.advanced_setting_generation_chaos) {
				Main.advanced_setting_generation_chaos = false;
				// System.out.println("chaos DISABLED");
				advanced_setting_generation_chaos.setText("Enable True database chaos");
			} else {
				// System.out.println("chaos ENABLED");
				advanced_setting_generation_chaos.setText("Disable True database chaos");
				Main.advanced_setting_generation_chaos = true;// toggles the boolean
			}
		}
		if (e.getSource() == Credits) {
			credits.setVisible(true);
		}
		if (e.getSource() == run_usersubm) {
			if (!core.isAlive()) {
				updateStatus("Starting...");
				core = new Thread(new thread(4));
				core.start();
			}
		}
		if (e.getSource() == option_cleardata) {
			Text.setText("");
			Main.window_line_count = 0;
		}
		if (e.getSource() == advanced_setting_general_music) {
			if (Main.advanced_setting_general_music) {
				Main.advanced_setting_general_music = false;
				// System.out.println("Music and sounds DISABLED");
				advanced_setting_general_music.setText("Enable Music");
			} else {
				// System.out.println("Music and sounds ENABLED");
				advanced_setting_general_music.setText("Disable Music");
				Main.advanced_setting_general_music = true;// toggles the boolean
			}
		}
		if (e.getSource() == advanced_setting_general_TTS) {
			if (Main.advanced_setting_general_TTS) {
				Main.advanced_setting_general_TTS = false;
				// System.out.println("Text to speech DISABLED");
				advanced_setting_general_TTS.setText("Enable TTS");
			} else {
				Main.advanced_setting_general_TTS = true;// toggles the boolean
				// System.out.println("Text to speech ENABLED");
				advanced_setting_general_TTS.setText("Disable TTS");
			}
		}
		if (e.getSource() == advanced_setting_general_userSubm) {
			if (Main.advanced_setting_general_userSubm) {
				Main.advanced_setting_general_userSubm = false;
				// System.out.println("User data DISABLED");
				advanced_setting_general_userSubm.setText("Enable User created data");
			} else {
				Main.advanced_setting_general_userSubm = true;// toggles the boolean
				// System.out.println("User data ENABLED");
				advanced_setting_general_userSubm.setText("Disable User created data");
			}
		}
		if (e.getSource() == advanced_setting_data_massDatabase) {
			if (Main.advanced_setting_data_massDatabase) {
				Main.advanced_setting_data_massDatabase = false;
				// System.out.println("DISABLED advanced_setting_data_massDatabase");
				advanced_setting_data_massDatabase.setText("ENABLE advanced_setting_data_massDatabase");
			} else {
				Main.advanced_setting_data_massDatabase = true;// toggles the boolean
				// System.out.println("ENABLED advanced_setting_data_massDatabase");
				advanced_setting_data_massDatabase.setText("DISABLE advanced_setting_data_massDatabase");
			}
		}
	}

	public static void Window_Run(Window bondow) {
		Window = bondow;
	}

	public void updateText(String line) {
		Text.append(line + "\n");
	}

	public void updateTextInfo(String line) {
		Info.setText(line + "\n");
	}

	public void updateStatus(String line) {
		Status.setText(line);
	}

	public void clearText() {
		Text.setText("");
	}

}
