# Spot.IM SDK for Android 🚀

This library provides an easy integration with Spot.IM into a native Android app.

![alt text](https://user-images.githubusercontent.com/607917/35045982-4f117920-fb9e-11e7-81bd-f193a764d02b.jpg)

# Requirements
- Android SDK verison (API 18) and above.
- Your application will need a permission to use the Internet. Add the following line to your **AndroidManifest.xml**:
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

# Installation
1. Add the following lines to your **project** module's `build.gradle` file.
```gradle
repositories {
   maven { url 'https://jitpack.io' }
}
```
2. Add the following lines to the **app** module's `build.gradle` file.
```gradle
implementation 'com.github.SpotIM.spotim-android-sdk:spotim-sdk:1.1.2'
```
3. Apply Spot.IM gradle plugin
	There are two options to implement the plugin:
	1. Using the [plugins DSL](#using-the-plugins-dsl)
	2. Using [legacy plugin application](#using-legacy-plugin-application)

	### Using the plugins DSL

	Add the following lines to the **app** module's `build.gradle` file.
	```gradle
	plugins {
	  id "im.spot" version "1.0"
	}
	```
	⚠️ **Note:** Maku sure to apply the plugin after the `com.android.application` plugin.

	### Using legacy plugin application

	1. Add the following lines to your **project** module's `build.gradle` file.
	```gradle
	buildscript {
	  repositories {
	    maven {
	      url "https://plugins.gradle.org/m2/"
	    }
	  }
	  dependencies {
	    classpath "gradle.plugin.im.spot:spotim-gradle-plugin:1.0"
	  }
	}
	```

	2. Add the following lines to the **app** module's `build.gradle` file.

	```gradle
	apply plugin: "im.spot"
	```

# Initialize The SDK

The SDK must be initialized before any interaction, in order to do that you should first call init method in onCreate() method of application class.

```java
SpotIm.init(context,"SPOT_ID");
```
⚠️ **Note:** To use the SDK you need to set your unique SpotID (which looks like 'sp_xxxxxxx'). If you don't know your SpotID, login to the [admin dashboard](https://admin.spot.im) and have a look at the URL.

# Introduction

Our SDK exposes one major flow to set up. The pre-conversation view is a view that displays a preview of 2-16 comments from the conversation, a text box to create new comments and a button to see all comments.

The Pre-conversation view should be displayed in your article Activity/Fragment below the article.

When the user wants to see more comments we open Activity which display all comments from the conversation.

When clicking on the text box to create a new comments we bring the user to the creation screen. The users needs to be logged in inorder to post new comments, this is where the hosting app needs to integrate it's authentication system.

# Ads Placements

Interstitial Display ad or video will be shown when navigating to the conversation page only once per article. In the article page the banner below the conversation.

Article page | Conversation page | Interstitial
--- | --- | --- |
<img src="https://i.ibb.co/NSpfzRT/image.png" width=300> | <img src="https://i.ibb.co/KWN1b7w/Conversation-Page.png" width=300> | <img src="https://i.ibb.co/wJn75Tx/Interstitial.png" width=300>  


Note about ads: Ads are currently being A/B tested. The ads will be shown to certain users according to an A/B test. The next step will be testing more ad formats to optimize both experience and revenue.


# Structure
    +---------------------------------------------------------------+
    |                                                               |
    |                 SpotIM-android-sdk : 37.3 kb                  |
    |                                                               |
    +---------------------------------------------------------------+
    +------------------------------+ +------------------------------+
    |                              | |                              | 
    |           SpotIM             | |           SpotIM             | 
    |           -Core:             | |           -Common:           | 
    |                              | |                              |
    |           1.4 mb             | |           25.5 kb            | 
    |                              | |                              | 
    +------------------------------+ +------------------------------+
   
Example: Including the SpotIM-SDK module (which depends on SpotIM-Common and SpotIM-Core) would increase your app's size by an estimated **1.46 mb** (without the dependencies)

# Usage

Our SDK inlcudes 5 main methods.
<p>
  <img src="https://user-images.githubusercontent.com/20803775/69654940-4bb07280-107e-11ea-8c1c-9985c155a33e.png" width="600">
</p>

### 1. Getting Pre-Conversation Fragmnet:

In order to get Pre-Conversaction Fragment you need to provide **ConversationID**.

```java
SpotIm.getPreConversationFragment(CONVERSATION_ID,new SpotCallback<Fragment>() { 
	@Override 
	public void onSuccess(Fragment fragment) { 
	//doSomething...
	} 
	
	@Override 
	public void onFailure(SpotException exception) { //doSomething... } 
});
```

⚠️ **Note:** Make sure to use the same **ConversationID** you use on your web application so that the SDK would be able to display the same comments from the web application.

#### Conversation options

In order to configure conversation parameters such as Theme, amount of comments that will be show in Pre-Conversation Fragment, you need to provide ConversationOptions.Builder() as the second parameter in method. 

```java
ConversationOptions options = ConversationOptions.Builder()
                                .addTheme(new SpotImThemeParams(false, themeMode, backgroundColor))
                                .addMaxCountOfPreConversationComments(4)
                                .build()
```
If you don't want to configure conversation options, you can omit this parameter.

```java
SpotIm.getPreConversationFragment(CONVERSATION_ID, options ,new SpotCallback<Fragment>() { 
	@Override 
	public void onSuccess(Fragment fragment) { 
	//doSomething...
	} 
	
	@Override 
	public void onFailure(SpotException exception) { //doSomething... } 
});
```


##### Theme settings

To specify theme settings use `.addTheme()` method in `ConversationOptions.Builder()` to setup `SpotImThemeParams` class as parameter

```java
SpotImThemeMode themeMode = SpotImThemeMode.DARK;
int backgroundColor = Color.parseColor("#00000");
SpotImThemeParams themeParams = new SpotImThemeParams(false, themeMode, backgroundColor);	
```

If you don't want to modify a theme of the SDK screens you can set `SpotImThemeParams.DEFAULT_THEME_PARAMS` or just omit this parameter.

```java
ConversationOptions options = ConversationOptions.Builder()
                .addTheme(themeParams)
                .build()
```

##### Amount of comments that will be show in Pre-Conversation Fragment

Tho specify amount of comments that will be show in Pre-Conversation Fragment use `addMaxCountOfPreConversationComments(COUNTER)` method `ConversationOptions.Builder()`

```java
ConversationOptions options = ConversationOptions.Builder()
                .addMaxCountOfPreConversationComments(2)
                .build()
```

⚠️ **Note:** **COUNTER** should be a value from 0 to 16. When the **COUNTER** less than 0 - the SDK will show 2 comments. When the **COUNTER** more than 16 - the SDK will show 16 comments.

##### Sorting Type

To specify sorting type for the Pre-Conversation Fragment user `addSortType()` method in `ConversationOptions.Builder()` to setup `SortType` as parameter

```java
 ConversationOptions options = new ConversationOptions.Builder()
                .addSortType(SortType.Companion.getSORT_NEWEST())
                .build();
```

##### Article

To specify article for current Conversation use `configureArticle()` method in `ConversationOptions.Builder()` to setup `Article` class as parameter.

```java
new ConversationOptions.Builder()
                .configureArticle(new Article(
                "URL", "THUMBNAIL_URL", "TITLE", "SUBTITLE"))
                .build();
```

### 2. Authentication with SSO:

#### SSO process stages:

1) Call startSSO() method -> receive 'code A'
2) Send 'code A' to your Back-End -> receive 'code B'
3) Complete the SSO with the given 'code B'

#### Example:

- Using startSSO():

```java
SpotIm.startSSO(new SpotCallback<StartSSOResponse>() { 
	@Override 
	public void onSuccess(StartSSOResponse response) { 
	String codeA = response.getCodeA(); 
	} 
	
	@Override 
	public void onFailure(SpotException exception) { //doSomething... } 
});
```

- Using completeSSO():

```java
SpotIm.completeSSO("CODE_B", new SpotCallback<CompleteSSOResponse>() { 
	@Override 
	public void onSuccess(CompleteSSOResponse response) { //doSomething...	} 
	
	@Override 
	public void onFailure(SpotException exception) { //doSomething... } 
});
```

#### SSO with JWT:

1. Authenticate a user with your backend
2. Call ssoWithJwt(JWT).

```java
SpotIm.ssoWithJwt("JWT_SECRET", new SpotCallback<SsoWithJwtResponse>() { 
	@Override 
	public void onSuccess(SsoWithJwtResponse response) { //doSomething...	} 
	
	@Override 
	public void onFailure(SpotException exception) { //doSomething... } 
});
```

For more information about sso authentication take a look at: [SSO (Single Sign On) with Spot.IM
](https://github.com/SpotIM/spotim-integration-docs/blob/master/api/single-sign-on/README.md)

### 3. Supporting Signup/Login flow:
There are several UI/UX flow in SDK that require the hosting app to signup or login, for example before user can post a new comment as a registered user. Each application has its own signup/login flow and knows how to initiate such flow.
By providing LoginDelegate the SDK can request the hosting app to start a signup/login flow for example by starting the hosting app signup/login Activity.

```java
SpotIm.setLoginDelegate(new LoginDelegate() {
       @Override
       public void startLoginFlow(Context activityContext) {
            //Implement your app logic for starting the login flow.
       }
});
```


### 4. Logout:

```java
SpotIm.logout(new SpotCallback<Void>() { 
	@Override 
	public void onSuccess(Void response) { //doSomething...	} 
	
	@Override 
	public void onFailure(SpotException exception) { //doSomething... } 
});
```

### 5. User status

Use `getUserStatus()` to know if the current user is a guest or a registered user.

```java
SpotIm.getUserStatus(new SpotCallback<UserStatus>() {
        @Override 
	public void onSuccess(UserStatus status) { //doSomething... } 
	
	@Override 
	public void onFailure(SpotException exception) { //doSomething... }
});
```

# Feedback
Feel free to contact us via mobile@spot.im

**Happy Coding!** ![](https://i.imgur.com/rneCZCN.png)
