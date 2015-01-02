package me.jezza.bdt.common.lib;

import me.jezza.oc.api.configuration.Config.ConfigInteger;

public class Reference {

    public static final String MOD_ID = "BookDimensionThing";
    public static final String MOD_NAME = "Book Dimension Thing";

    public static final String MOD_IDENTIFIER = MOD_ID + ":";

    public static final String SERVER_PROXY_CLASS = "me.jezza.bdt.common.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "me.jezza.bdt.client.ClientProxy";

    @ConfigInteger(category = "Gameplay", minValue = 10, maxValue = 100000, comment = "Used for the custom biome id.")
    public static int CUSTOM_BIOME_ID = 84;

    @ConfigInteger(category = "Gameplay", minValue = 10, maxValue = 100000, comment = "Used for the custom dimension id.")
    public static int CUSTOM_DIMENSION_ID = 84;

}
