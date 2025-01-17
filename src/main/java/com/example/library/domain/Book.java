package com.example.library.domain;

import com.example.library.validation.BookStatusValidator;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;


@Getter
public class Book {

    private long id;
    private String title;
    private String writer;
    private int pageNumber;
    private BookStatusType bookStatusType;

    private LocalDateTime bookReturnTime;

    private final static int ORGANAZING_TIME = 300;

    private Book(long id, String title, String writer, int pageNumber, BookStatusType bookStatusType, LocalDateTime bookReturnTime) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.pageNumber = pageNumber;
        this.bookStatusType = bookStatusType;
        this.bookReturnTime = bookReturnTime;
    }

    public static Book newInstance(long id, String title, String writer, int pageNumber, BookStatusType bookStatusType, LocalDateTime bookReturnTime) {
        return new Book(id, title, writer, pageNumber, bookStatusType, bookReturnTime);
    }


    public void printBook() {
        System.out.println("도서번호 : " + this.id + "\n" +
                "제목 : " + this.title + "\n" +
                "작가 이름 : " + this.writer + "\n" +
                "페이지 수 : " + this.pageNumber + "\n" +
                "상태 : " + this.bookStatusType + "\n\n" +
                "------------------------------\n");
    }

    public boolean isSame(String bookName) {
        if (this.title.contains(bookName)) {
            return true;
        } else
            return false;
    }

    public void borrowBook() {
        if (BookStatusValidator.borrowBook(this.bookStatusType))
            this.bookStatusType = BookStatusType.대여중;
    }


    public void returnBook() {
        if (BookStatusValidator.returnBook(this.bookStatusType)) {
            this.bookStatusType = BookStatusType.도서정리중;
            this.bookReturnTime = LocalDateTime.now();
        }
    }

    public void loseBook() {
        if (BookStatusValidator.loseBook(this.bookStatusType)) {
            this.bookStatusType = BookStatusType.분실됨;
        }
    }

    public boolean isExceededfiveMinute() {
        if (this.bookStatusType == BookStatusType.도서정리중) {
            Duration duration = Duration.between(this.bookReturnTime, LocalDateTime.now());

            if (duration.getSeconds() >= ORGANAZING_TIME) {
                this.bookStatusType = BookStatusType.대여가능;
                this.bookReturnTime = null;
                return true;
            }
        }
        return false;
    }

}
