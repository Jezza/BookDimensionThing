package me.jezza.bdt.common;

import me.jezza.bdt.common.items.ItemBookThingy;
import me.jezza.bdt.common.lib.Strings;
import net.minecraft.item.Item;

public class ModItems {

    public static Item bookThing;

    public static void init() {
        bookThing = new ItemBookThingy(Strings.BOOK_THINGY);
    }

}
