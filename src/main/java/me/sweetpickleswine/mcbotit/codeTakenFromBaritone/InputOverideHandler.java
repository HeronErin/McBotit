package me.sweetpickleswine.mcbotit.codeTakenFromBaritone;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;

import java.util.HashMap;
import java.util.Map;

public class InputOverideHandler {
    BlockBreakHelper blockBreakHelper;
    BlockPlaceHelper blockPlaceHelper;
    public InputOverideHandler(){
        blockBreakHelper = new BlockBreakHelper();
        blockPlaceHelper = new BlockPlaceHelper();
    }
    private final Map<Input, Boolean> inputForceStateMap = new HashMap();


    public final boolean isInputForcedDown(Input input) {
        return input != null && this.inputForceStateMap.getOrDefault(input, false);
    }

    public final void setInputForceState(Input input, boolean forced) {
        this.inputForceStateMap.put(input, forced);
    }

    public final void clearAllKeys() {
        this.inputForceStateMap.clear();
    }

    public final void onTick() {
        if (MinecraftClient.getInstance().player == null)
            return;


        if (this.isInputForcedDown(Input.CLICK_LEFT)) {
            this.setInputForceState(Input.CLICK_RIGHT, false);
        }

            this.blockBreakHelper.tick(this.isInputForcedDown(Input.CLICK_LEFT));
            this.blockPlaceHelper.tick(this.isInputForcedDown(Input.CLICK_RIGHT));
        if (this.inControl()) {
            if (MinecraftClient.getInstance().player.input.getClass() != PlayerMovementInput.class) {
                MinecraftClient.getInstance().player.input = new PlayerMovementInput(this);
            }
        } else if (MinecraftClient.getInstance().player.input.getClass() == PlayerMovementInput.class) {
            MinecraftClient.getInstance().player.input = new KeyboardInput(MinecraftClient.getInstance().options);
        }


    }

    private boolean inControl() {
        for (Input input : new Input[]{Input.MOVE_FORWARD, Input.MOVE_BACK, Input.MOVE_LEFT, Input.MOVE_RIGHT, Input.SNEAK}) {
            if (isInputForcedDown(input)) {
                return true;
            }
        }
        return false;
    }


}
