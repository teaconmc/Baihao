package org.teacon.baihao.data;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber
public class BaihaoDatagen {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        event.createProvider(MyModelProvider::new);
        event.createProvider(MyItemTagProvider::new);
//        event.createProvider(MyLanguageProvider.Chinese::new);
//        event.createProvider(MyLanguageProvider.English::new);
    }
}
