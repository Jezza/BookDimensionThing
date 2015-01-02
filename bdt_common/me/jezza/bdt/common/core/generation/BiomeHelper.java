package me.jezza.bdt.common.core.generation;

import me.jezza.bdt.common.lib.Reference;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenPlains;

public class BiomeHelper {

    private static BiomeGenBase customBiome;

    public static BiomeGenBase getBiome() {
        if (customBiome == null)
            customBiome = new BiomePlainsCustom(Reference.CUSTOM_BIOME_ID);
        return customBiome;
    }

    public static class BiomePlainsCustom extends BiomeGenPlains {
        public BiomePlainsCustom(int biomeID) {
            super(biomeID);
            setDisableRain();
            setBiomeName("Void");
        }
    }
}
