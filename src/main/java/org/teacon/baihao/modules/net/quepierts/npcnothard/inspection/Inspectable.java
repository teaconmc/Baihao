package org.teacon.baihao.modules.net.quepierts.npcnothard.inspection;

import org.jspecify.annotations.NonNull;

public interface Inspectable {

    void onInspect(final @NonNull InspectorBuilder builder);

}
