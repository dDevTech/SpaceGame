package dDev.tech.inputs;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public abstract class InputHandler implements InputProcessor {
    private boolean up,left,right,down;
    public abstract void onUpdate();
    @Override
    public boolean keyDown(int keycode) {

        if(keycode== Input.Keys.W) up=true;
        if(keycode== Input.Keys.S) down=true;
        if(keycode== Input.Keys.A) left=true;
        if(keycode== Input.Keys.D) right=true;
        onUpdate();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode== Input.Keys.W) up=false;
        if(keycode== Input.Keys.S) down=false;
        if(keycode== Input.Keys.A) left=false;
        if(keycode== Input.Keys.D) right=false;
        onUpdate();
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isDown() {
        return down;
    }
}
