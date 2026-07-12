package org.teacon.baihao.modules.net.quepierts.npcnothard.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.teacon.baihao.modules.net.quepierts.npcnothard.NpcNotHard;

@EventBusSubscriber(modid = NpcNotHard.PARENT)
public final class NpcNHNetwork {

    @SubscribeEvent
    public static void onRegisterPayloadHandlers(final RegisterPayloadHandlersEvent event) {

        var register = event.registrar(NpcNotHard.MODID);

        register.playToClient(
                OpenExhibitionEntityEditor.TYPE,
                OpenExhibitionEntityEditor.STREAM_CODEC,
                OpenExhibitionEntityEditor::handle
        );
        register.playToServer(
                UpdateExhibitionEntity.TYPE,
                UpdateExhibitionEntity.STREAM_CODEC,
                UpdateExhibitionEntity::handle
        );

    }

}
