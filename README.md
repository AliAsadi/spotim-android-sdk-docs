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
implementation 'com.github.SpotIM.spotim-android-sdk:spotim-sdk:0.1.9'
```

# Initialize The SDK

The SDK must be initialized before any interaction, in order to do that you should first call init method in onCreate() method of application class.

```java
SpotIm.init(context,"SPOT_ID");
```
⚠️ **Note:** To use the SDK you need to set your unique SpotID (which looks like 'sp_xxxxxxx'). If you don't know your SpotID, login to the [admin dashboard](https://admin.spot.im) and have a look at the URL.

# Introduction

Our SDK exposes one major flow to set up. The pre-conversation view is a view that displays a preview of 2 comments from the conversation, a text box to create new comments and a button to see all comments.

The Pre-conversation view should be displayed in your article Activity/Fragment below the article.

When the user wants to see more comments we open Activity which display all comments from the conversation.

When clicking on the text box to create a new comments we bring the user to the creation screen. The users needs to be logged in inorder to post new comments, this is where the hosting app needs to integrate it's authentication system.

# Usage

Our SDK inlcudes 4 main methods.
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

#### Theme settings

To specify theme settings use `SpotImThemeParams` class as the second parameter in method 

```java
SpotImThemeMode themeMode = SpotImThemeMode.DARK;
int backgroundColor = Color.parseColor("#00000");
SpotImThemeParams themeParams = new SpotImThemeParams(false, themeMode, backgroundColor);	
```

If you don't want to modify a theme of the SDK screens you can set `SpotImThemeParams.DEFAULT_THEME_PARAMS` or just omit this parameter.

```java
SpotIm.getPreConversationFragment(CONVERSATION_ID,themeParams,new SpotCallback<Fragment>() { 
	@Override 
	public void onSuccess(Fragment fragment) { 
	//doSomething...
	} 
	
	@Override 
	public void onFailure(SpotException exception) { //doSomething... } 
});
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

For more information about sso authentication take a look at: [SSO (Single Sign On) with Spot.IM
](https://github.com/SpotIM/spotim-integration-docs/blob/master/api/single-sign-on/README.md)

### 3. Logout:

```java
SpotIm.logout(new SpotCallback<Void>() { 
	@Override 
	public void onSuccess(Void response) { //doSomething...	} 
	
	@Override 
	public void onFailure(SpotException exception) { //doSomething... } 
});
```

# Feedback
Feel free to contact us via mobile@spot.im

**Happy Coding!** ![](https://i.imgur.com/rneCZCN.png)
