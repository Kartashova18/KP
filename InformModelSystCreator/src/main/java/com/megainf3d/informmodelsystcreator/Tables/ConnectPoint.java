package com.megainf3d.informmodelsystcreator.Tables;

/**
 * @param <X> first item
 * @param <Y> second item
 */
public class ConnectPoint<X,Y> {
    /**
     * first item
     */
    public X item1;
    /**
     * second item
     */
    public Y item2;

    /**
     * @param item1 for connect
     * @param item2 for connect
     */
    public ConnectPoint(X item1, Y item2) {
        this.item1 = item1;
        this.item2 = item2;
    }
}
