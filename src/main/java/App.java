import java.util.*;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("bands", Band.all());
        model.put("venues", Venue.all());
        model.put("template", "templates/home.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      String bandName = request.queryParams("bandName");
      if (bandName != null){
        Band newBand = new Band(bandName);
        newBand.save();
      }
      String venueName = request.queryParams("venueName");
      String venueLocation = request.queryParams("venueLocation");
      if (venueName != null && venueLocation != null){
        Venue newVenue = new Venue(venueName, venueLocation);
        newVenue.save();
      }
      response.redirect("/");
      return null;
    });

    get("/bands/:id", (request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        Band band = Band.find(Integer.parseInt(request.params(":id")));
        model.put("venues", Venue.all());
        model.put("band", band);
        model.put("template", "templates/band.vtl");
        return new ModelAndView(model, layout);
      }, new VelocityTemplateEngine());

      post("/add_venues", (request, response) -> {
        int bandId = Integer.parseInt(request.queryParams("band_id"));
        int venueId = Integer.parseInt(request.queryParams("venue_id"));
        Venue venue = Venue.find(venueId);
        Band band = Band.find(bandId);
        band.addVenue(venue);
        response.redirect("/bands/" + bandId);
        return null;
      });

    get("/bands/:id/delete", (request, response) -> {
        Map model = new HashMap();
        Band band = Band.find(Integer.parseInt(request.params(":id")));
        band.delete();
        response.redirect("/");
        return null;
        });

  }
}
