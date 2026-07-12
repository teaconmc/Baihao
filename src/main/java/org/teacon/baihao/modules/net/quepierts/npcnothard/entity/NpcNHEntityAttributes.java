package org.teacon.baihao.modules.net.quepierts.npcnothard.entity;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.teacon.baihao.modules.net.quepierts.npcnothard.NpcNotHard;
import org.teacon.baihao.modules.net.quepierts.npcnothard.reference.NpcNHEntities;

@EventBusSubscriber(modid = NpcNotHard.PARENT)
public class NpcNHEntityAttributes {

    @SubscribeEvent
    public static void onCreateEntityAttributes(final EntityAttributeCreationEvent event) {

        event.put(
                NpcNHEntities.EXHIBITION_HUMANOID.get(),
                ExhibitionHumanoid.createAttributes().build()
        );

    }

}
