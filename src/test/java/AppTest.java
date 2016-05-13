import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.fluentlenium.adapter.FluentTest;

  public class AppTest extends FluentTest {
    public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public ClearRule clear = new ClearRule();

  @Test
  public void RootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Concerts");
  }

  @Test
  public void BandCreated(){
    Band band = new Band("The Band");
    band.save();
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("The Band");
    }

  @Test
  public void VenueCreated(){
    Band band = new Band("The Band");
    band.save();
    Venue venue = new Venue("Epicodus", "Portland");
    venue.save();
    String url = String.format("http://localhost:4567/bands/%d", band.getId());
    goTo(url);
    band.addVenue(venue);
    assertThat(pageSource()).contains("Epicodus, Portland");
  }

  @Test
  public void BandPage(){
    Band band = new Band("The Band");
    band.save();
    String url = String.format("http://localhost:4567/bands/%d", band.getId());
    goTo(url);
    assertThat(pageSource()).contains("Here are the venues The Band have played at");
  }
}
