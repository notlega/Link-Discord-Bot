package records;

import classes.EmbedField;

import java.awt.*;
import java.time.temporal.TemporalAccessor;

public record EmbedClass(Color Color, EmbedField[] Field, String Footer, String Image, TemporalAccessor Timestamp,
                         String Title) {

}
