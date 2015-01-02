package me.jezza.bdt.common.items;

import me.jezza.bdt.common.core.Utils;
import me.jezza.bdt.common.core.teleport.TeleportHandler;
import me.jezza.bdt.common.lib.Reference;
import me.jezza.oc.common.utils.CoordSet;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

public class ItemBookThingy extends ItemBDT {

    public ItemBookThingy(String name) {
        super(name);
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack itemStack) {
        if (!(entityLiving instanceof EntityPlayer) || entityLiving.worldObj.isRemote || !entityLiving.isSneaking())
            return false;
        confirmTagCompound(itemStack);
        EntityPlayer player = (EntityPlayer) entityLiving;
        CoordSet coordSet = getCustomSpawn(itemStack, entityLiving.dimension);
        if (coordSet == null)
            player.addChatComponentMessage(new ChatComponentText("No custom spawn set."));
        else
            player.addChatComponentMessage(new ChatComponentText("Custom spawn @ " + coordSet.toString()));
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote)
            return itemStack;
        confirmTagCompound(itemStack);
        if (player.isSneaking()) {
            if (setCustomSpawn(itemStack, player)) {
                CoordSet coordSet = getCustomSpawn(itemStack, player.dimension);
                player.addChatComponentMessage(new ChatComponentText("Succeeded to set spawn to " + coordSet.toString()));
            } else
                player.addChatComponentMessage(new ChatComponentText("Failed to set spawn."));
        } else
            toggleDimension(player, itemStack);
        return itemStack;
    }

    private boolean setCustomSpawn(ItemStack itemStack, EntityPlayer player) {
        NBTTagCompound nbt = itemStack.getTagCompound();
        MovingObjectPosition mop = Utils.getCurrentMovingObjectPosition(player);
        if (mop == null)
            return false;
        if (mop.sideHit != 1) {
            player.addChatComponentMessage(new ChatComponentText("Must click on top of the block."));
            return false;
        }
        String tagName = createTagName(player.dimension);
        if (tagName.equals(""))
            return false;
        CoordSet coordSet = new CoordSet(mop.blockX, mop.blockY + 1, mop.blockZ);
        nbt.setIntArray(tagName, coordSet.asArray());
        return true;
    }

    private CoordSet getCustomSpawn(ItemStack itemStack, int currentDim) {
        NBTTagCompound nbt = itemStack.getTagCompound();
        String tagName = createTagName(currentDim);
        if (tagName.equals(""))
            return null;
        int[] coordArray = nbt.getIntArray(tagName);
        CoordSet coordSet = null;
        try {
            coordSet = new CoordSet(coordArray);
        } catch (Exception e) {
        }
        return coordSet;
    }

    private String createTagName(int dimID) {
        if (DimensionManager.getWorld(dimID) == null)
            DimensionManager.createProviderFor(dimID);
        String tagName = "";
        try {
            tagName = "CDS:" + DimensionManager.getProvider(dimID).getDimensionName();
        } catch (Exception e) {
        }
        return tagName;
    }

    public void toggleDimension(EntityPlayer player, ItemStack itemStack) {
        NBTTagCompound nbt = itemStack.getTagCompound();

        boolean insideVoid = player.dimension == Reference.CUSTOM_DIMENSION_ID;
        int targetDim = insideVoid ? nbt.getInteger("prevDimension") : Reference.CUSTOM_DIMENSION_ID;
        if (!insideVoid)
            nbt.setInteger("prevDimension", player.dimension);

        CoordSet coordSet = getSpawnPointForDim(itemStack, targetDim);
        TeleportHandler.teleportTo(player, targetDim, coordSet);
        generatePlatform(player.worldObj, coordSet);
    }

    private CoordSet getSpawnPointForDim(ItemStack itemStack, int targetDim) {
        CoordSet coordSet = getCustomSpawn(itemStack, targetDim);
        if (coordSet == null) {
            WorldProvider provider = DimensionManager.getProvider(targetDim);
            coordSet = CoordSet.fromChunkCoordinates(provider.getRandomizedSpawnPoint());
            if (targetDim == Reference.CUSTOM_DIMENSION_ID)
                coordSet.setY(60);
        }
        return coordSet;
    }

    private void generatePlatform(World world, CoordSet coordSet) {
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

    private void confirmTagCompound(ItemStack itemStack) {
        if (!itemStack.hasTagCompound())
            itemStack.setTagCompound(new NBTTagCompound());
    }
}
