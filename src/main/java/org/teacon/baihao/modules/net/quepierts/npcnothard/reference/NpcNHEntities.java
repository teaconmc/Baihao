package org.teacon.baihao.modules.net.quepierts.npcnothard.reference;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.teacon.baihao.modules.net.quepierts.npcnothard.NpcNotHard;
import org.teacon.baihao.modules.net.quepierts.npcnothard.entity.ExhibitionHumanoid;
import org.teacon.baihao.modules.net.quepierts.npcnothard.exhibition.ExhibitionNodeManager;

public final class NpcNHEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES
            = DeferredRegister.create(Registries.ENTITY_TYPE, NpcNotHard.MODID);

    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZER
            = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, NpcNotHard.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<ExhibitionHumanoid>> EXHIBITION_HUMANOID = ENTITIES.register("exhibition_humanoid",
            () -> EntityType.Builder.of(ExhibitionHumanoid::create, MobCategory.MISC)
                    .sized(0.6F, 1.8F)
                    .eyeHeight(1.62F)
                    .clientTrackingRange(8)
                    .updateInterval(64)
                    .build(NpcNotHard.key(BuiltInRegistries.ENTITY_TYPE.key(), "exhibition_humanoid")));

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<ExhibitionNodeManager>> EXHIBITION_NODE = ENTITY_DATA_SERIALIZER.register(
            "exhibition_node", () -> EntityDataSerializer.forValueType(ExhibitionNodeManager.STREAM_CODEC)
    );

}
