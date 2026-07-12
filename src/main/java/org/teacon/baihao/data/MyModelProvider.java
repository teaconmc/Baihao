package org.teacon.baihao.data;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import org.teacon.baihao.Baihao;
import org.teacon.baihao.common.BHItems;

public class MyModelProvider extends ModelProvider {
    public MyModelProvider(PackOutput output) {
        super(output, Baihao.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(BHItems.VERY_HARMFUL_AXE.get(), Items.NETHERITE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(BHItems.HARMFUL_SWORD.get(), Items.NETHERITE_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.generateFlatItem(BHItems.VITALITY_HELMET.get(), Items.NETHERITE_HELMET, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BHItems.VITALITY_CHESTPLATE.get(), Items.NETHERITE_CHESTPLATE, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BHItems.VITALITY_LEGGINGS.get(), Items.NETHERITE_LEGGINGS, ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BHItems.VITALITY_BOOTS.get(), Items.NETHERITE_BOOTS, ModelTemplates.FLAT_ITEM);
    }
}
