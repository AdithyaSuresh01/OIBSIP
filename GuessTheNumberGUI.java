import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GuessTheNumberGUI extends JFrame {
    private JTextField userInput;
    private JButton guessButton;
    private JLabel messageLabel;
    private JLabel scoreLabel;
    private int numberToGuess;
    private int attemptsLeft = 10;
    private int score = 0;

    public GuessTheNumberGUI() {
        super("Guess the Number Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new FlowLayout());

        userInput = new JTextField(10);
        guessButton = new JButton("Guess");
        messageLabel = new JLabel("Enter a number between 1 and 100:");
        scoreLabel = new JLabel("Score: 0");

        add(messageLabel);
        add(userInput);
        add(guessButton);
        add(scoreLabel);

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int guess = Integer.parseInt(userInput.getText());
                    if (guess < 1 || guess > 100) {
                        JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 100!");
                        return;
                    }

                    attemptsLeft--;
                    if (guess < numberToGuess) {
                        messageLabel.setText("Too low! Try again:");
                    } else if (guess > numberToGuess) {
                        messageLabel.setText("Too high! Try again:");
                    } else {
                        score += 10 + attemptsLeft; // Increase score
                        messageLabel.setText("Correct! Start a new game:");
                        resetGame();
                    }
                    scoreLabel.setText("Score: " + score);
                    if (attemptsLeft <= 0) {
                        JOptionPane.showMessageDialog(null, "You're out of attempts! Number was: " + numberToGuess);
                        resetGame();
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a number.");
                }
            }
        });

        resetGame();
    }

    private void resetGame() {
        Random random = new Random();
        numberToGuess = random.nextInt(100) + 1;
        attemptsLeft = 10;
        userInput.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GuessTheNumberGUI game = new GuessTheNumberGUI();
                game.setVisible(true);
            }
        });
    }
}