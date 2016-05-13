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
    }
  }

  @Test
  public void Band_BandInstatiatesCorrectly_True() {
    Band newBand = new Band("The Turtle Killers");
    assertTrue(newBand instanceof Band);
  }


}
