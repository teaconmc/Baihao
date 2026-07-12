package org.teacon.baihao.modules.net.quepierts.npcnothard.client.gui.inspector;

import net.minecraft.network.chat.Component;
import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.property.Property;

public abstract class InspectorModificationWidget<T> extends InspectorWidget {
    protected final Component   message;
    protected final Property<T> property;

    protected InspectorModificationWidget(
            int         height,
            Component   message,
            Property<T> property
    ) {
        super(height);
        this.message    = message;
        this.property   = property;
    }
}
