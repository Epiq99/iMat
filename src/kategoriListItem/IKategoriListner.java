package kategoriListItem;

import se.chalmers.cse.dat216.project.ProductCategory;

import java.util.List;

public interface IKategoriListner {
    void notify(ProductCategory[] pc);
}
