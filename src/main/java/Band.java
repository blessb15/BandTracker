import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Band {
  private String name;
  private int id;

  public Band(String name){
    this.name = name;
  }

  public String getName(){
    return name;
  }

  public int getId(){
    return id;
  }

  @Override
  public boolean equals(Object obj){
    if (!(obj instanceof Band)){
      return false;
    } else {
      Band newBand = (Band) obj;
      return newBand.getName().equals(this.getName());
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO bands (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.getName())
      .executeUpdate()
      .getKey();
    }
  }

  public static Band find(int id){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM bands WHERE id = :id";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Band.class);
    }
  }

  public static List<Band> all(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM bands";
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  public void addVenue(Venue venue){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO concerts (band_id, venue_id) VALUES (:band_id, :venue_id)";
      con.createQuery(sql)
      .addParameter("band_id", this.getId())
      .addParameter("venue_id", venue.getId())
      .executeUpdate();
    }
  }

  public List<Venue> getVenues(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT venue_id FROM concerts WHERE band_id = :band_id";
      List<Integer> venue_ids = con.createQuery(sql)
      .addParameter("band_id", this.getId())
      .executeAndFetch(Integer.class);

      List<Venue> venues = new ArrayList<Venue>();
      for( Integer venue_id : venue_ids ){
        String sql2 = "SELECT * FROM venues WHERE id = :venue_id";
        Venue venue = con.createQuery(sql2)
        .addParameter("venue_id", venue_id)
        .executeAndFetchFirst(Venue.class);
        venues.add(venue);
      }
      return venues;
    }
  }

  public void delete(){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM bands WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", this.getId())
      .executeUpdate();

      String sql2 = "DELETE FROM concerts WHERE band_id = :band_id";
      con.createQuery(sql2)
      .addParameter("band_id", this.getId())
      .executeUpdate();
    }
  }
}
