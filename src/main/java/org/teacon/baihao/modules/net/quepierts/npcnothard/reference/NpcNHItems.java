package org.teacon.baihao.modules.net.quepierts.npcnothard.reference;

import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.teacon.baihao.modules.net.quepierts.npcnothard.NpcNotHard;
import org.teacon.baihao.modules.net.quepierts.npcnothard.item.ExhibitionEntityEditor;
import org.teacon.baihao.modules.net.quepierts.npcnothard.item.ExhibitionEntitySpawner;

import java.util.List;

public final class NpcNHItems {

    public static final DeferredRegister.Items ITEMS
            = DeferredRegister.createItems(NpcNotHard.MODID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS
            = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NpcNotHard.MODID);

    public static final DeferredHolder<Item, ExhibitionEntityEditor> EXHIBITION_ENTITY_EDITOR
            = ITEMS.registerItem(
                "exhibition_entity_editor",
                ExhibitionEntityEditor::new,
                properties -> properties
                        .component(
                                DataComponents.LORE,
                                new ItemLore(lines("tooltip.npcnh.exhibition_entity_editor", 5))
                        )
            );

    public static final DeferredHolder<Item, ExhibitionEntitySpawner> EXHIBITION_HUMANOID
            = ITEMS.registerItem(
                "exhibition_humanoid",
                ExhibitionEntitySpawner::new,
                properties -> properties
                        .spawnEgg(NpcNHEntities.EXHIBITION_HUMANOID.get())
                        .component(
                                DataComponents.LORE,
                                new ItemLore(lines("tooltip.npcnh.exhibition_entity_spawner", 2))
                        )
            );


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> THE_TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.npcnh"))
            .icon(() -> new ItemStack(EXHIBITION_ENTITY_EDITOR.get()))
            .withTabsBefore(CreativeModeTabs.FOOD_AND_DRINKS, CreativeModeTabs.INGREDIENTS, CreativeModeTabs.SPAWN_EGGS)
            .build());

    private static Component line(final String key) {
        return Component.translatable(key).withStyle(ChatFormatting.GRAY);
    }

    private static List<Component> lines(final String key, final int lines) {
        final var builder = ImmutableList.<Component>builderWithExpectedSize(lines);

        for (int i = 1; i <= lines; i++) {
            builder.add(line(key + i));
        }

        return builder.build();
    }


    @EventBusSubscriber(modid = NpcNotHard.PARENT)
    private static final class Handler {
        @SubscribeEvent
        public static void onBuildCreativeModeTab(final BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == THE_TAB.getKey()) {
                for (final var entry : ITEMS.getEntries()) {
                    event.accept(entry.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }
        }
    }

}
