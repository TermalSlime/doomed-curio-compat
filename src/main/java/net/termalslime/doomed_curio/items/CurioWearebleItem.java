package net.termalslime.doomed_curio.items;

import net.mattlives.doomedmatu.item.WearableItem;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CurioWearebleItem extends WearableItem implements ICurioItem {

    public CurioWearebleItem(Properties properties,SlotType slot, float armor, float isolation) {
        super(
                properties,
                slot.getSlotString(),
                armor,
                isolation
        );
    }
}
