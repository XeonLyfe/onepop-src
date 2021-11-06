// 
// Decompiled by Procyon v0.6-prerelease
// 

package me.rina.turok.util;

import me.rina.turok.hardware.mouse.TurokMouse;

public class TurokRect
{
    public float x;
    public float y;
    public float width;
    public float height;
    public String tag;
    protected Dock docking;
    private DockDimension dimension;
    
    public TurokRect(final float x, final float y, final float width, final float height) {
        this.docking = Dock.TOP_LEFT;
        this.dimension = DockDimension.D2;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tag = "";
    }
    
    public TurokRect(final String tag, final float x, final float y, final float width, final float height) {
        this.docking = Dock.TOP_LEFT;
        this.dimension = DockDimension.D2;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tag = "";
    }
    
    public TurokRect(final float x, final float y) {
        this.docking = Dock.TOP_LEFT;
        this.dimension = DockDimension.D2;
        this.x = x;
        this.y = y;
        this.width = 0.0f;
        this.height = 0.0f;
        this.tag = "";
    }
    
    public TurokRect(final String tag, final float x, final float y) {
        this.docking = Dock.TOP_LEFT;
        this.dimension = DockDimension.D2;
        this.x = x;
        this.y = y;
        this.width = 0.0f;
        this.height = 0.0f;
        this.tag = tag;
    }
    
    public void setDimension(final DockDimension dimension) {
        this.dimension = dimension;
    }
    
    public DockDimension getDimension() {
        return this.dimension;
    }
    
    public TurokRect copy(final TurokRect rect) {
        this.x = rect.getX();
        this.y = rect.getY();
        this.width = rect.getWidth();
        this.height = rect.getHeight();
        return this;
    }
    
    public void set(final float x, final float y, final float w, final float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }
    
    public void setTag(final String tag) {
        this.tag = tag;
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
    
    public void setWidth(final float width) {
        this.width = width;
    }
    
    public void setHeight(final float height) {
        this.height = height;
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public float getX() {
        return this.x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public float getDistance(final TurokRect rect) {
        final float calculatedX = this.x - rect.getX();
        final float calculatedY = this.y - rect.getY();
        final float calculatedW = this.x + this.width - (rect.getX() + rect.getWidth());
        final float calculatedH = this.y + this.height - (rect.getY() + rect.getHeight());
        return TurokMath.sqrt(calculatedX * calculatedW + calculatedY * calculatedH);
    }
    
    public float getDistance(final float x, final float y) {
        final float calculatedX = this.x - x;
        final float calculatedY = this.y - y;
        final float calculatedW = this.x + this.width - x;
        final float calculatedH = this.y + this.height - y;
        return TurokMath.sqrt(calculatedX * calculatedW + calculatedY * calculatedH);
    }
    
    public boolean collideWithMouse(final TurokMouse mouse) {
        return mouse.getX() >= this.x && mouse.getX() <= this.x + this.width && mouse.getY() >= this.y && mouse.getY() <= this.y + this.height;
    }
    
    public boolean collideWithRect(final TurokRect rect) {
        return this.x <= rect.getX() + rect.getWidth() && this.x + this.width >= rect.getX() && this.y <= rect.getY() + rect.getHeight() && this.y + this.height >= rect.getY();
    }
    
    public Dock getDockHit(final TurokDisplay display, final int diff) {
        if (this.x <= diff) {
            if (this.docking == Dock.TOP_CENTER || this.docking == Dock.TOP_RIGHT) {
                this.docking = Dock.TOP_LEFT;
            }
            else if (this.docking == Dock.CENTER || this.docking == Dock.CENTER_RIGHT) {
                this.docking = Dock.CENTER_LEFT;
            }
            else if (this.docking == Dock.BOTTOM_CENTER || this.docking == Dock.BOTTOM_RIGHT) {
                this.docking = Dock.BOTTOM_LEFT;
            }
        }
        else if (this.y <= diff) {
            if (this.docking == Dock.CENTER_LEFT || this.docking == Dock.BOTTOM_LEFT) {
                this.docking = Dock.TOP_LEFT;
            }
            else if (this.docking == Dock.CENTER || this.docking == Dock.BOTTOM_CENTER) {
                this.docking = Dock.TOP_CENTER;
            }
            else if (this.docking == Dock.CENTER_RIGHT || this.docking == Dock.BOTTOM_RIGHT) {
                this.docking = Dock.TOP_RIGHT;
            }
        }
        if (this.dimension == DockDimension.D3) {
            if (this.x >= display.getScaledWidth() / 2.0f - (this.width + diff) / 2.0f && this.x <= display.getScaledWidth() / 2.0f + (this.width + diff) / 2.0f) {
                if (this.docking == Dock.TOP_LEFT || this.docking == Dock.TOP_RIGHT) {
                    this.docking = Dock.TOP_CENTER;
                }
                else if (this.docking == Dock.CENTER_LEFT || this.docking == Dock.CENTER_RIGHT) {
                    this.docking = Dock.CENTER;
                }
                else if (this.docking == Dock.BOTTOM_LEFT || this.docking == Dock.BOTTOM_RIGHT) {
                    this.docking = Dock.BOTTOM_CENTER;
                }
            }
            else if (this.y >= display.getScaledHeight() / 2.0f - (this.height + diff) / 2.0f && this.y <= display.getScaledHeight() / 2.0f + (this.height + diff) / 2.0f) {
                if (this.docking == Dock.TOP_LEFT || this.docking == Dock.BOTTOM_LEFT) {
                    this.docking = Dock.CENTER_LEFT;
                }
                else if (this.docking == Dock.TOP_CENTER || this.docking == Dock.BOTTOM_CENTER) {
                    this.docking = Dock.CENTER;
                }
                else if (this.docking == Dock.TOP_RIGHT || this.docking == Dock.BOTTOM_RIGHT) {
                    this.docking = Dock.CENTER_RIGHT;
                }
            }
        }
        if (this.x + this.width >= display.getScaledWidth() - diff) {
            if (this.docking == Dock.TOP_LEFT || this.docking == Dock.TOP_CENTER) {
                this.docking = Dock.TOP_RIGHT;
            }
            else if (this.docking == Dock.CENTER_LEFT || this.docking == Dock.CENTER) {
                this.docking = Dock.CENTER_RIGHT;
            }
            else if (this.docking == Dock.BOTTOM_LEFT || this.docking == Dock.BOTTOM_CENTER) {
                this.docking = Dock.BOTTOM_RIGHT;
            }
        }
        if (this.y + this.height >= display.getScaledHeight() - diff) {
            if (this.docking == Dock.TOP_LEFT || this.docking == Dock.CENTER_LEFT) {
                this.docking = Dock.BOTTOM_LEFT;
            }
            else if (this.docking == Dock.TOP_CENTER || this.docking == Dock.CENTER) {
                this.docking = Dock.BOTTOM_CENTER;
            }
            else if (this.docking == Dock.TOP_RIGHT || this.docking == Dock.CENTER_RIGHT) {
                this.docking = Dock.BOTTOM_RIGHT;
            }
        }
        return this.docking;
    }
    
    public Dock getDocking() {
        return this.docking;
    }
    
    public static boolean collideRectWith(final TurokRect rect, final TurokMouse mouse) {
        return rect.collideWithMouse(mouse);
    }
    
    public static boolean collideRectWith(final TurokRect rect, final TurokRect rect1) {
        return rect.collideWithRect(rect1);
    }
    
    public enum Dock
    {
        TOP_LEFT, 
        TOP_CENTER, 
        TOP_RIGHT, 
        CENTER_LEFT, 
        CENTER, 
        CENTER_RIGHT, 
        BOTTOM_LEFT, 
        BOTTOM_CENTER, 
        BOTTOM_RIGHT;
    }
    
    public enum DockDimension
    {
        D3, 
        D2;
    }
}
