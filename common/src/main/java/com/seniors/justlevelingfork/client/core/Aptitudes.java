
package com.seniors.justlevelingfork.client.core;

import com.seniors.justlevelingfork.registry.aptitude.Aptitude;

public final class Aptitudes {
    private final String key;

    public String getKey() {
        return key;
    }

    private final String getResource;

    public String getResource() {
        return getResource;
    }

    private final boolean isDroppable;

    public boolean isDroppable() {
        return isDroppable;
    }

    private final Aptitude getAptitude;

    public Aptitude getAptitude() {
        return getAptitude;
    }

    private final int aptitudeLvl;

    public int getAptitudeLvl() {
        return aptitudeLvl;
    }

    public Aptitudes(String key, String getResource, boolean isDroppable, Aptitude getAptitude, int aptitudeLvl) {
        this.key = key;
        this.getResource = getResource;
        this.isDroppable = isDroppable;
        this.getAptitude = getAptitude;
        this.aptitudeLvl = aptitudeLvl;
    }
}

