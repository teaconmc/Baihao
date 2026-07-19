package org.teacon.baihao.modules.net.quepierts.npcnothard.exhibition.node;

import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.context.ContextKey;
import org.jspecify.annotations.Nullable;
import org.teacon.baihao.modules.net.quepierts.npcnothard.exhibition.HierarchyEntry;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.Duplicatable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class InteractNode extends ExhibitionNode {

    public static final ContextKey<InteractNode> UNIQUE_KEY = InteractNode.createUniqueKey("interact");

    public static final MapCodec<InteractNode> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CommandNode.CODEC.codec().listOf(2, 2).fieldOf("commands").forGetter(InteractNode::getChildren),
            FacingPlayerNode.CODEC.codec().optionalFieldOf("facing", FacingPlayerNode.DEFAULT).forGetter(InteractNode::getFacingPlayer)
    ).apply(instance, InteractNode::new));

    public static final StreamCodec<ByteBuf, InteractNode> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(ArrayList::new, CommandNode.STREAM_CODEC),
            InteractNode::getChildren,
            FacingPlayerNode.STREAM_CODEC,
            InteractNode::getFacingPlayer,
            InteractNode::new
    );

    private final CommandNode left;
    private final CommandNode right;
    private final FacingPlayerNode facing;

    public InteractNode() {
        this.left   = new CommandNode("left_click");
        this.right  = new CommandNode("right_click");
        this.facing = new FacingPlayerNode();
    }

    public InteractNode(CommandNode left, CommandNode right, FacingPlayerNode facing) {
        this.left   = left;
        this.right  = right;
        this.facing = facing;
    }

    public InteractNode(final List<CommandNode> nodes, final FacingPlayerNode facing) {
        this.left   = nodes.get(0);
        this.right  = nodes.get(1);
        this.facing = facing == FacingPlayerNode.DEFAULT ? new FacingPlayerNode() : facing;
    }

    @Override
    public String type() {
        return "interact";
    }

    @Override
    public @Nullable ContextKey<? extends ExhibitionNode> uniqueKey() {
        return UNIQUE_KEY;
    }

    @Override
    public ExhibitionNode duplicate() {
        return new InteractNode(
                this.left.duplicate(),
                this.right.duplicate(),
                this.facing.duplicate()
        );
    }

    @Override
    public void paste(final Duplicatable copy) {
        if (copy.getClass() != InteractNode.class) {
            return;
        }

        final var node = (InteractNode) copy;
        this.left.paste(node.left);
        this.right.paste(node.right);
        this.facing.paste(node.facing);
    }

    @Override
    public String name() {
        return "Interact";
    }

    @Override
    public Collection<HierarchyEntry> children() {
        return List.of(this.left, this.right, this.facing);
    }

    public List<CommandNode> getChildren() {
        return List.of(this.left, this.right);
    }

    public FacingPlayerNode getFacingPlayer() {
        return this.facing;
    }

    public CommandNode getLeft() {
        return this.left;
    }

    public CommandNode getRight() {
        return this.right;
    }
}
