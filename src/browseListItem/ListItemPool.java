package browseListItem;

import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.HashMap;
import java.util.Map;

public class ListItemPool {

    private static IMatDataHandler handler = IMatDataHandler.getInstance();
    private static ListItemPool self;
    private Map<Product,BrowseListItem> listItems;

    private ListItemPool(){
        IMatDataHandler handler = IMatDataHandler.getInstance();
        listItems = new HashMap<>();

        for(ShoppingItem i: handler.getShoppingCart().getItems()) {
            listItems.put(i.getProduct(), new BrowseListItem(i));
        }

        for(Product p: handler.getProducts()) {
            if(!listItems.containsKey(p))
                listItems.put(p,new BrowseListItem(new ShoppingItem(p,0)));
        }
    }

    static public ListItemPool getInstance(){
        if(self == null)
            self = new ListItemPool();

        return self;
    }

    public BrowseListItem getBrowserListItem(Product p){
        BrowseListItem item = listItems.get(p);
        item.update();
        return item;
    }

    static public void updateAfterOrder(){
        if(self == null)
            return;

        for(ShoppingItem p: handler.getShoppingCart().getItems())
            self.listItems.put(p.getProduct(),new BrowseListItem(new ShoppingItem(p.getProduct())));
    }
}
