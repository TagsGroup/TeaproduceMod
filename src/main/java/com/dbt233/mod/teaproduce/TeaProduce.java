package com.dbt233.mod.teaproduce;

import com.dbt233.mod.teaproduce.blocks.block_entities.dry_rack.DryRackBlockEntityRenderer;
import com.dbt233.mod.teaproduce.blocks.block_entities.magic_tea_barrel.MagicTeaBarrelScreen;
import com.dbt233.mod.teaproduce.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.dbt233.mod.teaproduce.Utils.MODID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MODID)
public class TeaProduce
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    public TeaProduce()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        //modEventBus.addListener(this::onPlayerLoggedIn);

        ItemRegistry.ITEMS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockEntityRegistry.BLOCK_ENTITIES.register(modEventBus);
        EnchantmentRegsitry.ENCHANTMENTS.register(modEventBus);
        ModMenuTypeRegistry.MENUS.register(modEventBus);

        modEventBus.addListener(this::addCreative);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabRegistry.TEAPRODUCE_TAB) {
            ItemRegistry.CREATIVE_TAB_ITEMS.forEach(event::accept);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenuTypeRegistry.MAGIC_TEA_BARREL.get(), MagicTeaBarrelScreen::new);
        }

        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(BlockEntityRegistry.DRY_RACK_BLOCK_ENTITY.get(), DryRackBlockEntityRenderer::new);
        }
    }
}
