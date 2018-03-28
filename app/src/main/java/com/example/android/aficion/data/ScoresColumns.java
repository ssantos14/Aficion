package com.example.android.aficion.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;

import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by Sylvana on 3/28/2018.
 */

public interface ScoresColumns {

    @DataType(TEXT) @NotNull String AWAY_TEAM = "away_team";
    @DataType(TEXT) @NotNull String HOME_TEAM = "home_team";
    @DataType(TEXT) @NotNull String AWAY_TEAM_GOALS = "away_team_goals";
    @DataType(TEXT) @NotNull String HOME_TEAM_GOALS = "home_team_goals";

}
