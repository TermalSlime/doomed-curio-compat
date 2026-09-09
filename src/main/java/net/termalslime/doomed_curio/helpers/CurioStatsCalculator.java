package net.termalslime.doomed_curio.helpers;

import net.mattlives.doomedmatu.body.BodyPart;
import net.mattlives.doomedmatu.item.Wearable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.logging.log4j.core.jmx.Server;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public class CurioStatsCalculator {

    public static float claculateArmorForPart(ServerPlayer player, BodyPart part)
    {
        float armor = 0;
        LazyOptional<ICuriosItemHandler> lazyCurio = CuriosApi.getCuriosInventory(player);
        if (!lazyCurio.isPresent()) return armor;
        ICuriosItemHandler curio = lazyCurio.resolve().get();

        IItemHandlerModifiable items = curio.getEquippedCurios();
        int slots = items.getSlots();
        for (int i = 0; i < slots; ++i) {
            Item item = items.getStackInSlot(i).getItem();
            if (item instanceof Wearable wearable) {
                armor += wearable.wearableArmor() * Mth.clamp(wearable.armorCoverage(part), 0.0F, 1.0F);
            }
        }
        return armor;
    }

    public static float calculateInsulation(ServerPlayer player)
    {
        float ins = 0;
        LazyOptional<ICuriosItemHandler> lazyCurio = CuriosApi.getCuriosInventory(player);
        if (lazyCurio.isPresent()) {
            ICuriosItemHandler curio = lazyCurio.resolve().get();
            IItemHandlerModifiable items = curio.getEquippedCurios();
            int slots = items.getSlots();
            for (int i = 0; i < slots; ++i) {
                Item item = items.getStackInSlot(i).getItem();
                if (item instanceof Wearable wearable) {
                    ins += wearable.wearableIsolation();
                }
            }
        }
        return ins;
    }
}
