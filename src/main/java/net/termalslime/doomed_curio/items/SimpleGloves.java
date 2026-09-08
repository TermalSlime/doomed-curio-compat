package net.termalslime.doomed_curio.items;

import net.minecraft.world.item.Item;

public class SimpleGloves extends CurioWearebleItem {

    public SimpleGloves() {
        super(
                new Item.Properties().stacksTo(1).defaultDurability(0),
                SlotType.HANDS,
                1,
                1
        );
    }
}
