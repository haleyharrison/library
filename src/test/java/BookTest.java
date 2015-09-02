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
/*
  @Test
  public void checkoutSaves(){
    Book newBook = new Book("Fantastic Beasts and Where to Find Them", "August");
    newBook.save();


  }


  public void saveCheckout(int patron_id, String dueDate) {
    //make it so there is a row in the checkouts table which
    // contains book_id, patron_id, and dueDate
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO checkouts (patron_id, book_id, dueDate) values (:patron_id, :book_id, :dueDate)";
        con.createQuery(sql)
        .addParameter("patron_id", patron_id)
        .addParameter("book_id", this.id)
        .addParameter("dueDate", dueDate)
        .executeUpdate()
        .getKey();
    }

  }
*/
/*
  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Category firstCategory = new Category("Household chores");
    Category secondCategory = new Category("Household chores");
    assertTrue(firstCategory.equals(secondCategory));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    assertTrue(Category.all().get(0).equals(myCategory));
  }

  @Test
  public void find_findCategoryInDatabase_true() {
    Category myCategory = new Category("Household chores");
    myCategory.save();
    Category savedCategory = Category.find(myCategory.getId());
    assertTrue(myCategory.equals(savedCategory));
  }


  // new tests below
  @Test
  public void addTask_addsTaskToCategory() {
    Category myCategory = new Category("Household chores");
    myCategory.save();

    Task myTask = new Task("Mow the lawn");
    myTask.save();

    myCategory.addTask(myTask);
    Task savedTask = myCategory.getTasks().get(0);
    assertTrue(myTask.equals(savedTask));
  }

  @Test
  public void getTasks_returnsAllTasks_ArrayList() {
    Category myCategory = new Category("Household chores");
    myCategory.save();

    Task myTask = new Task("Mow the lawn");
    myTask.save();

    myCategory.addTask(myTask);
    List<Task> savedTasks = myCategory.getTasks();
    assertEquals(savedTasks.size(), 1);
  }

  @Test
  public void delete_deletesAllTasksAndListsAssoicationes() {
    Category myCategory = new Category("Household chores");
    myCategory.save();

    Task myTask = new Task("Mow the lawn");
    myTask.save();

    myCategory.addTask(myTask);
    myCategory.delete();
    assertEquals(myTask.getCategories().size(), 0);
  }

  @Test
  public void update_updateCategoryCorrectly_true(){
    Category myCategory = new Category("Household chores");
    myCategory.save();
    myCategory.update("Work");
    assertTrue(Category.all().get(0).getName().equals("Work"));

  }
*/
}
