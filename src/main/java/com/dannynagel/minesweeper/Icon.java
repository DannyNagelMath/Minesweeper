package com.dannynagel.minesweeper;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

// Static class containing the mine, flag, smiley face
public class Icon {

    public static Pane drawFlag(double TILE_WIDTH, double TILE_HEIGHT) {
        Pane flag = new Pane();

        // Two rectangles for the base, 1 rectangle for pole
        Rectangle baseWide = new Rectangle(0.25 * TILE_WIDTH,
                .70 * TILE_HEIGHT,
                TILE_WIDTH * 0.5,
                TILE_HEIGHT * 0.1);
        Rectangle baseSkinny = new Rectangle(baseWide.getX() + baseWide.getWidth() * 0.25,
                baseWide.getY() - baseWide.getHeight() * 0.7,
                baseWide.getWidth() * 0.5,
                baseWide.getHeight() * 0.7);
        Rectangle pole = new Rectangle(TILE_WIDTH * 0.5 - TILE_WIDTH * 0.07 * 0.5,
                TILE_HEIGHT * 0.25,
                TILE_WIDTH * 0.07,
                TILE_HEIGHT * 0.5);

        // Triangle for red flag
        // Top point, bottom point, middle point
        Polygon triangle = new Polygon(pole.getX() + pole.getWidth(), TILE_HEIGHT * 0.2,
                pole.getX() + pole.getWidth(), TILE_HEIGHT * 0.5,
                baseWide.getX(), (TILE_HEIGHT * 0.2 + TILE_HEIGHT * 0.5) * 0.5);
        triangle.setFill(Color.RED);

        flag.getChildren().addAll(baseWide, baseSkinny, pole, triangle);
        return flag;
    }

    public static Pane drawMine(double TILE_WIDTH, double TILE_HEIGHT) {
        Pane mine = new Pane();

        Ellipse ellipse = new Ellipse(TILE_WIDTH * 0.5,
                TILE_HEIGHT * 0.5,
                TILE_WIDTH * 0.25,
                TILE_HEIGHT * 0.25);
        ellipse.setFill(Color.BLACK);

        Rectangle rectangle = new Rectangle(TILE_WIDTH * 0.35,
                TILE_HEIGHT * 0.35,
                TILE_WIDTH * 0.08,
                TILE_HEIGHT * 0.08);
        rectangle.setFill(Color.WHITE);

        // Draw 4 lines in + and x shapes
        Line backSlash = new Line(TILE_WIDTH * 0.25,
                TILE_HEIGHT * 0.25,
                TILE_WIDTH * 0.75,
                TILE_HEIGHT * 0.75);

        Line forwardSlash = new Line(TILE_WIDTH * 0.75,
                TILE_HEIGHT * 0.25,
                TILE_WIDTH * 0.25,
                TILE_HEIGHT * 0.75);

        Line vertical = new Line(TILE_WIDTH * 0.5,
                TILE_HEIGHT * 0.15,
                TILE_WIDTH * 0.5,
                TILE_HEIGHT * 0.85);

        Line horizontal = new Line(TILE_WIDTH * 0.15,
                TILE_HEIGHT * 0.5,
                TILE_WIDTH * 0.85,
                TILE_HEIGHT * 0.5);

        mine.getChildren().addAll(backSlash, forwardSlash, vertical, horizontal, ellipse, rectangle);
        return mine;
    }

    public static Pane drawFace() {
        // Smiley face: 3 Circles and 1 Arc together in a Pane
        Pane faceGraphic = new Pane();

        Circle head = new Circle(15);
        head.relocate(0, 0);
        head.setFill(Color.GOLD);
        head.setStroke(Color.BLACK);

        Circle leftEye = new Circle(2);
        leftEye.relocate(head.getLayoutX(), head.getLayoutY());
        leftEye.setTranslateX(-7);
        leftEye.setTranslateY(-6);

        Circle rightEye = new Circle(2);
        rightEye.relocate(head.getLayoutX(), head.getLayoutY());
        rightEye.setTranslateX(3);
        rightEye.setTranslateY(-6);

        Arc smile = new Arc();
        smile.relocate(head.getLayoutX(), head.getLayoutY());
        smile.setTranslateY(3.5);
        smile.setRadiusX(7.0f);
        smile.setRadiusY(5.0f);
        smile.setStartAngle(190f);
        smile.setLength(160f);
        smile.setType(ArcType.OPEN);
        smile.setFill(null);
        smile.setStroke(Color.BLACK);

        faceGraphic.getChildren().addAll(head, leftEye, rightEye, smile);

        return faceGraphic;
    }

    public static Pane drawSunglasses() {
        Pane faceGraphic = new Pane();

        Circle head = new Circle(15);
        head.relocate(0, 0);
        head.setFill(Color.GOLD);
        head.setStroke(Color.BLACK);

        Circle leftSunglass = new Circle(4);
        leftSunglass.relocate(head.getLayoutX(), head.getLayoutY());
        leftSunglass.setTranslateX(-9);
        leftSunglass.setTranslateY(-6);

        Circle rightSunglass = new Circle(4);
        rightSunglass.relocate(head.getLayoutX(), head.getLayoutY());
        rightSunglass.setTranslateX(1);
        rightSunglass.setTranslateY(-6);

        Line topGlasses = new Line(10, 10, 20, 10);
        Line leftGlasses = new Line(0, 15, 8, 10);
        Line rightGlasses = new Line(22, 10, 30, 15);

        Arc smile = new Arc();
        smile.relocate(head.getLayoutX(), head.getLayoutY());
        smile.setTranslateY(3.5);
        smile.setRadiusX(7.0f);
        smile.setRadiusY(5.0f);
        smile.setStartAngle(190f);
        smile.setLength(160f);
        smile.setType(ArcType.OPEN);
        smile.setFill(null);
        smile.setStroke(Color.BLACK);

        faceGraphic.getChildren().addAll(head, leftSunglass, rightSunglass, smile, topGlasses, leftGlasses, rightGlasses);

        return faceGraphic;

    }
}
