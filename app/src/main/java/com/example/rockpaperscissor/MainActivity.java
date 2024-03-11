package com.example.rockpaperscissor;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
        // Start animation for player
        imagePlayer.setImageDrawable(animationDrawable);
        animationDrawable.start();

        imageComputer.setImageDrawable(computerAnimationDrawable);
        computerAnimationDrawable.start();

        // Wait for the animation to finish
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Stop animation
                animationDrawable.stop();
                computerAnimationDrawable.stop();

                // Get computer choice
                String[] choices = {"rock", "paper", "scissors"};
                Random random = new Random();
                String computerChoice = choices[random.nextInt(choices.length)];

                // Update player and computer images based on choices
                updateImages(playerChoice, computerChoice);

                // Determine winner and update scores
                String result = determineWinner(playerChoice, computerChoice);
                if (result.equals("player")) {
                    playerScore++;
                } else if (result.equals("computer")) {
                    computerScore++;
                }

                // Update score text view
                tvScore.setText("Score: Player " + playerScore + " - Computer " + computerScore);
            }
        }, 3000); // Wait for 3 seconds for animation to complete
    }

    private void updateImages(String playerChoice, String computerChoice) {
        int playerImageId = getImageId(playerChoice);
        int computerImageId = getImageId(computerChoice);
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
        tvScore.setText("Score: Player " + playerScore + " - Computer " + computerScore);
        imagePlayer.setImageResource(R.drawable.hand_draw);
        imageComputer.setImageResource(R.drawable.hand_draw);
    }
}
