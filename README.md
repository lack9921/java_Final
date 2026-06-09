# 星轨迷阵 Star Maze

一个使用 Java Swing 实现的桌面小游戏。玩家在程序生成的霓虹迷宫中收集星核，躲避巡航体，利用相位穿墙和轨迹回溯打开传送门。

## 编译运行

```powershell
.\build.ps1
java -jar .\dist\StarMaze.jar
```

## 自检

```powershell
java "-Djava.awt.headless=true" -cp .\dist\StarMaze.jar com.starmaze.StarMazeSmokeTest
```

## 操作

- `WASD` 或方向键：移动
- `Space`：相位穿墙
- `R`：轨迹回溯
- `P` 或 `Esc`：暂停
- `H`：帮助
- `M`：音效开关
