package ime.control.command;

import ime.control.ImageCommand;
import ime.model.ImageProcessor;
import java.io.IOException;

/**
 * This class represent a save command.
 */
public class Save implements ImageCommand {

  protected final String path;
  protected final String from;

  /**
   * Build a save command.
   *
   * @param path file path to save to
   * @param from source image name
   */
  public Save(String path, String from) {
    this.path = path;
    this.from = from;
  }


  @Override
  public void execute(ImageProcessor model) throws IOException {
    model.save(from, path);
  }
}
