// 
// Decompiled by Procyon v0.6-prerelease
// 

package com.onepop.api.tracker.management;

import java.util.Iterator;
import com.onepop.api.tracker.Tracker;
import java.util.ArrayList;

public class TrackerManager
{
    public static final float DEFAULT_DELAY = 0.0f;
    public static TrackerManager INSTANCE;
    private ArrayList<Tracker> trackerList;
    
    public TrackerManager() {
        TrackerManager.INSTANCE = this;
        this.trackerList = new ArrayList<Tracker>();
    }
    
    public void setTrackerList(final ArrayList<Tracker> trackerList) {
        this.trackerList = trackerList;
    }
    
    public ArrayList<Tracker> getTrackerList() {
        return this.trackerList;
    }
    
    public void registry(final Tracker tracker) {
        this.trackerList.add(tracker);
    }
    
    public void unregister(final Tracker tracker) {
        if (get(tracker.getClass()) == null) {
            return;
        }
        this.trackerList.remove(tracker);
    }
    
    public static Tracker get(final Class<?> clazz) {
        for (final Tracker trackers : TrackerManager.INSTANCE.getTrackerList()) {
            if (trackers.getClass() == clazz) {
                return trackers;
            }
        }
        return null;
    }
    
    public static Tracker get(final String name) {
        for (final Tracker trackers : TrackerManager.INSTANCE.getTrackerList()) {
            if (trackers.getName().equalsIgnoreCase(name)) {
                return trackers;
            }
        }
        return null;
    }
    
    public void onUpdateAll() {
        for (final Tracker trackers : TrackerManager.INSTANCE.getTrackerList()) {
            if (trackers.isRegistry()) {
                trackers.onUpdate();
            }
        }
    }
}
