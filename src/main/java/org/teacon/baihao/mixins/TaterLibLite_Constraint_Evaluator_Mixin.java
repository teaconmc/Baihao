package org.teacon.baihao.mixins;

import com.google.common.cache.CacheBuilder;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Mixin(targets = "org.adde0109.pcf.lib.taterapi.meta.Constraint$Evaluator")
public class TaterLibLite_Constraint_Evaluator_Mixin {
    @WrapOperation(
            method = "<clinit>",
            at = @At(
                    value = "FIELD",
                    target = "Lorg/adde0109/pcf/lib/taterapi/meta/Constraint$Evaluator;CACHE:Ljava/util/Map;",
                    opcode = Opcodes.PUTSTATIC
            )
    )
    private static void wipeTheirAss(Map<?, Boolean> value, Operation<Void> original) {
        original.call(CacheBuilder.newBuilder()
                .expireAfterAccess(60, TimeUnit.SECONDS)
                .maximumSize(65536)
                .removalListener(_ -> {
                    throw new AssertionError("SHOULD NOT BE HERE");
                })
                .build()
                .asMap());
    }
}
