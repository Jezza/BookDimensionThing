package me.jezza.bdt.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import me.jezza.bdt.common.lib.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBDT extends Item {

    public ItemBDT(String name) {
        setName(name);
        setCreativeTab(CreativeTabs.tabMisc);
        register(name);
    }

    private void setName(String name) {
        setUnlocalizedName(name);
        setTextureName(name);
    }

    public void register(String name) {
        GameRegistry.registerItem(this, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Reference.MOD_IDENTIFIER + getIconString());
    }

}
