package me.jezza.bdt.common.core.generation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldProviderVoid  extends WorldProvider {

    public WorldProviderVoid() {
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkGeneratorVoid(worldObj);
    }

    @Override
    public String getDepartMessage() {
        return "P'Li returns...";
    }

    @Override
    public String getWelcomeMessage() {
        return "Become one with the void...";
    }

    @Override
    public BiomeGenBase getBiomeGenForCoords(int x, int z) {
        return BiomeHelper.getBiome();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
        return 600000F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean getWorldHasVoidParticles() {
        return false;
    }

    @Override
    public boolean canDoLightning(Chunk chunk) {
        return false;
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return false;
    }

    @Override
    public boolean canSnowAt(int x, int y, int z, boolean checkLight) {
        return false;
    }

    @Override
    public String getDimensionName() {
        return "VoidDimension";
    }

}