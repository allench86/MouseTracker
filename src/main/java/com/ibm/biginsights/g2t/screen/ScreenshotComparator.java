package com.ibm.biginsights.g2t.screen;

import static com.ibm.biginsights.g2t.utils.EventConstants.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

import javax.imageio.ImageIO;

import com.joebowbeer.perceptualdiff.PerceptualDiff;

public class ScreenshotComparator
{
    private static final String IMAGE_TYPE = "png";
    private final ForkJoinPool pool;
    private final PerceptualDiff pd;
    private static ScreenshotComparator screenshotComparator;

    private ScreenshotComparator()
    {
        pd = new PerceptualDiff.Builder().build();
        pool = new ForkJoinPool();
    }

    public static ScreenshotComparator getInstance()
    {
        return screenshotComparator == null ? screenshotComparator = new ScreenshotComparator() : screenshotComparator;
    }

    public boolean areScreenshotsIdentical(ScreenshotEvent screenshotEvent, String screenshotFolder) throws IOException
    {
        String originalScreenshotFilePath = screenshotFolder + SCREENSHOT_ + screenshotEvent.getIndex() + PNG;
        String playbackScreenshotFilePath = screenshotFolder + SCREENSHOT_ + screenshotEvent.getIndex() + _PLAYBACK
                + PNG;
        String diffImageFilePath = screenshotFolder + SCREENSHOT_ + screenshotEvent.getIndex() + _DIFF
                + PNG;

        BufferedImage originalScreenshot = ImageIO.read(new File(originalScreenshotFilePath));
        BufferedImage playbackScreenshot = ImageIO.read(new File(playbackScreenshotFilePath));
        BufferedImage diffImage = ImageIO.read(new File(originalScreenshotFilePath));
        boolean result = pd.compare(pool, originalScreenshot, playbackScreenshot, diffImage);

        File outputDiffImageFile = new File(diffImageFilePath);
        ImageIO.write(diffImage, IMAGE_TYPE, outputDiffImageFile);

        return result;
    }
}
