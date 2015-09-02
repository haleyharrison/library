import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;


public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      model.put("books", Book.all());
      model.put("authors", Author.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/add_book", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String date = request.queryParams("date");
      Book newBook = new Book(name, date);
      newBook.save();
      response.redirect("/");
      return null;
    });

    post("/add_author", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String number = request.queryParams("number");
      Author newAuthor = new Author(name, number);
      newAuthor.save();
      response.redirect("/");
      return null;
    });

    get("/author/:id", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      int author_id = Integer.parseInt(request.params("id"));
      Author author = Author.find(author_id);
      ArrayList<Book> books = author.getBooks();
      model.put("books", books);
      model.put("all_books", Book.all());
      model.put("author", author);
      model.put("template", "templates/author.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/update_author", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int author_id = Integer.parseInt(request.queryParams("author_id"));
      Author myAuthor = Author.find(author_id);
      String name = request.queryParams("name");
      String number = request.queryParams("number");
      myAuthor.update(name, number);
      response.redirect("/authors/" + author_id);
      return null;
    });

    post("/add_books", (request, response) -> {
      int authorId = Integer.parseInt(request.queryParams("author_id"));
      Author author = Author.find(authorId);
      int bookId = Integer.parseInt(request.queryParams("book_id"));
      Book book = Book.find(bookId);
      author.addBook(book);
      response.redirect("/authors/" + authorId);
      return null;
    });

    get("/book/:id", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      int book_id = Integer.parseInt(request.params("id"));
      Book myBook = Book.find(book_id);
      ArrayList<Author> myAuthors = myBook.getAuthors();
      model.put("book", myBook);
      model.put("myAuthors", myAuthors);
      model.put("authors", Author.all());
      model.put("majors", Department.all());
      model.put("template", "templates/book.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/update_book", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int book_id = Integer.parseInt(request.queryParams("book_id"));
      Book myBook = Book.find(book_id);
      String name = request.queryParams("name");
      String date = request.queryParams("date");
      int majorId = Integer.parseInt(request.queryParams("major_id"));

      myBook.update(name, date, majorId);
      response.redirect("/book/" + book_id);
      return null;
    });

    post("/add_authors", (request, response) -> {
      int book_id = Integer.parseInt(request.queryParams("book_id"));
      Book myBook = Book.find(book_id);
      int author_id = Integer.parseInt(request.queryParams("author_id"));
      Author author = Author.find(author_id);
      myBook.addAuthor(author);
      response.redirect("/book/" + book_id);
      return null;
    });

    get("/bookList", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/bookList.vtl");
      model.put("books", Book.all());
      model.put("all_majors", Department.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // get("/delete_authors/:author_id", (request, response) -> {
    //   int author_id = Integer.parseInt(request.params("author_id"));
    //   Author myAuthor = Author.find(author_id);
    //   myAuthor.delete();
    //   response.redirect("/authors");
    //   return null;
    // });
    //


    //
    // get("/:author_id/isCompleted_books/:book_id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int author_id = Integer.parseInt(request.params("author_id"));
    //   int book_id = Integer.parseInt(request.params("book_id"));
    //   Book myBook = Book.find(book_id);
    //   myBook.isCompleted();
    //   response.redirect("/authors/" + author_id);
    //   return null;
    // });
    //
    // get("/:author_id/isNotCompleted_books/:book_id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int author_id = Integer.parseInt(request.params("author_id"));
    //   int book_id = Integer.parseInt(request.params("book_id"));
    //   Book myBook = Book.find(book_id);
    //   myBook.isNotCompleted();
    //   response.redirect("/authors/" + author_id);
    //   return null;
    // });
    //
    // get("/:author_id/delete_books/:book_id", (request, response) -> {
    //   int author_id = Integer.parseInt(request.params("author_id"));
    //   int book_id = Integer.parseInt(request.params("book_id"));
    //   Book myBook = Book.find(book_id);
    //   myBook.delete();
    //   response.redirect("/authors/" + author_id);
    //   return null;
    // });
    //
    // get("/books", (request,response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   List<Book> books = Book.all();
    //   model.put("books", books);
    //   model.put("completedBooks", Book.completedBooks());
    //   model.put("incompletedBooks", Book.incompletedBooks());
    //   model.put("template", "templates/books.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/books/:id", (request,response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int id = Integer.parseInt(request.params("id"));
    //   Book book = Book.find(id);
    //   model.put("book", book);
    //   model.put("allAuthors", Author.all());
    //   model.put("template", "templates/book.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/books", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   String name = request.queryParams("name");
    //   Book newBook = new Book(name);
    //   newBook.save();
    //   response.redirect("/books");
    //   return null;
    // });
    //
    //
    //
    // get("/delete_books/:book_id", (request, response) -> {
    //   int book_id = Integer.parseInt(request.params("book_id"));
    //   Book myBook = Book.find(book_id);
    //   myBook.delete();
    //   response.redirect("/books");
    //   return null;
    // });
    //
    // post("/add_authors", (request, response) -> {
    //   int bookId = Integer.parseInt(request.queryParams("book_id"));
    //   int authorId = Integer.parseInt(request.queryParams("author_id"));
    //   Author author = Author.find(authorId);
    //   Book book = Book.find(bookId);
    //   book.addAuthor(author);
    //   response.redirect("/books/" + bookId);
    //   return null;
    // });
    //
    // get("/isCompleted_books/:book_id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int book_id = Integer.parseInt(request.params("book_id"));
    //   Book myBook = Book.find(book_id);
    //   myBook.isCompleted();
    //   response.redirect("/books");
    //   return null;
    // });
    //
    // get("/isNotCompleted_books/:book_id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int book_id = Integer.parseInt(request.params("book_id"));
    //   Book myBook = Book.find(book_id);
    //   myBook.isNotCompleted();
    //   response.redirect("/books");
    //   return null;
    // });
    //
    // get("/update_books/:id", (request,response) ->{
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int id = Integer.parseInt(request.params("id"));
    //   Book book = Book.find(id);
    //   model.put("book", book);
    //   model.put("template", "templates/book-update.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/update_books/:id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int book_id = Integer.parseInt(request.params("id"));
    //   Book myBook = Book.find(book_id);
    //   String name = request.queryParams("name");
    //   myBook.update(name);
    //   response.redirect("/books/" + book_id);
    //   return null;
    // });
    //
    // post("/date_books/:id", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   int book_id = Integer.parseInt(request.params("id"));
    //   Book myBook = Book.find(book_id);
    //   String date = request.queryParams("date");
    //   myBook.date(date);
    //   response.redirect("/books/" + book_id);
    //   return null;
    // });

    /*
    put("/books/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params("id")));
      String name = request.queryParams("name");
      book.update("name");
      model.put("template", "templates/book.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    delete("/books/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params("id")));
      book.delete();
      model.put("template", "templates/book.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    */

  }
}
