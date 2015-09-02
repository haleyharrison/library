import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Book.all().size(), 0);
  }

  @Test
  public void addBook(){
    Book newBook = new Book("Fantastic Beasts and Where to Find Them", "August");
    newBook.save();
    String title = newBook.getName();
    assertEquals(title, "Fantastic Beasts and Where to Find Them");
  }

}
