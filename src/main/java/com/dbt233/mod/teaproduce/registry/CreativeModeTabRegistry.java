package com.dbt233.mod.teaproduce.registry;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.dbt233.mod.teaproduce.Utils.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeModeTabRegistry {
    public static CreativeModeTab TEAPRODUCE_TAB;

    @SubscribeEvent
    public static void registryCreativeModeTab(CreativeModeTabEvent.Register event) {
        TEAPRODUCE_TAB = event.registerCreativeModeTab(
                new ResourceLocation(MODID, "teaproduce_tab"),
                builder -> builder.icon(() -> new ItemStack(ItemRegistry.GREEN_TEA_LEAF.get()))
                        .title(Component.translatable("itemGroup.teaproduce_tab"))
        );
    }
}
