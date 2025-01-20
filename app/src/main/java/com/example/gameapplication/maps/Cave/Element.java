package com.example.gameapplication.maps.Cave;

import android.graphics.PointF;

public class Element {
    private static PointF pos;
    private static com.example.gameapplication.maps.Cave.Elements elements;

    public Element(PointF pos, com.example.gameapplication.maps.Cave.Elements elements) {
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
