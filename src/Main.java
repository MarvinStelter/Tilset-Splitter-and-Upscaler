import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Pfad eingeben:");
        String inputPfad = scanner.nextLine();

        System.out.println("Output Pfad eingeben:");
        String outputPfad = scanner.nextLine();

        System.out.println("Gewünschte Tile-Größe eingeben (z.B. 16, 32, 64):");
        int tileSize = Integer.parseInt(scanner.nextLine());

        System.out.println("Gewünschten Upscaling-Faktor eingeben (z.B. 1, 2, 4):");
        int upscalingFaktor = Integer.parseInt(scanner.nextLine());

        try {
            BufferedImage tileset = ImageIO.read(new File(inputPfad));

            int tileBreite = tileSize;
            int tileHoehe = tileSize;

            for (int y = 0; y < tileset.getHeight(); y += tileHoehe) {
                for (int x = 0; x < tileset.getWidth(); x += tileBreite) {
                    BufferedImage tile = tileset.getSubimage(x, y, tileBreite, tileHoehe);

                    // Hochskalieren auf die gewünschte Größe
                    int scaledSize = tileSize * upscalingFaktor;
                    BufferedImage scaledTile = new BufferedImage(scaledSize, scaledSize, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = scaledTile.createGraphics();
                    g2d.drawImage(tile, 0, 0, scaledSize, scaledSize, null);
                    g2d.dispose();

                    String dateiname = String.format("tile_%d_%d.png", x / tileBreite, y / tileHoehe);
                    ImageIO.write(scaledTile, "png", new File(outputPfad, dateiname));
                }
            }

            System.out.println("Tileset erfolgreich gesplittet und hochskaliert.");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

}