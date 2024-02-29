import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input path:");
        String inputPath = scanner.nextLine();

        System.out.println("Output path:");
        String outputPath = scanner.nextLine();

        System.out.println("Tile size (e.g., 16, 32, 64):");
        int tileSize = Integer.parseInt(scanner.nextLine());

        System.out.println("Upscaling factor  (e.g., 1, 2, 4):");
        int upscalingFactor = Integer.parseInt(scanner.nextLine());

        try {
            BufferedImage tileset = ImageIO.read(new File(inputPath));

            int tileWidth = tileSize;
            int tileHeight = tileSize;

            for (int y = 0; y < tileset.getHeight(); y += tileHeight) {
                for (int x = 0; x < tileset.getWidth(); x += tileWidth) {
                    BufferedImage tile = tileset.getSubimage(x, y, tileWidth, tileHeight);

                    // Upscaling to the desired size
                    int scaledSize = tileSize * upscalingFactor;
                    BufferedImage scaledTile = new BufferedImage(scaledSize, scaledSize, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = scaledTile.createGraphics();
                    g2d.drawImage(tile, 0, 0, scaledSize, scaledSize, null);
                    g2d.dispose();

                    String fileName = String.format("tile_%d_%d.png", x / tileWidth, y / tileHeight);
                    ImageIO.write(scaledTile, "png", new File(outputPath, fileName));
                }
            }

            System.out.println("Successfully split and exported under: " + outputPath);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
