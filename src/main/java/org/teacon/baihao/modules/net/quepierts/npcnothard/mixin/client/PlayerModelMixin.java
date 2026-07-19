package org.teacon.baihao.modules.net.quepierts.npcnothard.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teacon.baihao.modules.net.quepierts.npcnothard.client.render.entity.model.ExhibitionModelOverride;
import org.teacon.baihao.modules.net.quepierts.npcnothard.exhibition.node.PoseNode;

import java.util.List;

@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin extends HumanoidModel<AvatarRenderState> {

    @Mutable
    @Unique
    @Final
    private ExhibitionModelOverride npcnh$modelOverrider;

    public PlayerModelMixin(final ModelPart root) {
        super(root);
    }

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void npcnh$init(
            final ModelPart root, final boolean slim, final CallbackInfo ci
    ) {
        final var part = List.of(
                this.head,
                this.body,
                this.leftArm,
                this.rightArm,
                this.leftLeg,
                this.rightLeg
        );
        this.npcnh$modelOverrider = new ExhibitionModelOverride(part);
    }

    @Inject(
            method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;)V",
            at = @At("HEAD")
    )
    private void npcnh$prepare(
            final AvatarRenderState state,
            final CallbackInfo ci
    ) {
        this.head.visible = true;
    }

    @Inject(
            method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;)V",
            at = @At("TAIL")
    )
    private void npcnh$apply(
            final AvatarRenderState state,
            final CallbackInfo ci
    ) {
        final var pose = state.getRenderData(PoseNode.UNIQUE_KEY);
        if (pose != null) {
            this.npcnh$modelOverrider.apply(pose);
        }
    }

}