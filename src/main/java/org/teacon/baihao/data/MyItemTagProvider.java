package org.teacon.baihao.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.teacon.baihao.Baihao;
import org.teacon.baihao.common.BHItems;

import java.util.concurrent.CompletableFuture;

public class MyItemTagProvider extends ItemTagsProvider {
    public MyItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Baihao.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(ItemTags.AXES).add(BHItems.VERY_HARMFUL_AXE.get());
        tag(ItemTags.SWORDS).add(BHItems.HARMFUL_SWORD.get());
        tag(Tags.Items.MELEE_WEAPON_TOOLS).add(BHItems.VERY_HARMFUL_AXE.get(),BHItems.HARMFUL_SWORD.get());
        tag(ItemTags.HEAD_ARMOR).add(BHItems.VITALITY_HELMET.get());
        tag(ItemTags.CHEST_ARMOR).add(BHItems.VITALITY_CHESTPLATE.get());
        tag(ItemTags.LEG_ARMOR).add(BHItems.VITALITY_LEGGINGS.get());
        tag(ItemTags.FOOT_ARMOR).add(BHItems.VITALITY_BOOTS.get());
    }
}
