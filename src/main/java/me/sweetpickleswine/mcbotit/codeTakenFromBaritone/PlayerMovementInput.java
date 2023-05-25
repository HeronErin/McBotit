package me.sweetpickleswine.mcbotit.codeTakenFromBaritone;


public class PlayerMovementInput extends net.minecraft.client.input.Input {
    private final InputOverideHandler handler;

    PlayerMovementInput(InputOverideHandler handler) {
        this.handler = handler;
    }

    public void tick(boolean p_225607_1_, float f) {
        this.movementSideways = 0.0F;
        this.movementForward = 0.0F;
        this.jumping = this.handler.isInputForcedDown(Input.JUMP);
        if (this.pressingForward = this.handler.isInputForcedDown(Input.MOVE_FORWARD)) {
            ++this.movementForward;
        }

        if (this.pressingBack = this.handler.isInputForcedDown(Input.MOVE_BACK)) {
            --this.movementForward;
        }

        if (this.pressingLeft = this.handler.isInputForcedDown(Input.MOVE_LEFT)) {
            ++this.movementSideways;
        }

        if (this.pressingRight = this.handler.isInputForcedDown(Input.MOVE_RIGHT)) {
            --this.movementSideways;
        }

        if (this.sneaking = this.handler.isInputForcedDown(Input.SNEAK)) {
            this.movementSideways = (float) ((double) this.movementSideways * 0.3);
            this.movementForward = (float) ((double) this.movementForward * 0.3);
        }

    }
}
