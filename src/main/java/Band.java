import java.util.*;

public class Band {
  private String name;

  public Band(String name){
    this.name = name;
  }

  public String getName(){
    return name;
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
  
}
