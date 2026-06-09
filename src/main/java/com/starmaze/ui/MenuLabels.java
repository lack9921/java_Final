package com.starmaze.ui;

import com.starmaze.model.GameMode;
import com.starmaze.model.GameState;

public final class MenuLabels {
    private MenuLabels() {
    }

    public static String forAction(GameState state, String action) {
        return switch (action) {
            case MenuActions.START -> state.mode() == GameMode.GAME_OVER ? "重新开始 Enter" : "开始游戏 Enter";
            case MenuActions.HELP -> "玩法说明 H";
            case MenuActions.SETTINGS -> "设置";
            case MenuActions.RESUME -> "继续游戏";
            case MenuActions.RESTART_LEVEL -> "重开本层";
            case MenuActions.NEXT -> "进入下一层 Enter";
            case MenuActions.SOUND -> state.saveData().isSoundEnabled() ? "音效：开启" : "音效：关闭";
            case MenuActions.TITLE -> state.mode() == GameMode.HELP ? "返回标题 Esc" : "返回标题";
            default -> action;
        };
    }
}
