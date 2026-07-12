package org.teacon.baihao.modules.net.quepierts.npcnothard.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teacon.baihao.modules.net.quepierts.npcnothard.client.render.entity.model.ExhibitionModelExtension;
import org.teacon.baihao.modules.net.quepierts.npcnothard.client.render.entity.model.ExhibitionModelOverride;
import org.teacon.baihao.modules.net.quepierts.npcnothard.exhibition.node.PoseNode;

import java.util.List;
import java.util.function.Function;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin {

    @Shadow
    @Final
    public ModelPart head;
    @Shadow
    @Final
    public ModelPart body;
    @Shadow
    @Final
    public ModelPart leftArm;
    @Shadow
    @Final
    public ModelPart rightArm;
    @Shadow
    @Final
    public ModelPart leftLeg;
    @Shadow
    @Final
    public ModelPart rightLeg;
    @Mutable
    @Unique
    @Final
    private ExhibitionModelOverride npcnh$modelOverrider;

    @Inject(
            method = "<init>(Lnet/minecraft/client/model/geom/ModelPart;Ljava/util/function/Function;)V",
            at = @At("TAIL")
    )
    private void npcnh$init(
            final ModelPart root,
            final Function<Identifier, RenderType> renderType,
            final CallbackInfo ci
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
            method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V",
            at = @At("HEAD")
    )
    private void npcnh$reset(
            final HumanoidRenderState state,
            final CallbackInfo ci
    ) {
        // reset visible, prevent unexpected problem
        this.head.visible = true;
        this.body.visible = true;
        this.leftArm.visible = true;
        this.rightArm.visible = true;
        this.leftLeg.visible = true;
        this.rightLeg.visible = true;
    }

    @Inject(
            method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V",
            at = @At("TAIL")
    )
    private void npcnh$setupAnim(
            final HumanoidRenderState state,
            final CallbackInfo ci
    ) {
        final var pose = state.getRenderData(PoseNode.UNIQUE_KEY);
        if (pose != null) {
            this.npcnh$modelOverrider.apply(pose);
        }
    }

}
