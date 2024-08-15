package com.seniors.justlevelingfork.config.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LockItem {

    public String Item = "minecraft:diamond";

    public List<Aptitude> Aptitudes = List.of(new Aptitude());

    public LockItem() {
    }

    public LockItem(String itemName) {
        Item = itemName;
    }

    public LockItem(String itemName, Aptitude... aptitudes) {
        Item = itemName;
        Aptitudes = Arrays.stream(aptitudes).toList();
    }

    public static LockItem getLockItemFromString(String value, LockItem defaultValue) {
        try {
            return formatString(value);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    private static LockItem formatString(String value) {
        String[] initialSplit = value.split("#");
        LockItem lockItem = new LockItem(initialSplit[0]);

        List<Aptitude> aptitudeList = new ArrayList<>();
        String[] aptitudeSplit = initialSplit[1].split(";");

        for (String aptitudeString : aptitudeSplit) {
            String[] aptitude = aptitudeString.split(":");
            int level = Integer.parseInt(aptitude[1]);
            if(level < 2 || level > 1000) throw new IndexOutOfBoundsException();
            aptitudeList.add(new Aptitude(aptitude[0], level));
        }

        lockItem.Aptitudes = aptitudeList;
        return lockItem;
    }

    @Override
    public String toString() {
        List<String> strings = Aptitudes.stream().map(Aptitude::toString).toList();

        String aptitudeStringList = String.join(";", strings);

        return String.format("%s#%s", Item, aptitudeStringList);
    }

    public static class Aptitude {

        public EAptitude Aptitude;

        public int Level;

        public Aptitude(String aptitudeName, int level) {
            Aptitude = EAptitude.valueOf(CapitalizeString(aptitudeName));
            Level = level;
        }

        private String CapitalizeString(String str) {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }

        public Aptitude() {
            Aptitude = EAptitude.Strength;
            Level = 2;
        }

        @Override
        public String toString() {
            return String.format("%s:%d", Aptitude.toString(), Level);
        }
    }
}
