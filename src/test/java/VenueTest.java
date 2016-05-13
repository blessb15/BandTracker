import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class VenueTest {
private String user = "Blake";
private String pass = "blake1997";

  @Before
  public void setup(){
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/band_tracker_test", user, pass);
  }

  @After
  public void teardown(){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM venues*";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Venue_VenueInstatiatesCorrectly_True() {
    Venue newVenue = new Venue("Moda Center", "Portland, Oregon");
    assertTrue(newVenue instanceof Venue);
  }

  @Test
  public void Venue_VenueInstatiatesWithName_True() {
    Venue newVenue = new Venue("Moda Center", "Portland, Oregon");
    assertEquals("Moda Center", newVenue.getName());
  }

  @Test
  public void Venue_VenueInstatiatesWithLocation_True() {
    Venue newVenue = new Venue("Moda Center", "Portland, Oregon");
    assertEquals("Portland, Oregon", newVenue.getLocation());
  }

  @Test
  public void Venue_VenueEquals() {
    Venue newerVenue = new Venue("Moda Center", "Portland, Oregon");
    Venue newVenue = new Venue("Moda Center", "Portland, Oregon");
    assertTrue(newVenue.equals(newerVenue));
  }

  @Test
  public void Venue_VenueSavesToDataBase() {
    Venue newVenue = new Venue("Moda Center", "Portland, Oregon");
    newVenue.save();
    assertTrue(newVenue.equals(Venue.find(newVenue.getId())));
  }

  @Test
  public void Venue_ReturnAllVenuesFromDataBase(){
    Venue newVenue = new Venue("Moda Center", "Portland, Oregon");
    newVenue.save();
    assertEquals(1, Venue.all().size());
  }
}
