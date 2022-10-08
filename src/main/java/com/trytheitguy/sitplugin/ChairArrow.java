package com.trytheitguy.sitplugin;

import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;

public class ChairArrow {
    private Arrow arrow;
    private Block chair;

    public Arrow getArrow() {
        return arrow;
    }

    public void setArrow(Arrow arrow) {
        this.arrow = arrow;
    }

    public Block getChair() {
        return chair;
    }

    public void setChair(Block chair) {
        this.chair = chair;
    }
}
