package com.mec.app;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.mec.engine.Camera;
import com.mec.engine.Colors;
import com.mec.engine.GameContainer;
import com.mec.engine.GameObject;
import com.mec.engine.Input;
import com.mec.engine.MecEngineApp;
import com.mec.engine.Renderer;
import com.mec.engine.gfx.Image;
import com.mec.engine.gfx.ImageTile;
import com.mec.engine.gfx.Light;
import com.mec.engine.audio.SoundClip;
import com.mec.engine.gfx.Font;;

/** Represents a game using the engine. */
public class Application extends MecEngineApp 
{
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();

    public Application()
    {
        objects.add(new Player(2, 2));
    }

    @Override
    public void init(GameContainer gc) 
    {
        gc.getRenderer().setAmbientColor(Colors.WHITE);
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) 
    {
        for (GameObject gameObject : objects) 
        {
            gameObject.render(gc, renderer);
        }
    }

    @Override
    public void update(GameContainer gc, float deltaTime) 
    {
        for(int i = 0; i < objects.size(); i++)
        {
            objects.get(i).update(gc, this, deltaTime);
            if(objects.get(i).isDead()) 
            {
                objects.remove(i);
                i--;
            }
        }

    }

    /**Adds an object to the objects array. */
    public void addObject(GameObject obj)
    {
        objects.add(obj);
    }

    /**Get object by tag. */
    public GameObject getObject(String tag)
    {
        for (int i = 0; i < objects.size(); i++) 
        {
            if(objects.get(i).getTag().equals(tag)) return objects.get(i); 
        }

        return null; //Didn't find the object
    }

    public static void main(String args[])
    {
        GameContainer gc = new GameContainer(new Application());

        gc.setRenderResolution(960, 540);
        gc.setWindowScale(1.0f);

        gc.setFramerateLock(true);
        gc.setWindowTitle("Desafio Hardware Garaio");

        gc.start();
    }

}
