package net.termalslime.doomed_curio.items;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.termalslime.doomed_curio.DoomedCurio;

@Mod.EventBusSubscriber(modid = DoomedCurio.MODID)
public class ItemRegistry {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DoomedCurio.MODID);

    public static final RegistryObject<Item> SIMPLE_GLOVES = ITEMS.register("simple_gloves", SimpleGloves::new);

    public static void registerItems(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }

    @SubscribeEvent
    public void buildContents(BuildCreativeModeTabContentsEvent event) {
        // Add to ingredients tab
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(SIMPLE_GLOVES);
        }
    }
}
