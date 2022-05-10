
package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

public class ServerTest {

  @Test
  public void test_main() throws InterruptedException {
    Thread serverThread = new Thread() {
        @Override()
        public void run() {
          try {
            Server.main(new String[] {"8294"});
          } catch (Exception e) {

          }
        }
      };
    serverThread.start();
    Thread.sleep(100);
    serverThread.interrupt();
  }


  @Test
  public void test_wrong_run() throws InterruptedException {
    Server.main(new String[] {"abcd"});
  }

}
