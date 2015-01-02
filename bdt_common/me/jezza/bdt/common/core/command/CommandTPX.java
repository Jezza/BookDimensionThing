package me.jezza.bdt.common.core.command;

import me.jezza.bdt.common.core.teleport.TeleportHandler;
import me.jezza.oc.common.core.command.CommandAbstract;
import me.jezza.oc.common.utils.CoordSet;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class CommandTPX extends CommandAbstract {

    public CommandTPX(String commandName, String commandUsage) {
        super(commandName, commandUsage);
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        if (!(commandSender instanceof EntityPlayer)) {
            sendChatMessage(commandSender, "Must be in game.");
            return;
        }
        EntityPlayer player = (EntityPlayer) commandSender;
        int dimID = args.length > 0 ? parsePotential(args[0], player.dimension) : player.dimension;

        if (!DimensionManager.isDimensionRegistered(dimID)) {
            sendChatMessage(commandSender, "Dimension not found: " + dimID);
            return;
        }

        CoordSet coordSet = getSpawnPoint(dimID);
        int x = 0;
        int y = 0;
        int z = 0;
        boolean generatePlatform = false;

        int[] coordArray = coordSet.asArray();
        for (int i = 0; i < 3; i++) {
            if (args.length <= i)
                break;
            if (!args[i + 1].equals("~"))
                coordArray[i] = parseInt(args[i + 1]);
        }
        coordSet = new CoordSet(coordArray);

        if (args.length >= 4)
            generatePlatform = parseBoolean(args[4]);
        teleport(player, dimID, coordSet, generatePlatform);
        sendChatMessage(commandSender, "Teleported to " + coordSet);
    }

    private void teleport(EntityPlayer player, int dimID, CoordSet coordSet, boolean generatePlatform) {
        TeleportHandler.teleportTo(player, dimID, coordSet);
        if (generatePlatform) {
            try {
                generatePlatform(dimID, coordSet);
            } catch (Exception e) {
            }
        }
    }

    private int parsePotential(String line, int num) {
        return line.equals("~") ? num : Integer.parseInt(line);
    }

    private void generatePlatform(int dimID, CoordSet coordSet) {
        World world = DimensionManager.getWorld(dimID);
        int x = coordSet.getX();
        int y = coordSet.getY() - 1;
        int z = coordSet.getZ();
        if (!world.isAirBlock(x, y, z))
            return;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int tempX = x + i;
                int tempZ = z + j;
                if (world.isAirBlock(tempX, y, tempZ))
                    world.setBlock(tempX, y, tempZ, Blocks.stone, 0, 3);
            }
        }
    }

    private CoordSet getSpawnPoint(int dimID) {
        WorldServer world = DimensionManager.getWorld(dimID);
        CoordSet coordSet = CoordSet.fromChunkCoordinates(world.getSpawnPoint());
        loopUpForAir(world, coordSet);
        return coordSet;
    }

    private void loopUpForAir(World world, CoordSet coordSet) {
        int y = coordSet.getY();
        while (!(world.isAirBlock(coordSet.getX(), y, coordSet.getZ()) || world.isAirBlock(coordSet.getX(), y + 1, coordSet.getZ())))
            y += 1;
        coordSet.setY(y);
    }

}
