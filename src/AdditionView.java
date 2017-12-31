
/**
MIT License

Copyright (c) 2017 Frank Kopp

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * AdditionView
 * 31.12.2017
 * @author Frank Kopp
 */
public class AdditionView {
	private GridPane view ;
	private TextField xField;
	private TextField yField;
	private Label sumLabel;

	private AdditionController controller ;
	private AdditionModel model ;

	public AdditionView(AdditionController controller, AdditionModel model) {

		this.controller = controller ;
		this.model = model ;

		createAndConfigurePane();

		createAndLayoutControls();

		updateControllerFromListeners();

		observeModelAndUpdateControls();

	}

	public Parent asParent() {
		return view ;
	}

	private void observeModelAndUpdateControls() {
		model.xProperty().addListener((obs, oldX, newX) -> 
		updateIfNeeded(newX, xField));

		model.yProperty().addListener((obs, oldY, newY) -> 
		updateIfNeeded(newY, yField));

		sumLabel.textProperty().bind(model.sumProperty().asString());
	}

	private void updateIfNeeded(Number value, TextField field) {
		String s = value.toString() ;
		if (! field.getText().equals(s)) {
			field.setText(s);
		}
	}

	private void updateControllerFromListeners() {
		xField.textProperty().addListener((obs, oldText, newText) -> controller.updateX(newText));
		yField.textProperty().addListener((obs, oldText, newText) -> controller.updateY(newText));
	}

	private void createAndLayoutControls() {
		xField = new TextField();
		configTextFieldForInts(xField);

		yField = new TextField();
		configTextFieldForInts(yField);

		sumLabel = new Label();

		view.addRow(0, new Label("X:"), xField);
		view.addRow(1, new Label("Y:"), yField);
		view.addRow(2, new Label("Sum:"), sumLabel);
	}

	private void createAndConfigurePane() {
		view = new GridPane();

		ColumnConstraints leftCol = new ColumnConstraints();
		leftCol.setHalignment(HPos.RIGHT);
		leftCol.setHgrow(Priority.NEVER);

		ColumnConstraints rightCol = new ColumnConstraints();
		rightCol.setHgrow(Priority.SOMETIMES);

		view.getColumnConstraints().addAll(leftCol, rightCol);

		view.setAlignment(Pos.CENTER);
		view.setHgap(5);
		view.setVgap(10);
	}

	private void configTextFieldForInts(TextField field) {
		field.setTextFormatter(new TextFormatter<Integer>((Change c) -> {
			if (c.getControlNewText().matches("-?\\d*")) {
				return c ;
			}
			return null ;
		}));
	}
}
