
# Spot.Im Android SDK. Get Started

### Requirements

- Android SDK 18+

### Gradle Dependency

##### Repository

Add repository in your root `build.gradle` file

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```


Add auth token for SpotIm sdk into the ```gradle.properties``` file:

```gradle
    authToken=YOUR_AUTH_TOKEN
```


##### Dependency

Add dependency to your module's `build.gradle` file:

```Groovy
dependencies {
	implementation 'com.github.SpotIM:spotim-android-sdk:0.1.5'
}
```

### Setup

To use the SDK you need to set your unique Spot ID into the `AndroidManifest.xml`:

```xml
<application>
    <meta-data
            android:name="spotIm.Id"
            android:value="YOUR_SPOT_ID_KEY" />
</application>
```

We strongly recommended initialize SpotIm SDK into the `Application` as shown below:

```kotlin
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SpotIm.instance.init(this)
    }

}
```

### Flows

Our SDK exposes one major flow to set up. The pre-conversation view is a view that displays a preview of 2 comments from the conversation, a text box to create new comments and a button to see all comments.

The Pre-conversation view should be displayed in your article Activity/Fragment below the article.

When the user wants to see more comments we open Activity which display all comments from the conversation.

When clicking on the text box to create a new comments we bring the user to the creation screen. The users needs to be logged in inorder to post new comments, this is where the hosting app needs to integrate it's authentication system.

### Usage

End user is supposed to interact with the Pre-Conversation Fragment (PCF) first. To get and instance of it, you need an instance of `SpotIm`:

```
SpotIm.instance
```

This is the Singleton object and you can call it from everywhere.
Then you can instantiate PCF for specific post (article) ID and add them to the FragmentManager transaction:

```
supportFragmentManager.beginTransaction()
            .replace(R.id.you_container_id, SpotIm.instance.getPreConversationFragment(ARTICLE_ID))
            .commit()
```

##### ⚠️ IMPORTANT
Please make sure to use the same post id you use on your web application so that the SDK would be able to display the same comments from the web application.

### Authentication with SSO:

1. Authenticate a user with your backend
2. Get an instance of `SpotIm`
3. Call `loginSSO(SECRET)` function. The SECRET is your user token that you get into the 1 step.
4. If there’s no error in the call back and `SpotImResponse.Success.data.success` is true and `SpotImResponse.Success.data.codeA != null`, the authentication process finished successfully

##### Example

```kotlin
SpotIm.instance.loginSSO(SECRET) { response ->
       when(response) {
          is SpotImResponse.Success -> {
                if (response.data.success && response.data.codeA != null) {
                    //user authorized           
                } else {
                    //show error
                }
          }
          is SpotImResponse.Error -> {
                //show error
          }
       }        
}
```

Our SDK allow developers to configure can guest user post comments or not.

Also, user can be redirect from our SDK Activities to the your login flow. To implement this feature add to your `AndroidManifest.xml`:

```xml
<activity android:name=".YourLoginActivity">
            <intent-filter>
                <action android:name="im.spot.core.OPEN_PUBLISHER_LOG_IN_ACTION" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
</activity>
```

And than handle into your login Activity redirect to the our SDK after login:

```kotlin
SpotIm.instance.loginSSO(SECRET) {
                runOnUiThread {
                    if (intent.action == "im.spot.core.OPEN_PUBLISHER_LOG_IN_ACTION") {
                        finish()
                    } else {
                        showArticlesScreen()
                    }
                }
            }
```

### Dark Theme

Our SDK is allowed to handle Dark Theme in two ways:
- manual changing Dark mode
- system changing Dark Theme (from Android API 29 and higher)

The scheme of how dark and light modes are applied to screens:
<img src="apply_dark_mode_scheme.png"/>

To specify theme settings use `SpotImThemeParams` class as the second parameter in method `getPreConversationFragment(ARTICLE_ID, SpotImThemeParams)`:

```kotlin
SpotIm.instance.getPreConversationFragment(
                    ARTICLE_ID,
                    SpotImThemeParams(
                        isSupportSystemDarkMode = isSupportSystemNightMode,
                        themeMode = if (isDarkMode) SpotImThemeMode.DARK else SpotImThemeMode.LIGHT,
                        darkColor = Color.parseColor(YOUR_DARK_THEME_BACKGROUND_COLOR)
                    )
                )
```
You can specify at the constructor `SpotImThemeParams`  the next parameters:
 - does the app support system Dark Theme (from Android API 29 and higher). Use parameter `isSupportSystemDarkMode`
 - setup current app `SpotImThemeMode.LIGHT` or `SpotImThemeMode.DARK` mode. Use parameter `themeMode`.
 - the main background color which your app uses for Dark Theme. Use parameter `darkColor`.

If you don't want to modify a theme of the SDK screens you can set `SpotImThemeParams.DEFAULT_THEME_PARAMS` or just omit this parameter.

```kotlin
SpotIm.instance.getPreConversationFragment(
                    ARTICLE_ID,
                    SpotImThemeParams.DEFAULT_THEME_PARAMS
                )
```

The next parameters will be applied in this case:
- `isSupportSystemDarkMode = false`
- `themeMode = SpotImThemeMode.LIGHT`
- `darkColor  = "#181818"` (doesn't matter)


