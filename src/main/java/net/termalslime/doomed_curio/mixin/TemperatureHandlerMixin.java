package net.termalslime.doomed_curio.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.mattlives.doomedmatu.capability.DoomedData;
import net.mattlives.doomedmatu.item.Wearable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@Debug(export = true)
@Mixin(net.mattlives.doomedmatu.body.TemperatureHandler.class)
public class TemperatureHandlerMixin {
    @Inject(method = "tick(Lnet/minecraft/server/level/ServerPlayer;Lnet/mattlives/doomedmatu/capability/DoomedData;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/mattlives/doomedmatu/capability/DoomedData;getBodyTemp()F",
                    shift = At.Shift.AFTER,
                    ordinal = 0),
            remap = false)
    private static void onTick(CallbackInfo ci, @Local(name = "insulation") LocalFloatRef insulation, @Local(argsOnly = true) ServerPlayer sp, @Local(argsOnly = true) DoomedData d)
    {
        LazyOptional<ICuriosItemHandler> lazyCurio = CuriosApi.getCuriosInventory(sp);
        if (lazyCurio.isPresent()) {
            ICuriosItemHandler curio = lazyCurio.resolve().get();
            float ins = insulation.get();

            IItemHandlerModifiable items = curio.getEquippedCurios();
            int slots = items.getSlots();
            for (int i = 0; i < slots; ++i) {
                Item item = items.getStackInSlot(i).getItem();
                if (item instanceof Wearable wearable) {
                    ins += wearable.wearableIsolation();
                }
            }
            insulation.set(ins);
        }
    }
}
