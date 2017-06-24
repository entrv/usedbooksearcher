package kr.yookn.usedbooksearcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by entrv on 2017-06-25.
 */

public class IsbnInfo {
    String isbn;
    private final List<BookInfo> bookinfo = new ArrayList<BookInfo>();

    public IsbnInfo(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<BookInfo> getBookinfo() {
        return bookinfo;
    }
}
