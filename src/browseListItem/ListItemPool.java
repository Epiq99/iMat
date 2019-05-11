package browseListItem;

import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.util.HashMap;
import java.util.Map;

public class ListItemPool {

    private static ListItemPool self;
    private Map<Product,BrowseListItem> listItems;

    private ListItemPool(){
        IMatDataHandler handler = IMatDataHandler.getInstance();
        listItems = new HashMap<>();

        for(Product p: handler.getProducts())
            listItems.put(p,new BrowseListItem(p));
    }

    static public ListItemPool getInstance(){
        if(self == null)
            self = new ListItemPool();

        return self;
    }

    public BrowseListItem getBrowserListItem(Product p){
        return listItems.get(p);
    }

}
