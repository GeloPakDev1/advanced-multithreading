package com.gpd.esm.runner;

import com.gpd.esm.fjp.ForkBlur;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

import static com.gpd.esm.fjp.ForkBlur.sThreshold;

public class ForkBlurRunner {
    private static final Logger logger = Logger.getLogger(ForkBlurRunner.class.getName());
    public static final String SOURCE_IMAGE_NAME = "src/main/resources/computer-science.jpg";
    public static final String DESTINATION_IMAGE_NAME = "src/main/resources/blurred-computer-science.jpg";

    public static void main(String[] args) {
        try {
            //Reading the file
            File srcFile = new File(SOURCE_IMAGE_NAME);
            BufferedImage image = ImageIO.read(srcFile);
            System.out.println("Source image: " + SOURCE_IMAGE_NAME);

            //Blurring the image
            BufferedImage blurredImage = blur(image);
            System.out.println("Blurred image: " + blurredImage);

            //Write the output image in the directory
            File dstFile = new File(DESTINATION_IMAGE_NAME);
            ImageIO.write(blurredImage, "jpg", dstFile);
            System.out.println("Output image: " + DESTINATION_IMAGE_NAME);
        } catch (IOException e) {
            logger.info("Error occurred during the processing of the image: " + e.getMessage());
        }
    }

    public static BufferedImage blur(BufferedImage srcImage) {
        int w = srcImage.getWidth();
        int h = srcImage.getHeight();

        int[] src = srcImage.getRGB(0, 0, w, h, null, 0, w);
        int[] dst = new int[src.length];

        System.out.println("Array size is " + src.length);
        System.out.println("Threshold is " + sThreshold);

        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println(processors + " processor"
                + (processors != 1 ? "s are " : " is ")
                + "available");

        ForkBlur fb = new ForkBlur(src, 0, src.length, dst);

        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.currentTimeMillis();
        pool.invoke(fb);
        long endTime = System.currentTimeMillis();

        System.out.println("Image blur took " + (endTime - startTime) +
                " milliseconds.");

        BufferedImage dstImage =
                new BufferedImage(w, h, BufferedImage.SCALE_SMOOTH);
        dstImage.setRGB(0, 0, w, h, dst, 0, w);

        return dstImage;
    }
}
