import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.File;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Main {
    static class ColoredTrackSliderUI extends BasicSliderUI {
        Color filledColor;
        Color UnderColor;

        public ColoredTrackSliderUI(JSlider b, Color filledColor, Color underColor) {
            super(b);
            this.filledColor = filledColor;
            this.UnderColor = underColor;
        }

        public void setFilledColor(Color c) {
            this.filledColor = c;
        }

        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Rectangle t = trackRect;

            // 1. Draw the "empty" background of the track (Dark Gray)
            g2d.setColor(this.UnderColor);
            // Draw a thin bar (height 4) centered in the track area
            g2d.fillRect(t.x, t.y + (t.height / 2) - 2, t.width, 4);

            // 2. Draw the "filled" part up to the thumb position
            int thumbX = thumbRect.x + (thumbRect.width / 2);
            int fillWidth = thumbX - t.x;

            g2d.setColor(filledColor);
            g2d.fillRect(t.x, t.y + (t.height / 2) - 2, fillWidth, 4);
        }
    }
    /*
    This gambling game is called 'Limbo', and is quite possibly the most degenerate form of gambling.
    It is not as simple as flipping a coin, but rather its designed to bleed you dry of all your money.
    Just for fun, but don't actualy gamble irl
    Inspired by BossManJack loosing over 50k to this 'game'
    */
    //rules
    public static final float max_win_chance = 74f;
    //commonly used stuff
    static Random rng;
    public static String stats;
    //stats
    public static final float HOUSE_EDGE = 1.0f;
    public static float rate = 0.04f;
    public static float wallet = 150f;
    public static float lmao = wallet;
    public static float win_chance = 49.500f;
    public static float bet_amount = 0f;
    public static float rollover = 100f - win_chance;
    public static float winnings = (100f - HOUSE_EDGE) / win_chance;;
    public static float play_amount = 50;
    //play counter
    static int plays = 0;
    //windows
    static JFrame frame_window;
    static JFrame frame_deposit;
    static JFrame frame_cashout_underwater_warning;
    //drop down menu items
    static JMenu menu = new JMenu("Menu");
    static JMenuBar menuBar = new JMenuBar();
    static JMenuItem menu_item_stat = new JMenuItem("Stats");
    static JMenuItem menu_item_opti = new JMenuItem("Options");
    static JMenuItem menu_item_exit = new JMenuItem("Exit");
    //on-screen buttons
    static JButton button_play;
    static JButton button_inc_bet;
    static JButton button_dec_bet;
    static JButton button_play_style_manual;
    static JButton button_play_style_auto;
    static JButton button_cash_out;
    //input fields
    static JTextField textField_bet_amount_input;
    static JSlider slider_win_result;
    static JSlider slider_win_chance;
    //display fields
    static JLabel label_current_winnings = new JLabel(winnings + "X");
    static JLabel label_current_rollover = new JLabel(rollover + "@");
    static JLabel label_current_win_chance = new JLabel(win_chance + "%");
    static JLabel label_slider_position;
    static JLabel label_system_message = new JLabel("Click 'play' to play");
    static JLabel label_wallet = new JLabel("Wallet: " + wallet + " Total spent: " + lmao);

    //panels
    //left side:
    static JPanel panel_left_side;
    static JPanel panel_center_right;
    static JPanel panel_inner_lower_panel;

    //deposit items
    static JButton button_deposit_yes;
    static JButton button_deposit_no;
    static JPanel panel_deposit;

    //cash out window items
    static JButton button_cash_out_leave_yes;
    static JButton button_cash_out_leave_no;


    public static void createWindow() {
        System.out.println("Creating windows..");
        frame_cashout_underwater_warning = new JFrame("Hold on there!");
        frame_cashout_underwater_warning.setMinimumSize(new Dimension(350, 280));
        JPanel panel_cashout = new JPanel();
        panel_cashout.setLayout(new GridLayout(3, 1, 5, 5));
        panel_cashout.add(new JLabel("You are leaving with less money than you started with!"));
        button_cash_out_leave_yes = new JButton("I'll just leave with my losses (exits)");
        button_cash_out_leave_no = new JButton("I'll go back and leave with more money! (returns to the game)");
        panel_cashout.add(button_cash_out_leave_no);
        panel_cashout.add(button_cash_out_leave_yes);
        frame_cashout_underwater_warning.add(panel_cashout);
        frame_cashout_underwater_warning.pack();
        frame_cashout_underwater_warning.setVisible(false);

        button_cash_out_leave_yes.addActionListener(e -> {
            System.out.println("You left with a loss of $" + (Main.lmao - Main.wallet) + "!");
            System.out.println("Better luck next time!");
            System.exit(0);
        });

        button_cash_out_leave_no.addActionListener(e -> {
            frame_cashout_underwater_warning.setVisible(false);
        });


        frame_deposit = new JFrame("Deposit money?");
        frame_deposit.setLayout(new BorderLayout(10, 10));
        frame_deposit.setMinimumSize(new Dimension(350, 280));
        frame_deposit.setDefaultLookAndFeelDecorated(true);
        button_deposit_yes = new JButton("Yes");
        button_deposit_no = new JButton("No");
        panel_deposit = new JPanel();
        panel_deposit.setLayout(new GridLayout(3, 3, 5, 5));
        panel_deposit.add(new JLabel("Oh no! You lost most/all your money!"));
        panel_deposit.add(new JLabel("Deposit another $150 more?"));
        panel_deposit.add(new JLabel("Adds another $150"));
        panel_deposit.add(button_deposit_yes);
        panel_deposit.add(new JLabel("Exits the game"));
        panel_deposit.add(button_deposit_no);
        frame_deposit.add(panel_deposit);
        frame_deposit.pack();
        frame_deposit.setVisible(false);

        button_deposit_no.addActionListener(e ->{
            System.out.println("It took " + plays + " plays for you to loose $" + lmao + "!! Please do not gamble.");
            try {
                Files.writeString(Paths.get("stats.txt"), Main.stats + "\nFail: " + plays);
            } catch (IOException f) {
                f.printStackTrace();
            }
            System.exit(0);
        });

        button_deposit_yes.addActionListener(e -> {
            Main.wallet += 150f;
            Main.lmao += 150f;
            Main.updatestats();
            frame_deposit.setVisible(false);
        });

        frame_window = new JFrame("Limbo");
        frame_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Gaps of 10px horizontal and vertical make it look less cramped
        frame_window.setLayout(new BorderLayout(10, 10));

        frame_window.setMinimumSize(new Dimension(640, 480));
        frame_window.setDefaultLookAndFeelDecorated(true);

        // Menu setup (Kept the same)
        menu.add(menu_item_stat);
        menu.add(menu_item_opti);
        menu.add(menu_item_exit);
        menuBar.add(menu);
        frame_window.setJMenuBar(menuBar);

        // --- TOP SECTION ---
        JPanel top = new JPanel();
        // Make the font bigger so it stands out
        label_wallet.setFont(new Font("Arial", Font.BOLD, 24));
        top.add(label_wallet);

        // --- LEFT SIDE (CONTROLS) ---
        panel_left_side = new JPanel();
        // CHANGE 2: Use GridLayout (3 rows, 1 column) to stack these elements vertically
        panel_left_side.setLayout(new GridLayout(8, 1, 5, 5));

        button_play = new JButton("Play");
        button_cash_out = new JButton("Cash out");

        // Amount Panel (Inner panel for the text field and +/- buttons)
        JPanel amount_panel = new JPanel();
        textField_bet_amount_input = new JTextField("50.00", 5); // Added columns for size
        button_inc_bet = new JButton("x2");
        button_dec_bet = new JButton("1/2");

        amount_panel.add(textField_bet_amount_input);
        amount_panel.add(button_dec_bet);
        amount_panel.add(button_inc_bet);
        amount_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        // Add elements to the left panel
        // We wrap "Amount" in a JPanel to align it nicely if needed, or just add the label
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.add(new JLabel("Bet Amount:"));

        panel_left_side.add(labelPanel);
        panel_left_side.add(amount_panel);
        panel_left_side.add(button_play);
        panel_left_side.add(button_cash_out);

        // --- CENTER SECTION (GAME VISUALS) ---
        panel_center_right = new JPanel();
        // CHANGE 3: Use BorderLayout for the center panel
        panel_center_right.setLayout(new BorderLayout(10, 10));

        slider_win_result = new JSlider(JSlider.HORIZONTAL, 0, 100, 49);
        slider_win_result.setMajorTickSpacing(10);
        slider_win_result.setPaintTicks(true);
        slider_win_result.setPaintLabels(true); // Makes the slider look more "pro"

        slider_win_chance = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) win_chance);
        ColoredTrackSliderUI customUI2 = new ColoredTrackSliderUI(slider_win_chance, Color.RED, Color.GREEN);
        slider_win_chance.setUI(customUI2);
        slider_win_chance.setBackground(Color.BLACK); // Background behind the component
        slider_win_chance.setForeground(Color.WHITE); // Color of ticks and labels
        slider_win_chance.setMajorTickSpacing(10);
        slider_win_chance.setPaintTicks(true);

        JPanel panel_slider_vis = new JPanel();
        panel_slider_vis.setLayout(new GridLayout(3, 1, 5, 5));
        panel_slider_vis.add(slider_win_chance);
        panel_slider_vis.add(slider_win_result);

        label_slider_position = new JLabel("Result: " + ((int) slider_win_result.getValue()), JLabel.CENTER);
        label_slider_position.setFont(new Font("Arial", Font.BOLD, 20));

        // Bottom Stats Panel
        JPanel bottom_panel = new JPanel(new GridLayout(2, 3, 5, 5));
        // Styling headers
        JLabel h1 = new JLabel("Multiplier"); h1.setHorizontalAlignment(JLabel.CENTER);
        JLabel h2 = new JLabel("Roll Over");  h2.setHorizontalAlignment(JLabel.CENTER);
        JLabel h3 = new JLabel("Win Chance"); h3.setHorizontalAlignment(JLabel.CENTER);

        bottom_panel.add(h1);
        bottom_panel.add(h2);
        bottom_panel.add(h3);

        // Center align the values
        label_current_winnings.setHorizontalAlignment(JLabel.CENTER);
        label_current_rollover.setHorizontalAlignment(JLabel.CENTER);
        label_current_win_chance.setHorizontalAlignment(JLabel.CENTER);

        bottom_panel.add(label_current_winnings);
        bottom_panel.add(label_current_rollover);
        bottom_panel.add(label_current_win_chance);

        // Assemble Center Panel
        panel_center_right.add(label_slider_position, BorderLayout.NORTH);
        panel_center_right.add(panel_slider_vis, BorderLayout.CENTER);
        panel_center_right.add(bottom_panel, BorderLayout.SOUTH);

        //add logic to the buttons and elements
        button_play.addActionListener(e -> {
            Main.plays++;
            if (Main.play_amount > Main.wallet) {
                Main.play_amount = Main.wallet;
                textField_bet_amount_input.setText(Float.toString(Main.play_amount));
            }
            String in = textField_bet_amount_input.getText();
            try {
                if (!in.isBlank() || !in.isEmpty()) {
                    if (Float.parseFloat(in) <= 0f) {
                        label_system_message.setText("Bet is a zero. You cannot bet zero!");
                        return;
                    } else if (Float.parseFloat(in) > wallet) {
                        label_system_message.setText("Bet is too high for your wallet. Enter a number less than or equal to " + wallet + ", then press play.");
                        return;
                    }
                    play_amount = Float.parseFloat(in);
                }
            } catch (Exception f) {
                label_system_message.setText("Please input a valid number...");
                return;
            }
            // betting lmao
            float bet = rng.nextFloat() * 100f;
            slider_win_result.setValue((int) bet);
            Main.wallet -= Main.play_amount;
            if (bet > 100f - Main.win_chance) {
                Main.wallet += Main.play_amount * Main.winnings;
                customUI2.setFilledColor(Color.GREEN);
            } else {
                //Main.playWav("Bonk.wav");
                try {
                new ProcessBuilder("aplay", "Bonk.wav").start();
                } catch (Exception f) {}
                customUI2.setFilledColor(Color.RED);
            }
            updatestats();
            if (wallet <= 3f) {
                label_system_message.setText("Lmao, you lost all your money");
                frame_deposit.setVisible(true);
            }
        });
        button_inc_bet.addActionListener(e -> {
            Main.play_amount *= 2;
            if (Main.play_amount > Main.wallet)
                Main.play_amount = Main.wallet;
            textField_bet_amount_input.setText(Float.toString(Main.play_amount));
        });
        button_dec_bet.addActionListener(e -> {
            Main.play_amount /= 2;
            if (Main.play_amount < 1)
                Main.play_amount = 1;
            textField_bet_amount_input.setText(Float.toString(Main.play_amount));
        });

        button_cash_out.addActionListener(e -> {
            if (Main.wallet <= Main.lmao) {
                //wait! you still need to give chase to the money you left!
                frame_cashout_underwater_warning.setVisible(true);
            } else {
                System.out.println("You left the game with $" + Main.wallet);
                System.out.println("That is a difference of $" + (Main.wallet - Main.lmao) + " before you played!");
                System.out.println("Perhaps next time you can walk away with more money!");
                System.exit(0);
            }
        });

        slider_win_chance.addChangeListener(e -> {
            // Get value (invert so slider left = low chance, right = high chance, or however you prefer)
            // Let's assume the slider returns the Win Chance directly (0 to 100)
            float selectedChance = 100 - (float) slider_win_chance.getValue();

            // Clamp it to prevent division by zero or negative multipliers
            if (selectedChance < 0.01f) selectedChance = 0.01f;
            if (selectedChance > 98f) selectedChance = 98f; // Most sites cap at 98% or 99%

            Main.win_chance = selectedChance;

            // THE REAL FORMULA:
            // Multiplier = (100 - Edge) / WinChance
            Main.winnings = (100f - Main.HOUSE_EDGE) / Main.win_chance;

            // Rollover is usually just: 100 - WinChance (Use this for the < comparison)
            // OR: In some games, Rollover = 100 / Multiplier.
            // For standard Limbo: Target > Multiplier.
            // For standard Dice: Roll > (100 - WinChance).
            Main.rollover = 100f - Main.win_chance;

            Main.updatestats();
        });

        menu_item_exit.addActionListener(e -> {
            System.exit(0);
        });

        //create window and show it
        frame_window.add(top, BorderLayout.NORTH);
        frame_window.add(panel_left_side, BorderLayout.WEST);
        frame_window.add(panel_center_right);
        frame_window.pack();
        frame_window.setVisible(true);
    }

    public static void main(String[] args) {
        rng = new Random();
        try {
            stats = Files.readString(Paths.get("stats.txt"));
        } catch (Exception e) {
            System.out.println("Can not find 'stats.txt' in current directory. Make sure that its there then run it again");
            System.exit(1);
        }
        play_amount = 50;
        createWindow();
    }

    public static void updatestats() {
        label_current_winnings.setText(winnings + "X");
        label_current_rollover.setText(rollover + "@");
        label_current_win_chance.setText(win_chance + "%");
        label_system_message.setText("Click 'play' to play");
        label_slider_position.setText("Current position: " + ((int) slider_win_result.getValue()));
        label_wallet.setText("Wallet: " + wallet + " Total spent: " + lmao);
    }
    public static void playWav(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void say_leave_loss(float loss) {

    }
}
