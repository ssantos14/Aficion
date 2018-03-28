package com.example.android.aficion.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;

import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by Sylvana on 3/27/2018.
 */

public interface NewsColumns {
    @DataType(TEXT) @NotNull String TITLE = "title";
    @DataType(TEXT) @NotNull String IMAGE_URL = "image_url";
    @DataType(TEXT) @NotNull String ARTICLE_URL = "article_url";
}
