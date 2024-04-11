package com.example.rockpaperscissor;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class RPSGame extends AppCompatActivity {

    private ImageView IconPlayer, IconComp;
    private Button btnRock, btnPaper, btnScissors;
    private TextView Score;

    private int playerScore = 0, compScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpsgame);

        IconPlayer = findViewById(R.id.imagePlayer);
        IconComp = findViewById(R.id.imageComputer);
        btnRock = findViewById(R.id.btnRock);
        btnPaper = findViewById(R.id.btnPaper);
        btnScissors = findViewById(R.id.btnScissors);
        Score = findViewById(R.id.Score);

        btnRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RPSGame("rock");
            }
        });

        btnPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RPSGame("paper");
            }
        });

        btnScissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RPSGame("scissors");
            }
        });
    }


    private void RPSGame(String choice) {

        String[] choices = {"rock", "paper", "scissors"};
        Random random = new Random();
        int compChoice = random.nextInt(3);
        String computerChoice = choices[compChoice];

        IconPlayer.setImageResource(R.drawable.hand_animation);
        IconComp.setImageResource(R.drawable.hand_animation);

        AnimationDrawable playerAnimation = (AnimationDrawable) IconPlayer.getDrawable();
        AnimationDrawable compAnimation = (AnimationDrawable) IconComp.getDrawable();

        playerAnimation.start();
        compAnimation.start();

        int animationDuration = 1000;
        IconPlayer.postDelayed(new Runnable() {
            @Override
            public void run() {
                playerAnimation.stop();
                compAnimation.stop();

                if (choice.equals("rock")) {
                    IconPlayer.setImageResource(R.drawable.rock);
                } else if (choice.equals("paper")) {
                    IconPlayer.setImageResource(R.drawable.paper);
                } else if (choice.equals("scissors")) {
                    IconPlayer.setImageResource(R.drawable.scissors);
                }

                if (computerChoice.equals("rock")) {
                    IconComp.setImageResource(R.drawable.rock);
                } else if (computerChoice.equals("paper")) {
                    IconComp.setImageResource(R.drawable.paper);
                } else if (computerChoice.equals("scissors")) {
                    IconComp.setImageResource(R.drawable.scissors);
                }


                String result = Winner(choice, computerChoice);
                TallyScore(result);
            }
        }, animationDuration);
    }



    private String Winner(String playerChoice, String computerChoice) {
        playerChoice = playerChoice.toLowerCase();
        computerChoice = computerChoice.toLowerCase();

        if (playerChoice.equals(computerChoice)) {
            return "Tie";
        } else if ((playerChoice.equals("rock") && computerChoice.equals("scissors")) ||
                (playerChoice.equals("paper") && computerChoice.equals("rock")) ||
                (playerChoice.equals("scissors") && computerChoice.equals("paper"))) {
            return "You win";
        } else {
            return "You Lose";
        }
    }

    private void TallyScore(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

        if (result.contains("You win")) {
            playerScore++;
        } else if (result.contains("You Lose")) {
            compScore++;
        }

        Score.setText("Computer: " + compScore + " | "+"Player: "+ + playerScore);
        if (playerScore == 5 || compScore == 5) {
            endGame();
        }
    }

    private void endGame() {
        String winner;

        if (playerScore == 3) {
            winner = "You win";
            AlertDialog.Builder builder = new AlertDialog.Builder(RPSGame.this);
            builder.setMessage("Player Wins   " +"\n"+ "Score: "+ compScore +" | "+playerScore );
            AlertDialog dialog = builder.create();
                    dialog.show();


        } else {
            winner = "Computer wins";
            AlertDialog.Builder builder = new AlertDialog.Builder(RPSGame.this);
            builder.setMessage("Computer Wins   " +"\n"+ "Score: "+ compScore +" | "+playerScore );
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        resetGame();
    }

    private void resetGame() {
        playerScore = 0;
        compScore = 0;
        Score.setText("Computer: 0 | Player: 0 ");
        IconPlayer.setImageResource(R.drawable.user);
        IconComp.setImageResource(R.drawable.comp);
    }

}
