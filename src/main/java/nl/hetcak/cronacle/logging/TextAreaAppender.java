package nl.hetcak.cronacle.logging;

import javafx.scene.control.TextArea;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

import java.io.Serializable;

public class TextAreaAppender extends AbstractAppender implements Appender {
    private TextArea textArea;

    protected TextAreaAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout);
    }

    protected TextAreaAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void append(LogEvent logEvent) {
        textArea.appendText(new String(this.getLayout().toByteArray(logEvent)));
    }

    public static Appender createAppender(String name, Filter filter, Layout<? extends Serializable> layout, TextArea textArea) {
        TextAreaAppender textAreaAppender = new TextAreaAppender(name, filter, layout);
        textAreaAppender.textArea = textArea;
        return textAreaAppender;
    }
}
