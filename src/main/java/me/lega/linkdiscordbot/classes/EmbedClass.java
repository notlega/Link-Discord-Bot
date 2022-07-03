package me.lega.linkdiscordbot.classes;

import java.awt.Color;
import java.time.temporal.TemporalAccessor;

public class EmbedClass {
    
    private Color Color;
    private EmbedField[] Field;
    private String Footer;
    private String Image;
    private TemporalAccessor Timestamp;
    private String Title;
    
    public EmbedClass(Color Color, EmbedField[] Field, String Footer, String Image, TemporalAccessor Timestamp, String Title) {
        this.Color = Color;
        this.Field = Field;
        this.Footer = Footer;
        this.Image = Image;
        this.Timestamp = Timestamp;
        this.Title = Title;
    }

    public Color getColor() {
        return Color;
    }

    public void setColor(Color Color) {
        this.Color = Color;
    }

    public EmbedField[] getField() {
        return Field;
    }

    public void setField(EmbedField[] Field) {
        this.Field = Field;
    }

    public String getFooter() {
        return Footer;
    }

    public void setFooter(String Footer) {
        this.Footer = Footer;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public TemporalAccessor getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(TemporalAccessor Timestamp) {
        this.Timestamp = Timestamp;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }
    
    
}
