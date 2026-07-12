package org.teacon.baihao.modules.net.quepierts.npcnothard.network;

import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.permissions.Permissions;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.teacon.baihao.modules.net.quepierts.npcnothard.NpcNotHard;
import org.teacon.baihao.modules.net.quepierts.npcnothard.entity.ExhibitionEntity;
import org.teacon.baihao.modules.net.quepierts.npcnothard.exhibition.ExhibitionNodeManager;

@MethodsReturnNonnullByDefault
public record UpdateExhibitionEntity(
        int                     entityId,
        ExhibitionNodeManager   manager
) implements CustomPacketPayload {

    public static final StreamCodec<ByteBuf, UpdateExhibitionEntity> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            UpdateExhibitionEntity::entityId,
            ExhibitionNodeManager.STREAM_CODEC,
            UpdateExhibitionEntity::manager,
            UpdateExhibitionEntity::new
    );

    public static final Type<UpdateExhibitionEntity> TYPE
            = new Type<>(NpcNotHard.location("update_exhibition_entity"));

    public static UpdateExhibitionEntity of(final ExhibitionEntity entity) {
        return new UpdateExhibitionEntity(entity.getId(), entity.getExhibitionNode());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            final var player = context.player();

            if (!player.isCreative()
                    || !player.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER)) {
                return;
            }

            final var level  = player.level();
            final var entity = level.getEntity(entityId);

            if (!(entity instanceof ExhibitionEntity exhibition)) {
                return;
            }

            if ((!player.permissions().hasPermission(Permissions.COMMANDS_ADMIN))) {
                return;
            }

            exhibition.update(manager());

        });
    }
}
