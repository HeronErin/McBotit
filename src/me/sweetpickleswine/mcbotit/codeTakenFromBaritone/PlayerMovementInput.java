/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

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


        /// This just looks wrong, but it is right
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
