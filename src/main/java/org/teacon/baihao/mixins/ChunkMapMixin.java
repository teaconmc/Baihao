package org.teacon.baihao.mixins;

import net.minecraft.CrashReport;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BooleanSupplier;

@Mixin(ChunkMap.class)
public class ChunkMapMixin {
    @Inject(method = "processUnloads", at = @At("HEAD"))
    private void assertThread(BooleanSupplier haveTime, CallbackInfo ci) {
        assertThread();
    }

    @Inject(method = "updateChunkScheduling", at = @At("HEAD"))
    private void assertThread(long node, int level, ChunkHolder chunk, int oldLevel, CallbackInfoReturnable<ChunkHolder> cir) {
        assertThread();
    }

    @Unique
    private static void assertThread() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null && !server.isSameThread()) {
            server.delayCrash(new CrashReport("Burning_TNT is watching you!", new UnsupportedOperationException("Methods in ChunkMap aren't thread-safe!")));
        }
    }
}
