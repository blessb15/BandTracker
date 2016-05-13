import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class ClearRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/band_tracker_test", "Blake", "blake1997");
  }

  protected void after() {
    try(Connection con = DB.sql2o.open()){
      String sqlVenues = "DELETE FROM venues *;";
      String sqlBands = "DELETE FROM bands *;";
      con.createQuery(sqlVenues).executeUpdate();
      con.createQuery(sqlBands).executeUpdate();
    }
  }
}
