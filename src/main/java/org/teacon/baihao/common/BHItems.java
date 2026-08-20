package org.teacon.baihao.common;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.teacon.baihao.Baihao;

@EventBusSubscriber
public final class BHItems {
    static final DeferredRegister.Items BAIHAO_ITEMS = DeferredRegister.createItems(Baihao.MODID);
    public static final DeferredItem<Item> VERY_HARMFUL_AXE = BAIHAO_ITEMS.registerSimpleItem("very_harmful_axe", properties -> properties.axe(ToolMaterial.NETHERITE, 996, -3.0f).durability(1).fireResistant());
    public static final DeferredItem<Item> NEW_LAVA_BUCKET = BAIHAO_ITEMS.registerItem("new_lava_bucket",
            properties -> new BucketItem(BHFluids.NEW_LAVA.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<Item> HARMFUL_SWORD = BAIHAO_ITEMS.registerSimpleItem("harmful_sword", properties -> properties.sword(ToolMaterial.NETHERITE, 75, -2.4f));
    public static final DeferredItem<Item> VITALITY_HELMET = BAIHAO_ITEMS.registerSimpleItem("vitality_netherite_helmet", properties -> properties
            .durability(ArmorType.HELMET.getDurability(ArmorMaterials.NETHERITE.durability()))
            .attributes(ArmorMaterials.NETHERITE.createAttributes(ArmorType.HELMET)
                    .withModifierAdded(Attributes.MAX_HEALTH,
                            new AttributeModifier(Identifier.withDefaultNamespace("armor." + ArmorType.HELMET.getName()),
                                    20, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.HEAD))
            .enchantable(ArmorMaterials.NETHERITE.enchantmentValue())
            .component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.HEAD).setEquipSound(ArmorMaterials.NETHERITE.equipSound()).setAsset(ArmorMaterials.NETHERITE.assetId()).build())
            .fireResistant());
    public static final DeferredItem<Item> VITALITY_CHESTPLATE = BAIHAO_ITEMS.registerSimpleItem("vitality_netherite_chestplate", properties -> properties
            .durability(ArmorType.CHESTPLATE.getDurability(ArmorMaterials.NETHERITE.durability()))
            .attributes(ArmorMaterials.NETHERITE.createAttributes(ArmorType.CHESTPLATE)
                    .withModifierAdded(Attributes.MAX_HEALTH,
                            new AttributeModifier(Identifier.withDefaultNamespace("armor." + ArmorType.CHESTPLATE.getName()),
                                    20, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.CHEST))
            .enchantable(ArmorMaterials.NETHERITE.enchantmentValue())
            .component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.CHEST).setEquipSound(ArmorMaterials.NETHERITE.equipSound()).setAsset(ArmorMaterials.NETHERITE.assetId()).build())
            .fireResistant());
    public static final DeferredItem<Item> VITALITY_LEGGINGS = BAIHAO_ITEMS.registerSimpleItem("vitality_netherite_leggings", properties -> properties
            .durability(ArmorType.LEGGINGS.getDurability(ArmorMaterials.NETHERITE.durability()))
            .attributes(ArmorMaterials.NETHERITE.createAttributes(ArmorType.LEGGINGS)
                    .withModifierAdded(Attributes.MAX_HEALTH,
                            new AttributeModifier(Identifier.withDefaultNamespace("armor." + ArmorType.LEGGINGS.getName()),
                                    20, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.LEGS))
            .enchantable(ArmorMaterials.NETHERITE.enchantmentValue())
            .component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.LEGS).setEquipSound(ArmorMaterials.NETHERITE.equipSound()).setAsset(ArmorMaterials.NETHERITE.assetId()).build())
            .fireResistant());
    public static final DeferredItem<Item> VITALITY_BOOTS = BAIHAO_ITEMS.registerSimpleItem("vitality_netherite_boots", properties -> properties
            .durability(ArmorType.BOOTS.getDurability(ArmorMaterials.NETHERITE.durability()))
            .attributes(ArmorMaterials.NETHERITE.createAttributes(ArmorType.BOOTS)
                    .withModifierAdded(Attributes.MAX_HEALTH,
                            new AttributeModifier(Identifier.withDefaultNamespace("armor." + ArmorType.BOOTS.getName()),
                                    20, AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.FEET))
            .enchantable(ArmorMaterials.NETHERITE.enchantmentValue())
            .component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.FEET).setEquipSound(ArmorMaterials.NETHERITE.equipSound()).setAsset(ArmorMaterials.NETHERITE.assetId()).build())
            .fireResistant());

    public static void register(IEventBus modEventBus) {
        BAIHAO_ITEMS.register(modEventBus);
    }
}
