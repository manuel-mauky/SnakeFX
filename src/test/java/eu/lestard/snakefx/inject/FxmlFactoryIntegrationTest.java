package eu.lestard.snakefx.inject;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import eu.lestard.snakefx.util.FxmlFactory;
import javafx.fxml.FXMLLoader;

import org.junit.Before;
import org.junit.Ignore;

@Ignore
public class FxmlFactoryIntegrationTest {

	private static final String FXML_FILE = "/fxml/example.fxml";
	private static final String INVALID_FXML_FILE = "/fxml/invalidExample.fxml";

	private FXMLLoader fxmlLoader;
	private FxmlFactory factory;

	@Before
	public void setup() {
		fxmlLoader = spy(new FXMLLoader());

		factory = new FxmlFactory(null);
	}

	// @Test
	// public void testGetFxmlRoot() {
	// Object presenter = new Object();
	//
	// Parent root = factory.getFxmlRoot(FXML_FILE, presenter, fxmlLoader);
	// assertThat(root).isNotNull();
	//
	// // VBox is the root element of the example.fxml file
	// assertThat(root).isInstanceOf(VBox.class);
	//
	// verify(fxmlLoader).setController(presenter);
	// }
	//
	// /**
	// * The presenter must not be null
	// */
	// @Test(expected = IllegalArgumentException.class)
	// public void testGetFxmlRootFailControllerIsNull() {
	// factory.getFxmlRoot(FXML_FILE, null);
	// }
	//
	// @Test(expected = IllegalArgumentException.class)
	// public void testGetFxmlRootFailWrongFilename() {
	// Object presenter = new Object();
	//
	// Parent root = factory.getFxmlRoot("wrong/filename.fxml", presenter,
	// fxmlLoader);
	// assertThat(root).isNotNull();
	//
	// // VBox is the root element of the example.fxml file
	// assertThat(root).isInstanceOf(VBox.class);
	//
	// verify(fxmlLoader).setController(presenter);
	// }
	//
	// /**
	// * the file INVALID_FXLM_FILE is no valid FXML file. In this case a
	// * IllegalStateException has to be thrown
	// */
	// @Test(expected = IllegalStateException.class)
	// public void testGetFxmlRootFailMalformedFile() {
	// Object presenter = new Object();
	//
	// factory.getFxmlRoot(INVALID_FXML_FILE, presenter);
	// }

}
