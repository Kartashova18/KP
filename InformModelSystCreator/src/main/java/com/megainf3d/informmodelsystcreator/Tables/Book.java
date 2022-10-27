package com.megainf3d.informmodelsystcreator.Tables;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class Book {
    public Book()
    {}
    protected String isbn;
    protected String title;
    public Book(final String isbn, final String title) {
        this.isbn = isbn;
        this.title = title;
    }
    @XmlElement
    public String getIsbn() {
        return this.isbn;
    }
    @XmlElement
    public String getTitle() {
        return this.title;
    }
}
