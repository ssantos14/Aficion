package com.example.android.aficion.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;

import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by Sylvana on 3/28/2018.
 */

public interface HighlightsColumns {

    @DataType(TEXT) @NotNull String VIDEO_TITLE = "title";
    @DataType(TEXT) @NotNull String VIDEO_ID = "id";
    @DataType(TEXT) @NotNull String THUMBNAIL_URL = "url";

}
