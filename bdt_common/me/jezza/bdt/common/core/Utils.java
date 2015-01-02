package me.jezza.bdt.common.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Utils {

    public static MovingObjectPosition getCurrentMovingObjectPosition(EntityPlayer player) {
        double distance = player.capabilities.isCreativeMode ? 5.0F : 4.5F;
        Vec3 posVec = Vec3.createVectorHelper(player.posX, player.posY + player.eyeHeight , player.posZ);
        Vec3 lookVec = player.getLook(1);
        lookVec = posVec.addVector(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance);
        return player.worldObj.rayTraceBlocks(posVec, lookVec);
    }

}
