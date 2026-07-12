package org.teacon.baihao.modules.net.quepierts.npcnothard;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.teacon.baihao.Baihao;
import org.teacon.baihao.modules.net.quepierts.npcnothard.reference.NpcNHDataComponents;
import org.teacon.baihao.modules.net.quepierts.npcnothard.reference.NpcNHEntities;
import org.teacon.baihao.modules.net.quepierts.npcnothard.reference.NpcNHItems;

@Mod(NpcNotHard.PARENT)
public class NpcNotHard {

    public static final String MODID    = "npcnh";
    public static final String PARENT   = Baihao.MODID;

    public static Identifier location(final String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    public static <T> ResourceKey<T> key(
            final ResourceKey<? extends Registry<T>> key,
            final String value
    ) {
        return ResourceKey.create(key, location(value));
    }

    public NpcNotHard(IEventBus modbus) {
        NpcNHDataComponents.DATA_COMPONENTS.register(modbus);
        NpcNHEntities.ENTITY_DATA_SERIALIZER.register(modbus);
        NpcNHEntities.ENTITIES.register(modbus);
        NpcNHItems.ITEMS.register(modbus);
        NpcNHItems.CREATIVE_MODE_TABS.register(modbus);
    }

}
