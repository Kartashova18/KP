package com.megainf3d.informmodelsystcreator.Tables;

import com.megainf3d.informmodelsystcreator.StyleType;
import de.saxsys.javafx.test.JfxRunner;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JfxRunner.class)
class AttributeTest {

    @Test
    void update() {
        new JFXPanel();
        Table table =new Table(2,2,"d",StyleType.BRIGHT);
        Attribute attribute=table.addAttribute("d",KeyType.FOREIGN,AttributeType.CHAR,StyleType.BRIGHT);
        attribute.update("sdsd",KeyType.NONE,AttributeType.INT, StyleType.BRIGHT);
        assertEquals(attribute.getText(), "sdsd");
    }
}