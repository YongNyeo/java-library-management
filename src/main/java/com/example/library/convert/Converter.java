package com.example.library.convert;

import com.example.library.domain.Book;
import com.example.library.domain.BookStatus;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Converter {
    public static Book convertJsonToBook(JSONObject jsonObject) {
        String id = (String) jsonObject.get("id");
        String title = (String) jsonObject.get("title");
        String writer = (String) jsonObject.get("writer");
        String pageNumber = (String) jsonObject.get("pageNumber");
        String bookStatus = (String) jsonObject.get("bookStatus");
        String bookReturnTime = (String) jsonObject.get("bookReturnTime");

        return new Book(Long.parseLong(id), title, writer, pageNumber,
                convertStringToBookStatus(bookStatus), convertStringToLocalDateTime(bookReturnTime));
    }

    public static JSONObject convertBookToJson(List<Book> books) {
        JSONArray jsonArray = new JSONArray();

        books.stream()
                .forEach(book -> jsonArray.add(book.convertJson()));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("books", jsonArray);

        return jsonObject;
    }

    public static String convertLocalDateTimeToString(LocalDateTime bookReturnTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        return bookReturnTime.format(formatter);
    }


    private static BookStatus convertStringToBookStatus(String status) {
        if (status.equals("대여중")) {

            return BookStatus.대여중;

        } else if (status.equals("대여가능")) {

            return BookStatus.대여가능;

        } else if (status.equals("도서 정리중")) {

            return BookStatus.도서정리중;

        } else
            return BookStatus.분실됨;
    }

    private static LocalDateTime convertStringToLocalDateTime(String time) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        return LocalDateTime.parse(time, formatter);
    }

}