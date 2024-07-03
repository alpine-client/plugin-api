# Alpine Client: Plugin API [![Version](https://lib.alpn.cloud/api/badge/latest/alpine-public/com/alpineclient/api-plugin?color=A62639&name=Latest)](https://lib.alpn.cloud/#/alpine-public/com/alpineclient/api-plugin)

### 🛡️ For Server Admins
This plugin does the following by default:

- Allows you to prevent any module from being enabled on your server.
- Provides the client with the current world name for more accurate map data handling
- `/accheck [player]` - Displays whether the given player is on Alpine Client. If they are, also displays their Minecraft version and mod platform. Requires the `alpineapi.check` permission.
- `/acnotify [player] [content]` - Sends a notification to the given player containing the given content.  Requires the `alpineapi.notify` permission.
- `/aclist` - Displays a list of all online Alpine Client users. Requires the `alpineapi.list` permission.

The plugin should work on any server running on at least `1.8.8` and Java 8.

Much more is possible with this API, however it would require your development team to implement it with your plugins manually. This is what is available by default with no extra development work.

<br/>

### 💻 For Developers
This plugin exposes an API that can be leveraged to add Alpine Client integrations to your plugins. The following are currently supported:

- Accessing certain user data:
  - Minecraft version
  - Mod platform
  - Loaded mod IDs
- Sending custom notifications to the client
- Sending temporary waypoints to the client
- Sending cooldown information to the client

The plugin can be added as a dependency to your Gradle buildscript like so:

```kotlin
repositories {
    maven("https://lib.alpn.cloud/alpine-public/")
}

dependencies {
    implementation("com.alpineclient:api-plugin:{version}")
}
```
> ⚠️ Replace `{version}` with the [latest release](https://github.com/alpine-client/alpine-client-api/releases/)

There is [documentation](https://docs.alpineclient.com/) available, however most of the functionality can be figured out by looking at the `AlpineClientApi` class. If you end up importing a class that isn't in `com.alpineclient.plugin.api` then you probably did something wrong.

<br/>

### 🛠️ Technical Information
Utilizes the Minecraft [plugin channel](https://wiki.vg/Plugin_channels) system to communicate with users on Alpine Client.

We currently use the following channels:

- `ac:handshake` - Used by both the client and server to perform a handshake upon login.
- `ac:play` - Used by both the client and the server to send/receive custom packets relating to the API.

<br/>

### 📜 License
This repository is licensed under the [Mozilla Public License, version 2.0](https://www.mozilla.org/en-US/MPL/2.0/). Before interacting with the code in any way, please ensure that you understand your obligations under the license. Mozilla provides a list of frequently asked questions [here](https://www.mozilla.org/en-US/MPL/2.0/FAQ/).