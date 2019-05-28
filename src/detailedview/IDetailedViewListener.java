package detailedview;

import browseListItem.BrowseListItem;

public interface IDetailedViewListener {
    void addToCartNotification(DetailedView item);
    void closeDetailedView(DetailedView item);
}
