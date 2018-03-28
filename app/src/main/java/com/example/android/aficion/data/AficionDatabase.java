package com.example.android.aficion.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Sylvana on 3/27/2018.
 */

@Database(version = AficionDatabase.VERSION)
public final class AficionDatabase {

    public static final int VERSION = 2;

    @Table(NewsColumns.class) public static final String NEWS = "news";
    @Table (ScoresColumns.class) public static final String SCORES = "scores";
    @Table(HighlightsColumns.class) public static final String HIGHLIGHTS = "highlights";

}
