package me.sweetpickleswine.mcbotit.commands;

import baritone.api.BaritoneAPI;
import me.sweetpickleswine.mcbotit.Bin;
import me.sweetpickleswine.mcbotit.Client;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.json.JSONObject;

public class realisticRotate extends BaseCommand{
    @Override
    public void onExec(Client c, JSONObject job) {
        long stepTime = (long)(1000f/((float)MinecraftClient.getInstance().getCurrentFps())); // Only step once per frame
        Bin.instance.registerAndStartThread(new Thread(()->{
            PlayerEntity p = MinecraftClient.getInstance().player;

            float originalPitch = MathHelper.wrapDegrees(p.getPitch());
            float originalYaw = MathHelper.wrapDegrees(p.getYaw());
            float newPitch = MathHelper.wrapDegrees(job.getFloat("pitch"));
            float newYaw = MathHelper.wrapDegrees(job.getFloat("yaw"));

            float r_pitch = -(originalPitch - newPitch);
            float p_yaw1 = (originalYaw - newYaw) ;
            float p_yaw2 = -( 360-p_yaw1 ) % 360;
            float r_yaw;

            if (Math.abs(p_yaw1) < Math.abs(p_yaw2))
                r_yaw = p_yaw1;
            else
                r_yaw = p_yaw2;

            if (Math.abs(r_yaw) > 180)
                r_yaw =  r_yaw > 0 ? -(360-Math.abs(r_yaw)): (360-Math.abs(r_yaw));
            r_yaw = -r_yaw;




            long startTime =  System.currentTimeMillis();
            
            long durration = (long)(Math.abs(Math.abs(r_yaw)+Math.abs(r_pitch))/90f*job.getFloat("time_per_90")*1000);

            long lastTime = startTime;
            Bin.instance.lockScreen=true;
            while (System.currentTimeMillis() < durration+startTime ){
                if (job.has("x") && job.has("y") && job.has("z")){
                    BlockPos bp = new BlockPos(job.getInt("x"), job.getInt("y"), job.getInt("z"));
                    if (BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().isLookingAt(bp)){
                        Bin.instance.lockScreen=false;
                        return;
                    }
                }
                float diff = (System.currentTimeMillis()-lastTime)/((float)durration);
                lastTime=System.currentTimeMillis();

                p.setPitch(p.getPitch()+ r_pitch*diff);
                p.setYaw(p.getYaw()+ r_yaw*diff);



                try {Thread.sleep(stepTime);} catch (InterruptedException e) {return;}
            }
            p.setPitch(newPitch);
            p.setYaw(newYaw);
            Bin.instance.lockScreen=false;

        }));

    }
}
