package com.megainf3d.informmodelsystcreator.Iterator;

import com.megainf3d.informmodelsystcreator.Tables.Tables;
import java.util.ArrayList;


/**
 * iterator of slides
 */
public class DiagramsIterator implements Iterator {
    private int current=0;
    String bi;
    public ArrayList<String> diagrams;
    public DiagramsIterator(ArrayList<String> diagrams)
    {
        this.diagrams = diagrams;
    }
    @Override
    public boolean hasNext(int mode) {
        if (current < diagrams.size()) {
            String selected = diagrams.get(current);
                try {
                    bi = selected;
                    return true;

                } catch (Exception ex) {
                    System.err.println("Не удалось загрузить картинку! ");
                    ex.printStackTrace();
                    return false;
                }
        }
        else if(mode == 0) preview();
        return true;
    }

    @Override
    public Object next() {
        current++;
        if(this.hasNext(0)){
            return bi;
        }
        return null;
    }

    @Override
    public void preview() {
        current=0;
        this.hasNext(0);
    }
    @Override
    public int getCurrent()
    {
        return current;
    }
}
