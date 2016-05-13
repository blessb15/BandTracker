import java.util.*;
import org.sql2o.*;

public class Band {
  private String name;
  private static int id;

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

}
