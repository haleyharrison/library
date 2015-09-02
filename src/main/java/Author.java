import java.util.List;
import java.util.ArrayList;
import java.util.*;
import org.sql2o.*;

public class Author {
  private int id;
  private String author_name, number;

  public int getId() {
    return id;
  }

  public String getName() {
    return author_name;
  }

  public String getNumber() {
    return number;
  }

  public Author(String author_name, String number) {
    this.author_name = author_name;
    this.number = number;
  }

  public static List<Author> all() {
    String sql = "SELECT * FROM authors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Author.class);
    }
  }

  @Override
  public boolean equals(Object otherAuthor){
    if (!(otherAuthor instanceof Author)) {
      return false;
    } else {
      Author newAuthor = (Author) otherAuthor;
      return this.getName().equals(newAuthor.getName()) &&
              this.getNumber().equals(newAuthor.getNumber());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors (author_name, number) VALUES (:author_name, :number)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("author_name", this.author_name)
        .addParameter("number", this.number)
        .executeUpdate()
        .getKey();
    }
  }

  public static Author find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM authors where id=:id";
      Author Author = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Author.class);
      return Author;
    }
  }

  public void update(String author_name, String number) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE authors SET author_name = :author_name, number = :number WHERE id = :id";
      con.createQuery(sql)
        .addParameter("author_name", author_name)
        .addParameter("number", number)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void addBook(Book book) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors_books (author_id, book_id) VALUES (:author_id, :book_id)";
      con.createQuery(sql)
        .addParameter("author_id", this.getId())
        .addParameter("book_id", book.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Book> getBooks() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT book_id FROM authors_books WHERE author_id = :author_id";
      List<Integer> bookIds = con.createQuery(sql)
        .addParameter("author_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Book> books = new ArrayList<Book>();

      for (Integer bookId : bookIds) {
          String bookQuery = "Select * From books WHERE id = :bookId";
          Book book = con.createQuery(bookQuery)
            .addParameter("bookId", bookId)
            .executeAndFetchFirst(Book.class);
          books.add(book);
      }
      return books;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM authors WHERE id = :id";
        con.createQuery(deleteQuery)
          .addParameter("id", id)
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM authors_books WHERE author_id = :authorId";
        con.createQuery(joinDeleteQuery)
          .addParameter("authorId", this.getId())
          .executeUpdate();
    }
  }

  // public static List<Book> completedAuthorBooks() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String joinQuery = "SELECT books.* FROM authors JOIN authors_books ON (authors.id=authors_books.author_id) JOIN books ON (authors_books.book_id = books.id) WHERE books.iscompleted = true ORDER BY duedate";
  //     return con.createQuery(joinQuery).executeAndFetch(Book.class);
  //   }
  // }
  //
  // public static List<Book> incompletedAuthorBooks() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String joinQuery = "SELECT books.* FROM authors JOIN authors_books ON (authors.id=authors_books.author_id) JOIN books ON (authors_books.book_id = books.id) WHERE books.iscompleted = false ORDER BY duedate";
  //     return con.createQuery(joinQuery).executeAndFetch(Book.class);
  //   }
  // }

}
