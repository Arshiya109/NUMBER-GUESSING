import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class NumberGuessingGame extends JFrame {

    private int randomNumber;
    private int attemptsLeft;
    private int score;

    private final JTextField guessField = new JTextField(8);
    private final JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel attemptsLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel scoreLabel = new JLabel("", SwingConstants.CENTER);

    // Utility: Resize icons
    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }

    public NumberGuessingGame() {
        super("Number Guessing Game 🎮");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 550);
        setLocationRelativeTo(null);

        // Background image
        final Image background = (new ImageIcon("background.png")).getImage();

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (background != null) {
                    g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(25, 25, 112));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setOpaque(false);

        // Fonts
        Font titleFont = new Font("SansSerif", Font.BOLD, 30);
        Font labelFont = new Font("SansSerif", Font.BOLD, 20);

        // Title with icon
        JLabel title = new JLabel(" Guess The Number (1 - 100)", SwingConstants.CENTER);
        title.setFont(titleFont);
        title.setForeground(new Color(255, 255, 255));
        title.setIcon(resizeIcon("target.png", 32, 32)); 

        guessField.setFont(labelFont);
        guessField.setHorizontalAlignment(JTextField.CENTER);

        messageLabel.setFont(labelFont);
        messageLabel.setForeground(new Color(255, 215, 0)); // Gold-yellow

        attemptsLabel.setFont(labelFont);
        attemptsLabel.setForeground(new Color(255, 140, 0)); // Dark orange

        scoreLabel.setFont(labelFont);
        scoreLabel.setForeground(new Color(50, 255, 50)); // Bright green

        // Buttons with scaled icons
        JButton checkBtn = new JButton("Check");
        JButton playAgainBtn = new JButton("Play Again");

        checkBtn.setIcon(resizeIcon("check.png", 22, 22));    
        playAgainBtn.setIcon(resizeIcon("reload.png", 22, 22));

        styleButton(checkBtn);
        styleButton(playAgainBtn);


        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;  // prevents stretching
        mainPanel.add(title, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Your Guess:\n", SwingConstants.CENTER), gbc);

        gbc.gridx = 1;
        mainPanel.add(guessField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        mainPanel.add(checkBtn, gbc);

        gbc.gridy++;
        mainPanel.add(messageLabel, gbc);

        gbc.gridy++;
        mainPanel.add(attemptsLabel, gbc);

        gbc.gridy++;
        mainPanel.add(scoreLabel, gbc);

        gbc.gridy++;
        mainPanel.add(playAgainBtn, gbc);


        add(mainPanel);
        startNewGame();

        // Actions
        checkBtn.addActionListener(e -> checkGuess());
        guessField.addActionListener(e -> checkGuess());
        playAgainBtn.addActionListener(e -> startNewGame());
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setBackground(new Color(30, 144, 255));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void startNewGame() {
        Random r = new Random();
        randomNumber = r.nextInt(100) + 1;
        attemptsLeft = 10;
        messageLabel.setText("Enter your guess!");
        attemptsLabel.setText("Attempts Left: " + attemptsLeft);
        scoreLabel.setText("Score: " + score);
        guessField.setEnabled(true);
        guessField.setText("");
        guessField.requestFocusInWindow();
    }

    private void checkGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText().trim());
            attemptsLeft--;

            if (guess == randomNumber) {
                messageLabel.setText("✅ Correct! You win!");
                score += 10;
                scoreLabel.setText("Score: " + score);
                guessField.setEnabled(false);
            } else if (guess < randomNumber) {
                messageLabel.setText("📉 Too Low! Try Again.");
            } else {
                messageLabel.setText("📈 Too High! Try Again.");
            }

            if (attemptsLeft <= 0 && guess != randomNumber) {
                messageLabel.setText("❌ Game Over! Number was: " + randomNumber);
                guessField.setEnabled(false);
            }

            attemptsLabel.setText("Attempts Left: " + attemptsLeft);

        } catch (NumberFormatException e) {
            messageLabel.setText("⚠️ Please enter a valid number!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumberGuessingGame app = new NumberGuessingGame();
            app.setVisible(true);
        });
    }
}
