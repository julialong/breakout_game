package game_jbl34;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Display {

	private Text displayText;

	public Display(String text, int xPos, int yPos) {
		displayText = new Text(text);
		displayText.setFont(new Font(12));
		displayText.setX(xPos);
		displayText.setY(yPos);
	}

	public Text getDisplayText() {
		return this.displayText;
	}

	public void changeDisplay(String text) {
		this.displayText.setText(text);
	}
}
