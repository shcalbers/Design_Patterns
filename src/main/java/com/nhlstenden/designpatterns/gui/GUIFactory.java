package com.nhlstenden.designpatterns.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public abstract class GUIFactory {

    private static double offset;

    public static Button createButton(String name, String tooltip, EventHandler<MouseEvent> clickEventHandler){
        Button button = new Button();
        offset += 24;
        button.setTranslateY(offset);
        button.addEventHandler(MouseEvent.MOUSE_RELEASED, clickEventHandler);

        button.setTooltip(new Tooltip(tooltip));

        BackgroundImage backgroundImage = new BackgroundImage(new Image(GUIFactory.class.getResource("/img/icons/" + name + ".png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        button.setBackground(background);
        button.setMinSize(24,24);

        return button;
    }

    public static ColorPicker createColorPicker(String tooltip, EventHandler<ActionEvent> actionEventHandler) {
        ColorPicker colorPicker = new ColorPicker();
        offset += 24;
        colorPicker.setTranslateY(offset);
        colorPicker.setOnAction(actionEventHandler);

        colorPicker.setTooltip(new Tooltip(tooltip));

        // Ugly way to force the ColorPicker to display properly, might fix later.
        colorPicker.getStyleClass().add("button");
        colorPicker.getStylesheets().add(GUIFactory.class.getResource("/css/colorpicker.css").toExternalForm());

        BackgroundImage backgroundImage = new BackgroundImage(new Image(GUIFactory.class.getResource("/img/icons/pipette.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        colorPicker.setBackground(background);
        colorPicker.setMinSize(24,24);
        colorPicker.setMaxSize(24,24);

        return colorPicker;
    }

}
