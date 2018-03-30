package com.example.android.aficion.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;

import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by Sylvana on 3/28/2018.
 */

public interface TeamsColumns {
    @DataType(TEXT) @NotNull String NAME = "name";
    @DataType(TEXT) @NotNull String ID = "id";
    @DataType(TEXT) @NotNull String LOGO_URL = "url";
    @DataType(TEXT) @NotNull String AREA_ID = "area_id";
    @DataType(TEXT) @NotNull String TYPE = "type";
}
