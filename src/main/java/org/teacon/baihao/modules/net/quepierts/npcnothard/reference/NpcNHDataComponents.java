package org.teacon.baihao.modules.net.quepierts.npcnothard.reference;

import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.teacon.baihao.modules.net.quepierts.npcnothard.NpcNotHard;
import org.teacon.baihao.modules.net.quepierts.npcnothard.exhibition.ExhibitionNodeManager;

import java.util.UUID;

public final class NpcNHDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, NpcNotHard.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> EDITING_ENTITY = DATA_COMPONENTS.register(
            "editing_entity", () -> DataComponentType.<UUID>builder()
                    .persistent(UUIDUtil.CODEC)
                    .networkSynchronized(UUIDUtil.STREAM_CODEC)
                    .build()
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ExhibitionNodeManager.Immutable>> EXHIBITION_NODES = DATA_COMPONENTS.register(
            "exhibition_nodes", () -> DataComponentType.<ExhibitionNodeManager.Immutable>builder()
                    .persistent(ExhibitionNodeManager.Immutable.CODEC)
                    .networkSynchronized(ExhibitionNodeManager.Immutable.STREAM_CODEC)
                    .build()
    );

}
