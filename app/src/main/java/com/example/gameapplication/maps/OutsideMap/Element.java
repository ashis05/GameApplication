package com.example.gameapplication.maps.OutsideMap;

import android.graphics.PointF;

public class Element {
    private static PointF pos;
    private static Elements elements;

    public Element(PointF pos, Elements elements) {
        this.pos = pos;
        this.elements = elements;
    }

    public static Elements getElementType() {
        return elements;
    }

    public static PointF getPos() {
        return pos;
    }
}
