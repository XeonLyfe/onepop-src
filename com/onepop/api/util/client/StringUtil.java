// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.util.client;

import me.rina.turok.render.font.hal.CFontRenderer;

public class StringUtil
{
    public static String trimStringToWidth(final CFontRenderer font, final String text, final int width, final boolean reverse) {
        final StringBuilder stringbuilder = new StringBuilder();
        int i = 0;
        final int j = reverse ? (text.length() - 1) : 0;
        final int k = reverse ? -1 : 1;
        boolean flag = false;
        boolean flag2 = false;
        for (int l = j; l >= 0 && l < text.length() && i < width; l += k) {
            final char c0 = text.charAt(l);
            final int i2 = font.getStringWidth(Character.toString(c0));
            if (flag) {
                flag = false;
                if (c0 != 'l' && c0 != 'L') {
                    if (c0 == 'r' || c0 == 'R') {
                        flag2 = false;
                    }
                }
                else {
                    flag2 = true;
                }
            }
            else if (i2 < 0) {
                flag = true;
            }
            else {
                i += i2;
                if (flag2) {
                    ++i;
                }
            }
            if (i > width) {
                break;
            }
            if (reverse) {
                stringbuilder.insert(0, c0);
            }
            else {
                stringbuilder.append(c0);
            }
        }
        return stringbuilder.toString();
    }
}
