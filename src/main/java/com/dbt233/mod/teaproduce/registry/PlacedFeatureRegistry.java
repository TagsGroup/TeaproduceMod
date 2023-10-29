package com.dbt233.mod.teaproduce.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static com.dbt233.mod.teaproduce.Utils.MODID;

public class PlacedFeatureRegistry {

    static class OrePlacement {
        private static List<PlacementModifier>
        orePlacement(PlacementModifier modifier1, PlacementModifier modifier2) {
            return List.of(modifier1, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
        }

        private static List<PlacementModifier>
        commonOrePlacement(int n, PlacementModifier modifier) {
            return orePlacement(CountPlacement.of(n), modifier);
        }

        private static List<PlacementModifier>
        rareOrePlacement(int n, PlacementModifier modifier) {
            return orePlacement(RarityFilter.onAverageOnceEvery(n), modifier);
        }
    }
    public static final ResourceKey<PlacedFeature> MAGIC_PURPLE_ORE_PLACED_KEY =
            createKey("magic_purple_ore_placed_key");
    private static ResourceKey<PlacedFeature> createKey(String name) {
        return  ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(MODID, name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> cont) {
        HolderGetter<ConfiguredFeature<?, ?>> conf = cont.lookup(Registries.CONFIGURED_FEATURE);
        reg(cont, MAGIC_PURPLE_ORE_PLACED_KEY,
                conf.getOrThrow(ConfiguredFeatureRegistry.MAGIC_PURPLE_ORE_KEY_IN_OVERWORLD),
                OrePlacement.commonOrePlacement(8 /* 每个区块8簇矿石 */, HeightRangePlacement.uniform(
                        VerticalAnchor.aboveBottom(0/* y>0生成 */),
                        VerticalAnchor.absolute(64 /* y上64格可生成(y>0+64) */)
                )));
    }

    private static void reg(BootstapContext<PlacedFeature> cont, ResourceKey<PlacedFeature> key,
                            Holder<ConfiguredFeature<?,?>> conf, List<PlacementModifier> modifiers) {
        cont.register(key, new PlacedFeature(conf, List.copyOf(modifiers)));
    }
}
