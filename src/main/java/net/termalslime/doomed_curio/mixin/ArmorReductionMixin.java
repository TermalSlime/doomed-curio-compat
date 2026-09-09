package net.termalslime.doomed_curio.mixin;

import net.termalslime.doomed_curio.helpers.CurioStatsCalculator;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.mattlives.doomedmatu.body.BodyPart;
import net.mattlives.doomedmatu.capability.DoomedData;
import net.mattlives.doomedmatu.item.Wearable;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
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

        return original + CurioStatsCalculator.claculateArmorForPart(sp, part);
    }
}
