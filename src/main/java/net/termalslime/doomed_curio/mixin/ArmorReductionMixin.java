package net.termalslime.doomed_curio.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.mattlives.doomedmatu.body.BodyPart;
import net.mattlives.doomedmatu.capability.DoomedData;
import net.mattlives.doomedmatu.item.Wearable;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@Debug(export = true)
@Mixin(net.mattlives.doomedmatu.body.ArmorReduction.class)
public class ArmorReductionMixin {
    @ModifyReturnValue(method = "partProtection(Lnet/minecraft/server/level/ServerPlayer;Lnet/mattlives/doomedmatu/capability/DoomedData;Lnet/mattlives/doomedmatu/body/BodyPart;)F",
            at = @At("RETURN"),
            remap = false)
    private static float onPartProtection(float original,
                                          ServerPlayer sp,
                                          DoomedData d,
                                          BodyPart part) {

        sp.sendSystemMessage(Component.literal("mixin call"));
        float armor = original;
        float old_armor = armor;
        LazyOptional<ICuriosItemHandler> lazyCurio = CuriosApi.getCuriosInventory(sp);
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
        sp.sendSystemMessage(Component.literal("old_armor: " + old_armor + "; new_armor: " + armor));
        return armor;
    }
}
