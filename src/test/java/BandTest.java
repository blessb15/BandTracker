import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class BandTest {
private String user = "Blake";
private String pass = "blake1997";

  @Before
  public void setup(){
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/band_tracker_test", user, pass);
  }

  @After
  public void teardown(){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM bands *";
      con.createQuery(sql).executeUpdate();
      String sql2 = "DELETE FROM venues *";
      con.createQuery(sql2).executeUpdate();
    }
  }

  @Test
  public void Band_BandInstatiatesCorrectly_True() {
    Band newBand = new Band("The Turtle Killers");
    assertTrue(newBand instanceof Band);
  }

  @Test
  public void Band_BandInstatiatesWithName_True() {
    Band newBand = new Band("The Turtle Killers");
    assertEquals("The Turtle Killers", newBand.getName());
  }

  @Test
  public void Band_BandEquals() {
    Band newerBand = new Band("The Turtle Killers");
    Band newBand = new Band("The Turtle Killers");
    assertTrue(newBand.equals(newerBand));
  }

  @Test
  public void Band_BandSavesToDataBase() {
    Band newBand = new Band("The Turtle Killers");
    newBand.save();
    assertTrue(newBand.equals(Band.find(newBand.getId())));
  }

  @Test
  public void Band_ReturnAllBandsFromDataBase(){
    Band newBand = new Band("The Turtle Killers");
    newBand.save();
    assertEquals(1, Band.all().size());
  }

  @Test
  public void Band_BandAddVenue(){
    Band newBand = new Band("Dope Diapers");
    newBand.save();
    Venue newVenue = new Venue("Moda Center", "Portland, Oregon");
    newVenue.save();
    newBand.addVenue(newVenue);
    assertEquals(1, newBand.getVenues().size());
  }
}
