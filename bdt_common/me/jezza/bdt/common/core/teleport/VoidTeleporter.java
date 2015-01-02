package me.jezza.bdt.common.core.teleport;

import me.jezza.oc.common.utils.CoordSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class VoidTeleporter extends Teleporter {

    private CoordSet targetSet;

    public VoidTeleporter(WorldServer worldServer, CoordSet targetSet) {
        super(worldServer);
        this.targetSet = targetSet;
    }

    @Override
    public void placeInPortal(Entity entity, double x, double y, double z, float par8) {
        if (!(entity instanceof EntityPlayerMP))
            return;
        EntityPlayerMP player = (EntityPlayerMP) entity;

        double posX = targetSet.getX() + 0.5F;
        double posY = targetSet.getY() + 1;
        double posZ = targetSet.getZ() + 0.5F;

        player.playerNetServerHandler.setPlayerLocation(posX, posY, posZ, player.rotationYaw, player.rotationPitch);
        player.fallDistance = 0;
    }

    public void transferPlayerToDimension(EntityPlayerMP player, int dimID) {
        MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, dimID, this);
    }

    public static VoidTeleporter createTeleporter(int dimID, CoordSet targetSet) {
        return new VoidTeleporter(MinecraftServer.getServer().worldServerForDimension(dimID), targetSet);
    }
}
