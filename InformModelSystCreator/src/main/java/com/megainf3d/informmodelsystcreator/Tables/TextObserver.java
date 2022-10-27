package com.megainf3d.informmodelsystcreator.Tables;

/**
 * observer class
 */
public class TextObserver {
    /**
     * observer
     */
    RelationListener update;

    /**
     * @param updateConnections observer
     */
    public TextObserver(RelationListener updateConnections) {
        update = updateConnections;
    }

    /**
     * update
     */
    public void update()
    {
        update.onEdit();
    }
}
