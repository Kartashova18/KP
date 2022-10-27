package com.megainf3d.informmodelsystcreator.Iterator;

/**
 * Iteration interface
 */
public interface Iterator {
    /**
     * @return has next object
     */
    boolean hasNext(int mode);

    Object next();

    /**
     * go to first object
     */
    void preview();

    /**
     * @return position in object-list
     */
    int getCurrent();

}