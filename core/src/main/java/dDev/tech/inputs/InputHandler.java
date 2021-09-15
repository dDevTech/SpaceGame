package dDev.tech.inputs;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.github.czyzby.websocket.serialization.Transferable;
import dDev.tech.entities.Packets;
import dDev.tech.screens.SpaceGame;
import dDev.tech.serialized.EntityPacket;

public class InputHandler implements InputProcessor {
    SpaceGame core;
    private boolean[]movements = new boolean[4];
    public InputHandler(SpaceGame core){
        this.core = core;
    }
    @Override
    public boolean keyDown(int keycode) {

        if(keycode== Input.Keys.W) movements[0]=true;
        if(keycode== Input.Keys.A) movements[1]=true;
        if(keycode== Input.Keys.S) movements[2]=true;
        if(keycode== Input.Keys.D) movements[3]=true;
        Transferable inputData = core.mainPlayer.onKeyMovements(movements);
        core.getConnection().getSocket().send(core.getConnection().manual.serialize(inputData));
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode== Input.Keys.W) movements[0]=false;
        if(keycode== Input.Keys.A) movements[1]=false;
        if(keycode== Input.Keys.S) movements[2]=false;
        if(keycode== Input.Keys.D) movements[3]=false;
        Transferable inputData = core.mainPlayer.onKeyMovements(movements);
        core.getConnection().getSocket().send(core.getConnection().manual.serialize(inputData));

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
        return movements[1];
    }

    public boolean isUp() {
        return movements[0];
    }

    public boolean isRight() {
        return movements[3];
    }
    public boolean[] getMovement(){
        return movements;
    }

    public boolean isDown() {
        return movements[2];
    }
}
