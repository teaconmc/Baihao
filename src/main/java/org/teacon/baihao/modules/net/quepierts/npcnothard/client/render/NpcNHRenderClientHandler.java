package org.teacon.baihao.modules.net.quepierts.npcnothard.client.render;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import org.teacon.baihao.modules.net.quepierts.npcnothard.NpcNotHard;
import org.teacon.baihao.modules.net.quepierts.npcnothard.client.render.entity.ExhibitionHumanoidRenderer;
import org.teacon.baihao.modules.net.quepierts.npcnothard.client.render.renderstate.ExhibitionHumanoidModifier;
import org.teacon.baihao.modules.net.quepierts.npcnothard.reference.NpcNHEntities;

@EventBusSubscriber(value = Dist.CLIENT, modid = NpcNotHard.PARENT)
public final class NpcNHRenderClientHandler {

    @SubscribeEvent
    public static void onRegisterRenderStateModifiers(final RegisterRenderStateModifiersEvent event) {

        event.registerEntityModifier(
                ExhibitionHumanoidRenderer.class,
                ExhibitionHumanoidModifier.INSTANCE
        );

    }

    @SubscribeEvent
    public static void onRegisterEntityRenderers(final EntityRenderersEvent.RegisterRenderers event) {

        event.registerEntityRenderer(NpcNHEntities.EXHIBITION_HUMANOID.get(), ExhibitionHumanoidRenderer::regular);

    }

}
