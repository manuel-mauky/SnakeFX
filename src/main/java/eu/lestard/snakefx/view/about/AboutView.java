package eu.lestard.snakefx.view.about;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.InjectViewModel;
import javafx.fxml.FXML;

public class AboutView implements FxmlView<AboutViewModel> {

    @InjectViewModel
    private AboutViewModel viewModel;

    @FXML
    public void homepageLink() {
        viewModel.openHomepageLink();
    }

    @FXML
    public void licenseLink() {
        viewModel.openLicenseLink();
    }
}
