package com.example.rockpaperscissor;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rockpaperscissor.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView imagePlayer;
    private ImageView imageComputer;
    private Button btnRock;
    private Button btnPaper;
    private Button btnScissors;
    private Button btnReset;
    private TextView tvScore;

    private int playerScore = 0;
    private int computerScore = 0;

    private AnimationDrawable animationDrawable;
    private AnimationDrawable computerAnimationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagePlayer = findViewById(R.id.imagePlayer);
        imageComputer = findViewById(R.id.imageComputer);
        btnRock = findViewById(R.id.btnRock);
        btnPaper = findViewById(R.id.btnPaper);
        btnScissors = findViewById(R.id.btnScissors);
        btnReset = findViewById(R.id.btnReset);
        tvScore = findViewById(R.id.tvScore);

        btnRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame("rock");
            }
        });

        btnPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame("paper");
            }
        });

        btnScissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame("scissors");
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        // Initialize animation drawable
        // For the player's hand animation
        animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.hand_draw), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.left_hand_draw), 100); // Left shake frame
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.right_hand_draw), 100); // Right shake frame
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.hand_draw), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.left_hand_draw), 100); // Left shake frame
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.right_hand_draw), 100); // Right shake frame
        animationDrawable.setOneShot(true);

// For the computer's hand animation
        computerAnimationDrawable = new AnimationDrawable();
        computerAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.hand_draw), 100);
        computerAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.left_hand_draw), 100); // Left shake frame
        computerAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.right_hand_draw), 100); // Right shake
        computerAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.hand_draw), 100);
        computerAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.left_hand_draw), 100); // Left shake frame
        computerAnimationDrawable.addFrame(getResources().getDrawable(R.drawable.right_hand_draw), 100); // Right shake frame
        computerAnimationDrawable.setOneShot(true);
    }

    private void playGame(final String playerChoice) {
        Toast.makeText(MainActivity.this, "Rock Paper Scissor Shoot", Toast.LENGTH_SHORT).show();

        btnPaper.setEnabled(false);
        btnRock.setEnabled(false);
        btnScissors.setEnabled(false);
        btnReset.setEnabled(false);

        // Start animation for player
        imagePlayer.setImageDrawable(animationDrawable);
        animationDrawable.start();

        imageComputer.setImageDrawable(computerAnimationDrawable);
        computerAnimationDrawable.start();

        // Calculate the total duration of both animations
        int totalDuration = getTotalAnimationDuration(animationDrawable) + getTotalAnimationDuration(computerAnimationDrawable);

        // Wait for the animations to finish
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Get computer choice
                String[] choices = {"rock", "paper", "scissors"};
                Random random = new Random();
                String computerChoice = choices[random.nextInt(choices.length)];

                // Update player and computer images based on choices
                updateImages(playerChoice, computerChoice);

                // Determine winner and update scores
                String result = determineWinner(playerChoice, computerChoice);

                // Show winner toast
                String winnerMessage;
                if (result.equals("player")) {
                    playerScore++;
                    winnerMessage = "Player wins!";
                } else if (result.equals("computer")) {
                    computerScore++;
                    winnerMessage = "Computer wins!";
                } else {
                    winnerMessage = "It's a tie!";
                }
                tvScore.setText("Player: " + playerScore + "  Computer: " + computerScore);
                Toast.makeText(MainActivity.this, winnerMessage, Toast.LENGTH_SHORT).show();

                // Check if either player or computer has reached 10 points
                if (playerScore == 5) {
                    tvScore.setText("Congratulations! Player Wins!" );
                    endGame();
                    return;
                } else if (computerScore == 5) {
                    tvScore.setText("Congratulations! Computer Wins!");
                    endGame();
                    return;
                }

                // Enable buttons after animations and logic completion
                btnPaper.setEnabled(true);
                btnRock.setEnabled(true);
                btnScissors.setEnabled(true);
                btnReset.setEnabled(true);
            }
        }, totalDuration + 100); // Add a small extra delay to ensure animations complete

    }

    private void endGame() {
        // Disable buttons to prevent further play
        btnPaper.setEnabled(false);
        btnRock.setEnabled(false);
        btnScissors.setEnabled(false);
        btnReset.setEnabled(true); // Allow reset

        // You can add any additional end-game actions here
    }

    private int getTotalAnimationDuration(AnimationDrawable animationDrawable) {
        int duration = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            duration += animationDrawable.getDuration(i);
        }
        return duration;
    }


    private void updateImages(String playerChoice, String computerChoice) {
        int playerImageId = getImageId(playerChoice);
        int computerImageId = getImageId(computerChoice);
        animationDrawable.stop();
        computerAnimationDrawable.stop();
        imagePlayer.setImageResource(playerImageId);
        imageComputer.setImageResource(computerImageId);
    }

    private int getImageId(String choice) {
        switch (choice) {
            case "rock":
                return R.drawable.hand_draw;
            case "paper":
                return R.drawable.paper_draw;
            case "scissors":
                return R.drawable.scissor_draw;
            default:
                return R.drawable.hand_draw;
        }
    }

    private String determineWinner(String playerChoice, String computerChoice) {
        if (playerChoice.equals(computerChoice)) {
            return "tie";
        } else if ((playerChoice.equals("rock") && computerChoice.equals("scissors")) ||
                (playerChoice.equals("paper") && computerChoice.equals("rock")) ||
                (playerChoice.equals("scissors") && computerChoice.equals("paper"))) {
            return "player";
        } else {
            return "computer";
        }
    }

    private void resetGame() {
        playerScore = 0;
        computerScore = 0;
        tvScore.setText("Player: " + playerScore + "  Computer: " + computerScore);
        imagePlayer.setImageResource(R.drawable.hand_draw);
        imageComputer.setImageResource(R.drawable.hand_draw);
    }
}
