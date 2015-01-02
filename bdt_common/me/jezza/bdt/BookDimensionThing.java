package me.jezza.bdt;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import me.jezza.bdt.common.CommonProxy;
import me.jezza.bdt.common.ModItems;
import me.jezza.bdt.common.core.command.CommandTPX;
import me.jezza.bdt.common.core.generation.WorldProviderVoid;
import me.jezza.bdt.common.lib.Reference;
import me.jezza.oc.api.configuration.Config;
import net.minecraftforge.common.DimensionManager;

@Config.Controller
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME)
public class BookDimensionThing {

    @Mod.Instance(Reference.MOD_ID)
    public static BookDimensionThing instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModItems.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.initServerSide();
        proxy.initClientSide();

        DimensionManager.registerProviderType(Reference.CUSTOM_DIMENSION_ID, WorldProviderVoid.class, true);
        DimensionManager.registerDimension(Reference.CUSTOM_DIMENSION_ID, Reference.CUSTOM_DIMENSION_ID);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Mod.EventHandler
    public void initServer(FMLServerStartingEvent event) {
        new CommandTPX("tpx", "[dimID] [x] [y] [z] [generatePlatform]");
    }

}
