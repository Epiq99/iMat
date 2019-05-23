package cartPage.cartListItem;

public interface ICartItemListener {
    void removeFromList(CartListItem item);
    void changeValues(CartListItem item);
}
