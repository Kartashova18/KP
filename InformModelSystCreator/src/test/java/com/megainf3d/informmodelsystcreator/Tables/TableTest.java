package com.megainf3d.informmodelsystcreator.Tables;

import com.megainf3d.informmodelsystcreator.StyleType;
import de.saxsys.javafx.test.JfxRunner;
import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JfxRunner.class)
class TableTest {

    @Test
    void updatePosition() {
        new JFXPanel();
        Table table =new Table(2,2,"d", StyleType.BRIGHT);
        table.updatePosition(4,23);
        assertEquals(table.X,6);
    }

    @Test
    void getArrayOfMinMaxPoints() {
        new JFXPanel();
        Table table =new Table(2,2,"d", StyleType.BRIGHT);
        assertEquals(table.getArrayOfMinMaxPoints().get(0).getX(),2);
        assertEquals(table.getArrayOfMinMaxPoints().get(0).getY(),2);
    }

    @Test
    void testEquals() {
        new JFXPanel();
        Table table =new Table(2,2,"d", StyleType.BRIGHT);
        Assert.assertTrue(table.equals(new Table(2,2,"d", StyleType.BRIGHT)));
    }
}