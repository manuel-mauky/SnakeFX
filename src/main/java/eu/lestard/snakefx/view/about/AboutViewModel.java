package eu.lestard.snakefx.view.about;


import de.saxsys.jfx.mvvm.api.ViewModel;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutViewModel implements ViewModel {
    private static final String URL_LESTARD_EU = "http://www.lestard.eu";
    private static final String URL_GPL = "http://www.gnu.org/licenses/gpl.html";


    public void openHomepageLink() {
        openLink(URL_LESTARD_EU);
    }

    public void openLicenseLink(){
        openLink(URL_GPL);
    }

    private void openLink(String urlAsString) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();

            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(new URI(urlAsString));
                } catch (IOException | URISyntaxException e) {
                    System.err.println("Can't open the URL:" + urlAsString);
                    e.printStackTrace();
                    return;
                }
            }
        }
        System.err.println("Can't open the URL:" + urlAsString);
    }
}
