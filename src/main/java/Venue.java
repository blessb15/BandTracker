import java.util.List;
import org.sql2o.*;

public class Venue {
  private String name;
  private String location;
  private static int id;

  public Venue(String name, String location){
    this.name = name;
    this.location = location;
  }

  public String getName(){
    return name;
  }

  public String getLocation(){
    return name;
  }

  public int getId(){
    return id;
  }

  @Override
  public boolean equals(Object obj){
    if (!(obj instanceof Venue)){
      return false;
    } else {
      Venue newVenue = (Venue) obj;
      return newVenue.getName().equals(this.getName());
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO venues (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.getName())
      .executeUpdate()
      .getKey();
    }
  }

  public static Venue find(int id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM venues WHERE id = :id";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Venue.class);
    }
  }

  public static List<Venue> all(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM venues";
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }
  public void delete(){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM venues WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", this.getId())
      .executeUpdate();

      String sql2 = "DELETE FROM concerts WHERE venue_id = :venue_id";
      con.createQuery(sql)
      .addParameter("venue_id", this.getId())
      .executeUpdate();
    }
  }

}