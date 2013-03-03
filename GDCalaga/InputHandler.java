import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class InputHandler implements KeyListener {

    private boolean[] bufferKeyState = new boolean[KeyEvent.KEY_LAST + 1];
    private boolean[] currentKeyState = new boolean[KeyEvent.KEY_LAST + 1];
    private boolean[] lastKeyState = currentKeyState.clone();
    
    //TODO: Add states for mouse position and mouse buttons
    
    public void keyPressed(KeyEvent e)
    {
        bufferKeyState[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e)
    {
        bufferKeyState[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e) 
    {
        //Not sure what this method is supposed to do
    }
    
    public boolean IsKeyDown(int key)
    {
        if(key > KeyEvent.KEY_LAST){
            return false;
        } else {
            return currentKeyState[key];
        }
    }
    
    public boolean WasKeyPressed(int key)
    {
        if(key > KeyEvent.KEY_LAST){
            return false;
        } else {
            return currentKeyState[key] && !lastKeyState[key];
        }
    }
    
    public boolean WasKeyReleased(int key)
    {
        if(key > KeyEvent.KEY_LAST){
            return false;
        } else {
            return !currentKeyState[key] && lastKeyState[key];
        }
    }
    
    public void update()
    {
        lastKeyState = currentKeyState.clone();
        currentKeyState = bufferKeyState.clone();
    }

}
