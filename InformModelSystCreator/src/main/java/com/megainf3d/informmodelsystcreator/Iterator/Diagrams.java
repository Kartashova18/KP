package com.megainf3d.informmodelsystcreator.Iterator;

import com.megainf3d.informmodelsystcreator.Tables.Tables;

import java.util.ArrayList;

/**
 * list of slides
 */
public class Diagrams implements Aggregate {
    private final ArrayList<String> diagrams;
    public Diagrams(ArrayList<String> diagrams) {
        this.diagrams = diagrams;
    }
    public void addDiagram(String diagram)
    {
        diagrams.add(diagram);
    }

    public ArrayList<String> getDiagrams() {
        return diagrams;
    }

    @Override
    public Iterator getIterator() {
        return new DiagramsIterator(diagrams);
    }
}