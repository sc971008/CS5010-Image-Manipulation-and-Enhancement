package ime.control.command;

import java.io.FileNotFoundException;

import ime.control.ImageCommand;
import ime.model.ImageProcessor;

/**
 * This class represent a load command.
 */
public class Load implements ImageCommand {

  private String path;
  private String imgName;

  /**
   * Build a load command.
   *
   * @param path string to load file from
   * @param imgName name of image file will be store as
   */
  public Load(String path, String imgName) {
    this.path = path;
    this.imgName = imgName;
  }

  @Override
  public void execute(ImageProcessor model) throws FileNotFoundException {
    model.loadImage(this.path, this.imgName);
  }
}
