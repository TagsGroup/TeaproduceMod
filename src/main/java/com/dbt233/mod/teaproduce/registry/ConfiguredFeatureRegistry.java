package com.dbt233.mod.teaproduce.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

import static com.dbt233.mod.teaproduce.Utils.MODID;

public class ConfiguredFeatureRegistry {

    public static void bootstrap(BootstapContext<ConfiguredFeature<?,?>> cont) {
        RuleTest stoneReplace = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> magicPurpleOreInOverworld =
                List.of(OreConfiguration.target(stoneReplace, BlockRegistry.MAGIC_PURPLE_ORE.get().defaultBlockState()));
        reg(cont, MAGIC_PURPLE_ORE_KEY_IN_OVERWORLD, Feature.ORE, new OreConfiguration(magicPurpleOreInOverworld, 9));
    }

    public static final ResourceKey<ConfiguredFeature<?,?>> MAGIC_PURPLE_ORE_KEY_IN_OVERWORLD = regKey("magic_purple_ore_key_in_overworld");
    private static ResourceKey<ConfiguredFeature<?,?>> regKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>>
        void reg(BootstapContext<ConfiguredFeature<?,?>> cont, ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC conf) {
        cont.register(key, new ConfiguredFeature<>(feature, conf));
    }
}
