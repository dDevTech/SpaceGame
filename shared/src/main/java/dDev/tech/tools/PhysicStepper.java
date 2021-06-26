package dDev.tech.tools;

import com.badlogic.gdx.physics.box2d.World;
import dDev.tech.constants.Constants;


public class PhysicStepper {
    private float accumulator = 0;

    public void doPhysicsStep(float deltaTime, World world) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= Constants.TIME_STEP) {

            world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
            accumulator -= Constants.TIME_STEP;
        }
    }
}
