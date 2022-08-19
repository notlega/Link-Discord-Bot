package records;

import java.awt.*;
import java.time.temporal.TemporalAccessor;

public record EmbedClass(Color color, EmbedField[] field, String footer, String image, TemporalAccessor timestamp,
						 String title) {

}
