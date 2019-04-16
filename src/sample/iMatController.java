package sample;
import se.chalmers.cse.dat216.project.*;

public class iMatController {
    public void test() {
        IMatDataHandler handler = IMatDataHandler.getInstance();

        System.out.println(handler.getProducts().get(0).getImageName());

    }
}
