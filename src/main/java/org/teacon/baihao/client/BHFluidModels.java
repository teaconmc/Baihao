package org.teacon.baihao.client;

import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;
import net.neoforged.neoforge.client.fluid.FluidTintSource;
import org.teacon.baihao.Baihao;
import org.teacon.baihao.common.BHFluids;


@EventBusSubscriber(value = Dist.CLIENT, modid = Baihao.MODID)
public final class BHFluidModels {

    @SubscribeEvent
    private static void onRegisterFluidModels(RegisterFluidModelsEvent event) {
        event.register(new FluidModel.Unbaked(
                        new Material(Identifier.withDefaultNamespace("block/lava_still")),
                        new Material(Identifier.withDefaultNamespace("block/lava_flow")),
                        null,
                        (FluidTintSource) null),
                BHFluids.NEW_LAVA.get(), BHFluids.FLOWING_NEW_LAVA.get());
    }

    private BHFluidModels() {
    }
}
