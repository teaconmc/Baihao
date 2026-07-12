package org.teacon.baihao.modules.net.quepierts.npcnothard.exhibition;

import org.teacon.baihao.modules.net.quepierts.npcnothard.inspection.Duplicatable;

import java.util.Collection;

public interface HierarchyEntry extends Duplicatable {
    String name();

    Collection<HierarchyEntry> children();

    default int color() {
        return 0xff888888;
    }
}
