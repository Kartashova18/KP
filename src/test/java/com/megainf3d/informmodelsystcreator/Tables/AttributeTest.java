package com.megainf3d.informmodelsystcreator.Tables;

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
        Table table =new Table(2,2,"d");
        Attribute attribute=table.addAttribute("d",KeyType.FOREIGN,AttributeType.CHAR);
        attribute.update("sdsd",KeyType.NONE,AttributeType.INT);
        assertEquals(attribute.getText(), "sdsd");
    }
}