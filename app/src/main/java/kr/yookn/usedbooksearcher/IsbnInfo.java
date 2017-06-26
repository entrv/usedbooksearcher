package kr.yookn.usedbooksearcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by entrv on 2017-06-25.
 */

public class IsbnInfo {
    String isbn;
    int showPrice = 1;

    public int getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(int showPrice) {
        this.showPrice = showPrice;
    }

    private List<NaverBookItem> naverBookItems = new ArrayList<NaverBookItem>();;

    public List<NaverBookItem> getNaverBookItems() {
        return naverBookItems;
    }

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

    public static class NaverBookItem {
        private String title;
        private String link;
        private String image;
        private String author;
        private String price;
        private String discount;
        private String publisher;
        private String pubdate;
        private String isbn;
        private String description;

        // getter and setter


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getPubdate() {
            return pubdate;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    ", image='" + image + '\'' +
                    ", author='" + author + '\'' +
                    ", price='" + price + '\'' +
                    ", discount='" + discount + '\'' +
                    ", publisher='" + publisher + '\'' +
                    ", pubdate='" + pubdate + '\'' +
                    ", isbn='" + isbn + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

}
