package org.teacon.baihao.modules.net.quepierts.npcnothard.inspection;

import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;

@MethodsReturnNonnullByDefault
public interface DisplayableType {
    Component getTypeDisplayName();

    Component getDisplayName();
}
