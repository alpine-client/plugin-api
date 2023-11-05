# Alpine Client API
> Current version: 1.1.0

### For Server Admins
This plugin does the following by default:

- Allows you to prevent any module from being enabled on your server.
- Provides the client with the current world name for more accurate map data handling
- `/accheck [player]` - Displays whether the given player is on Alpine Client. If they are, also displays their Minecraft version and mod platform. Requires the `alpineapi.check` permission.
- `/acnotify [player] [content]` - Sends a notification to the given player containing the given content.  Requires the `alpineapi.notify` permission.
- `/aclist` - Displays a list of all online Alpine Client users. Requires the `alpineapi.list` permission.

The plugin has been explicitly verified to work on server versions `1.8.8`, `1.19.4` and `1.20.1`, however all versions in between and above *should* work.

Much more is possible with this API, however it would require your development team to implement it with your plugins manually. This is what is available by default with no extra development work.

### For Developers
This plugin exposes an API that can be leveraged to add Alpine Client integrations to your plugins. The following are currently supported:

- Accessing certain user data:
  - Minecraft version
  - Mod platform
  - Loaded mod IDs
- Sending custom notifications to the client
- Sending temporary waypoints to the client

The plugin can be added as a dependency to your Gradle buildscript like so:

```
repositories {
    maven {
        name 'Alpine Public'
        url 'https://lib.alpn.cloud/alpine-public'
    }
}

dependencies {
    implementation 'com.alpineclient:api-plugin:1.1.0'
}
```

There is [documentation](https://docs.alpineclient.com/) available, however most of the functionality can be figured out by looking at the `AlpineClientApi` class. If you end up importing a class that isn't in `com.alpineclient.plugin.api` then you probably did something wrong.


### Technical Information
Utilizes the Minecraft [plugin channel](https://wiki.vg/Plugin_channels) system to communicate with users on Alpine Client.

We currently use the following channels:

- `ac:handshake` - Used by both the client and server to perform a handshake upon login.
- `ac:play` - Used by both the client and the server to send/receive custom packets relating to the API.