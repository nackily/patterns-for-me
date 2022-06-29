package com.aoligei.behavioral.memento;

/**
 * Client
 *
 * @author coder
 * @date 2022-06-28 18:48:23
 * @since 1.0.0
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("|==> Game Start -------------------------------------------------------|");
        // 创建一个游戏对局
        Game game = new Game("someone like you");
        // 游戏进行 2s
        Thread.sleep(2000);
        // 第一次存档
        SavepointHistory history = new SavepointHistory(game);
        history.store();
        // 换一首bgm
        game.switchBgm("my love");
        // 游戏进行 3s
        Thread.sleep(3000);
        // 第二次存档
        history.store();
        // 游戏进行 3s
        Thread.sleep(3000);

        // 第一次读档
        history.restore();
        // 游戏进行 2s
        Thread.sleep(2000);
        // 第二次读档
        history.restore();
        System.exit(0);
    }
}
