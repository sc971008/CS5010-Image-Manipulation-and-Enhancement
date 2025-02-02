package gime.view;

import gime.model.ReadOnlyImageProcessor;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * A class extends JPanel and draw a Histogram on GUI.
 */
public class Histogram extends JPanel {

  private int[][][] image;
  private int[] info;

  /**
   * A Constructor to construct a Histogram.
   *
   * @param imgName the name of image to be drawn
   * @param model   a read-only model
   */
  public void showHistogram(String imgName, ReadOnlyImageProcessor model) {
    image = model.getImage(imgName);
    info = model.getInfo(imgName);
    repaint();
  }

  /**
   * A private helper method to count the frequency of each RGB and Intensity value.
   *
   * @param mode the value to calculate on
   * @return a list of frequency of the mode
   */
  private int[] getCount(String mode) {
    int[] count = new int[256];
    switch (mode) {
      case "R":
        for (int row = 0; row < info[1]; row++) {
          for (int col = 0; col < info[0]; col++) {
            count[image[row][col][0]]++;
          }
        }
        break;
      case "G":
        for (int row = 0; row < info[1]; row++) {
          for (int col = 0; col < info[0]; col++) {
            count[image[row][col][1]]++;
          }
        }
        break;
      case "B":
        for (int row = 0; row < info[1]; row++) {
          for (int col = 0; col < info[0]; col++) {
            count[image[row][col][2]]++;
          }
        }
        break;
      case "I":
        int intensity = -1;
        for (int row = 0; row < info[1]; row++) {
          for (int col = 0; col < info[0]; col++) {
            if (image[row][col][0] == image[row][col][1]
                && image[row][col][1] == image[row][col][2]) {
              intensity = image[row][col][0];
            } else {
              intensity = (int) (image[row][col][0] * 0.2126
                  + image[row][col][1] * 0.7152
                  + image[row][col][2] * 0.0722);
            }
            count[intensity]++;
          }
        }
        break;
      default:
        throw new IllegalArgumentException("Invalid mode");
    }

    return count;
  }

  @Override
  protected void paintComponent(Graphics g) {

    if (image == null || info == null) {
      return;
    }

    super.paintComponent(g);
    int w = getWidth();
    int h = getHeight();
    int[] redCount = getCount("R");
    int[] greenCount = getCount("G");
    int[] blueCount = getCount("B");
    int[] intensityCount = getCount("I");

    //getMaxHeight
    int maxCount = 0;
    for (int i = 0; i <= 255; i++) {
      maxCount = Math.max(redCount[i], maxCount);
      maxCount = Math.max(greenCount[i], maxCount);
      maxCount = Math.max(blueCount[i], maxCount);
      maxCount = Math.max(intensityCount[i], maxCount);
    }

    int interval = (int) (w / 255.0);

    for (int i = 0; i < 255; i++) {
      //Draw RedLine
      g.setColor(Color.RED);
      g.drawLine(i * interval, (int) (h - (double) redCount[i] / (double) maxCount * h),
          (i + 1) * interval,
          (int) (h - (double) redCount[i + 1] / (double) maxCount * h));

      //Draw GreenLine
      g.setColor(new Color(56, 129, 31));
      g.drawLine(i * interval, (int) (h - (double) greenCount[i] / (double) maxCount * h),
          (i + 1) * interval,
          (int) (h - (double) greenCount[i + 1] / (double) maxCount * h));

      //Draw BlueLine
      g.setColor(Color.blue);
      g.drawLine(i * interval, (int) (h - (double) blueCount[i] / (double) maxCount * h),
          (i + 1) * interval,
          (int) (h - (double) blueCount[i + 1] / (double) maxCount * h));

      //Draw Intensity Line
      g.setColor(Color.black);
      g.drawLine(i * interval, (int) (h - (double) intensityCount[i] / (double) maxCount * h),
          (i + 1) * interval,
          (int) (h - (double) intensityCount[i + 1] / (double) maxCount * h));

    }

  }


}
