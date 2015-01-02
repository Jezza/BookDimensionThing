package me.jezza.bdt.common.core.teleport;

import me.jezza.oc.common.utils.CoordSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class TeleportHandler {

    public static boolean teleportTo(EntityPlayer entityPlayer, int targetDim, CoordSet coordSet) {
        if (entityPlayer.worldObj.isRemote || !(entityPlayer instanceof EntityPlayerMP))
            return false;

        int dimID = ((EntityPlayerMP) entityPlayer).dimension;
        VoidTeleporter teleporter = VoidTeleporter.createTeleporter(dimID, coordSet);
        teleporter.transferPlayerToDimension((EntityPlayerMP) entityPlayer, targetDim);
        return true;
    }

    public static CoordSet getDefaultSpawn() {
        return new CoordSet(0, 60, 0);
    }

}
