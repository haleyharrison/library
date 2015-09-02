import java.util.List;
import java.util.ArrayList;
import java.util.*;
import org.sql2o.*;

public class Patron {
  private int id;
  private String patron_name;

  public int getId() {
    return id;
  }

  public String getName() {
    return patron_name;
  }

  public Patron(String patron_name) {
    this.patron_name = patron_name;
  }

  public static List<Patron> all() {
    String sql = "SELECT * FROM patrons";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Patron.class);
    }
  }

  @Override
  public boolean equals(Object otherPatron){
    if (!(otherPatron instanceof Patron)) {
      return false;
    } else {
      Patron newPatron = (Patron) otherPatron;
      return this.getName().equals(newPatron.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patrons (patron_name) VALUES (:patron_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("patron_name", this.patron_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Patron find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patrons where id=:id";
      Patron Patron = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Patron.class);
      return Patron;
    }
  }

  public void update(String patron_name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patrons SET patron_name = :patron_name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("patron_name", patron_name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void addBook(Book book) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patrons_books (patron_id, book_id) VALUES (:patron_id, :book_id)";
      con.createQuery(sql)
        .addParameter("patron_id", this.getId())
        .addParameter("book_id", book.getId())
        .executeUpdate();
    }
  }

  public ArrayList<Book> getBooks() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT book_id FROM patrons_books WHERE patron_id = :patron_id";
      List<Integer> bookIds = con.createQuery(sql)
        .addParameter("patron_id", this.getId())
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
      String deleteQuery = "DELETE FROM patrons WHERE id = :id";
        con.createQuery(deleteQuery)
          .addParameter("id", id)
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM patrons_books WHERE patron_id = :patronId";
        con.createQuery(joinDeleteQuery)
          .addParameter("patronId", this.getId())
          .executeUpdate();
    }
  }

  // public static List<Book> completedPatronBooks() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String joinQuery = "SELECT books.* FROM patrons JOIN patrons_books ON (patrons.id=patrons_books.patron_id) JOIN books ON (patrons_books.book_id = books.id) WHERE books.iscompleted = true ORDER BY duedate";
  //     return con.createQuery(joinQuery).executeAndFetch(Book.class);
  //   }
  // }
  //
  // public static List<Book> incompletedPatronBooks() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String joinQuery = "SELECT books.* FROM patrons JOIN patrons_books ON (patrons.id=patrons_books.patron_id) JOIN books ON (patrons_books.book_id = books.id) WHERE books.iscompleted = false ORDER BY duedate";
  //     return con.createQuery(joinQuery).executeAndFetch(Book.class);
  //   }
  // }

}
