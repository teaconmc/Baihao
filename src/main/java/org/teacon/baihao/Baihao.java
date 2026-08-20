package org.teacon.baihao;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import org.teacon.baihao.common.BHFluids;
import org.teacon.baihao.common.BHItems;

@Mod(Baihao.MODID)
public class Baihao {

    public static final String MODID = "baihao";
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public Baihao(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        BHItems.register(modEventBus);
        BHFluids.register(modEventBus);
        modEventBus.addListener(BHFluids::registerFluidInteractions);
    }

}
