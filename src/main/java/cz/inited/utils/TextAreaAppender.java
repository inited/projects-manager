package cz.inited.utils;

import cz.inited.Application;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by ondre on 08.07.2016.
 */
public class TextAreaAppender extends WriterAppender {

	private static TextArea textArea;

	public static void setTextArea(TextArea textArea) {
		TextAreaAppender.textArea = textArea;
	}

	@Override
	public void append(LoggingEvent event) {
		final String message = layout.format(event);
		if(Application.graphics) {
			try {
				Platform.runLater(() -> {
					try {
						if (textArea != null) {
							if (textArea.getText().length() == 0) {
								textArea.setText(message);
							} else {
								textArea.selectEnd();
								textArea.insertText(textArea.getText().length(),
										message);
							}
						}
					} catch (final Throwable t) {
						System.out.println("Unable to append log to text area: "
								+ t.getMessage());
					}
				});
			} catch (Exception ex) {

			}
		}
	}
}
