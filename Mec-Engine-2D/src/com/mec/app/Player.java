package com.mec.app;

import com.mec.engine.*;
import com.mec.engine.audio.SoundClip;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class Player extends GameObject 
{
    private Food food;
    private SoundClip pickupSound;
    private Direction direction = Direction.NONE;

    private float speed = 2f;
    private float speedX = 0;
    private float speedY = 0;

    /*To avoid adding the food to the objects list more than one time,
    which will destroy the framerate.*/
    private boolean alreadyAdded = false;
    private boolean debug = false;
    private boolean gameOver = false;

    //Snake always starts with 3 pieces.
    private int numberOfPieces = 3;
    private int score = 0;

    //All snake pieces.
    private ArrayList<Piece> pieces = new ArrayList<>();

    public Player(int posX, int posY)
    {
        this.tag = "snake";
        this.posX = posX * 32;
        this.posY = posY * 32;
        this.width = 10;
        this.height = 10;

        //Adding the the initial pieces.
        for (int i = 0; i < numberOfPieces; i++) pieces.add(new Piece(10, 10, Colors.WHITE));

        food = new Food();
        pickupSound = new SoundClip("/res/Audio/pickup.wav");
    }

    @Override
    public void update(GameContainer gc, Application app, float deltaTime)
    {
        posX += speedX;
        posY += speedY;

        //Debug info switch.
        if(gc.getInput().isKeyDown(Input.KEY_CTRL)) debug = true;
        if(gc.getInput().isKeyDown(Input.KEY_SHIFT)) debug = false;

        int i = 1;
        if((gc.getInput().isKeyPressed(Input.KEY_A) || gc.getInput().isKeyPressed(Input.KEY_LEFT)) && !gameOver)
        {
            speedX = -speed;
            speedY = 0;
            posX -= deltaTime * speed;
            direction = Direction.LEFT;
            for (Piece piece : pieces)
            {
                piece.setDirection(Direction.LEFT);

            }
        }

        if((gc.getInput().isKeyPressed(Input.KEY_D) || gc.getInput().isKeyPressed(Input.KEY_RIGHT)) && !gameOver)
        {
            speedX = speed;
            speedY = 0;
            posX += deltaTime * speed;
            direction = Direction.RIGHT;
            for (Piece piece : pieces) piece.setDirection(Direction.RIGHT);
        }

        if((gc.getInput().isKeyPressed(Input.KEY_S) || gc.getInput().isKeyPressed(Input.KEY_DOWN)) && !gameOver)
        {
            speedY = speed;
            speedX = 0;
            posY += deltaTime * speed;
            direction = Direction.DOWN;
            for (Piece piece : pieces) piece.setDirection(Direction.DOWN);
        }

        if((gc.getInput().isKeyPressed(Input.KEY_W) || gc.getInput().isKeyPressed(Input.KEY_UP)) && !gameOver)
        {
            speedY = -speed;
            speedX = 0;
            posY -= deltaTime * speed;
            direction = Direction.UP;
            for (Piece piece : pieces) piece.setDirection(Direction.UP);
        }

        if(posY >= gc.getWindowHeight() - 10)
        {
            posY = gc.getWindowHeight() - 10; //Ground collision
            gameOver = true;
        }

        if(posY <= 0)
        {
            posY = 0; //Sky collision
            gameOver = true;
        }

        if(posX >= gc.getWindowWidth() - 10)
        {
            posX = gc.getWindowWidth() - 10; //Right collision
            gameOver = true;
        }

        if(posX <= 0)
        {
            posX = 0; //Left collision
            gameOver = true;
        }

        //Restarting.
        if(gameOver && gc.getInput().isKeyPressed(Input.KEY_R)) restart(gc);

        //Adding the food to the GameObject list.
        if(!alreadyAdded)
        {
            app.addObject(food);
            alreadyAdded = true;
        }

        if(eat())
        {
            food.randomizePosition();

            score += 100;
            speed += 0.2;

            //New piece every time the player eats.
            pieces.add(new Piece(10, 10, Colors.WHITE));

            pickupSound.playAudio();
        }

        for (Piece piece : pieces)
        {
            switch (piece.getDirection())
            {
                case UP:
                {

                }
                case DOWN:
                {

                }
                case LEFT:
                {

                }
                case RIGHT:
                {

                }
            }
        }
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) 
    {
        if(gameOver)
        {
            speed = 0;
            speedX = 0;
            speedY = 0;
            renderer.drawRect(Colors.RED, (gc.getWindowWidth() / 2) - 55, (gc.getWindowHeight() / 2) - 8, 150, 80);
            renderer.drawText("GAME OVER", (gc.getWindowWidth() / 2) - 30, gc.getWindowHeight() / 2, Colors.RED);
            renderer.drawText("SCORE: " + score, (gc.getWindowWidth() / 2) - 30, gc.getWindowHeight() - 250, Colors.RED);
            renderer.drawText("RESTART: R", (gc.getWindowWidth() / 2) - 30, gc.getWindowHeight() - 220, Colors.RED);
        }

        //Rendering pieces behind the snake.
        int amountPieces = 0;
        int piecePosX = 10;
        for ( Piece piece : pieces)
        {
            renderer.drawFilledRect(piece.getColor(), (int)posX - piecePosX, (int)posY, piece.getWidth(), piece.getHeight());
            amountPieces++;
            piecePosX += 10;
        }

        //Red Rectangle around the map.
        renderer.drawRect(Colors.RED, 0, 0, gc.getWindowWidth() - 1, gc.getWindowHeight() - 1);

        //Player
        renderer.drawFilledRect(Colors.RED, (int)posX, (int)posY, width, height);

        //Score
        renderer.drawText("Score: " + score, 2, gc.getWindowHeight() - 17, Colors.WHITE);

        //Show debug stuff.
        if(debug)
        {
            renderer.drawText("Player X: " + (int) posX + " Y: " + (int) posY, 2, 15, Colors.YELLOW);
            renderer.drawText("Food X: " + (int)food.getPosX() + " Y: " + (int)food.getPosY(), 2, 30, Colors.YELLOW);
            renderer.drawText("Distance to food: " + getDistance(posX, posY, food.getPosX(), food.getPosY()), 2, 45, Colors.YELLOW);
            renderer.drawText("Speed: " + speed, 2, 60, Colors.YELLOW);
            renderer.drawText("Amount of Pieces: " + amountPieces, 2, 75, Colors.YELLOW);
            renderer.drawText("Game Over: " + gameOver, 2, 90, Colors.YELLOW);
            renderer.drawText("Sound File SRC: " + pickupSound.getPath(), 2, 105, Colors.YELLOW);
            renderer.drawText("Head Direction: " + direction, 2, 120, Colors.YELLOW);

            int i = 0;
            int textOffset = 135;
            for (Piece piece : pieces)
            {
                i++;
                renderer.drawText("Piece [" + i + "] Direction: " + piece.getDirection(), 2, textOffset, Colors.YELLOW);
                textOffset += 15;
            }

        }
    }

    //True when the distance to food is less than or equal to 9.5.
    private boolean eat()
    {
        if(getDistance(posX, posY, food.getPosX(), food.getPosY()) <= 9.5) return true;
        return false;
    }

    //Get distance to food based on pythagorean theorem.
    private double getDistance(float x1, float y1, float x2, float y2)
    {
        float xDistance = x2 - x1;
        float yDistance = y2 - y1;

        double result = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

        return result;
    }

    //Restart func.
    //Passing the container to get access to window size.
    private void restart(GameContainer gc)
    {
        gameOver = false;
        score = 0;
        posX = gc.getWindowWidth() / 2;
        posY = gc.getWindowHeight() / 2;
        speedX = 0;
        speedY = 0;
        speed = 2f;
        food.randomizePosition();
        direction = Direction.NONE;

        pieces.clear();

        //Re-adding the the initial pieces.
        for (int i = 0; i < numberOfPieces; i++) pieces.add(new Piece(10, 10, Colors.WHITE));
    }
}