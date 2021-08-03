# hSignAPI

```java
public class ExampleSign implements CommandExecutor {

    private final HSignAPI signAPI;

    public ExampleSign(Plugin plugin) {
        this.signAPI = SignAPI.getInstance(plugin);
    }

    public HSignAPI getSignAPI() {
        return this.signAPI;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (command.getName().equals("opensign")) {
            HSign hSign = this.signAPI.getSignCreator().setLines("line 1", "line 2", "line 3", "line 4").setType(Material.valueOf("SIGN_POST")).create();
            hSign.open(player, strings -> {
                Bukkit.broadcastMessage(strings[0]);
                Bukkit.broadcastMessage(strings[1]);
                Bukkit.broadcastMessage(strings[2]);
                Bukkit.broadcastMessage(strings[3]);
            });
        }
        return false;
    }
}
```
