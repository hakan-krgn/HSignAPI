# InventoryAPI

```java
public class ExampleInventory extends JavaPlugin {

    private InventoryAPI inventoryAPI;

    @Override
    public void onEnable() {
        this.inventoryAPI = InventoryAPI.getInstance(this);
    }

    public InventoryAPI getInventoryAPI() {
        return this.inventoryAPI;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equals("normalgui")) {
            HInventory hInventory = this.inventoryAPI.getInventoryCreator().setTitle("inventory title!!").setClosable(false).setSize(3).setId("normalgui").create();

            hInventory.setItem(11, ClickableItem.of(new ItemStack(Material.APPLE), (event) -> {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
                player.sendMessage("§enormal item !");
            }));

            hInventory.setItem(13, ClickableItem.of(new ItemStack(Material.DIAMOND), (event) -> {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                player.sendMessage("§agood item !");
                hInventory.close(player);
            }));

            hInventory.setItem(15, ClickableItem.of(new ItemStack(Material.MAP), (event) -> {
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1, 1);
                player.sendMessage("§cbad item !");
            }));

            hInventory.open(player);
        } else if (command.getName().equals("pagegui")) {
            HInventory hInventory = this.inventoryAPI.getInventoryCreator().setTitle("a").setClosable(false).setSize(5).setId("b").create();

            Pagination pagination = hInventory.getPagination();

            pagination.setItemSlots(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35));
            List<ClickableItem> clickableItemList = new ArrayList<>();
            for (Material material : Material.values()) {
                clickableItemList.add(ClickableItem.of(new ItemStack(material), event -> {
                    Bukkit.broadcastMessage(material.name());
                }));
            }
            pagination.setItems(clickableItemList);

            hInventory.setItem(38, ClickableItem.of(new ItemStack(Material.ARROW), (event) -> {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                pagination.previousPage();
            }));

            hInventory.setItem(40, ClickableItem.of(new ItemStack(Material.BARRIER), (event) -> {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                hInventory.close(player);
            }));

            hInventory.setItem(42, ClickableItem.of(new ItemStack(Material.ARROW), (event) -> {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                pagination.nextPage();
            }));

            hInventory.open(player);
        }
        return false;
    }
}
```
