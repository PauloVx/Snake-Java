package com.mec.app;

import java.util.Random;

import com.mec.engine.Colors;
import com.mec.engine.GameContainer;
import com.mec.engine.GameObject;
import com.mec.engine.Renderer;
import com.mec.engine.audio.SoundClip;
import com.mec.engine.gfx.Light;

public class Food extends GameObject
{
    private Random random = new Random();

    public Food()
    {
        this.tag = "food";
        randomizePosition();
        this.width = 10;
        this.height = 10;
    }

    @Override
    public void update(GameContainer gc, Application app, float deltaTime) 
    {
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) 
    {
        renderer.drawFilledRect(Colors.ORANGE, (int)posX, (int)posY, width, height);
    }

    //Moves the food to a new random pos.
    public void randomizePosition()
    {
        this.posX = random.nextInt(960 - 15);
        this.posY = random.nextInt(540 - 15);
    }
}