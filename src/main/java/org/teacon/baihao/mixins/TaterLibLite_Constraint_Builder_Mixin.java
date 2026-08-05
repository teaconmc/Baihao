package org.teacon.baihao.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.teacon.baihao.utils.UnmodifiableCollection;

import java.util.Collection;

@Mixin(targets = "org.adde0109.pcf.lib.taterapi.meta.Constraint$Builder")
public class TaterLibLite_Constraint_Builder_Mixin {
    @Redirect(
            method = "build",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Collections;unmodifiableCollection(Ljava/util/Collection;)Ljava/util/Collection;"
            )
    )
    private static <E> Collection<E> wipeTheirAss(Collection<E> c) {
        return new UnmodifiableCollection<>(c);
    }
}
