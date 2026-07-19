package org.teacon.baihao.modules.net.quepierts.npcnothard.exhibition.node;

import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.Entity;
import org.jspecify.annotations.Nullable;
import org.teacon.baihao.modules.net.quepierts.npcnothard.entity.ExhibitionEntity;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.Duplicatable;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.Inspectable;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.InspectorBuilder;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.constraint.NumberConstraint;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.property.BooleanProperty;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.property.FloatProperty;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.property.StringProperty;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class EntityNode extends ExhibitionNode implements Inspectable {

    public static final ContextKey<EntityNode> UNIQUE_KEY = EntityNode.createUniqueKey("entity");

    public static final MapCodec<EntityNode> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.optionalFieldOf("name", "").forGetter(EntityNode::getName),
            Codec.BOOL.optionalFieldOf("showName", false).forGetter(EntityNode::isShowName)
    ).apply(instance, (name, show) -> new EntityNode(0, 0, 0, 0, 0, name, show)));

    public static final StreamCodec<ByteBuf, EntityNode> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            EntityNode::getX,
            ByteBufCodecs.FLOAT,
            EntityNode::getY,
            ByteBufCodecs.FLOAT,
            EntityNode::getZ,
            ByteBufCodecs.FLOAT,
            EntityNode::getYaw,
            ByteBufCodecs.FLOAT,
            EntityNode::getPitch,
            ByteBufCodecs.STRING_UTF8,
            EntityNode::getName,
            ByteBufCodecs.BOOL,
            EntityNode::isShowName,
            EntityNode::new
    );

    public static final float RANGE = 256f;

    private final FloatProperty x           = FloatProperty.simple(0);
    private final FloatProperty y           = FloatProperty.simple(0);
    private final FloatProperty z           = FloatProperty.simple(0);

    private final FloatProperty yaw         = FloatProperty.simple(0);
    private final FloatProperty pitch       = FloatProperty.simple(0);

    private final StringProperty name       = StringProperty.simple("");
    private final BooleanProperty showName  = BooleanProperty.simple(false);

    private final NumberConstraint<Float> cx;
    private final NumberConstraint<Float> cy;
    private final NumberConstraint<Float> cz;

    public static EntityNode of(Entity entity) {
        return new EntityNode(
                (float) entity.getX(),
                (float) entity.getY(),
                (float) entity.getZ(),
                Mth.wrapDegrees(entity.getYRot()),
                Mth.wrapDegrees(entity.getXRot()),
                "",
                false
        );
    }

    public EntityNode(
            final float x,
            final float y,
            final float z,
            final float yaw,
            final float pitch,
            final String name,
            final boolean showName
    ) {
        this.setup(x, y, z, yaw, pitch, name, showName);

        this.cx = NumberConstraint.number(x - RANGE, x + RANGE, x);
        this.cy = NumberConstraint.number(y - RANGE, y + RANGE, y);
        this.cz = NumberConstraint.number(z - RANGE, z + RANGE, z);
    }

    @Override
    public void onInspect(InspectorBuilder builder) {
        builder .title(Component.literal(this.name()))

                .title(Component.literal("Position"))
                .inputFloat(Component.literal("X"), this.x, this.cx)
                .inputFloat(Component.literal("Y"), this.y, this.cy)
                .inputFloat(Component.literal("Z"), this.z, this.cz)

                .space()
                .title(Component.literal("Rotation"))
                .inputFloat(Component.literal("Yaw"), this.yaw, NumberConstraint.DEGREES)
                .inputFloat(Component.literal("Pitch"), this.pitch, NumberConstraint.DEGREES)

                .space()
                .inputString(Component.literal("Name"), this.name)
                .checkbox(Component.literal("Custom Name Visible"), this.showName)
        ;
    }

    @Override
    public String name() {
        final var name = this.name.get();
        return name.isEmpty() ? "Entity" : name;
    }

    @Override
    public String type() {
        return "entity";
    }

    @Override
    public ExhibitionNode duplicate() {
        return new EntityNode(
                this.x.getValue(),
                this.y.getValue(),
                this.z.getValue(),
                this.yaw.getValue(),
                this.pitch.getValue(),
                this.name.get(),
                this.showName.get()
        );
    }

    @Override
    public void paste(final Duplicatable other) {
        if (other.getClass() == EntityNode.class) {
            final var node = (EntityNode) other;
            this.x.setValue(node.x.getValue());
            this.y.setValue(node.y.getValue());
            this.z.setValue(node.z.getValue());
            this.yaw.setValue(node.yaw.getValue());
            this.pitch.setValue(node.pitch.getValue());
            this.name.set(node.name.get());
            this.showName.set(node.showName.get());
        }
    }

    @Override
    public @Nullable ContextKey<? extends ExhibitionNode> uniqueKey() {
        return UNIQUE_KEY;
    }

    @Override
    public void init(final ExhibitionEntity entity) {
        this.setup(
                (float) entity.getX(),
                (float) entity.getY(),
                (float) entity.getZ(),
                entity.getYRot(),
                entity.getXRot(),
                this.name.get(),
                this.showName.get()
        );
    }

    @Override
    public void apply(final ExhibitionEntity entity) {
        entity.setPos(
                this.x.getNumber(),
                this.y.getNumber(),
                this.z.getNumber()
        );

        final var yaw   = this.yaw.getValue();
        final var pitch = this.pitch.getValue();
        entity.setYRot(yaw);
        entity.setYHeadRot(entity.getYRot());
        entity.setYBodyRot(entity.getYRot());
        entity.setXRot(pitch);

        final var name  = this.getOptionalName();
        if (name != null) {
            entity.setCustomName(Component.literal(name));
        } else if (entity.hasCustomName()) {
            entity.setCustomName(null);
        }
        entity.setCustomNameVisible(this.showName.get());
    }

    private void setup(
            final float x,
            final float y,
            final float z,
            final float yaw,
            final float pitch,
            final String name,
            final boolean showName
    ) {
        this.x          .setValue(x);
        this.y          .setValue(y);
        this.z          .setValue(z);
        this.yaw        .setValue(yaw);
        this.pitch      .setValue(pitch);
        this.name       .set(name);
        this.showName   .set(showName);
    }

    public float getX() {
        return this.x.getValue();
    }

    public float getY() {
        return this.y.getValue();
    }

    public float getZ() {
        return this.z.getValue();
    }

    public float getYaw() {
        return this.yaw.getValue();
    }

    public float getPitch() {
        return this.pitch.getValue();
    }

    public String getName() {
        return this.name.get();
    }

    public boolean isShowName() {
        return this.showName.get();
    }

    public @Nullable String getOptionalName() {
        final var string = this.name.get();
        return string.isEmpty() ? null : string;
    }
}
