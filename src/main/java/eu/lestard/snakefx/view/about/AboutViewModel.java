package eu.lestard.snakefx.view.about;


import de.saxsys.mvvmfx.ViewModel;
import javafx.application.HostServices;

import javax.inject.Singleton;

@Singleton
public class AboutViewModel implements ViewModel {
    private static final String URL_LESTARD_EU = "http://www.lestard.eu";
    private static final String URL_GPL = "http://www.gnu.org/licenses/gpl.html";


    private HostServices hostServices;

    public void openHomepageLink() {
        openLink(URL_LESTARD_EU);
    }

    public void openLicenseLink(){
        openLink(URL_GPL);
    }

    public AboutViewModel(HostServices hostServices){
        this.hostServices = hostServices;
    }

    private void openLink(final String urlAsString) {
        hostServices.showDocument(urlAsString);
    }
}
