package com.omniful.app.models



/**
 * ROOT
 */
data class AppConfig(
    val app_settings: AppSettings? = null,
    val tab_settings: TabSettings? = null
)

/**
 * APP SETTINGS
 */
data class AppSettings(
    val app_branding: AppBranding? = null,
    val splash: Splash? = null,
    val app_urls: AppUrls? = null,
    val menu_items: List<MenuItem>? = null
)

/**
 * BRANDING
 */
data class AppBranding(
    val app_name: String? = null,
    val app_logo: String? = null,
    val app_color: AppColor? = null
)

data class AppColor(
    val primary_colour: String? = null,
    val secondary_color: String? = null
)

/**
 * SPLASH
 */
data class Splash(
    val media_type: String? = null,
    val src: String? = null
)

/**
 * APP URLS
 */
data class AppUrls(
    val privacy_policy: AppLink? = null,
    val terms_and_conditions: AppLink? = null
)

data class AppLink(
    val title: String? = null,
    val url: String? = null
)

/**
 * MENU
 */
data class MenuItem(
    val type: String? = null,
    val title: String? = null,
    val options: List<MenuOption>? = null
)

data class MenuOption(
    val title: String? = null,
    val icon: String? = null,
    val url: String? = null
)

/**
 * TABS
 */
data class TabSettings(
    val tabs: List<TabItem>? = null
)

data class TabItem(
    val id: String? = null,
    val title: String? = null,
    val icon: String? = null,
    val color: String? = null
)


//"""
//{
//  "app_settings": {
//    "app_branding": {
//      "app_name": "District KSA",
//      "app_logo": "https://cdn.example.com/logo.png",
//      "app_color": {
//        "primary_colour": "#D75B08",
//        "secondary_color": "#FFFFFF"
//      }
//    },
//    "splash": {
//      "media_type": "image",
//      "src": "https://cdn.example.com/splash.png"
//    },
//    "app_urls": {
//      "privacy_policy": {
//        "title": "Privacy Policy",
//        "url": "https://example.com/privacy"
//      },
//      "terms_and_conditions": {
//        "title": "Terms & Conditions",
//        "url": "https://example.com/terms"
//      }
//    },
//    "menu_items": [
//      {
//        "type": "all_bookings",
//        "title": "My Bookings",
//        "options": [
//          {
//            "title": "Upcoming Bookings",
//            "icon": "calendar",
//            "url": "app://bookings/upcoming"
//          }
//        ]
//      },
//      {
//        "type": "vouchers",
//        "title": "Vouchers",
//        "options": [
//          {
//            "title": "My Vouchers",
//            "icon": "ticket",
//            "url": "app://vouchers"
//          }
//        ]
//      },
//      {
//        "type": "payments",
//        "title": "Payments",
//        "options": [
//          {
//            "title": "Saved Cards",
//            "icon": "credit_card",
//            "url": "app://payments/cards"
//          }
//        ]
//      },
//      {
//        "type": "support",
//        "title": "Support",
//        "options": [
//          {
//            "title": "Help Center",
//            "icon": "help",
//            "url": "https://example.com/support"
//          }
//        ]
//      },
//      {
//        "type": "more",
//        "title": "More",
//        "options": [
//          {
//            "title": "About Us",
//            "icon": "info",
//            "url": "https://example.com/about"
//          }
//        ]
//      }
//    ]
//  },
//  "tab_settings": {
//    "tabs": [
//      {
//        "id": "for_you",
//        "title": "FOR YOU",
//        "icon": "home",
//        "color": "#D75B08"
//      },
//      {
//        "id": "events",
//        "title": "EVENTS",
//        "icon": "event",
//        "color": "#D75B08"
//      },
//      {
//        "id": "movies",
//        "title": "MOVIES",
//        "icon": "movie",
//        "color": "#D75B08"
//      },
//      {
//        "id": "activities",
//        "title": "ACTIVITIES",
//        "icon": "activity",
//        "color": "#D75B08"
//      },
//      {
//        "id": "stores",
//        "title": "STORES",
//        "icon": "store",
//        "color": "#D75B08"
//      },
//      {
//        "id": "play",
//        "title": "PLAY",
//        "icon": "sports",
//        "color": "#D75B08"
//      }
//    ]
//  }
//}"""