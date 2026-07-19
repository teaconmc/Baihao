package org.teacon.baihao.modules.net.quepierts.npcnothard.exhibition.node;

import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;
import org.teacon.baihao.modules.net.quepierts.npcnothard.entity.ExhibitionEntity;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.Duplicatable;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.Inspectable;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.InspectorBuilder;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.constraint.NumberConstraint;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.property.BooleanProperty;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.property.FloatProperty;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FacingPlayerNode extends ExhibitionNode implements Inspectable {

    public static final ContextKey<FacingPlayerNode> UNIQUE_KEY = ExhibitionNode.createUniqueKey("facing");

    public static final MapCodec<FacingPlayerNode> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("enabled", false).forGetter(FacingPlayerNode::isEnabled),
            Codec.BOOL.optionalFieldOf("localOnly", false).forGetter(FacingPlayerNode::isLocalOnly),
            Codec.FLOAT.optionalFieldOf("distance", 5.0f).forGetter(FacingPlayerNode::getDistance)
    ).apply(instance, FacingPlayerNode::new));

    public static final StreamCodec<ByteBuf, FacingPlayerNode> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            FacingPlayerNode::isEnabled,
            ByteBufCodecs.BOOL,
            FacingPlayerNode::isLocalOnly,
            ByteBufCodecs.FLOAT,
            FacingPlayerNode::getDistance,
            FacingPlayerNode::new
    );

    public static final NumberConstraint<Float> DISTANCE = NumberConstraint.number(0.0f, 128.0f, 5.0f);

    private final BooleanProperty enabled       = BooleanProperty.simple(false);
    private final BooleanProperty localOnly     = BooleanProperty.simple(false);
    private final FloatProperty distance        = FloatProperty.simple(6.0f);

    public FacingPlayerNode() { }

    private FacingPlayerNode(
            final boolean   enabled,
            final boolean   localOnly,
            final float     distance
    ) {
        this.enabled        .setValue(enabled);
        this.localOnly      .setValue(localOnly);
        this.distance       .setValue(distance);
    }

    @Override
    public String type() {
        return "facing";
    }

    @Override
    public FacingPlayerNode duplicate() {
        return new FacingPlayerNode(
                this.enabled.getValue(),
                this.localOnly.getValue(),
                this.distance.getValue()
        );
    }

    @Override
    public void paste(final Duplicatable copy) {
        if (copy.getClass() != FacingPlayerNode.class) {
            return;
        }

        final var node = (FacingPlayerNode) copy;
        this.enabled        .setValue(node.enabled.getValue());
        this.localOnly      .setValue(node.localOnly.getValue());
        this.distance       .setValue(node.distance.getValue());
    }

    @Override
    public String name() {
        return "Facing";
    }

    @Override
    public @Nullable ContextKey<? extends ExhibitionNode> uniqueKey() {
        return UNIQUE_KEY;
    }

    public void onProcess(final ExhibitionEntity entity) {

        if (!this.enabled.getValue()) {
            return;
        }

        final var level = entity.level();
        final var localOnly     = this.localOnly.getValue();

        double closestDistance  = this.distance.getValue() * this.distance.getValue();
        Player closestPlayer    = null;

        for (final var player : level.players()) {
            if (localOnly && !player.isLocalPlayer()) {
                continue;
            }

            final var distance = entity.distanceToSqr(player);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPlayer = player;
            }
        }

        if (closestPlayer != null) {
            entity.lookAt(closestPlayer, 10f, 10f);
            entity.setYHeadRot(entity.getYRot());
        }

    }


    public boolean isEnabled() {
        return this.enabled.getValue();
    }

    public boolean isLocalOnly() {
        return this.localOnly.getValue();
    }

    public float getDistance() {
        return this.distance.getValue();
    }

    @Override
    public void onInspect(final InspectorBuilder builder) {
        builder .title(Component.literal("Facing Player"))

                .checkbox(Component.literal("Enabled"), this.enabled)
                .checkbox(Component.literal("Local Only"), this.localOnly)
                .inputFloat(Component.literal("Distance"), this.distance, DISTANCE);
    }
}
