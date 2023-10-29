package com.dbt233.mod.teaproduce.registry;

import com.dbt233.mod.teaproduce.enchantments.FallingThunderEnchantment;
import com.dbt233.mod.teaproduce.enchantments.FlyingEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.dbt233.mod.teaproduce.Utils.MODID;

public class EnchantmentRegsitry {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MODID);

    public static final RegistryObject<Enchantment> FLYING = ENCHANTMENTS.register("flying",
            () -> new FlyingEnchantment(Enchantment.Rarity.RARE, EnchantmentCategory.WEAPON,
                    EquipmentSlot.MAINHAND
            ));

    public static final RegistryObject<Enchantment> FALLING_THUNDER = ENCHANTMENTS.register("falling_thunder",
            () -> new FallingThunderEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.WEAPON,
                    EquipmentSlot.MAINHAND
            ));

}
