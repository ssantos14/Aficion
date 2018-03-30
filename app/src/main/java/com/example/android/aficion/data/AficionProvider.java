package com.example.android.aficion.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Sylvana on 3/27/2018.
 */

@ContentProvider(authority = AficionProvider.AUTHORITY,database = AficionDatabase.class)
public final class AficionProvider {

    public static final String AUTHORITY = "com.example.android.aficion.data.AficionProvider";

    @TableEndpoint(table = AficionDatabase.NEWS) public static class News{

        @ContentUri(
                path = "news",
                type = "vnd.android.cursor.dir/article",
                defaultSort = NewsColumns.TITLE + " ASC"
        )
        public static final Uri NEWS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/news");
    }

    @TableEndpoint(table = AficionDatabase.SCORES) public static class Scores{

        @ContentUri(
                path = "scores",
                type = "vnd.android.cursor.dir/game",
                defaultSort = ScoresColumns.AWAY_TEAM + " ASC"
        )
        public static final Uri SCORES_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/scores");
    }

    @TableEndpoint(table = AficionDatabase.HIGHLIGHTS) public static class Highlights{

        @ContentUri(
                path = "highlights",
                type = "vnd.android.cursor.dir/video",
                defaultSort = HighlightsColumns.VIDEO_TITLE + " ASC"
        )
        public static final Uri HIGHLIGHTS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/highlights");
    }

    @TableEndpoint(table = AficionDatabase.TEAMS) public static class Teams{

        @ContentUri(
                path = "teams",
                type = "vnd.android.cursor.dir/team",
                defaultSort = TeamsColumns.NAME + " ASC"
        )
        public static final Uri TEAMS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/teams");
    }

}
