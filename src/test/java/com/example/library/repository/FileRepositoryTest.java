package com.example.library.repository;

import com.example.library.domain.Book;
import com.example.library.domain.BookStatusType;
import com.example.library.file.FileInit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileRepositoryTest {

    BookRepository bookRepository;
    Book book;

    @BeforeEach
    public void initBook() {

        book = Book.newInstance(9999L, "Java", "kim", 1214, BookStatusType.대여가능, LocalDateTime.now());
        bookRepository = new BookFileRepository();

    }

    @Test
    @DisplayName("json파일을 BookList로 파싱(초기화)한 Repository의 size가 3이상이다.")
    public void convertJsonToBookList() {
        Map<Long, Book> books = FileInit.initializeRepository();
        assertThat(books.size()).isGreaterThan(3);
    }

    @Test
    @DisplayName("bookRepository에 book을 추가하면 size가 1 커진다.")
    public void addBook() {
        int before = bookRepository.size();
        bookRepository.addBook(book);
        int after = bookRepository.size();

        assertThat(before + 1).isEqualTo(after);
    }

    @Test
    @DisplayName("존재하는 도서 번호를 입력하면 도서를 삭제한다.")
    public void successDeleteBook() {

        assertTrue(bookRepository.deleteBook(1));

    }

    @Test
    @DisplayName("존재하지 않는 도서 번호를 입력하면 삭제 실패한다.")
    public void failDeleteBook() {

        assertFalse(bookRepository.deleteBook(1124));

    }
}
