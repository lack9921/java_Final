import com.starmaze.audio.SoundEngine;
import com.starmaze.model.GameState;
import com.starmaze.model.SaveData;
import com.starmaze.ui.GamePanel;
import com.starmaze.ui.VisualConfig;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

public final class RenderPreview {
    private RenderPreview() {
    }

    public static void main(String[] args) throws Exception {
        SaveData saveData = SaveData.loadDefault();
        SoundEngine soundEngine = new SoundEngine(false);
        GameState state = new GameState(saveData, soundEngine);
        state.startNewGame();
        for (int i = 0; i < 12; i++) {
            state.update();
        }
        GamePanel panel = new GamePanel(state);
        panel.setSize(VisualConfig.WINDOW_DEFAULT_WIDTH, VisualConfig.WINDOW_DEFAULT_HEIGHT);
        BufferedImage image = new BufferedImage(VisualConfig.WINDOW_DEFAULT_WIDTH,
                VisualConfig.WINDOW_DEFAULT_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        panel.paint(graphics);
        graphics.dispose();
        panel.stopLoop();
        soundEngine.shutdown();
        File output = new File("docs/game-preview.png");
        ImageIO.write(image, "png", output);
        System.out.println(output.getAbsolutePath());
        System.exit(0);
    }
}
